/* Author: Mingcheng Chen */

import java.util.Random;

public class QAgent implements Agent {
  public QAgent() {
    this.rand = new Random();
  }

  public void initialize(int numOfStates, int numOfActions) {
    this.qValue = new double[numOfStates][numOfActions];
    this.numOfStates = numOfStates;
    this.numOfActions = numOfActions;

    for (int i = 0; i < numOfStates; i++) {
      for (int j = 0; j < numOfActions; j++) {
        this.qValue[i][j] = 0.0;
      }
    }
  }

  private double bestUtility(int state) {
    double result = this.qValue[state][0];
    for (int i = 1; i < this.numOfActions; i++) {
      if (this.qValue[state][i] > result) {
        result = this.qValue[state][i];
      }
    }

    return result;
  }

  public int chooseAction(int state) {
    if (this.rand.nextDouble() <= epsilon) {
      return this.rand.nextInt(numOfActions);
    }

    double maxQ = this.bestUtility(state);

    ArrayList<Integer> candidates = new ArrayList<Integer>();
    for (int i = 0; i < this.numOfActions; i++) {
      if (this.qValue[state][i] == maxQ) {
        candidates.add(i);
      }
    }

    return candidates.get(this.rand.nextInt(candidates.size()));
  }

  public void updatePolicy(double reward, int action,
                           int oldState, int newState) {
    this.qValue[oldState][action] *= (1.0 - rate);
    this.qValue[oldState][action] += rate * (reward + discount * this.bestUtility(newState));
  }

  public Policy getPolicy() {
    for (
  }

  private static final double discount = 0.9;
  private static final double rate = 0.1;
  private static final double epsilon = 0.05;

  private int numOfStates;
  private int numOfActions;
  private double[][] qValue;
  private Random rand;
}
