/* Author: Mariya Vasileva */

import java.util.Random;
import java.util.ArrayList;

public class QLearningAgent implements Agent {
    public QLearningAgent() {
        this.rand = new Random();
    }

    /**
     * External initializer
     * @param numOfStates - total number of states in the world
     * @param numOfActions - total number of actions per state in the world
     */
    public void initialize(int numOfStates, int numOfActions) {
        this.qValue = new double[numOfStates][numOfActions];
        this.numOfStates = numOfStates;
        this.numOfActions = numOfActions;
    }

    /**
     * Finds the maximum utility over all actions for a given state
     * @param state - state to check actions for
     * @return maximum utility over all actions for the state
     */
    private double bestUtility(int state) {
        double maxUtility = qValue[state][0];
        for(int i = 0; i < numOfActions; i++) {
            maxUtility = Math.max(maxUtility, qValue[state][i]);
        }
        return maxUtility;
    }

    /**
     * Chooses the best action given a state that maximizes the utility
     * @param state - state for which you want to get the best action
     * @return the best action for given state
     */
    public int chooseAction(int state) {
        double maxUtility = qValue[state][0];
        ArrayList<Integer> ties = new ArrayList<Integer>();

        for(int i = 0; i < numOfActions; i++) {
            double currentUtility = qValue[state][i];
            if (currentUtility > maxUtility) {
                maxUtility = currentUtility;
                ties.clear();                                   // reset ties array since the max utility changed
                ties.add(new Integer(i));
            } else if (currentUtility == maxUtility) {
                ties.add(new Integer(i));                       // accumulate indices that have the same utility
            }
        }

        double x = rand.nextDouble();                           // generate a random double to perform epsilon greedy
        if (x < epsilon) {                                      // choice
            return rand.nextInt(numOfActions);
        }

        int index = rand.nextInt(ties.size());                  // randomly choose an indice from the ties
        int bestAction = ties.get(index).intValue();

        return bestAction;
    }

    /**
     * Updates the utility matrix per iteration given its params
     * @param reward - amount earned or lost given action
     * @param action - action performed in the current iteration
     * @param oldState - initial state before action
     * @param newState - state reached on performed action
     */
    public void updatePolicy(double reward, int action,
                             int oldState, int newState) {
        qValue[oldState][action] = qValue[oldState][action] +
                rate * (reward + discount * bestUtility(newState) - qValue[oldState][action]);
    }

    /**
     * Helper method to generate best actions for all states
     * @return Policy object that contains an array of best actions
     */
    public Policy getPolicy() {
        int[] actions = new int[numOfStates];
        for (int i = 0; i < numOfStates; i++) {
            actions[i] = chooseAction(i);
        }
        return new Policy(actions);
    }

    // Relevant parameters for Part I:
    private static final double discount = 0.9;
    private static final double rate = 0.1;
    private static final double epsilon = 0;

    private int numOfStates;
    private int numOfActions;
    private double[][] qValue;

    private Random rand;
}