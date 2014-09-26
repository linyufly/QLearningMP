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

  //returns the q-value of the best action that can be taken from a certain state
  private double bestUtility(int state) {
    //assume action 0 has the best utility, then search for another action with a higher utility
	int bestaction = 0;
    for (int i = 1 ; i < this.numOfActions ; i++) {
		if(this.qValue[state][i] > this.qValue[state][bestaction])
			bestaction = i;
	}
	return this.qValue[state][bestaction];
  }

  //chooses the best action that can be taken from a certain state, based on the q-values from that state.
  private int chooseActionNoEpsilon(int state) {
	int bestaction = 0;
	int countthese = 1;
	
	//find the best action to take (or one of the best ones, if there's a tie)
	for (int i = 1 ; i < this.numOfActions ; i++) {
		if(this.qValue[state][i] > this.qValue[state][bestaction]) {
			bestaction = i;
			countthese = 1;
		}
		else if(this.qValue[state][i] == this.qValue[state][bestaction]) {
			countthese++;
		}
	}
	
	if(countthese == 1)
		return bestaction;
	
	//we have a tie between two possible actions we could take.  choose a random one
	
	//choose one of them
	int chosen = this.rand.nextInt(countthese);
	for (int i = 0 ; i < this.numOfActions ; i++) {
		if(this.qValue[state][i] == this.qValue[state][bestaction]) {
			if(chosen == 0)
				return i;
			chosen--;
		}
	}
	
	//execution will never get here, but in case it does, let's make it cause an error
	return -1;
  }
  
  //chooses an action for the learning agent to take.  is not deterministic
  public int chooseAction(int state) {
	 if(this.rand.nextDouble() < this.epsilon) {
		//epsilon time!
		return this.rand.nextInt(this.numOfActions);
	}
	else {
		//pick the best one
		return chooseActionNoEpsilon(state);
	}
  }

  //updates the q-values after an action was taken and a reward added
  public void updatePolicy(double reward, int action,
                           int oldState, int newState) {
    this.qValue[oldState][action] = (1 - this.rate) * this.qValue[oldState][action] + this.rate * (reward + this.discount * this.bestUtility(newState));
  }

  //returns a Policy object with the agent's current best policy
  public Policy getPolicy() {
    //copied from RandomAgent.java (mostly at least)
	int[] actions = new int[this.numOfStates];
    for (int i = 0; i < this.numOfStates; i++) {
      actions[i] = chooseActionNoEpsilon(i);
    }

    return new Policy(actions);
  }

  private static final double discount = 0.9;
  private static final double rate = 0.1;
  private static final double epsilon = 0.05;

  private int numOfStates;
  private int numOfActions;
  private double[][] qValue;
  
  private Random rand;
}
