package com.bol.test.lm;

/**
 * Created with IntelliJ IDEA.
 * User: IPetrov
 * Date: 8/19/14
 * Time: 9:47 AM
 */
public interface GameBoard {
    public static final int PITS_COUNT = 6;
    public static final int STONES_COUNT = 6;

    public int getLm();

    public int getStonesCountAtPit(int pitNumber);
}
