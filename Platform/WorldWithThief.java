/* Author: Mingcheng Chen */
/*
The world is below.

.#T.1
.#TOO
..T..
.#T..
C#T.2

1 and 2 are the two customers.
C is the company location.
# has low slipperiness.
O has high slipperiness.
T is the footprint of the thief.
*/

import java.util.Random;

public class WorldWithThief implements World {
  public void beAwareOfThief() {
    this.thiefKnown = true;
  }

  public void beIgnorantOfThief() {
    this.thiefKnown = false;
  }

  public WorldWithThief() {
    this.rand = new Random();
  }

  public void initialize() {
    this.thiefRow = rand.nextInt(numOfRows);
  }

  public boolean hasThief() {
    return true;
  }

  public boolean knowsThief() {
    return this.thiefKnown;
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
    return this.thiefRow;
  }

  public int getNumberOfRows() {
    return numOfRows;
  }

  public int getNumberOfCols() {
    return numOfCols;
  }

  public int getNumberOfStates() {
    return this.thiefKnown ? numOfRows * numOfCols * numOfRows * 2 * 2 :
                             numOfRows * numOfCols * 2 * 2;
  }

  public int getInitialState() {  // called immediately after initialize()
    return this.thiefKnown ? this.getState(this.getNumberOfRows() - 1, 0, this.thiefRow, true, true) :
                             this.getState(this.getNumberOfRows() - 1, 0, true, true);
  }

  public int getState(int robotRow, int robotCol, boolean hasP1, boolean hasP2) {  // called when the thief is unknown
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

  public int getState(int robotRow, int robotCol, int thiefRow, boolean hasP1, boolean hasP2) {  // called when the thief is known
    int state = (robotRow * this.getNumberOfCols() + robotCol) * this.getNumberOfRows() + thiefRow;

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

  public void evolve() {
    boolean upward = (this.rand.nextInt() % 2 == 0);

    if (upward && thiefRow > 0) {
      thiefRow--;
    } else if (!upward && thiefRow + 1 < numOfRows) {
      thiefRow++;
    }
  }

  public double slipperiness(int row, int col) {
    if (col == 1 && row != 2) {
      return lowSlipperiness;
    }

    if (row == 1 && col > 2) {
      return highSlipperiness;
    }

    return 0.0;
  }

  public boolean meetsThief(int row, int col) {
    return row == thiefRow && col == thiefCol;
  }

  private Random rand;
  private int thiefRow;
  private boolean thiefKnown;

  private static final int thiefCol = 2;
  private static final int numOfRows = 5;
  private static final int numOfCols = 5;
  private static final double highSlipperiness = 0.9;
  private static final double lowSlipperiness = 0.2;
}
