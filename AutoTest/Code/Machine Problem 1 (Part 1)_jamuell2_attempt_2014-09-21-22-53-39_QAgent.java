/* Author: Mingcheng Chen */

import java.util.Random;
import java.util.*;

public class QAgent implements Agent {
  private static final double discount = 0.9;
  private static final double rate = 0.1;
  private static final double epsilon = 0.0;

  private Random rand;
  private int numOfStates;
  private int numOfActions;
  private double[][] qValue;

  public QAgent() {
    this.rand = new Random();
  }

  public void initialize(int numOfStates, int numOfActions) {
    this.qValue = new double[numOfStates][numOfActions];
    this.numOfStates = numOfStates;
    this.numOfActions = numOfActions;
  }

  private double bestUtility(int state) {
    double maxUtil = Double.MIN_VALUE;

    for (int i = 0; i < numOfActions; i++) {
      double util = this.qValue[state][i];
      if (util > maxUtil) {
        maxUtil = util;
      }
    }

    return maxUtil;
  }

  public int chooseAction(int state) {
    double randDouble = rand.nextDouble();
    if (randDouble <= epsilon) {
      int nextState = rand.nextInt(numOfActions);
    } else {
      double maxUtil = this.qValue[state][0];
      ArrayList<Integer> bestActionIndices = new ArrayList<Integer>();
      bestActionIndices.add(0);

      for (int i = 1; i < numOfActions; i++) {
        double util = this.qValue[state][i];
        if (util > maxUtil) {
          bestActionIndices.clear();
          bestActionIndices.add(i);
          maxUtil = util;
        } else if (util == maxUtil) {
          bestActionIndices.add(i);
        }
      }

      int numBestActions = bestActionIndices.size();
      if (numBestActions == 1) {
        return bestActionIndices.get(0);
      } else {
        int randBest = rand.nextInt(numBestActions);
        return bestActionIndices.get(randBest);
      }
    }
    return 0;
  }

  public void updatePolicy(double reward, int action,
                           int oldState, int newState) {
    double prevQ = this.qValue[oldState][action];
    this.qValue[oldState][action] = prevQ + rate * (reward + discount * bestUtility(newState) - prevQ);
  }

  public Policy getPolicy() {
    int[] stateBestActions = new int[numOfStates];
    for (int i = 0; i < numOfStates; i++) {
      stateBestActions[i] = chooseAction(i);
    }

    return new Policy(stateBestActions);
  }
}