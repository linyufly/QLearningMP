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
	double maxvalue = qValue[state][0];
	
	for(int i = 1; i < this.numOfActions; i++)
	    {
		if(qValue[state][i] > maxvalue)
		    maxvalue = qValue[state][i];	      
	    }
	return maxvalue;
    }
    
    public int chooseAction(int state) {
	if(Math.random() < epsilon){ //exploration
	    return rand.nextInt(this.numOfActions);
	}
	else {
	    double maxvalue = qValue[state][0];
	    int counter = 0;
	    int l = 0;
	    //search for the max Q
	    for(int i = 0; i < this.numOfActions; i++)
	      {
		  if(qValue[state][i] == maxvalue)
		      counter++;
		  else if(qValue[state][i] > maxvalue)
		      {
			  maxvalue = qValue[state][i];
			  l = i;
			  counter = 1;
		      }
	      }
	  //locate where it is
	  //if there is more than one optimal solution, randomly choose one
	 
	  if(counter == 1)
	      return l;
	  else{
	      int[] track = new int[counter];
	      int q = -1;
	      for(int i = 0; i < this.numOfActions; i++)
		  {
		      if(qValue[state][i] == maxvalue) {
			  q++;
			  track[q] = i;
			
		      }
		  } 
	      
	      return track[rand.nextInt(counter)];
	  }
	}
	
    }
    
    public void updatePolicy(double reward, int action,
			     int oldState, int newState) {
	
	double newqvalue = qValue[oldState][action] + rate*(reward + discount*bestUtility(newState) - qValue[oldState][action]);
	qValue[oldState][action] = newqvalue;
	
    }
    
    public Policy getPolicy() {
      int[] actions = new int[this.numOfStates];
      for(int i = 0; i < this.numOfStates; i++){
	  actions[i] = chooseAction(i);
      }
      return new Policy(actions);
    }
    
    private static final double discount = 0.9;
    private static final double rate = 0.1;
    private static final double epsilon = 0.05;
    
    private int numOfStates;
    private int numOfActions;
    private double[][] qValue;
    private Random rand;
}
