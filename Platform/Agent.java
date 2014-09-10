/* Author: Mingcheng Chen */

public interface Agent {
  public void initialize(int numOfStates, int numOfActions, int initialState);
  public int chooseAction(double reward, int newState);
  public Policy getPolicy();
}
