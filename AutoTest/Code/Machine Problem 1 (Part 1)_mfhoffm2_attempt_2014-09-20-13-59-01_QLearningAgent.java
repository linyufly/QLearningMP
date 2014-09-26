/* Author: Mingcheng Chen */

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

  private int actionWithBestUtility(int state) {
    double[] possibleActions = qValue[state];
    ArrayList<Integer> bestActions = new ArrayList<Integer>();
    double highestUtilityFound = Double.NEGATIVE_INFINITY;
    for (int i = 0; i < numOfActions; i++) {
      if (possibleActions[i] == highestUtilityFound) {
        bestActions.add(i);
      } else if (possibleActions[i] > highestUtilityFound) {
        highestUtilityFound = possibleActions[i];
        bestActions.clear();
        bestActions.add(i);
      }
    }

    return rand.nextInt(bestActions.size());
  }

  private double bestUtility(int state) {
    double[] possibleActions = qValue[state];
    double highestUtilityFound = Double.NEGATIVE_INFINITY;
    for (int i = 0; i < numOfActions; i++) {
      if (possibleActions[i] > highestUtilityFound) {
        highestUtilityFound = possibleActions[i];
      }
    }
    return highestUtilityFound;
  }

  public int chooseAction(int state) {
    if (rand.nextDouble() < epsilon) {
      return rand.nextInt(numOfActions);
    }
    return actionWithBestUtility(state);
  }

  public void updatePolicy(double reward, int action,
                           int oldState, int newState) {
    qValue[oldState][action] = qValue[oldState][action] + rate*(reward - qValue[oldState][action] + discount*bestUtility(newState));
  }

  public Policy getPolicy() {
    int[] actionForState = new int[numOfStates];
    for (int i = 0; i < numOfStates; i++) {
      actionForState[i] = actionWithBestUtility(i);
    }
    return new Policy(actionForState);
  }

  private static final double discount = 0.9;
  private static final double rate = 0.1;
  private static final double epsilon = 0.0;

  private Random rand;
  private int numOfStates;
  private int numOfActions;
  private double[][] qValue;
}
