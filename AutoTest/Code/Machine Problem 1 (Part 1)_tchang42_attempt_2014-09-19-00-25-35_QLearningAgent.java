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
	for(int i=0;i<numOfStates;i++)
	{
		for(int j=0;j<numOfActions;j++)
		{
			this.qValue[i][j]=0;
		}
	}
  }

  private double bestUtility(int state) {
	int bestaction=0;
	for(int i=0;i<numOfActions;i++)
	{
		if(this.qValue[state][i]>this.qValue[state][bestaction])
		bestaction=i;
	}
	return this.qValue[state][bestaction];
  }

  public int chooseAction(int state) {
	int r = rand.nextInt(1000), r1;
	int nextAction=0;
	int i;
	int same_num=0;
	int[] same = new int[numOfActions];
	if(r<1000*epsilon)
	{
		nextAction = rand.nextInt(this.numOfActions);
	}
	else
	{
		for (i = 0; i < this.numOfActions; i++)
		{
			if(this.qValue[state][i]>=this.qValue[state][nextAction])
			nextAction = i;
			same[i]=0;
		}
		for (i = 0; i < this.numOfActions; i++)
		{
			if(this.qValue[state][i]==this.qValue[state][nextAction])
			{
				same[same_num]=i;
				same_num++;
			}
		}
		if(same_num>0)
		{
			r1=rand.nextInt(same_num);
			nextAction=same[r1];
		}
	}
	return nextAction;
  }

  public void updatePolicy(double reward, int action, int oldState, int newState) {
	this.qValue[oldState][action]= this.qValue[oldState][action]+rate*(reward+discount*bestUtility(newState)-this.qValue[oldState][action]);
	return;
  }

  public Policy getPolicy() {
	int[] actions = new int[this.numOfStates];
    for (int i = 0; i < this.numOfStates; i++) {
		for(int j=0; j < this.numOfActions;j++)
		{
			if(this.qValue[i][j]>this.qValue[i][actions[i]])
			actions[i]=j;
		}
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
