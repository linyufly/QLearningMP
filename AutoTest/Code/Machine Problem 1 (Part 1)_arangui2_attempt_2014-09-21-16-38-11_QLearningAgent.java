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
    double stateBestUtility = -1;
    for (int i = 0; i < this.numOfActions; i++) {
      if (this.qValue[state][i] > stateBestUtility) {
        stateBestUtility = this.qValue[state][i];
      }
    }
    return stateBestUtility;
  }

  public int chooseAction(int state) {
    double stateBestUtility = this.bestUtility(state);
    ArrayList<Integer> bestActions = new ArrayList<Integer>(0);
    for (int i = 0; i < this.numOfActions; i++) {
      if (this.qValue[state][i] == stateBestUtility) {
        bestActions.add(i);
      }
    }
    int chosenAction = bestActions.get(rand.nextInt(bestActions.size()));
    if (rand.nextDouble() < this.epsilon) {
        chosenAction = rand.nextInt(this.numOfActions);
    }
    // System.out.println(chosenAction);
    return chosenAction;
  }

  // Q(a,s) = Q(a,s) + alpha * (r_s + gamma * max_a'(Q(a',s')) - Q(a,s))
  public void updatePolicy(double reward, int action,
                           int oldState, int newState) {
    this.qValue[oldState][action] +=
        rate * (reward + discount * this.bestUtility(newState) -
                this.qValue[oldState][action]);

  }

  public Policy getPolicy() {
    int[] actions = new int[this.numOfStates];
    for (int i = 0; i < this.numOfStates; i++) {
      actions[i] = this.chooseAction(i);
    }
    return new Policy(actions);
  }

  private static final double discount = 0.9;
  private static final double rate = 0.1;
  private static final double epsilon = 0.00;

  private Random rand;
  private int numOfStates;
  private int numOfActions;
  private double[][] qValue;
}
