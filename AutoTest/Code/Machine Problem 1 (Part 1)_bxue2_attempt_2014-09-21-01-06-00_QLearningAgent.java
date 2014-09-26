package Platform;
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

  private int bestUtility(int state) {
	  int ret = 0;
	  for(int i = 1; i < numOfActions; i++){
		  if(qValue[state][ret] < qValue[state][i]){
			  ret = i;
		  }
		  else if(qValue[state][ret] == qValue[state][i]){	//if values are same, pick one randomly
			  if(rand.nextInt(2) == 0){
				  ret = i;
			  }
		  }
	   }
	  return ret;
  }

  public int chooseAction(int state) {
	  //Epsilon-greed check
	  if(rand.nextDouble() < epsilon){
		  return rand.nextInt(numOfActions);
	  }
	  else{
		  //find largest value in newQ
		  return bestUtility(state);
	   }
  }

  public void updatePolicy(double reward, int action,
                           int oldState, int newState) {
	  double newQ = qValue[oldState][action];
	  
	  double learnedValue = discount*qValue[newState][bestUtility(newState)];
	  learnedValue += reward;
	  learnedValue -= qValue[oldState][action];
	  learnedValue = learnedValue * rate;
	  
	  newQ += learnedValue;
	  
	  qValue[oldState][action] = newQ;
  }

  public Policy getPolicy() {
	  int[] actionList = new int[numOfStates];
	  for(int i = 0; i < numOfStates; i++){
			  actionList[i] = this.bestUtility(i);
	  }
	  Policy retPolicy = new Policy(actionList);
	  return retPolicy;
  }

  private static final double discount = 0.9;
  private static final double rate = 0.1;
  private static final double epsilon = 0.0;	//0.05 originally?
  
  private Random rand; 
  private int numOfStates;
  private int numOfActions;
  private double[][] qValue;
}
