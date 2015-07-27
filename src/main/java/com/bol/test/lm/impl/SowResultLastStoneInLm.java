package com.bol.test.lm.impl;

/**
 * User: Igor
 * Date: 15.08.14
 * Time: 23:17
 */
class SowResultLastStoneInLm extends BaseSowResult {
    SowResultLastStoneInLm(GameBoardImpl board) {
        super(board);
    }

    public void apply() {
        if (board.pitsAreEmpty()) {
            ((GameBoardImpl) board.getGame().getOpponentBoard(board.getPlayer())).moveAllToLm();
        }
    }
}
