//author Margaret Stutz

import java.util.Random;
import java.util.ArrayList;

public class QLearningAgent implements Agent {
  


	private static final double discount = 0.9;
	private static final double rate = 0.1;
	private static final double epsilon = 0.00;
	  

	private int numOfStates;
	private int numOfActions;
	private double[][] qValue;
	private Random rand;
	
  public QLearningAgent() {
    this.rand = new Random();
  }

  public void initialize(int numOfStates, int numOfActions) {
    this.qValue = new double[numOfStates][numOfActions];
    this.numOfStates = numOfStates;
    this.numOfActions = numOfActions;
  }

  public int chooseAction(int state) {
	 //returns your selected action (0,1,2,3)
	  /*given the current state, want to pick best action w probability epsilon
	  and a randomly selected action w prob 1-epsilon */
	  rand = new Random(); //random number
	  if(rand.nextFloat() <= this.epsilon){
		  //do a random action w math.random
		  //randomly choose action from 0 to 3
		  return rand.nextInt(4);//will randomly choose a number from 0-3  
	  }
	  else{//choose action the correct way
		//compare all Q values surrounding 
		 //return action that brings you to the best Q value
		  	//use bestUtility if u want
		  double bestQ = 0;
		  int bestAction=0;
		  boolean allSame;
		  for(int i=0; i< numOfActions; i++){
			  if(qValue[state][i]> bestQ){
				  bestQ = qValue[state][i];
				  bestAction = i;
			  }
			  //should be storing all Qvalues in an array and randomly choosing between them
			  else if(qValue[state][i] == bestQ){
				  //randomly choose one
				  if(rand.nextBoolean())
					  bestAction =i;
				  //otherwise will stay the same
			  }
			  
		  }//for
		  return bestAction;
		  
	  }//else
	  
  }//end chooseAction

  public void updatePolicy(double reward, int action,
                           int oldState, int newState) {
	  //processes reward receieved from executing action 
	  //updates qvalue[][]
	  double bestQ = 0;
	  for(int i=0; i< numOfActions; i++){
		  if(qValue[newState][i]> bestQ){
			  bestQ = qValue[newState][i];
		  }
		 
	  }//for
	  
	  double updatedQ = qValue[oldState][action] + this.rate*(reward + (this.discount*bestQ) - qValue[oldState][action]);
	  
	  qValue[oldState][action] = updatedQ;

  }//end updatePolicy

  public Policy getPolicy() {
	 /* returns a Policy object that has an array of actions and specifies the action for each state */
	  /*will look at the double qValue array and see what action gives the best Qvlaue for each state
	  and then puts that action into the array for that state */
	  //Policy bestPolicy = new Policy();
	  
	  double bestQ=0;
	  int bestAction=0;
	  int[] actions_arr = new int[numOfStates];
	  for(int i=0; i<numOfStates; i++){
		  int j =0;
		  for(; j<numOfActions; j++){
			  if(qValue[i][j] > bestQ){
				bestQ = qValue[i][j];
			  	bestAction = j;
			  }//if
			  else if(qValue[i][j] == bestQ){
				  //randomly choose one
				  if(rand.nextBoolean())
					  bestAction =j;
				  //otherwise will stay the same
			  }
			  
		  }//for actions
		 actions_arr[i] = bestAction;
	  }//outside for
	  
	  Policy bestPolicy = new Policy(actions_arr);
	  return bestPolicy;
	  
	  	
  }//end getPolicy

}//end QLearningAgent
