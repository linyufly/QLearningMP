/* Author: Mingcheng Chen */

import java.util.Random;
import java.util.ArrayList;

public class QAgent implements Agent {
  public QAgent() {
    this.rand = new Random();
  }

  public void initialize(int numOfStates, int numOfActions) {
    this.qValue = new double[numOfStates][numOfActions];
    this.numOfStates = numOfStates;
    this.numOfActions = numOfActions;
  }

  private double bestUtility(int state) {
    double best = 0.0;
    for (double value: qValue[state])
    {
      if (value > best)
        best = value;
    }
    return best;
  }

  private int chooseBestAction(int state) {
    double best = bestUtility(state);
    ArrayList<Integer> best_list = new ArrayList<Integer>();
    for (int i = 0; i < numOfActions; i++)
    {
      if (qValue[state][i] == best)
        best_list.add(i);
    }
    return best_list.get(rand.nextInt(best_list.size())).intValue();
  }

  public int chooseAction(int state) {
    if (rand.nextDouble() < epsilon)
    {
      return rand.nextInt(numOfActions);
    }
    else
    {
      return chooseBestAction(state);
    }
  }

  public void updatePolicy(double reward, int action,
                           int oldState, int newState) {
    qValue[oldState][action] = (1.0 - rate) * qValue[oldState][action] + rate * (reward + discount * bestUtility(newState));
  }

  public Policy getPolicy() {
    int[] values = new int[numOfStates];
    for(int i = 0; i < numOfStates; i++)
    {
      values[i] = chooseBestAction(i);
    }
    return new Policy(values); 
  }

  private static final double discount = 0.9;
  private static final double rate = 0.1;
  private static final double epsilon = 0.0;

  private int numOfStates;
  private int numOfActions;
  private double[][] qValue;
  private Random rand;
}
