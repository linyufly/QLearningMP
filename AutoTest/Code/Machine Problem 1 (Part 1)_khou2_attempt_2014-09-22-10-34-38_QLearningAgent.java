/* Author: Kevin Hou */

/* Discount factor = 0.9
 * Learning rate = 0.1
 * e = 0.0 
 */

//Run with: java Simulator WorldWithThief QLearningAgent y 1000 5000 policy.txt episodes.txt

import java.util.Random;

public class QLearningAgent implements Agent {
  public QLearningAgent() {
    this.rand = new Random();
  }

  public void initialize(int numOfStates, int numOfActions) {
    this.numOfStates = numOfStates;
    //Set number of actions to 4 always
    this.numOfActions = 4;
    this.map = new double[numOfStates][numOfActions];
  }

  public int chooseAction(int state) {
	//If rewards are all the same
	if(map[state][0] == map[state][1] && map[state][0] == map[state][2] && map[state][0] == map[state][3])
		return rand.nextInt(this.numOfActions);
	
	//Else, choose best reward
	double max = map[state][0];
	int direction = 0;
	
	if (map[state][1] > max){
		max = map[state][1];
		direction = 1;
	}
	else if (map[state][1] == max){
		if (rand.nextInt(2) == 0){
			max = map[state][1];
			direction = 1;
		}
	}
	if (map[state][2] > max){
		max = map[state][2];
		direction = 2;
	}
	else if (map[state][2] == max){
		if (rand.nextInt(2) == 0){
			max = map[state][2];
			direction = 2;
		}
	}
	if (map[state][3] > max){
		max = map[state][3];
		direction = 3;
	}
	else if (map[state][3] == max){
		if (rand.nextInt(2) == 0){
			max = map[state][3];
			direction = 3;
		}
	}
	
	return direction;
  }

  public void updatePolicy(double reward, int action, int oldState, int newState) {
	  
	double max = map[newState][0];
	if (map[newState][1] > max)
		max = map[newState][1];
	if (map[newState][2] > max)
		max = map[newState][2];
	if (map[newState][3] > max)
		max = map[newState][3];

    map[oldState][action] = map[oldState][action] + rate*(reward + discount*max - map[oldState][action]);
    return;
  }

  public Policy getPolicy() {
    int[] actions = new int[this.numOfStates];
    for (int i = 0; i < this.numOfStates; i++) {
    	double max = map[i][0];
    	int direction = 0;
    	if (map[i][1] > max){
    		max = map[i][1];
    		direction = 1;
    	}
    	if (map[i][2] > max){
    		max = map[i][2];
    		direction = 2;
    	}
    	if (map[i][3] > max){
    		max = map[i][3];
    		direction = 3;
    	}
      actions[i] = direction;
    }

    return new Policy(actions);
  }
  

  private static final double discount = 0.9; //gamma
  private static final double rate = 0.1; //alpha
  private static final double epsilon = 0.0;  
  
  private Random rand;
  private int numOfStates;
  private int numOfActions;
  private double map[][];
}
