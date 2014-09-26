/* Author: Nathaniel Cook*/

import java.util.Random;


public class QLearningAgent implements Agent {


  public QLearningAgent() {
  }

  public void initialize(int numOfStates, int numOfActions) {
    this.qValue = new double[numOfStates][numOfActions];
    this.numOfStates = numOfStates;
    this.numOfActions = numOfActions;
    this.rand = new Random();
  }

  private double bestUtility(int state) {
    double max = -Double.MAX_VALUE;
    for (int i = 0; i < qValue[state].length; i++) {
      double value = qValue[state][i];
      if (value > max) {
        max = value;
      }
    }

    return max;
  }

  public int chooseAction(int state) {

    if (rand.nextDouble() < epsilon) {
      return rand.nextInt(this.numOfActions);
    }

    double max = -Double.MAX_VALUE;
    int action = -1;
    for (int i = 0; i < qValue[state].length; i++) {
      double value = qValue[state][i];
      if (value >= max) {
        if (value == max && action != -1 && rand.nextBoolean()) {
          continue;
        }
        action = i;
        max = value;
      }
    }
    return action;
  }

  public void updatePolicy(double reward, int action,
                           int oldState, int newState) {
    qValue[oldState][action] = (1.0 - rate) * qValue[oldState][action] +
                               rate * (reward + discount * bestUtility(newState));

  }

  public Policy getPolicy() {
    //Turn off randomness for saving policy
    double oldEpsilon = epsilon;
    epsilon = 0;
    int[] actions = new int[this.numOfStates];
    for (int i = 0; i < qValue.length; i++) {
      actions[i] = chooseAction(i);
    }
    epsilon = oldEpsilon;
    return new Policy(actions);
  }

  private static final double discount = 0.9;
  private static final double rate = 0.1;
  private static double epsilon = 0.00;

  private int numOfStates;

  private int numOfActions;
  private double[][] qValue;

  private Random rand;
}
