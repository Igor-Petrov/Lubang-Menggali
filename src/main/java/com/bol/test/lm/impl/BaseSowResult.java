package com.bol.test.lm.impl;

/**
 * User: Igor
 * Date: 15.08.14
 * Time: 23:14
 */
abstract class BaseSowResult {
    protected final GameBoardImpl board;

    protected BaseSowResult(GameBoardImpl board) {
        this.board = board;
    }

    public abstract void apply();
}
