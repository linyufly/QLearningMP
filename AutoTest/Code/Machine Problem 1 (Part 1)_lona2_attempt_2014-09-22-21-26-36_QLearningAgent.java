/* Author: Stephanie Lona */

import java.util.ArrayList;
import java.util.Random;

public class QLearningAgent implements Agent {
  public QLearningAgent() {
    this.rand = new Random();
  }

  public void initialize(int numOfStates, int numOfActions) {
    this.numOfActions=numOfActions;
    this.numOfStates=numOfStates;
    this.QValue=new double[numOfStates][numOfActions];
  }

  public int chooseAction(int state) {
	  //rand
	  //epsilon adds randomness
	  //with possibility of eps you choose action
	  //use q learning
	  //return;
	  if(Math.random()>1-epsilon){
		  return (int) (Math.random()*numOfActions);
	  }
	  int bestA = 0;
	  double bestQValue = QValue[state][0];
	  for(int j=1; j<numOfActions; j++)
	  {
		  //not sure how to do the random
		  double thisQValue = this.QValue[state][j];
		  if(thisQValue>bestQValue){
			  bestQValue=thisQValue;
		  }
		                                        
	  }
	  bestA=tieBreak(bestQValue,state);
	  return bestA;
  }

  public void updatePolicy(double reward, int action, int oldState, int newState) {
	  double maxQA = QValue[newState][0];
	  for(int j=1; j<numOfActions; j++)
	  {
		  double thisQValue = this.QValue[newState][j];
		  if(thisQValue>maxQA){
			  maxQA=thisQValue;
		  }                                   
	  }
	  
	  QValue[oldState][action] = QValue[oldState][action] + 
	  			rate*(reward + discount*maxQA - QValue[oldState][action]); 
  }

  public Policy getPolicy() {
    //array of actions
	  //remove random
	  //best actions
	  int[] actions = new int[numOfStates];
	  //Qold=Qold+(rate(reward+discount*est of optimal future value-Qold))
	  for(int i=0; i< numOfStates; i++){
		  double bestQAdd = QValue[i][0];
		  for(int j=1; j<numOfActions; j++){
			  double thisQ = QValue[i][j];
			  if(thisQ>bestQAdd){
				  bestQAdd = thisQ;
			  }
			  
		  }
		  actions[i]=tieBreak(bestQAdd, i);
	  }
	  return new Policy(actions);
  }
  
  public int tieBreak(double bestQ, int state){
	  ArrayList<Integer> ties = new ArrayList<Integer>();
	  for(int i=0; i<numOfActions; i++)
	  {
		  if(QValue[state][i]==bestQ)
			  ties.add(i);
	  }
	  int random = (int) (Math.random()*ties.size());
	  int toRet = ties.get(random);
	  return toRet;
  }
 

  private static final double discount = 0.9;
  private static final double rate = 0.1;
  private static final double epsilon = 0.0;

  
  private Random rand;
  private int numOfStates;
  private int numOfActions;
  private double[][] QValue;
}
