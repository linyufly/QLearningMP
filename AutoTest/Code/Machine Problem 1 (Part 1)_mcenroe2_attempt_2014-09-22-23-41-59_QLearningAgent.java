/* Framework Author: Mingcheng Chen */
/* Student work by: Martin McEnroe mcenroe2 */

import java.util.Random;
import java.util.ArrayList;

public class QLearningAgent implements Agent {
  public QLearningAgent() {
    this.rand = new Random();
  }

  public void initialize(int numOfStates, int numOfActions) {
    this.qValue = new double[numOfStates][numOfActions];
    this.transFreq = new double[numOfStates][numOfActions];
    this.transCumReward = new double[numOfStates][numOfActions];
    this.tiedActions = new int[numOfActions+1];
    this.numOfStates = numOfStates;
    this.numOfActions = numOfActions;
  }

  private double bestUtility(int forThisState) { 
    //just read across the state row of qValue and pick the best, no need to break ties, return the value of the best
    double maxActionUtility = 0;
    for (int i = 0; i < this.numOfActions; i++) {  
      if (maxActionUtility < this.qValue[forThisState][i]) {  
          maxActionUtility = this.qValue[forThisState][i];
      } 
    }
    return maxActionUtility;
  }

  private int bestAction(int forThisState) { 
    double maxActionUtility = 0;   
    int maxActionInt = 0;  
    for (int a = 0; a < this.numOfActions; a++){
    	this.tiedActions[a] = 0;
    }
    int j = 0; //we'll use this to count matching actions
    for (int i = 0; i < this.numOfActions; i++) { 
      if (maxActionUtility < this.qValue[forThisState][i]) {  
          maxActionUtility = this.qValue[forThisState][i];
          maxActionInt = i;
      } else {  
        if (maxActionUtility == this.qValue[forThisState][i]) {  
          j++;
          tiedActions[j] = i;
        }
      }  
    }
    if (j > 0) {  //UNIFORMLY pick among best actions
    	int a = rand.nextInt(j) + 1;
    	maxActionInt = this.tiedActions[a];
    }
    return maxActionInt;
  }

  public int chooseAction(int state) { 
    int nextAction;
    if (rand.nextDouble() < epsilon) {
      nextAction = rand.nextInt(this.numOfActions);
      // System.out.println("I'm a random robot!  " + nextAction);
    } else {
      nextAction = bestAction(state);
    }
    return nextAction;
  }

  public void updatePolicy(double reward, int action,
                           int oldState, int newState) {
    double nextReward;
    transFreq[oldState][action] = transFreq[oldState][action] + 1;
    transCumReward[oldState][action] = transCumReward[oldState][action] + reward;
	int b = bestAction(newState);    
    if (transFreq[newState][b] > 0){
    	nextReward = (transCumReward[newState][b] / transFreq[newState][b]);
    } else {
    	nextReward = 0;
    }
    //equation 21.8
    qValue[oldState][action] = (1 - rate) * qValue[oldState][action] + rate * (nextReward + discount * bestUtility(newState) );
  }

  public Policy getPolicy() {
    int[] actions = new int[this.numOfStates];
    for (int s = 0; s < this.numOfStates; s++) {
      actions[s] = bestAction(s);  
    }
  return new Policy(actions);
  }

  private static final double discount = 0.9;
  private static final double rate = 0.1;
  private static final double epsilon = 0.0;

  private Random rand;
  private int numOfStates;  //# of states in the world
  private int numOfActions;  //# of actions possible - generally four NSEW 0213
  private int[] tiedActions;
  private double[][] qValue;  //the matrix that  tells us the best utility for a state-action pair
  private double[][] transFreq;  // a state-action matrix that counts how many times an action was taken
  private double[][] transCumReward;  // a state-action matrix that accumulates the rewards over all the times the state-action pair happened
}
