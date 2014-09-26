/* Author: Michael Bay */

import java.util.Random;
import java.util.ArrayList;

public class QLearningAgent implements Agent {
	private Random rand;
	
  public QLearningAgent() {
    this.rand = new Random();
  }

  public void initialize(int numOfStates, int numOfActions) {
    this.qValue = new double[numOfStates][numOfActions];
    this.numOfStates = numOfStates;
    this.numOfActions = numOfActions;
  }

  private double bestUtility(int state) {
	  	double largest_qvalue= Double.NEGATIVE_INFINITY;
	  	  double [] state_actions= qValue[state];
	  for (double cur_qvalue : state_actions){
		  if (largest_qvalue< cur_qvalue){
			  largest_qvalue=cur_qvalue;
		  }
	  }
	  return largest_qvalue;
		  
  }

  public int chooseAction(int state) {
	  double prob= Math.random();
	  if (prob >= epsilon) {
		  return bestAction(state);
	  }
	  else {
		  int random_step= rand.nextInt(this.numOfActions);
		 // System.out.println("Random step to "+random_step);
		  return random_step;
	  }
  }

  public void updatePolicy(double reward, int action,
                           int oldState, int newState) {
	  	qValue[oldState][action]= qValue[oldState][action]+ rate*(reward+discount*(bestUtility(newState) - qValue[oldState][action]) );
  }

  public Policy getPolicy() {
	  int [] actions=new int [numOfStates];
	
	 for (int i=0; i<actions.length;i++){
		  actions[i]=bestAction(i);
	  }
	  
	  return new Policy(actions);
  }
  
 public int bestAction(int state){
	 double [] stateactions= qValue[state];
	  int max_action= rand.nextInt(this.numOfActions);
	  int i;
	  //System.out.println("Number of available actions: " +stateactions.length);
	  for (i=0; i< this.numOfActions; i++){
		  if (stateactions[max_action]< stateactions[i])
		  max_action= i;
	  }
	  //System.out.println("Step to "+ max_action);
	  return max_action;	  
  }

  private static final double discount = 0.9;
  private static final double rate = 0.1;
  private static final double epsilon = 0.0;

  private int numOfStates;
  private int numOfActions;
  private double[][] qValue;
}
