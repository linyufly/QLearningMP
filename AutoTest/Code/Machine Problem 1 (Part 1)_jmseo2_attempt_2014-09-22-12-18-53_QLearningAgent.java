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
    double best = qValue[state][0];
    for (int action = 1; action < numOfActions; action++) {
      best = Math.max(best, qValue[state][action]);
    }
    return best;
  }

  // This function returns the best action to take from state state.
  // Simply choose the action that has the highest qValue[state][action].
  // If several optimal actions exist, choose one randomly.
  private int bestAction(int state) {
    ArrayList<Integer> bestActions = new ArrayList<Integer>();
    double bestUtility = bestUtility(state);
    for (int action = 0; action < numOfActions; action++) {
      if (qValue[state][action] == bestUtility) {
        bestActions.add(action);
      }
    }
    // We choose action randomly from several optimal actions
    return bestActions.get(this.rand.nextInt(bestActions.size()));
  }

  public int chooseAction(int state) {
    int nextAction = -1;
    double x = this.rand.nextDouble();
    // With probability epsilon, choose a random action
    if (x < epsilon) {
      nextAction = this.rand.nextInt(numOfActions);
    // With probability (1 - epsilon), choose the best action
    } else {
      nextAction = bestAction(state);
    }
    return nextAction;
  }

  public void updatePolicy(double reward, int action,
                           int oldState, int newState) {
    qValue[oldState][action] = 
      (1 - rate) * qValue[oldState][action] + rate * (reward + discount * bestUtility(newState));
  }

  public Policy getPolicy() {
    int [] actions = new int[numOfStates];
    for (int state = 0; state < numOfStates; state++) {
      actions[state] = bestAction(state);
    }
    return new Policy(actions);
  }

  private static final double discount = 0.9;
  private static final double rate = 0.1;
  private static final double epsilon = 0.00;

  private int numOfStates;
  private int numOfActions;
  private double[][] qValue;
  private Random rand;
}
