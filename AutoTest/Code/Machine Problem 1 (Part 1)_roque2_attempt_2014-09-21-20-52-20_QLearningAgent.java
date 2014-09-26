/* Author: roque2 */

import java.util.Random;
import java.util.ArrayList;

public class QLearningAgent implements Agent {
  private static final double discount = 0.9;
  private static final double rate = 0.1;
  private static final double epsilon = 0.0;
  
  private Random rand;
  private int numOfStates;
  private int numOfActions;
  private double[][] qValue;
  
  /*
   * Constructor. Instantiates a new random number generator for the agent to use.
   */
  public QLearningAgent() {
    this.rand = new Random();
  }

  /*
   * Initialization function that initiates the number of states, actions, and an array of arbitrary q-values.
   * 
   * @param numOfStates - The number of states in the world.
   * @param numOfActions - the number of actions in the world.
   */
  public void initialize(int numOfStates, int numOfActions) {
    this.numOfStates = numOfStates;
    this.numOfActions = numOfActions;
    this.qValue = new double[numOfStates][numOfActions];
    
    for(int i = 0; i < numOfStates; i++) {
      for(int j = 0; j < numOfActions; j++) {
        
        this.qValue[i][j] = this.rand.nextDouble();
        
      }
    }
  }
  
  /*
   * Helper function that calculates the highest utility a given state can achieve at the current point of time.
   * 
   * @param state - The state whose utility we want to know.
   * 
   * @return - The value of our state's highest utility.
   */
  private double bestUtility(int state) {
    double currBest = 0.0;
    
    for(int i = 0; i < this.numOfActions; i++) {
      if(this.qValue[state][i] > currBest) {
        currBest = this.qValue[state][i];
      } else if (this.qValue[state][i] == currBest) {
        double chance = this.rand.nextDouble();
        if( chance >= 0.5)
         currBest = this.qValue[state][i];
      }
    }
    
    return currBest;
  }
  
  /*
   * Helper function that determines the actions that yields the highest utility at the current point of time.
   * 
   * @param state - The state whose most beneficial action we want to know.
   * 
   * @return - The id of the state's most beneficial action.
   */
  private int bestAction(int state) {
    int currBest = 0;
    
    for(int i = 0; i < this.numOfActions; i++) {
      if(this.qValue[state][i] > this.qValue[state][currBest]) {
        currBest = i;
      } else if (this.qValue[state][i] == this.qValue[state][currBest]) {
        double chance = this.rand.nextDouble();
        
        if( chance >= 0.5)
          currBest = i;
      }
    }
    
    return currBest;
  } 
  
  /*
   * Determins what action to choose. As this agent is following epsilon-greedy, we choose the action that yields the highest estimated utility. There is a chance indicated by epsilon that we will instead choose a random action.
   * 
   * @param state - Our starting state from which we will perform an action.
   * 
   * @return - The id of the optimal action to perform from our given state.
   */
  public int chooseAction(int state) {
    double detRand = this.rand.nextDouble();
    
    if( detRand < epsilon )
      return this.rand.nextInt(this.numOfActions);
    
    return this.bestAction(state);
    
  }
  
  /*
   * Updates a state in the q-values table with the latest estimated utility.
   * 
   * @param reward - The reward recieved by travelling to newState.
   * @param action - The id of the action we are to perform.
   * @param oldState - Our starting state.
   * @param newState - Our destination state.
   */
  public void updatePolicy(double reward, int action, int oldState, int newState) {

    this.qValue[oldState][action] = this.qValue[oldState][action] + this.rate * 
      ( reward + this.discount * this.bestUtility(newState) - this.qValue[oldState][action] );
    
  }
  
  /*
   * Returns a policy containing optimal actions corresponding to given states.
   *
   *@return - A policy containing the optimal actions for corresponding states.
   */
  public Policy getPolicy() {
    int[] actions = new int[this.numOfStates];
    
    for (int i = 0; i < this.numOfStates; i++)
      actions[i] = this.bestAction(i);
    
    return new Policy(actions);
  }
}