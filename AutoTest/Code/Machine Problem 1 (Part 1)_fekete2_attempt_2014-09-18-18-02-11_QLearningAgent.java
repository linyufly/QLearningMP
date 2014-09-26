import java.util.Random;

/**
 * Created by Alec on 9/17/2014.
 */
public class QLearningAgent implements Agent {
    private static final double discount = 0.9;
    private static final double rate = 0.1;
    private static final double epsilon = 0.1;

    private int numOfStates;
    private int numOfActions;
    private double[][] qValue; //qValue[state][action]
    private int[][] attemptedMove; //[state][action] # of times action a in state s was performed
    private int[][][] resultingMove; //[initial state][action][final state]
                                     //# of times action transitions initial state to final state

    private Random rand;
    private int max, min; //used to set range of rand.nextInt

    public QLearningAgent() {
        this.rand = new Random();
    }

    public void initialize(int numOfStates, int numOfActions) {
        this.numOfStates = numOfStates;
        this.numOfActions = numOfActions;
        this.qValue = new double[numOfStates][numOfActions];
        this.attemptedMove = new int[numOfStates][numOfActions];
        this.resultingMove = new int[numOfStates][numOfActions][numOfStates];
    }

    /**
     * Finds action with highest q value for current state. If all q values are same, return random action
     * @param state Current state
     * @return action with highest associated q value or random action if all q values are same
     */
    private int findBestAction(int state) {
        int bestAction = -1;
        double largestQ = 0;
        boolean allSame = true;
        for(int i=0; i<numOfActions; i++) {
            double currQ = qValue[state][i];
            if(bestAction == -1) { //during first loop, set values
                bestAction = i;
                largestQ = currQ;
            }
            if(currQ != largestQ)
                allSame = false;
            if(currQ > largestQ) {  //replace best action if found better
                largestQ = currQ;
                bestAction = i;
            }
        }
        if(allSame) {//if none of the actions have been tested
            max = numOfActions-1;
            min = 0;
            return rand.nextInt((max - min) + 1) + min;
        }
        return bestAction;
    }

    private double findBestQ(int state) {
        double largestQ = 0;
        for(int i=0; i<numOfActions; i++) {
            double currQ = qValue[state][i];
            if(currQ > largestQ) {
                largestQ = currQ;
            }
        }
        return largestQ;
    }

    public int chooseAction(int state) {
        if(Math.random() < epsilon) { //choose random action
            max = numOfActions-1;
            min = 0;
            return rand.nextInt((max - min) + 1) + min;
        } else {
            return findBestAction(state);
        }
    }

    public void updatePolicy(double reward, int action, int oldState, int newState) {
        //update T function
        attemptedMove[oldState][action]++;
        resultingMove[oldState][action][newState]++;
        //update Q function
        qValue[oldState][action] = ((1-rate)*qValue[oldState][action]) + (rate*(reward+(discount*findBestQ(newState))));
    }

    public Policy getPolicy() {
        int[] policy = new int[numOfStates];
        for(int i=0; i<numOfStates; i++) {
            policy[i] = findBestAction(i);
        }
        return new Policy(policy);
    }
}
