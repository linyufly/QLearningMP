/* Author: Mingcheng Chen */

import java.util.Random;
import java.util.ArrayList;

public class QAgent implements Agent {
  public QAgent() {
    this.numOfStates = 0;
    this.numOfActions = 0;
    this.qValue = null;
  }

  public void initialize(int numOfStates, int numOfActions) {
    this.qValue = new double[numOfStates][numOfActions];
    this.numOfStates = numOfStates;
    this.numOfActions = numOfActions;
  }

  private double bestUtility(int state) {
	double maxUtil = qValue[state][0];
  	int bestAction = 0;
	for(int i = 0; i < this.numOfActions; i++){
		
		if( this.qValue[state][i] > maxUtil ){
			bestAction = i;
			maxUtil = this.qValue[state][i];
		}
	}
	return maxUtil;
  }

  public int chooseAction(int state) {
	Random rand = new Random();
	double randAction = rand.nextDouble();
	if(randAction < epsilon){
		return rand.nextInt(4);
	}
	
  	double maxUtil = qValue[state][0];
  	int bestAction = 0;
	for(int i = 1; i < this.numOfActions; i++){
		
		if( this.qValue[state][i] > maxUtil ){
			bestAction = i;
			maxUtil = this.qValue[state][i];
		}
		else if( this.qValue[state][i] == maxUtil ){
			if( rand.nextBoolean() == false){
				maxUtil = this.qValue[state][i];
				bestAction = i;
			}
		}
	}

	return bestAction;
  }

  public void updatePolicy(double reward, int action,
                           int oldState, int newState) {
    

	this.qValue[oldState][action] = this.qValue[oldState][action] + rate * ( reward + discount * this.bestUtility(newState) - this.qValue[oldState][action] );

  }

  public Policy getPolicy() {
	int[] actions = new int[this.qValue.length];
	for(int i = 0; i < this.qValue.length; i++){
		actions[i] = this.chooseAction(i);
	}
	return new Policy(actions);
  }

  private static final double discount = 0.9;
  private static final double rate = 0.1;
  private static final double epsilon = 0.00;

  private int numOfStates;
  private int numOfActions;
  private double[][] qValue;
}
