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
	
	//initialize Q values to 0
		for(int i=0; i<numOfStates; i++)
			for(int j=0; j<numOfActions; j++)
				qValue[i][j] = 0;

  }



  private double bestUtility(int state) {

		double best = 0;											//holder for max Q
	
		for(int i=0; i<numOfActions; i++)		//Find highest Q for all actions at the state
			if(qValue[state][i] > best)
				best = qValue[state][i];

		return best;
  }

  public int chooseAction(int state) {
		
		int action = 0;							//holder for best action index
		
		for(int i = 1; i<numOfActions; i++)						//finds highest Q action for the state
		{
			if(qValue[state][i] == qValue[state][action])
			{
				if(rand.nextInt(2) == 0)									//chooses action at random is Q is equal
					action = i;
			}

			else if(qValue[state][i] > qValue[state][action])
				action = i;
		}
		
		if(rand.nextInt(100) < (int)(epsilon*100.0))						//account for random exploration
			action = rand.nextInt(numOfActions);				//choosing a random action with P = epsilon
		
		return action;
  }

  public void updatePolicy(double reward, int action,
                           int oldState, int newState) {

		//Q(s,a) <- (1-a)Q(s,a) + a(R(s) + gamma(maxQ(s',a'))
		qValue[oldState][action] =   ((1-rate) * qValue[oldState][action] )
															 + (rate * (reward + (discount*(bestUtility(newState) ) ) ) );

		
  }

  public Policy getPolicy() {
		
		int[] actions = new int[this.numOfStates];
    for (int i = 0; i < this.numOfStates; i++) {
      actions[i] = chooseAction(i);
    }

    return new Policy(actions);
  }


  private static final double discount = 0.9;
  private static final double rate = 0.1;
  private static final double epsilon = 0.0;

  private Random rand;
  private int numOfStates;
  private int numOfActions;
  private double[][] qValue;
}
