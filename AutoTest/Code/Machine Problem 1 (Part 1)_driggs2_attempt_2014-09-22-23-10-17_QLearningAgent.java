/* Author: Andrew Driggs */

import java.util.Random;

public class QLearningAgent implements Agent {
    public QLearningAgent() {
        this.rand = new Random();
    }

    public void initialize(int numOfStates, int numOfActions) {
        this.qValue = new double[numOfStates][numOfActions];
        this.numOfStates = numOfStates;
        this.numOfActions = numOfActions;
    }

    public int chooseAction(int state) {
        int chosenAction = 0;

        if (this.rand.nextDouble() < epsilon)
        {
            chosenAction = this.rand.nextInt(this.numOfActions);
        }
        else
        {
            chosenAction = findBestAction(state);
        }

        return chosenAction;
    }

    private int findBestAction(int state)
    {
        int currentBestAction = 0;
        double currentMaxQ = this.qValue[state][currentBestAction];
        for ( int i = 1; i < this.qValue[state].length; i++)
        {
            int currentAction = i;
            double currentQ = this.qValue[state][currentAction];
            if (currentQ > currentMaxQ)
            {
                currentBestAction = currentAction;
                currentMaxQ = currentQ;
            }
            else if (currentQ == currentMaxQ && this.rand.nextBoolean())
            {
                currentBestAction = currentAction;
                currentMaxQ = currentQ;
            }
        }
        return currentBestAction;
    }

    private double maxQ(int state)
    {
        double currentMaxQ = this.qValue[state][0];
        for (int i = 1; i < this.qValue[state].length; i++)
        {
            double currentQ = this.qValue[state][i];
            if (currentQ > currentMaxQ)
            {
                currentMaxQ = currentQ;
            }
        }
        return currentMaxQ;
    }

    public void updatePolicy(double reward, int action,
                             int oldState, int newState) {
        double oldQValue = this.qValue[oldState][action];
        double newMaxQ = maxQ(newState);
        this.qValue[oldState][action] = oldQValue + alpha * (reward + gamma * newMaxQ - oldQValue);
    }

    public Policy getPolicy() {
        int[] actions = new int[this.numOfStates];
        for (int i = 0; i < this.numOfStates; i++) {
            int bestAction = findBestAction(i);
            actions[i] = bestAction;
        }

        return new Policy(actions);
    }

    private static final double alpha = 0.9;
    private static final double gamma = 0.1;
    private static final double epsilon = 0.05;

    private Random rand;
    private int numOfStates;
    private int numOfActions;
    private double[][] qValue;
}
