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

  public int chooseAction(int state) {
	// If this is one of the times we should
	// pick a random action then just do that
	if(this.rand.nextInt(100) < (epsilon*100)){
		return this.rand.nextInt(numOfActions) ;
	}

	// else lets be somewhat smart
	double highestQ = Double.NEGATIVE_INFINITY ;
	int highestAction = 0 ;
	ArrayList<Integer> ties = new ArrayList<Integer>() ;
	for(int i=0; i < numOfActions; i++){
		if(qValue[state][i] > highestQ){
			highestQ = qValue[state][i] ;
			highestAction = i ;
		} 
	}

	for(int i=0; i < numOfActions; i++){
		if(qValue[state][i] == highestQ){
			ties.add(i) ;
		}
	}

	if(ties.size() > 1){
		return ties.get(this.rand.nextInt(ties.size())) ;
	} else {
		return highestAction ;	
	}
  }

  public void updatePolicy(double reward, int action,
                           int oldState, int newState) {
	double maxFutureUtil = Double.NEGATIVE_INFINITY;
	for(int i=0; i < numOfActions; i++){
		if(qValue[newState][i] > maxFutureUtil) {
			maxFutureUtil = qValue[newState][i] ;
		}
	}
	
	qValue[oldState][action] = qValue[oldState][action] + rate * (reward + (discount * maxFutureUtil) - qValue[oldState][action]);
  }

  public Policy getPolicy() {
	int[] actions = new int[numOfStates] ;
	for(int i=0; i < numOfStates; i++){
		double highestQ = Double.NEGATIVE_INFINITY ;
		int actionToBeUsed = 0;
		for(int j=0; j < numOfActions; j++){
			if(qValue[i][j] > highestQ){
				highestQ = qValue[i][j] ;
				actionToBeUsed = j ;	
			}
		}
		actions[i] = actionToBeUsed ;
	}
	return new Policy(actions) ;
  }

  private static final double discount = 0.9;
  private static final double rate = 0.1;
  private static final double epsilon = 0.0;

  private Random rand ;

  private int numOfStates;
  private int numOfActions;
  private double[][] qValue;
}
