package com.bol.test.lm.impl;

import com.bol.test.lm.Player;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * User: Igor
 * Date: 16.08.14
 * Time: 0:37
 */
public class GameTest {
    private GameImpl game;
    private Player player1;
    private Player player2;

    @Before
    public void setUp() {
        player1 = new Player("1", "1");
        player2 = new Player("2", "2");
        game = new GameImpl(player1, player2);
    }

    @Test
    public void testIsFinished() {
        game.getBoard1().moveAllToLm();
        game.getBoard2().moveAllToLm();
        assertEquals(true, game.isFinished());
    }

    @Test
    public void testIsNotFinished() {
        assertEquals(false, game.isFinished());
    }

    @Test(expected = IllegalStateException.class)
    public void testGetWinnerNotFinished() {
        game.getWinner();
    }

    @Test
    public void testGetWinnerNoWinner() {
        game.getBoard1().moveAllToLm();
        game.getBoard2().moveAllToLm();
        assertEquals(null, game.getWinner());
    }

    @Test
    public void testGetWinnerPlayer1() {
        game.getBoard1().moveAllToLm();
        game.getBoard2().moveAllToLm();
        game.getBoard1().addToLm(1);
        assertEquals(game.getBoard1().getPlayer(), game.getWinner());
    }

    @Test
    public void testGetWinnerPlayer2() {
        game.getBoard1().moveAllToLm();
        game.getBoard2().moveAllToLm();
        game.getBoard2().addToLm(1);
        assertEquals(game.getBoard2().getPlayer(), game.getWinner());
    }

    @Test
    public void testSwitchTurn() {
        assertTrue(game.isPlayerTurn(player1));
        game.switchTurn();
        assertTrue(game.isPlayerTurn(player2));
        game.switchTurn();
        assertTrue(game.isPlayerTurn(player1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetOpponentBoardWrongPlayer() {
        game.getOpponentBoard(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetPlayerBoardWrongPlayer() {
        game.getPlayerBoard(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetPlayerWrong() {
        game.getPlayer(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetOpponentWrong() {
        game.getOpponent(null);
    }

    @Test
    public void testGetBoardPlayer1() {
        assertEquals(game.getBoard2(), game.getPlayerBoard(player2));
    }

    @Test
    public void testGetBoardPlayer2() {
        assertEquals(game.getBoard1(), game.getPlayerBoard(player1));
    }

    @Test
    public void testGetOpponentBoardPlayer1() {
        assertEquals(game.getBoard2(), game.getOpponentBoard(player1));
    }

    @Test
    public void testGetOpponentBoardPlayer2() {
        assertEquals(game.getBoard1(), game.getOpponentBoard(player2));
    }

    @Test
    public void testGetPlayer1() {
        assertEquals(player1, game.getPlayer(player1.getId()));
    }

    @Test
    public void testGetPlayer2() {
        assertEquals(player2, game.getPlayer(player2.getId()));
    }

    @Test
    public void testGetOpponent1() {
        assertEquals(player1, game.getOpponent(player2.getId()));
    }

    @Test
    public void testGetOpponent2() {
        assertEquals(player2, game.getOpponent(player1.getId()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsPlayerTurnWrongPlayer() {
        game.isPlayerTurn(null);
    }

    @Test
    public void testIsPlayerTurn() {
        assertTrue(game.isPlayerTurn(player1));
        game.switchTurn();
        assertTrue(game.isPlayerTurn(player2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTurnWrongPlayer() {
        game.sow(player2, 0);
    }

    @Test
    public void testDoTurnPlayer1() {
        game.sow(player1, 1);
        assertEquals(0, game.getBoard1().getStonesCountAtPit(1));
        game.sow(player2, 1);
        assertEquals(0, game.getBoard2().getStonesCountAtPit(1));
    }

    @Test
    public void testTurnResultEmptySwitchTurn() {
        game.sow(player1, 1);
        assertTrue(game.isPlayerTurn(player2));
    }

    @Test
    public void testTurnResultLastInLmNoSwitch() {
        game.sow(player1, 0);
        assertTrue(game.isPlayerTurn(player1));
    }

    @Test
    public void testLastStoneInEmptyPit() {
        game.sow(player1, 0);
        game.sow(player1, 1);

        assertEquals(1, game.getBoard1().getStonesCountAtPit(0));
        assertEquals(0, game.getBoard1().getStonesCountAtPit(1));
        for (int i = 2; i < GameBoardImpl.PITS_COUNT; i++) {
            assertEquals(GameBoardImpl.STONES_COUNT + 2, game.getBoard1().getStonesCountAtPit(i));
        }
        assertEquals(GameBoardImpl.STONES_COUNT + 3, game.getBoard1().getLm());

        for (int i = 0; i < GameBoardImpl.PITS_COUNT - 2; i++) {
            assertEquals(GameBoardImpl.STONES_COUNT, game.getBoard2().getStonesCountAtPit(i));
        }
        assertEquals(0, game.getBoard2().getStonesCountAtPit(GameBoardImpl.PITS_COUNT - 2));
        assertEquals(GameBoardImpl.STONES_COUNT, game.getBoard2().getStonesCountAtPit(GameBoardImpl.PITS_COUNT - 1));
        assertEquals(0, game.getBoard2().getLm());
    }

    @Test
    public void testLastStoneInLmFinished() {
        for (int i = 0; i < GameBoardImpl.PITS_COUNT - 1; i++) {
            game.getBoard1().revokeFromPit(i);
        }
        game.getBoard1().setPit(GameBoardImpl.PITS_COUNT - 1, 1);
        game.sow(player1, GameBoardImpl.PITS_COUNT - 1);

        assertTrue(game.isFinished());
    }

    @Test
    public void testLastStoneInEmptyPitFinished() {
        for (int i = 1; i < GameBoardImpl.PITS_COUNT; i++) {
            game.getBoard1().revokeFromPit(i);
        }
        game.getBoard1().setPit(0, 1);
        game.sow(player1, 0);

        assertTrue(game.isFinished());
    }

    @Test
    public void testLastStoneInEmptyPitFinishedOpponent() {
        game.getBoard1().setPit(0, 1);
        game.getBoard1().revokeFromPit(1);

        for (int i = 0; i < GameBoardImpl.PITS_COUNT; i++) {
            game.getBoard2().revokeFromPit(i);
        }
        game.getBoard2().setPit(GameBoardImpl.STONES_COUNT - 2, GameBoardImpl.STONES_COUNT);

        game.sow(player1, 0);

        assertTrue(game.isFinished());
    }
}
