package com.ipetrov.lm.impl;

/**
 * User: Igor
 * Date: 15.08.14
 * Time: 23:18
 */
class SowResultLastStoneInEmptyPit extends BaseSowResult {
    private final int pitNumber;

    SowResultLastStoneInEmptyPit(GameBoardImpl board, int pitNumber) {
        super(board);
        this.pitNumber = pitNumber;
    }

    public void apply() {
        GameBoardImpl opponentBoard = (GameBoardImpl) board.getGame().getOpponentBoard(board.getPlayer());
        int stonesCount = opponentBoard.revokeFromPit(GameBoardImpl.PITS_COUNT - pitNumber - 1);
        stonesCount += board.revokeFromPit(pitNumber);
        board.addToLm(stonesCount);
        board.getGame().switchTurn();

        if (board.pitsAreEmpty()) {
            ((GameBoardImpl) board.getGame().getOpponentBoard(board.getPlayer())).moveAllToLm();
        }
        if (opponentBoard.pitsAreEmpty()) {
            board.moveAllToLm();
        }
    }
}
