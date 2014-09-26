/* Author: Sayan Roychowdhury */

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

  // This private helper function returns the maxQ value
  private double bestUtility(int state) {
    double maxQ = qValue[state][0];
    
    for(int i = 0; i < numOfActions; i++){
      if(qValue[state][i] > maxQ){
        maxQ = qValue[state][i];
      }
    }
    return maxQ;
  }

    // This function returns an action
  public int chooseAction(int state) {
      double random = rand.nextDouble();          // chooses a random number between 0 and 1

      if(epsilon > random){                       // epsilon randomization
      return rand.nextInt(numOfActions);
    }
    double maxQ = qValue[state][0];
    int action = 0;
    for(int i = 0; i < numOfActions; i++){        //  chooses action with highest Q value
      if(qValue[state][i] > maxQ){
        maxQ = qValue[state][i];
        action = i;
      }
      else if(qValue[state][i] == maxQ){             // if the max value is repeated, choose randomly
        if(rand.nextBoolean())
          action = i;
      }
    }
    return action;
  }

    // This function updates the policy
  public void updatePolicy(double reward, int action, int oldState, int newState) {
      double maxQ = bestUtility(newState);
      qValue[oldState][action] = qValue[oldState][action] + rate * (reward + (discount * maxQ) - qValue[oldState][action]);
  }

    // This function returns the policy
  public Policy getPolicy() {
      int [] finalPol = new int[this.numOfStates];
      for(int i = 0; i < numOfStates; i++)
	  finalPol[i] = chooseAction(i);
      return new Policy(finalPol);
  }

  private static final double discount = 0.9;
  private static final double rate = 0.1;
  private static final double epsilon = 0.0;

  private Random rand;
  private int numOfStates;
  private int numOfActions;
  private double[][] qValue;
}
