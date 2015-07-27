package com.bol.test.lm;

/**
 * Created with IntelliJ IDEA.
 * User: IPetrov
 * Date: 8/19/14
 * Time: 9:45 AM
 */
public interface Game {
    public void sow(Player player, int pitNumber);

    public boolean isFinished();

    /**
     * @return null draw
     */
    public Player getWinner();

    public GameBoard getPlayerBoard(Player player);

    public GameBoard getOpponentBoard(Player player);

    public Player getPlayer(String playerId);

    public Player getOpponent(String playerId);

    public boolean isPlayerTurn(Player player);
}
