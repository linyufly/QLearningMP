/* Author: Mingcheng Chen */

/* chooseAction, updatePolicy and getPolicy methods By Amirhossein Aleyasen (aleyase2) */


import java.util.Random;

public class QAgent implements Agent {

    public QAgent() {
    }

    @Override
    public void initialize(int numOfStates, int numOfActions) {
        rand = new Random();
        this.qValue = new double[numOfStates][numOfActions];
        this.numOfStates = numOfStates;
        this.numOfActions = numOfActions;
    }

    @Override
    public int chooseAction(int state) {
        if (rand.nextDouble() <= epsilon) {
            return rand.nextInt(4);
        } else {
            int greedy_action = rand.nextInt(4); // for preventing stuck when there are multiple states with max reward.
            double greedy_val = qValue[state][greedy_action];
            for (int i = 0; i < numOfActions; i++) {
                if (qValue[state][i] > greedy_val) {
                    greedy_val = qValue[state][i];
                    greedy_action = i;
                }
            }
            return greedy_action;
        }
    }

    @Override
    public void updatePolicy(double reward, int action,
            int oldState, int newState) {
        double max_return = qValue[newState][rand.nextInt(4)];
        //findling maximum reward on next step
        for (int i = 0; i < numOfActions; i++) {
            if (qValue[newState][i] > max_return) {
                max_return = qValue[newState][i];
            }
        }
        qValue[oldState][action] = (1 - rate) * qValue[oldState][action] + rate * (reward + discount * max_return);
    }

    @Override
    public Policy getPolicy() {
        int[] actions = new int[this.numOfStates];
        for (int i = 0; i < this.numOfStates; i++) {
            int max_action = rand.nextInt(4);  // for preventing stuck when there are multiple best states.
            double max = qValue[i][max_action];
            for (int j = 0; j < numOfActions; j++) {
                if (max < qValue[i][j]) {
                    max = qValue[i][j];
                    max_action = j;
                }
            }
            actions[i] = max_action;
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
