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

  private double bestUtility(int state) {
    double max = qValue[state][0];
    for (int i = 0; i < qValue[state].length; i++) {
      if (qValue[state][i] > max) {
        max = qValue[state][i];
      }
    }
    return max;
  }

  public int chooseAction(int state) {
    // randomness factor
    double r = rand.nextDouble();
    if (r < epsilon) return rand.nextInt(qValue[state].length);

    // normal chooseAction
    double best = bestUtility(state);
    ArrayList<Integer> optimalStates = new ArrayList<Integer>();
    for (int i = 0; i < qValue[state].length; i++)
      if (qValue[state][i] == best)
        optimalStates.add(i);
    return optimalStates.get(rand.nextInt(optimalStates.size()));
  }

  public void updatePolicy(double reward, int action, int oldState, int newState) {
    qValue[oldState][action] += rate * (reward + discount * bestUtility(newState) - qValue[oldState][action]);
  }

  public Policy getPolicy() {
    int[] p = new int[numOfStates];
    for (int state = 0; state < p.length; state++) {
      double best = bestUtility(state);
      ArrayList<Integer> optimalStates = new ArrayList<Integer>();
      for (int i = 0; i < qValue[state].length; i++)
        if (qValue[state][i] == best)
          optimalStates.add(i);
      p[state] = optimalStates.get(rand.nextInt(optimalStates.size()));
    }
    return new Policy(p);
  }

  private static final double discount = 0.9;
  private static final double rate = 0.1;
  private static final double epsilon = 0.05;

  private int numOfStates;
  private int numOfActions;
  private double[][] qValue;

  private Random rand;
}
