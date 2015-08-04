package com.ipetrov.lm.impl;

import com.ipetrov.lm.Game;
import com.ipetrov.lm.GameBoard;
import com.ipetrov.lm.Player;

/**
 * User: Igor
 * Date: 15.08.14
 * Time: 22:14
 */
public class GameImpl implements Game {
    private final GameBoardImpl board1;
    private final GameBoardImpl board2;
    private boolean isPlayer1Turn = true;

    public GameImpl(PlayerImpl player1, PlayerImpl player2) {
        board1 = new GameBoardImpl(player1, this);
        board2 = new GameBoardImpl(player2, this);
    }

    public void sow(Player player, int pitNumber) {
        if (!isPlayerTurn(player)) {
            throw new IllegalArgumentException();
        }
        if (isPlayer1Turn) {
            board1.sow(pitNumber).apply();
        } else {
            board2.sow(pitNumber).apply();
        }
    }

    public boolean isFinished() {
        return board1.pitsAreEmpty()
                && board2.pitsAreEmpty();
    }

    public PlayerImpl getWinner() {
        if (!isFinished()) {
            throw new IllegalStateException();
        }
        if (board1.getLm() > board2.getLm()) {
            return board1.getPlayer();
        }
        if (board1.getLm() < board2.getLm()) {
            return board2.getPlayer();
        }
        return null;
    }

    void switchTurn() {
        isPlayer1Turn = !isPlayer1Turn;
    }

    public GameBoard getPlayerBoard(Player player) {
        if (board1.getPlayer().equals(player)) {
            return board1;
        } else if (board2.getPlayer().equals(player)) {
            return board2;
        }
        throw new IllegalArgumentException();
    }

    public GameBoard getOpponentBoard(Player player) {
        if (board1.getPlayer().equals(player)) {
            return board2;
        } else if (board2.getPlayer().equals(player)) {
            return board1;
        }
        throw new IllegalArgumentException();
    }

    public Player getPlayer(String playerId) {
        if (board1.getPlayer().getId().equals(playerId)) {
            return board1.getPlayer();
        } else if (board2.getPlayer().getId().equals(playerId)) {
            return board2.getPlayer();
        }
        throw new IllegalArgumentException();
    }

    public Player getOpponent(String playerId) {
        if (board1.getPlayer().getId().equals(playerId)) {
            return board2.getPlayer();
        } else if (board2.getPlayer().getId().equals(playerId)) {
            return board1.getPlayer();
        }
        throw new IllegalArgumentException();
    }

    public boolean isPlayerTurn(Player player) {
        if (board1.getPlayer().equals(player)) {
            return isPlayer1Turn;
        } else if (board2.getPlayer().equals(player)) {
            return !isPlayer1Turn;
        }
        throw new IllegalArgumentException();
    }

    GameBoardImpl getBoard2() {
        return board2;
    }

    GameBoardImpl getBoard1() {
        return board1;
    }
}
