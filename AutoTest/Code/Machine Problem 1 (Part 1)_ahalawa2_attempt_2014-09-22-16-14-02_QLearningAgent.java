// Jayant Ahalawat - ahalawa2@illinois.edu
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
		int i = 0;
		double max;
		max = qValue[state][i];
		for (i = 0; i < numOfActions; i++) {
			if (qValue[state][i] > max) {
				max = qValue[state][i];
			}
		}
		return max;
	}

	public int chooseAction(int state) {
		if(rand.nextFloat()<epsilon) {
			return rand.nextInt(this.numOfActions);
		} // Running an epsilon-greedy Exploration
		
		int count, i;
		double max;
		int[] maxpos = new int[numOfActions];//value is 1 for the action which has highest utility.
		//helps in choosing a random action if more than one have highest utility.
		max = qValue[state][0];
	for (count =0; count < numOfActions; count++)
	{
		maxpos[count]=1;
	}
	for (count =0; count < numOfActions; count++)
	{
		if (qValue[state][count]>max) {
			max = qValue[state][count];
		}
	}
		for (count = 0; count < numOfActions; count++) {
			if (qValue[state][count] < max) {
				maxpos[count] = 0;
			}// to assign 0 to all actions which have lesser than maximum utility.
		}
		for(i=rand.nextInt(this.numOfActions), count=0;count<numOfActions; count++) {
			if(maxpos[i]==1) {
				return i;
			}
			i++;
			if(i==numOfActions){
				i=0;
			}
		}// choose randomly if more than one action have same values.
		return -1;
	}

	public void updatePolicy(double reward, int action, int oldState,
			int newState) {
		double old = qValue[oldState][action];
		qValue[oldState][action] = old + (rate * (reward + (discount * (bestUtility(newState))) - old));
	}

	public Policy getPolicy() {
		double max;
		int[] actions = new int[this.numOfStates];
		for (int i = 0; i < this.numOfStates; i++) {
			max = qValue[i][0];
			actions[i]=0;
			for(int j = 0; j<this.numOfActions; j++) {
				if (qValue[i][j]>max) {
					max = qValue[i][j];
					actions[i]=j;
				}
			}
		} //Select the action with maximum utility for each state.
		return new Policy(actions);

	}

	private static final double discount = 0.9;
	private static final double rate = 0.1;
	private static final double epsilon = 0.0;

	private int numOfStates;
	private int numOfActions;
	private double[][] qValue;
	private Random rand;
}
