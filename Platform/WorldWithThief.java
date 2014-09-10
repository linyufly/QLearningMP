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
  public WorldWithThief(boolean thiefKnown) {
    this.rand = new Random();
    this.thiefKnown = thiefKnown;
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
