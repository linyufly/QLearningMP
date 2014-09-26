/* Author: Zelun Luo */

import java.util.Random;
import java.util.ArrayList;

public class QLearningAgent implements Agent {
  public QLearningAgent() {
    this.rand = new Random();
  }

  public void initialize(int numOfStates, int numOfActions) {
    this.qValue = new double[numOfStates][numOfActions];
    this.numOfStates = numOfStates;
    this.numOfActions = numOfActions;
  }

  private double bestUtility(int state) {
    double bestU = qValue[state][0];
    for (int i = 1; i < this.numOfActions; i++)
    {
      if (bestU < qValue[state][i])
        bestU = qValue[state][i];
    }
    return bestU;
  }

  private int bestAction(int state) {
    double bestU = bestUtility(state);
    ArrayList<Integer> bestAs = new ArrayList<Integer>();
    for (int i = 0; i < this.numOfActions; i++)
    {
      if (Double.compare(bestU, qValue[state][i]) == 0)
      {
        bestAs.add(i);
      }
    }
    int index = rand.nextInt(bestAs.size());
    return bestAs.get(index);
  }

  public int chooseAction(int state) {
    int explore = rand.nextInt(100);
    int action;
    if (explore >= epsilon * 100)
    {
      action = bestAction(state);
    }
    else
    {
      action = rand.nextInt(this.numOfActions);
    }
    return action;
  }

  public void updatePolicy(double reward, int action,
                           int oldState, int newState) {
    double oldValue = qValue[oldState][action];
    qValue[oldState][action] = oldValue + 
        rate * (reward + discount * bestUtility(newState) - oldValue);
  }

  public Policy getPolicy() {
    int[] actions = new int[this.numOfStates];
    for (int i = 0; i < this.numOfStates; i++) {
      actions[i] = bestAction(i);
    }
    return new Policy(actions);
  }

  private static final double discount = 0.9;
  private static final double rate = 0.1;
  private static final double epsilon = 0;//0.05;
    
  private Random rand;
  private int numOfStates;
  private int numOfActions;
  private double[][] qValue;
}
