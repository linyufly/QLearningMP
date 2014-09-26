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

  /*private double bestUtility(int state) {

  }*/

  public int chooseAction(int state) {
		if(this.rand.nextDouble()<epsilon) return this.rand.nextInt(numOfActions);
		
		int action;
		int[] bestActions=new int[this.numOfActions];
		double maxQ=this.qValue[state][0];
		int numMax=1;
		
		bestActions[0]=0;
		
		for(action=1;action<numOfActions;action++){
			if(this.qValue[state][action]==maxQ){
				bestActions[numMax]=action;
				numMax++;
			}
			else if(this.qValue[state][action]>maxQ){
				bestActions[0]=action;
				numMax=1;
				maxQ=this.qValue[state][action];
			}
		}
		
		return bestActions[this.rand.nextInt(numMax)];
  }

  public void updatePolicy(double reward, int action,
                           int oldState, int newState) {
		this.qValue[oldState][action]=this.qValue[oldState][action]+rate*(reward+discount*this.qValue[newState][chooseAction(newState)]-this.qValue[oldState][action]);
  }

  public Policy getPolicy() {
  	int state;
  	int [] actions=new int[this.numOfStates];
  	double maxQ;
  	int numMax;
  	int curaction;
  	int[] bestActions=new int[this.numOfActions];
		
		for(state=0;state<numOfStates;state++){
			maxQ=this.qValue[state][0];
			numMax=1;
		
			bestActions[0]=0;
		
			for(curaction=1;curaction<numOfActions;curaction++){
				if(this.qValue[state][curaction]==maxQ){
					bestActions[numMax]=curaction;
					numMax++;
				}
				else if(this.qValue[state][curaction]>maxQ){
					bestActions[0]=curaction;
					numMax=1;
					maxQ=this.qValue[state][curaction];
				}
			}
			actions[state]=bestActions[this.rand.nextInt(numMax)];
		}

		Policy p=new Policy(actions);
		
		return p;
  }

  private static final double discount = 0.9;
  private static final double rate = 0.1;
  private static final double epsilon = 0.05;

	private Random rand;
  private int numOfStates;
  private int numOfActions;
  private double[][] qValue;
}
