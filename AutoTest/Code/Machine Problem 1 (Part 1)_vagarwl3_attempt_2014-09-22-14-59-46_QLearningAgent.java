/* Author: Vineet Agarwal */

import java.util.Random;
import java.util.ArrayList;

public class QLearningAgent implements Agent {
  public QLearningAgent() {
    QLearningAgent.setRand(new Random());
  }

  public void initialize(int numOfStates, int numOfActions) {
    this.qValue = new double[numOfStates][numOfActions];
    this.numOfStates = numOfStates;
    this.numOfActions = numOfActions;
  }
  
  public double bestUtility(int state) {
	  double max=0;
	  for(int i=0;i<this.numOfActions;i++){
		  if (this.qValue[state][i]>max)
			  max=this.qValue[state][i];
	  }
	  return max;
  }

  public int chooseAction(int state) {
	  double newrand = QLearningAgent.getRand().nextDouble();
	  if (newrand<QLearningAgent.epsilon)
		  return QLearningAgent.getRand().nextInt(this.numOfActions);
	  else {
		  double max=0;
		  int flag=0;
		  for(int i=0;i<this.numOfActions;i++){
			  if (this.qValue[state][i]>max){
				  max=this.qValue[state][i];
				  flag=i;
			  }
			  else if (this.qValue[state][i]==max){
				  if(rand.nextBoolean())
					  flag=i;
			  }
		  }
	  return flag;	  
	  }
  }

  public void updatePolicy(double reward, int action,
                           int oldState, int newState) {
	  this.qValue[oldState][action]=this.qValue[oldState][action]+
			  QLearningAgent.rate*(reward+QLearningAgent.discount*bestUtility(newState)-
					  this.qValue[oldState][action]);
  }
  public Policy getPolicy() {
	  int actions[]=new int[this.numOfStates];
	  for(int i=0;i<this.numOfStates;i++){
		  actions[i]=chooseAction(i);
	  }
	  return new Policy(actions);
  }
  public static Random getRand() {
	return rand;
}

public static void setRand(Random rand) {
	QLearningAgent.rand = rand;
}
private static final double discount = 0.9;
  private static final double rate = 0.1;
  private static final double epsilon = 0.0;
  private static Random rand;
  private int numOfStates;
  private int numOfActions;
  private double[][] qValue;
}
