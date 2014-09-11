/* Author: Mingcheng Chen */
/*
The world is below.

..O.1
..#..
..#OO
..#..
C.#.2

1 and 2 are the two customers.
C is the company location.
# has low slipperiness.
O has high slipperiness.
*/

import java.lang.Runtime;

public class WorldWithoutThief implements World {
  public void beAwareOfThief() {
  }

  public void beIgnorantOfThief() {
  }

  public void initialize() {
  }

  public boolean hasThief() {
    return false;
  }

  public boolean knowsThief() {
    return false;
  }

  public boolean inCompany(int row, int col) {
    return row == this.getNumberOfRows() - 1 && col == 0;
  }

  public int hasCustomer(int row, int col) {
    if (row == 0 && col == this.getNumberOfCols() - 1) {
      return 1;
    }

    if (row == this.getNumberOfRows() - 1 && col == this.getNumberOfCols() - 1) {
      return 2;
    }

    return 0;
  }

  public int getThiefRow() {
    System.out.println("Error: This world does not have thief.");
    System.exit(0);

    return -1;
  }

  public int getNumberOfRows() {
    return numOfRows;
  }

  public int getNumberOfCols() {
    return numOfCols;
  }

  public int getNumberOfStates() {
    return numOfRows * numOfCols * 2 * 2;
  }

  public int getInitialState() {
    return this.getState(this.getNumberOfRows() - 1, 0, true, true);
  }

  public int getState(int robotRow, int robotCol, boolean hasP1, boolean hasP2) {
    int state = robotRow * this.getNumberOfCols() + robotCol;

    if (hasP1) {
      state = state * 2 + 1;
    } else {
      state = state * 2;
    }

    if (hasP2) {
      state = state * 2 + 1;
    } else {
      state = state * 2;
    }

    return state;
  }

  public int getState(int robotRow, int robotCol, int thiefRow, boolean hasP1, boolean hasP2) {
    System.out.println("Error: This world does not have thief.");
    System.exit(0);

    return -1;
  }

  public void evolve() {
  }

  public double slipperiness(int row, int col) {
    if (row > 0 && col == 2) {
      return lowSlipperiness;
    }

    if (row == 0 && col == 2 || row == 2 && col > 2) {
      return highSlipperiness;
    }

    return 0.0;
  }

  public boolean meetsThief(int row, int col) {
    return false;
  }

  private static final int numOfRows = 5;
  private static final int numOfCols = 5;
  private static final double highSlipperiness = 0.6;
  private static final double lowSlipperiness = 0.1;
}
