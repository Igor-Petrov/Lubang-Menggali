package com.bol.test.lm.impl;

/**
 * User: Igor
 * Date: 15.08.14
 * Time: 23:16
 */
class SowResultEmpty extends BaseSowResult {
    SowResultEmpty(GameBoardImpl board) {
        super(board);
    }

    public void apply() {
        board.getGame().switchTurn();
    }
}
