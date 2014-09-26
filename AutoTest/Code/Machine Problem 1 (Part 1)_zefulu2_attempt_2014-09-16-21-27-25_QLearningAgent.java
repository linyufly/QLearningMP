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

    private double bestUtility(int state) {
	double maxReward = this.qValue[state][0];
	for (int i = 0; i < numOfActions; i++){
	    if (this.qValue[state][i] > maxReward){
		maxReward = this.qValue[state][i];
	    }
	}
	return maxReward;
    }
    
    public int chooseAction(int state) {
	int[] candidate = new int[this.numOfActions];
        int action = 0;
	int count = 0;
	for (int j = 0; j < this.numOfActions; j++){
	    if (this.qValue[state][j] == this.bestUtility(state)){
		candidate[count++] = j;
	    }
	}
	
	if(this.rand.nextDouble() <= this.epsilon){
	    while(true){
		int temp = this.rand.nextInt(this.numOfActions);
		if (temp != action){
		    action = temp;
		    break;
		}
	    }
	}else{
	    action = candidate[this.rand.nextInt(count)];
	}
        return action;
    }
    
    public void updatePolicy(double reward, int action,
			     int oldState, int newState) {
	this.qValue[oldState][action] = this.rate * (reward + discount*this.bestUtility(newState)) + (1.0 - this.rate)*(this.qValue[oldState][action]);
    }
    
    public Policy getPolicy() {
	int[] actions = new int[this.numOfStates];
	for (int i = 0; i < actions.length; i++){
	    actions[i] = this.chooseAction(i);
	}
	return new Policy(actions);
    }
    
    private static final double discount = 0.9;
    private static final double rate = 0.1;
    private static final double epsilon = 0.0;
    
    private int numOfStates;
    private int numOfActions;
    private double[][] qValue;
    private Random rand;
}
