package com.ipetrov.lm.web;

import com.ipetrov.lm.Game;
import com.ipetrov.lm.GameBoard;
import com.ipetrov.lm.Player;
import com.ipetrov.lm.impl.PlayerImpl;
import com.ipetrov.lm.impl.GameImpl;
import com.ipetrov.lm.web.dto.Message;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created with IntelliJ IDEA.
 * User: IPetrov
 * Date: 8/18/14
 * Time: 5:45 PM
 */
public class GameEngine {
    private final Map<String, Session> sessions = new ConcurrentHashMap<>();
    private final Map<String, Game> games = new ConcurrentHashMap<>();
    private PlayerImpl waitingPlayer;
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
            sendMessage(opponent, Constants.MSG_OPPONENT_DISCONNECTED);
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
                createGame(waitingPlayer, new PlayerImpl(session.getId(), login));
                waitingPlayer = null;
            } else {
                waitingPlayer = new PlayerImpl(session.getId(), login);
                sendMessage(waitingPlayer, Constants.MSG_WAIT);
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
                    sendMessage(player, Constants.MSG_WIN);
                    sendMessage(opponent, Constants.MSG_LOSE);
                } else if (game.getWinner().equals(opponent)) {
                    sendMessage(player, Constants.MSG_LOSE);
                    sendMessage(opponent, Constants.MSG_WIN);
                } else {
                    sendMessage(player, Constants.MSG_DRAW);
                    sendMessage(opponent, Constants.MSG_DRAW);
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

    private Game createGame(PlayerImpl player1, PlayerImpl player2) throws IOException, EncodeException {
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
                .add(Constants.MSG_PROP_TYPE, Constants.MSG_TYPE_GAME)
                .add("opponentLogin", opponent.getName())
                .add("myLm", myBoard.getLm())
                .add("opponentLm", opponentBoard.getLm())
                .add("myPits", myPits.build())
                .add("opponentPits", opponentPits.build())
                .add("isMyTurn", game.isPlayerTurn(player))
                .build());
    }
}
