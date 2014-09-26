/* Author: Mingcheng Chen */

import java.security.SecureRandom;
import java.util.ArrayList;

public class QLearningAgent implements Agent {
  public QLearningAgent() {
    //create a new random generator
    this.rand = new SecureRandom();
  }

  public void initialize(int numOfStates, int numOfActions) {
    //initialize vars
    this.qValue = new double[numOfStates][numOfActions];
    this.numOfStates = numOfStates;
    this.numOfActions = numOfActions;

    //set array locations to all 0
    for(int i = 0; i < this.numOfStates; i++) {
        for(int k = 0; k < this.numOfActions; k++) {
            this.qValue[i][k] = 0;
        }
    }
  }

  public int chooseAction(int state) {
    int flag = 0, epFail = 0;
    ArrayList<Integer> idx = new ArrayList<Integer>();

    //check for greediness
    double randEps = this.rand.nextDouble();
    if(randEps > epsilon) {
      epFail = 1;
    }

    // //look through all states and actions and check if some unexplored
    // for(int i = 0; i < this.numOfStates; i++) {
    //   for(int k = 0; k < this.numOfActions; k++) {
    //       //if unexplored state still exists
    //       if(this.qValue[i][k] == 0) {
    //           flag = 1;
    //           break;
    //       }
    //   }
    //
    //   //break if unexplored found
    //   if(flag == 1)
    //     break;
    // }

    //if greedy or we haven't explored all, choose something random to do
    if(epFail == 1 || flag == 1) {
        int randomChoice = this.rand.nextInt(this.numOfActions);
        return randomChoice;
    }

    //otherwise, look for the best q value and choose it (random if multiple best found)
    double bestVal = Integer.MIN_VALUE;
    for (int i = 0; i < this.numOfActions; i++) {
         //if we find something better than current, update
         if (this.qValue[state][i] > bestVal) {
             bestVal = this.qValue[state][i];
         }
     }
     for (int i = 0; i < this.numOfActions; i++) {
         if (bestVal == this.qValue[state][i])
            idx.add(i);
      }

     //return the best direction found
     return idx.get(this.rand.nextInt(idx.size()));
  }

  public void updatePolicy(double reward, int action,
                           int oldState, int newState) {
    //get the old prediction
    double oldPrediction = this.qValue[oldState][action];

    //search for the best q from newstate
    double maxQ = Integer.MIN_VALUE;
    int idx = 0;

    for (int i = 0; i < this.numOfActions; i++) {
        if(this.qValue[newState][i] > maxQ) {
            maxQ = this.qValue[newState][i];
        }
    }

    //update state q value
    this.qValue[oldState][action] = oldPrediction + rate * (reward + discount*maxQ - oldPrediction);
  }

  public Policy getPolicy() {

      //look through all of the states and pick the best action for each one
      int [] actions = new int[this.numOfStates];
      for(int i = 0; i < this.numOfStates; i++) {
          double maxQ = Integer.MIN_VALUE;
          int idx = 0;
          for(int k = 0; k < this.numOfActions; k++) {
              if(this.qValue[i][k] > maxQ) {
                    maxQ = this.qValue[i][k];
                    idx = k;
              }
          }
          actions[i] = idx;
      }

      //create a new policy from the array and return it
      Policy p = new Policy(actions);
      return p;
  }

  private static final double discount = 0.9;
  private static final double rate = 0.1;
  private static final double epsilon = 0.0;

  private int numOfStates;
  private int numOfActions;
  private double[][] qValue;
  private SecureRandom rand;
}
