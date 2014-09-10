/* Author: Mingcheng Chen */
/*
The world is below.

..#.1
..#..
..#OO
..#..
C.#.2

1 and 2 are the two customers.
C is the company location.
# has low slipperiness.
O has high slipperiness.
*/

public class WorldWithoutThief implements World {
  public void initialize() {
  }

  public boolean hasThief() {
    return false;
  }

  public boolean knowsThief() {
    return false;
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

  public void evolve() {
  }

  public double slipperiness(int row, int col) {
    if (col == 2) {
      return lowSlipperiness;
    }

    if (row == 2 && col > 2) {
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
  private static final double lowSlipperiness = 0.2;
}
