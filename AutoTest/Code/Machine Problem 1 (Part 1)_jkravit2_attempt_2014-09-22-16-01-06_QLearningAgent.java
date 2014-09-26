/* Author: Jordan Kravitz */

import java.util.Random;

public class QLearningAgent implements Agent {
	private Random rand;
	private static final double discount = 0.9;
	private static final double rate = 0.10;
	private static final double epsilon = 0.00;
	private int numOfStates;
	private int numOfActions;
	private double[][] qValue;

	public QLearningAgent() {
		this.rand = new Random();
	}

	public void initialize(int numOfStates, int numOfActions) {
		this.qValue = new double[numOfStates][numOfActions];
		this.numOfStates = numOfStates;
		this.numOfActions = numOfActions;
	}

	private double bestUtility(int state) {
		double curBest=qValue[state][0];
		for(int i=1; i<numOfActions;i++){
			if(qValue[state][i]>curBest){
				curBest=qValue[state][i];
			}
		}		
		return curBest;
	}

	public int chooseAction(int state) {
		if(epsilon >rand.nextFloat() ){
			return rand.nextInt(this.numOfActions);
		}
		else
		{	
			double curBest=qValue[state][0];
			int bestAction=0;
			for(int i=1; i<numOfActions;i++){
				if(qValue[state][i]>curBest){
					curBest=qValue[state][i];
					bestAction=i;
				}
				else if(qValue[state][i]==curBest && rand.nextFloat()>=.5){
					//arbitrarily choose action in a tie
					curBest=qValue[state][i];
					bestAction=i;
				}
			}
			return bestAction;
		}
	}

	public void updatePolicy(double reward, int action, int oldState, int newState) {
		//Q(state, action) = Q(state, action) + alpha(R(state, action) + Gamma * Max[Q(next state, all actions)])
		//alpha= learning rate, discount is gamma
		qValue[oldState][action] += rate*(reward + (discount*bestUtility(newState))-qValue[oldState][action]);
		//whats the difference between a policy and qvalue??
	}

	public Policy getPolicy() {
		int[] actions = new int[this.numOfStates];
		int bestAction;
	    for (int i = 0; i < this.numOfStates; i++) {
	    	double curBest=qValue[i][0];
			bestAction=0;
			for(int j=1; j<numOfActions;j++){
				if(qValue[i][j]>curBest){
					curBest=qValue[i][j];
					bestAction=j;
				}
			}
	      actions[i] = bestAction;
	    }

	    return new Policy(actions);
	}


}
