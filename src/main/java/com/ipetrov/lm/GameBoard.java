package com.ipetrov.lm;

/**
 * Created with IntelliJ IDEA.
 * User: IPetrov
 * Date: 8/19/14
 * Time: 9:47 AM
 */
public interface GameBoard {
    int PITS_COUNT = 6;
    int STONES_COUNT = 6;

    int getLm();

    int getStonesCountAtPit(int pitNumber);
}
