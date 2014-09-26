/**
 * @file QLearningAgent.java
 *
 * @brief A Q learning agent that implements the QAgent interface. It should run the \epsilon-greedy Q-Learning algorithm
 * 
 * @author tfliu2
 */

/// CMC ///

import java.lang.Math;
import java.util.ArrayList;

public class QLearningAgent implements Agent {
    // Variables
    /**
     * @brief The number of states in the world.
     *
     */
    private int _states_number;

    /**
     * @brief The number of actions in the world.
     *
     */
    private int _actions_number;

    /**
     * @brief The table for storing the q values of all the stuff.
     *
     */
    private double[][] _qtable;

    /**
     * @brief The epsilon value that determines liklihood of exploration.
     *
     */
    private double _epsilon;

    /**
     * @brief The Policy.
     *
     */
    private int[] _policy;

     /**
     * @brief How much we want to learn.
     *
     */
    private double _alpha;

    /**
     * @brief The discount factor.
     *
     */
    private double _gamma;

    
    //Methods
    /**
     * @brief Construct QLearningAgent
     *
     */
    public QLearningAgent()
    {
	_epsilon = 0.0;
	_alpha = 0.1;
	_gamma = 0.9;
    }

    /**
     * @brief Initialize the agent only once, telling it the number of states and actions in the world.
     * @param numOfStates Number of states in the world.
     * @param numOfActions Number of actions in the world.
     */
    public void initialize (int numOfStates, int numOfActions) {
	_qtable = new double[numOfStates][numOfActions];
	_policy = new int[numOfStates];
	for (int ii = 0; ii < _policy.length; ii++) {
	    _policy[ii] = (int)(Math.random() * 4);
	}
	_states_number = numOfStates;
	_actions_number = numOfActions;
    }

    /**
     * @brief Return the selected action, an integer between 0 and 3.
     * 
     * @param state An integer that represents the current state.
     * @return An int that represents the selected action.
     */
    public int chooseAction (int state) {
	// Let's just follow the policy thing here.
	// System.out.println(_policy[state]);
	if (Math.random() > _epsilon){
	    return _policy[state];
	}
	return (int)(Math.random() * 4);
    }

    /**
     * @brief Process the reward received.
     *
     * @param reward The reward received.
     * @param action The action to execute.
     * @param oldState The state before the action.
     * @param newState The state after the action.
     */
    public void updatePolicy (double reward, int action, int oldState, int newState) {
	_qtable[oldState][action] = _qtable[oldState][action] + _alpha * (reward + (_gamma * array_max(_qtable[newState])) - _qtable[oldState][action]);
	
	// System.out.println(_qtable[oldState][action]);
	// printGrid(_qtable);
	
	for (int ii = 0; ii < _policy.length; ii++) {
	    double max = array_max(_qtable[ii]);
	    ArrayList<Integer> choices = new ArrayList<Integer>();
	    for (int jj = 0; jj < _qtable[ii].length; jj++) {
		if (_qtable[ii][jj] == max) {
		    choices.add(jj);
		}
	    }
	    _policy[ii] = (int) choices.get( (int)(Math.random() * choices.size()) );
	}
    }

    /**
     * @brief Return a policy specifying the action for each state.
     * 
     * @return A policy object specifying the action for each state.
     */
    public Policy getPolicy () {
	// Go through every row for the state and pick the action that has the most utility?
	return new Policy(_policy);
    }

    private double array_max(double[] arr) {
	double max = -Double.MAX_VALUE;
	
	for (int ii = 0; ii < arr.length; ii++) {
	    if (arr[ii] > max) {
		max = arr[ii];
	    }
	}
	return max;
    }
    public void printGrid(double[][] arr)
    {
	for(int i = 0; i < arr.length; i++)
	    {
		for(int j = 0; j < arr[0].length; j++)
		    {
			System.out.printf("%5f ", arr[i][j]);
		    }
		System.out.println();
	    }
    }
}
