package com.ipetrov.lm;

import com.ipetrov.lm.impl.PlayerImpl;

/**
 * Created with IntelliJ IDEA.
 * User: IPetrov
 * Date: 8/19/14
 * Time: 9:45 AM
 */
public interface Game {
    void sow(Player player, int pitNumber);

    boolean isFinished();

    /**
     * @return null draw
     */
    PlayerImpl getWinner();

    GameBoard getPlayerBoard(Player player);

    GameBoard getOpponentBoard(Player player);

    Player getPlayer(String playerId);

    Player getOpponent(String playerId);

    boolean isPlayerTurn(Player player);
}
