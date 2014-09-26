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
    // The bestUtility method returns the highest Q-Value
    // possible by taking an action in the given state
    double max = qValue[state][0];
    for (int i = 1; i < this.numOfActions; i++) {
      double curr = qValue[state][i];
      if (curr > max) max = curr;
    }
    return max;
  }

  public int chooseAction(int state) {
    // The chooseAction method returns your selected action 
    // (an integer from {0, 1, 2, 3}) given the state as an argument.
      // choose randomly if ε > x
    if (rand.nextDouble() < epsilon)
      return rand.nextInt(this.numOfActions);
      // else be greedy with the current best option
    double max = qValue[state][0];
    int bestAction = 0;
    for (int i = 1; i < this.numOfActions; i++) {
      double curr = qValue[state][i];
      if (curr > max) {
        max = curr;
        bestAction = i;
      }
        // if two options have the same Q-Value, randomly choose one
      else if ( (curr == max) && rand.nextBoolean())
        bestAction = i;
    }
    return bestAction;
  }

  public void updatePolicy(double reward, int action,
                           int oldState, int newState) {
    // The updatePolicy method processes the reward received by 
    // executing action which transitions from oldState to newState.
    qValue[oldState][action] = (1 - rate) * qValue[oldState][action] 
                                + rate * (reward + discount * bestUtility(newState) );
    return;
  }

  public Policy getPolicy() {
    // The getPolicy method should return a Policy object, 
    // which specifies the action for each state. 
    // More details can be found in Policy.java.
    int[] actions = new int[this.numOfStates];
    for (int i = 0; i < this.numOfStates; i++)
      actions[i] = chooseAction(i);
    return new Policy(actions);
  }

  private static final double discount = 0.9;
  private static final double rate = 0.1;
  private static final double epsilon = 0.0;  // changes with respect to requirement


  private Random rand;
  private int numOfStates;
  private int numOfActions;
  private double[][] qValue;
}
