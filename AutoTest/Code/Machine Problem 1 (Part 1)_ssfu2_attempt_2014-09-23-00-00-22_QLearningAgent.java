import java.util.Random;
import java.util.ArrayList;

public class QLearningAgent implements Agent {
    public QLearningAgent() {
        this.rand = new Random();
    }

    public void initialize(int numOfStates, int numOfActions) {
        // initializes Q matrix to 0
        this.qMatrix = new double[numOfStates][numOfActions];
        this.numOfStates = numOfStates;
        this.numOfActions = numOfActions;
    }

    /*
     * helper function that returns the best action to take from given state
     */
    public int findBestAction(int state) {
        int bestAction = 0;
        for (int i = 1; i < this.numOfActions; i ++) {
            if (qMatrix[state][i] > qMatrix[state][bestAction]) {
                bestAction = i;
            } else if (qMatrix[state][i] == qMatrix[state][bestAction]) {
                // if multiple optimal options, choose one at random
                // (technically this isn't a normal distribution but I'm hoping
                // that there's only a two-way tie most of the time)
                if (rand.nextDouble() < 0.5) {
                    bestAction = i;
                }
            }
        }
        return bestAction;
    }

    /*
     * returns the best action to take from given state but takes into account
     * epsilon-greedy
     */
    public int chooseAction(int state) {
        // start with epsilon-greedy so there's a chance to pick random action
        if (rand.nextDouble() < epsilon) {
            return rand.nextInt(this.numOfActions);
        } else { // 1 - epsilon chance of picking the best action
            return findBestAction(state);
        }
    }

    /*
     * helper function that finds the maximum Q value for an action
     */
    private double findMax(int state) {
        return Math.max(qMatrix[state][0],
               Math.max(qMatrix[state][1],
               Math.max(qMatrix[state][2],
                        qMatrix[state][3])));
    }

    public void updatePolicy(double reward, int action,
                             int oldState, int newState) {
        qMatrix[oldState][action] = qMatrix[oldState][action] +
                                    rate * (reward +
                                            discount * findMax(newState) -
                                            qMatrix[oldState][action]);
    }

    public Policy getPolicy() {
        int[] actions = new int[this.numOfStates];
        for (int i = 0; i < this.numOfStates; i++) {
            actions[i] = findBestAction(i);
        }
        return new Policy(actions);
    }

    private static final double discount = 0.9; // gamma
    private static final double rate = 0.1; // alpha
    private static final double epsilon = 0.0; // select random epsilon and select best 1-epsilon

    private int numOfStates;
    private int numOfActions;
    private double[][] qMatrix;
    private Random rand;
}