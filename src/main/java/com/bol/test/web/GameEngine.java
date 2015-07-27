package com.bol.test.web;

import com.bol.test.lm.Game;
import com.bol.test.lm.GameBoard;
import com.bol.test.lm.Player;
import com.bol.test.lm.impl.GameImpl;
import com.bol.test.web.dto.Message;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import static com.bol.test.web.Constants.*;

/**
 * Created with IntelliJ IDEA.
 * User: IPetrov
 * Date: 8/18/14
 * Time: 5:45 PM
 */
public class GameEngine {
    private final Map<String, Session> sessions = new ConcurrentHashMap<String, Session>();
    private final Map<String, Game> games = new ConcurrentHashMap<String, Game>();
    private Player waitingPlayer;
    private ReentrantLock waitingPlayerLock = new ReentrantLock();

    public void onSessionOpened(Session session) {
        sessions.put(session.getId(), session);
    }

    public void onSessionClosed(Session session) throws IOException, EncodeException {
        sessions.remove(session.getId());
        Game game = games.get(session.getId());
        if (game != null) {
            Player opponent = game.getOpponent(session.getId());
            endGame(game, session);
            sendMessage(opponent, MSG_OPPONENT_DISCONNECTED);
        } else {
            try {
                waitingPlayerLock.lock();
                if (waitingPlayer != null && session.getId().equals(waitingPlayer.getId())) {
                    waitingPlayer = null;
                }
            } finally {
                waitingPlayerLock.unlock();
            }
        }
    }

    public void onLogged(Session session, String login) throws IOException, EncodeException {
        try {
            waitingPlayerLock.lock();
            if (waitingPlayer != null && !waitingPlayer.getId().equals(session.getId())) {
                createGame(waitingPlayer, new Player(session.getId(), login));
                waitingPlayer = null;
            } else {
                waitingPlayer = new Player(session.getId(), login);
                sendMessage(waitingPlayer, MSG_WAIT);
            }
        } finally {
            waitingPlayerLock.unlock();
        }
    }

    public void onSow(Session session, int pitNumber) throws IOException, EncodeException {
        Game game = games.get(session.getId());
        if (game != null) {
            Player player = game.getPlayer(session.getId());
            Player opponent = game.getOpponent(player.getId());

            game.sow(player, pitNumber);

            sendMessage(player, composeGameMessage(game, player));
            sendMessage(opponent, composeGameMessage(game, opponent));

            if (game.isFinished()) {
                if (game.getWinner().equals(player)) {
                    sendMessage(player, MSG_WIN);
                    sendMessage(opponent, MSG_LOSE);
                } else if (game.getWinner().equals(opponent)) {
                    sendMessage(player, MSG_LOSE);
                    sendMessage(opponent, MSG_WIN);
                } else {
                    sendMessage(player, MSG_DRAW);
                    sendMessage(opponent, MSG_DRAW);
                }
                endGame(game, session);
            }
        }
    }

    private void sendMessage(Player player, Message message) throws IOException, EncodeException {
        Session session = sessions.get(player.getId());
        if (session != null) {
            session.getBasicRemote().sendObject(message);
        }
    }

    private Game createGame(Player player1, Player player2) throws IOException, EncodeException {
        Game game = new GameImpl(player1, player2);
        games.put(player1.getId(), game);
        games.put(player2.getId(), game);

        sendMessage(player1, composeGameMessage(game, player1));
        sendMessage(player2, composeGameMessage(game, player2));

        return game;
    }

    private void endGame(Game game, Session session) {
        games.remove(session.getId());
        games.remove(game.getOpponent(session.getId()).getId());
    }

    private Message composeGameMessage(Game game, Player player) {
        Player opponent = game.getOpponent(player.getId());
        GameBoard myBoard = game.getPlayerBoard(player);
        GameBoard opponentBoard = game.getPlayerBoard(opponent);

        JsonArrayBuilder myPits = Json.createArrayBuilder();
        for (int i = 0; i < GameBoard.PITS_COUNT; i++) {
            myPits.add(myBoard.getStonesCountAtPit(i));
        }

        JsonArrayBuilder opponentPits = Json.createArrayBuilder();
        for (int i = 0; i < GameBoard.PITS_COUNT; i++) {
            opponentPits.add(opponentBoard.getStonesCountAtPit(i));
        }

        return new Message(Json.createObjectBuilder()
                .add(MSG_PROP_TYPE, MSG_TYPE_GAME)
                .add("opponentLogin", opponent.getName())
                .add("myLm", myBoard.getLm())
                .add("opponentLm", opponentBoard.getLm())
                .add("myPits", myPits.build())
                .add("opponentPits", opponentPits.build())
                .add("isMyTurn", game.isPlayerTurn(player))
                .build());
    }
}
