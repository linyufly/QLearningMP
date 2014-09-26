/* Author: Mingcheng Chen */

/*
 * if rand[0,1] < epsilon , be greedy choose best state
 * else choose a random state
 * For each s, a, initialize table entry Q(s,a) <- 0
 Observe current state s
 Do forever:
 Select an action a and execute it
 Receive immediate reward r
 Observe the new state s'
 Update the table entry for Q(s, a) as follows:
 Q (s, a) = Q(s, a) + α [ r + γ max Q (s', a') - Q (s, a)] 
 s <- s'
 */

import java.util.Random;
import java.util.ArrayList;
import java.lang.Math;
import java.util.List;
import java.security.SecureRandom;

public class QLearningAgent implements Agent {
	
	private static final double discount = 0.9;
	private static final double rate = 0.1;
	private static final double epsilon = 0.0;

	private SecureRandom rand;
	private int numOfStates;
	private int numOfActions;
	private double[][] qValue;
	
	
	public QLearningAgent() {
		this.rand = new SecureRandom();
	}

	public void initialize(int numOfStates, int numOfActions) {

		this.qValue = new double[numOfStates][numOfActions];
		this.numOfStates = numOfStates;
		this.numOfActions = numOfActions;

	}

	private double bestUtility(int state) {
		return 0;
	}

	public int chooseAction(int state) {
		double random_float = rand.nextDouble();
		double Q_Value = Integer.MIN_VALUE;
		if (random_float > epsilon) {// choose greedy
			int action = 0;
			List<Integer> counter = new ArrayList<Integer>();
			for (int j = 0; j < this.numOfActions; j++) {
				if (Q_Value< this.qValue[state][j])
					{
					action = j;
					Q_Value = this.qValue[state][j];
					}
					
			}

			for (int i = 0; i < this.numOfActions; i++) {
				if (Q_Value == this.qValue[state][i])
					counter.add(i);
			}
			// choose a random one from the actions which have the same Q value
			if (counter.size() > 0)
				return counter.get(rand.nextInt(counter.size()));
			else
				return action;

		} else {
			// choose a random action
			int random_action = this.rand.nextInt(this.numOfActions);
			return random_action;
		}

	}

	public void updatePolicy(double reward, int action, int oldState,
			int newState) {
		/*
		 * Q(s,t)(updated value) = Q(s,t)(old value) + alpha(Reward(t+1) +
		 * discountFactor(Max(future Q values ) )
		 */

		int newAction = Integer.MIN_VALUE;
		double Q_Value = Integer.MIN_VALUE;
		for (int j = 0; j < this.numOfActions; j++) {
			if (Q_Value < this.qValue[newState][j])
				
				{
					Q_Value = this.qValue[newState][j];
					newAction = j;
				}
				
		}
		this.qValue[oldState][action] = (this.qValue[oldState][action] * (1 - rate))
				+ rate * (reward + (discount * (Q_Value)));

	}

	public Policy getPolicy() {
		int [] actions = new int[this.numOfStates];
	     for(int i = 0; i < this.numOfStates; i++) {
	         double maxQ = Integer.MIN_VALUE;
	         int idx = 0;
	         for(int k = 0; k < this.numOfActions; k++) {
	             if(this.qValue[i][k] > maxQ) {
	                   maxQ = this.qValue[i][k];
	                   idx = k;
	             }
	         }
	         actions[i] = idx;
	     }
	    Policy p = new Policy(actions);
		return p;
	}

	
}
