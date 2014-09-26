/* Author: Meera Parat */

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

  private int bestUtility(int state) { //returns optimal action to take from given state
    double maxQ = -10000;
    double[] actions = new double[4];
    for (int j = 0; j < numOfActions; j++) {
      if (qValue[state][j] > maxQ) {
        actions[0] = -10000;
        actions[1] = -10000;
        actions[2] = -10000;
        actions[3] = -10000;
        maxQ = qValue[state][j];
        actions[j] = qValue[state][j]; 
      }
      else if (qValue[state][j] == maxQ) {
        actions[j] = qValue[state][j]; 
      } 
    }
    
    int val = 0;
    val = rand.nextInt(this.numOfActions);
    while (actions[val] == -10000) {
      val = rand.nextInt(this.numOfActions);
    }
      return val;
  }

  public int chooseAction(int state) { //given current state, find the optimal action to get to another state
    int val = rand.nextInt(101);
    if (val <= epsilon*100) {
       return rand.nextInt(this.numOfActions);
     } 
     else{
      return bestUtility(state);
     }
  }

  public void updatePolicy(double reward, int action, int oldState, int newState) {
    double maxQ = -10000;
    for (int j = 0; j < numOfActions; j++) {
        maxQ = qValue[newState][j] > maxQ ? qValue[newState][j] : maxQ;
    }
    qValue[oldState][action] = qValue[oldState][action] + rate * (reward + (discount * maxQ) - qValue[oldState][action]);
  }

  public Policy getPolicy() { 
    int[] actions = new int[this.numOfStates];
    for (int i = 0; i < this.numOfStates; i++) {
      actions[i] = bestUtility(i);
    }
    return new Policy(actions);
  }


  private static final double discount = 0.9;
  private static final double rate = 0.1;
  private static final double epsilon = 0.0;

  private Random rand;
  private int numOfStates;
  private int numOfActions;
  private double[][] qValue; //state, and best action to take to leave the state.
}
