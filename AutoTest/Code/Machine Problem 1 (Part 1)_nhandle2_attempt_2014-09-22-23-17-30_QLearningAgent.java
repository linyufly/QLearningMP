/* Author: Nathan Handler (nhandle2) */

import java.util.Random;
import java.util.ArrayList;

public class QLearningAgent implements Agent {
  public QLearningAgent() {
    this.rand = new Random();
  }

  public void initialize(int numOfStates, int numOfActions) {
    this.qValue = new double[numOfStates][numOfActions];
    for(int state = 0 ; state < numOfStates ; state++) {
      for(int action = 0 ; action < numOfActions ; action++) {
        this.qValue[state][action] = 0;
      }
    }
    this.numOfStates = numOfStates;
    this.numOfActions = numOfActions;
  }

  private double bestUtility(int state) {
    return 0.0;
  }

  private double maxQValueForState(int state) {
    double max = Double.NEGATIVE_INFINITY;
    for(int i = 0 ; i < this.qValue[state].length ; i++) {
      if(Double.compare(this.qValue[state][i], max) > 0) {
        max = this.qValue[state][i];
      }
    }
    return max;
  }

  private ArrayList<Integer> actionsWithMaxQValue(int state) {
    double maxQValue = maxQValueForState(state);
    double[] possible_actions = this.qValue[state];
    ArrayList<Integer> max_value_actions = new ArrayList<Integer>();
    for(int i = 0 ; i < possible_actions.length ; i++) {
      if(possible_actions[i] == maxQValue) {
        max_value_actions.add(i);
      }
    }
    return max_value_actions;
  }

  public int chooseAction(int state) {
    double prob = rand.nextDouble();
    if (prob < epsilon) {
      return rand.nextInt(this.numOfActions);
    }
    else {
      ArrayList<Integer> maxQs = actionsWithMaxQValue(state);
      return maxQs.get(rand.nextInt(maxQs.size()));
    }
  }

  public void updatePolicy(double reward, int action, int oldState, int newState) {
    this.qValue[oldState][action] = (1.0 - rate) * qValue[oldState][action] + rate * ( reward + discount * maxQValueForState(newState) );
  }

  public Policy getPolicy() {
    int[] actions = new int[this.numOfStates];
    for (int i = 0; i < this.numOfStates; i++) {
      actions[i] = actionsWithMaxQValue(i).get(0);
    }

    return new Policy(actions);
  }

  private static final double discount = 0.9; //gamma
  private static final double rate = 0.1; //alpha
  private static final double epsilon = 0.0;

  private Random rand;
  private int numOfStates;
  private int numOfActions;
  private double[][] qValue;
}
