package com.ipetrov.lm.impl;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * User: Igor
 * Date: 16.08.14
 * Time: 21:53
 */
public class BoardTest {
    private GameBoardImpl board;

    @Before
    public void setUp() {
        board = new GameBoardImpl(null, null);
    }

    @Test
    public void testPitsInitial() {
        for (int i = 0; i < GameBoardImpl.PITS_COUNT; i++) {
            assertEquals(GameBoardImpl.STONES_COUNT, board.getStonesCountAtPit(i));
        }
    }

    @Test
    public void testPitsAreNotEmpty() {
        assertFalse(board.pitsAreEmpty());
    }

    @Test
    public void testPitsAreEmpty() {
        board.moveAllToLm();
        assertTrue(board.pitsAreEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRevokeNegativePit() {
        board.revokeFromPit(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testStonesCountAtPitIllegalArg() {
        board.getStonesCountAtPit(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRevokeExceedPit() {
        board.revokeFromPit(GameBoardImpl.PITS_COUNT);
    }

    @Test
    public void testRevoke() {
        assertEquals(GameBoardImpl.STONES_COUNT, board.revokeFromPit(0));
        assertEquals(0, board.getStonesCountAtPit(0));
    }

    @Test
    public void testMoveAllToLm() {
        board.moveAllToLm();
        for (int i = 0; i < GameBoardImpl.PITS_COUNT; i++) {
            assertEquals(0, board.getStonesCountAtPit(i));
        }
        assertEquals(GameBoardImpl.STONES_COUNT * GameBoardImpl.PITS_COUNT, board.getLm());
    }

    @Test
    public void testAddToLm() {
        board.addToLm(1);
        for (int i = 0; i < GameBoardImpl.PITS_COUNT; i++) {
            assertEquals(GameBoardImpl.STONES_COUNT, board.getStonesCountAtPit(i));
        }
        assertEquals(1, board.getLm());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddToLmWrongNumber() {
        board.addToLm(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTurnNegativePit() {
        board.sow(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTurnExceedPit() {
        board.sow(GameBoardImpl.PITS_COUNT);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTurnEmptyPit() {
        board.revokeFromPit(0);
        board.sow(0);
    }

    @Test
    public void testTurnResultLastStoneInLm() {
        assertTrue(board.sow(0) instanceof SowResultLastStoneInLm);
        assertEquals(0, board.getStonesCountAtPit(0));
        for (int i = 1; i < GameBoardImpl.PITS_COUNT; i++) {
            assertEquals(GameBoardImpl.STONES_COUNT + 1, board.getStonesCountAtPit(i));
        }
        assertEquals(1, board.getLm());
    }

    @Test
    public void testTurnResultEmpty() {
        assertTrue(board.sow(1) instanceof SowResultEmpty);
        for (int i = 2; i < GameBoardImpl.PITS_COUNT; i++) {
            assertEquals(GameBoardImpl.STONES_COUNT + 1, board.getStonesCountAtPit(i));
        }
        assertEquals(1, board.getLm());
        assertEquals(GameBoardImpl.STONES_COUNT + 1, board.getStonesCountAtPit(0));
    }

    @Test
    public void testTurnLastPit() {
        assertTrue(board.sow(GameBoardImpl.PITS_COUNT - 1) instanceof SowResultEmpty);
        for (int i = 0; i < GameBoardImpl.PITS_COUNT - 1; i++) {
            assertEquals(GameBoardImpl.STONES_COUNT + 1, board.getStonesCountAtPit(i));
        }
        assertEquals(0, board.getStonesCountAtPit(GameBoardImpl.PITS_COUNT - 1));
        assertEquals(1, board.getLm());
    }

    @Test
    public void testTurnLastStoneInEmptyPit() {
        board.sow(0);
        assertTrue(board.sow(1) instanceof SowResultLastStoneInEmptyPit);
        assertEquals(1, board.getStonesCountAtPit(0));
        assertEquals(1, board.getStonesCountAtPit(1));
        for (int i = 2; i < GameBoardImpl.PITS_COUNT; i++) {
            assertEquals(GameBoardImpl.STONES_COUNT + 2, board.getStonesCountAtPit(i));
        }
        assertEquals(2, board.getLm());
    }
}
