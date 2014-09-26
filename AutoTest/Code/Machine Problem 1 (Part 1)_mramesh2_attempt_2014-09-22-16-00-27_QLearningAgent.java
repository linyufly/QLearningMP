/* Author: Manav Ramesh */

import java.util.Random;
import java.util.ArrayList;
import java.lang.Math;
public class QLearningAgent implements Agent {
  public QLearningAgent() 
  {
     this.rand = new Random();
  }

  public void initialize(int numOfStates, int numOfActions) 
	{
    this.qValue = new double[numOfStates][numOfActions];
    this.numOfStates = numOfStates;
    this.numOfActions = numOfActions;
	
	for(int i=0; i<numOfStates; i++)
		for(int j=0; j<numOfActions; j++)
			qValue[i][j] = 0;
  }

  private double bestUtility(int state) 
	{
		double best = 0;											
		for(int i=0; i<numOfActions; i++)	
			{
				if(qValue[state][i] > best)
				{
					best = qValue[state][i];
				}
			}
			return best;
		
  }

  public int chooseAction(int state) 
	{ 
		Random randomGenerator = new Random();
		double rng = Math.random();
		if(rng<epsilon)
		{
			 int randomInt = randomGenerator.nextInt(4);
			 return randomInt;
		}
		else
		{
			int a=0;
			for(int i = 1; i<numOfActions; i++)						
				{
					double r = Math.random();
					if(qValue[state][i] == qValue[state][a])
					{
						if(r>.5)
							{
								a = i;
							}									
					}

					else if(qValue[state][i] > qValue[state][a])
					{
						a = i;
					}
						
				}
				return a;
		}
  }

  public void updatePolicy(double reward, int action,
                           int oldState, int newState) 
  {
	  qValue[oldState][action] =  ((1-lrate) * qValue[oldState][action] ) + (lrate * (reward + (discount*(bestUtility(newState)))));
  }

  public Policy getPolicy() 
  {
      int[] actions = new int[this.numOfStates];
      for (int i = 0; i < this.numOfStates; i++) 
	  {
		  int a=0;
		  	for(int j = 1; j<numOfActions; j++)						
		  		{
		  			if(qValue[i][j] > qValue[i][a])
		  			{
		  				a = j;
		  			}
				}
		  				
        actions[i] = a;
      }

      return new Policy(actions);
  }

  private static final double discount = 0.9;
  private static final double lrate = 0.1;
  private static final double epsilon = 0.0;	
  
  private Random rand;
  private int numOfStates;
  private int numOfActions;
  private double[][] qValue;
}
