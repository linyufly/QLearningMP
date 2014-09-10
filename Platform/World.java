/* Author: Mingcheng Chen */

public interface World {
  public void initialize();
  public boolean hasThief();
  public boolean knowsThief();
  public int getNumberOfRows();
  public int getNumberOfCols();
  public int getNumberOfStates();
  public void evolve();
  public double slipperiness(int row, int col);
  public boolean meetsThief(int row, int col);
}
