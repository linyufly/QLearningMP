/* Author: Mingcheng Chen */

/// CMC ///

import java.util.Random;
import java.util.ArrayList;

public class QLearningAgent implements Agent {
  public QLearningAgent() {
    //this.rand = new Random();
  }

  public void initialize(int numOfStates, int numOfActions) {
    this.qValue = new double[numOfStates][numOfActions];
    this.numOfStates = numOfStates;
    this.numOfActions = numOfActions;
  }

  private double bestUtility(int state) {
    double cur_max = 0.0;
    for (int i=0; i<numOfActions; i++ ) {
      if( qValue[state][i] > cur_max ){
        // new best. start the list over. 
        cur_max = qValue[state][i];
      } 
    }
    return cur_max;
  }

  public int chooseAction(int state) {
    // use epsilon here 
    // likelihood that I should chooose action based on my policy or at random
    // use a random num gen if less that eps then choose at random 
    Random rand = new Random();
    double rand_double = rand.nextDouble();
    if ( Double.compare(rand_double,epsilon) > 0) {
      return (int) _chooseAction(state);
    } else {
      int rand_int = rand.nextInt(numOfActions);
      return rand_int;
    }
  }

  private int _chooseAction(int state) {
    Random rand = new Random();
    ArrayList<Integer> best_choices = new ArrayList<Integer>();
    double max_reward = -Double.MAX_VALUE;
    for (int i=0; i<numOfActions; i++ ) {
      if( Double.compare(qValue[state][i], max_reward) > 0 ){
        // new best. start the list over. 
        max_reward = qValue[state][i];
        best_choices.clear();
        best_choices.add(i);
      } else if ( Double.compare(qValue[state][i],max_reward) == 0) {
        // tie. add to existing list.
        best_choices.add(i);
      }
    }

    //if there are more than one choice choose randomly 
    int rand_int = rand.nextInt((int)best_choices.size());
    return (int) best_choices.get(rand_int);
    
  }

  public void updatePolicy(double reward, int action,
                           int oldState, int newState) {
    qValue[oldState][action] = qValue[oldState][action] + rate *( reward + (discount*bestUtility(newState)) - qValue[oldState][action]);

  }

  public Policy getPolicy() {
    int pol[] = new int[numOfStates];
    for (int i=0; i<numOfStates ; i++ ) {
      pol[i] = _chooseAction(i);
    }
    
    Policy p1 = new Policy(pol);
    return p1;
  }

  private static final double discount = 0.9; //gamma
  private static final double rate = 0.1; //alpha 
  private static final double epsilon = 0.0; 

  private int numOfStates;
  private int numOfActions;
  private double[][] qValue;
}
