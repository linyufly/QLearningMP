/*************************
 * CS440/ECE448 MP1
 * Sean Yen (seanyen1)
 *************************
 * QLearningAgent
 *  This class implements an Agent that employs the Q-learning 
 *  algorithm for finding a solution to the given problem of 
 *  delivering products.
 */

import java.util.Random;

public class QLearningAgent implements Agent 
{
	private double[][] Q;
	private int n_states;
	private int n_actions;
	
	private double alpha = 0.1;
	private double gamma = 0.9;
	private double epsilon = 0.00;
	private Random rand;
	
	public QLearningAgent()
	{
		this.rand = new Random();
	}
	
	public void initialize(int n_states, int n_actions)
	{
		this.Q = new double[n_states][n_actions];
		this.n_states = n_states;
		this.n_actions = n_actions;
		return;
	}

	public int chooseAction(int state)
	{
		int best = best_action(state);
		double temp = this.rand.nextDouble();
		if(temp<this.epsilon)
			return this.rand.nextInt(this.n_actions);
		else
			return best;
	}
	
	public void updatePolicy(double reward, int action, int old_state, int new_state)
	{
		int new_action = best_action(new_state);
		double temp = Q[old_state][action] + 
			this.alpha*(reward+this.gamma*Q[new_state][new_action]-Q[old_state][action]);
		Q[old_state][action] = temp;
		return;
	}
	
	public Policy getPolicy()
	{
		int[] actions = new int[this.n_states];
		for(int i=0; i<this.n_states; i++)
		{
			int best = best_action(i);
			actions[i] = best;
		}
		return new Policy(actions);
	}
	
	private int best_action(int state)
	{
		int max_i = 0;				// keep track of best action
		double temp = Q[state][0];	// temporary variable for storing best Q
		// variables for keeping track of which actions have the same Q
		int[] idx = new int[this.n_actions];
		int temp2 = 1;
		int temp3;
		
		for(int i=1; i<this.n_actions; i++)
		{
			if(temp < Q[state][i])
			{
				temp = Q[state][i];
				max_i = i;
				
				temp2 = 1;
				idx[temp2-1] = i;
			}
			else if(temp == Q[state][i])
			{
				temp2++;
				idx[temp2-1] = i;
			}
		}
		if(temp2 > 1)
		{
			temp3 = this.rand.nextInt(temp2);
			return idx[temp3];
		}
		else
			return max_i;
	}
}
