package com.bol.test.lm.impl;

import com.bol.test.lm.GameBoard;
import com.bol.test.lm.Player;

/**
 * User: Igor
 * Date: 15.08.14
 * Time: 22:14
 */
class GameBoardImpl implements GameBoard {
    private final int[] pits;
    private int lm;

    private final Player player;
    private final GameImpl game;

    GameBoardImpl(Player player, GameImpl game) {
        this.player = player;
        this.game = game;

        pits = new int[PITS_COUNT];
        for (int i = 0; i < pits.length; i++) {
            pits[i] = STONES_COUNT;
        }
    }

    BaseSowResult sow(int pitNumber) throws IllegalArgumentException {
        if (pitNumber < 0 || pitNumber >= pits.length || pits[pitNumber] == 0) {
            throw new IllegalArgumentException();
        }
        int currPitNumber = pitNumber;
        int pitVal = pits[currPitNumber];
        pits[currPitNumber] = 0;
        currPitNumber++;
        while (pitVal > 0) {
            pitVal--;
            if (currPitNumber == pits.length) {
                lm++;
                if (pitVal == 0) {
                    return new SowResultLastStoneInLm(this);
                }
                currPitNumber = 0;
            } else {
                pits[currPitNumber++]++;
                if (pitVal == 0 && pits[currPitNumber - 1] == 1) {
                    return new SowResultLastStoneInEmptyPit(this, currPitNumber - 1);
                }
            }
        }
        return new SowResultEmpty(this);
    }

    public int getLm() {
        return lm;
    }

    boolean pitsAreEmpty() {
        for (int i = 0; i < pits.length; i++) {
            if (pits[i] > 0) {
                return false;
            }
        }
        return true;
    }

    int revokeFromPit(int pitNumber) throws IllegalArgumentException {
        if (pitNumber < 0 || pitNumber >= pits.length) {
            throw new IllegalArgumentException();
        }
        int stonesCount = pits[pitNumber];
        pits[pitNumber] = 0;
        return stonesCount;
    }

    void setPit(int pitNumber, int pitValue) {
        pits[pitNumber] = pitValue;
    }

    public int getStonesCountAtPit(int pitNumber) {
        if (pitNumber < 0 || pitNumber >= pits.length) {
            throw new IllegalArgumentException();
        }
        return pits[pitNumber];
    }

    void moveAllToLm() {
        int stonesCount = 0;
        for (int i = 0; i < pits.length; i++) {
            stonesCount += pits[i];
            pits[i] = 0;
        }
        addToLm(stonesCount);
    }

    void addToLm(int stonesCount) {
        if (stonesCount < 0) {
            throw new IllegalArgumentException();
        }
        lm += stonesCount;
    }

    Player getPlayer() {
        return player;
    }

    GameImpl getGame() {
        return game;
    }
}
