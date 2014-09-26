/* Author: Mingcheng Chen */
/* Author: Philip Daian */

import java.util.Random;
import java.util.ArrayList;

public class QLearningAgent implements Agent {

  public QLearningAgent() {
    this.rand = new Random();
  }

  public void initialize(int numOfStates, int numOfActions) {
    this.qValue = new double[numOfStates][numOfActions];

    // Initialize Q values to 0
    for (int state = 0; state < numOfStates; state++) {
      for (int action = 0; action < numOfActions; action++) {
        qValue[state][action] = 0.0;
      }
    }

    this.numOfStates = numOfStates;
    this.numOfActions = numOfActions;
  }

  // Utility method to find best utility action in a given state, associated quality
  private BestStateAction bestUtility(int state) {
    // We estimate the action with the best utility by picking a random
    // action with the best quality score in the given state
    ArrayList<Integer> optimalActions = new ArrayList<Integer>();
    // Keep track of best quality found so far
    double bestQuality = -1.0;
    for (int action = 0; action < numOfActions; action++) {
      if (qValue[state][action] > bestQuality) {
        // Action represents best quality action so far from possible state
        bestQuality = qValue[state][action];
        optimalActions = new ArrayList<Integer>();
        optimalActions.add(action);
      }
      else if (qValue[state][action] == bestQuality) {
        // Action is as good quality as previous found optimal action
        // Add to set to be chosen from at random
        optimalActions.add(action);
      }
      // Otherwise, action is worse quality than previously found action
    }
    // Return an element of best quality action set at random
    int elementToReturn = rand.nextInt(optimalActions.size());
    return new BestStateAction(bestQuality, optimalActions.get(elementToReturn), state);
  }

  public int chooseAction(int state) {
    // Get random double from 0 to 1
    double randDouble = rand.nextDouble();
    // Return random action with probability epsilon
    if (randDouble < epsilon) {
      return rand.nextInt(numOfActions);
    }
    // Greedily return guess at optimal action
    return bestUtility(state).action;
  }

  public void updatePolicy(double reward, int action,
                           int oldState, int newState) {
    // Apply one iteration of Q-Learning formula
    double oldQValue = qValue[oldState][action];
    double learnedValue = reward + discount * bestUtility(newState).quality;
    double newQValue = oldQValue + rate * (learnedValue - oldQValue);
    qValue[oldState][action] = newQValue;
  }

  public Policy getPolicy() {
    // Get optimal action for each state, create policy
    int[] optimalActions = new int[numOfStates];
    for (int state = 0; state < numOfStates; state++) {
      optimalActions[state] = bestUtility(state).action;
    }
    return new Policy(optimalActions);
  }

  private static final double discount = 0.9;
  private static final double rate = 0.1;
  private static final double epsilon = 0.0;

  private int numOfStates;
  private int numOfActions;
  private double[][] qValue;

  private Random rand;

  // Utility class representing best action to take from a state and the quality
  // associated with the action in the source state.
  private class BestStateAction {
    public double quality;
    public int action;
    public int source_state;

    public BestStateAction(double quality, int action, int source_state) {
      this.quality = quality;
      this.action = action;
      this.source_state = source_state;
    }
  }

}
