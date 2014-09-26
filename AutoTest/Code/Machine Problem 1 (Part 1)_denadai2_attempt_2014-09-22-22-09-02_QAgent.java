/* Author: Mingcheng Chen */

import java.util.Random;
import java.util.ArrayList;

public class QAgent implements Agent {

    Random rand;

    public QAgent() {
        this.rand = new Random();
    }

    public void initialize(int numOfStates, int numOfActions) {
        this.qValue = new double[numOfStates][numOfActions];
        this.numOfStates = numOfStates;
        this.numOfActions = numOfActions;
    }

    private double bestUtility(int state) {
        double best = qValue[state][rand.nextInt(this.numOfActions)];
        for (int i = 0; i < this.numOfActions; i++) {
            if(best < qValue[state][i])
                best = qValue[state][i];
        }
        return best;
    }

    public int chooseAction(int state) {
        int action = rand.nextInt(this.numOfActions);
        double best = qValue[state][action];
        for (int i = 0; i < this.numOfActions; i++) {
            if(best < qValue[state][i]) {
                best = qValue[state][i];
                action = i;
            }
        }
        return action;
    }

    public void updatePolicy(double reward, int action,
                             int oldState, int newState) {
        qValue[oldState][action] = (1.0 - rate)*qValue[oldState][action] + rate*(reward + discount*qValue[newState][chooseAction(newState)]);
    }

    public Policy getPolicy() {
        int[] actions = new int[this.numOfStates];
        for (int i = 0; i < this.numOfStates; i++) {
            if(rand.nextDouble() < epsilon){
                actions[i] = rand.nextInt(this.numOfActions);
            }
            else{
                actions[i] = chooseAction(i);
            }
        }
        return new Policy(actions);
    }

    private static final double discount = 0.9;
    private static final double rate = 0.1;
    private static final double epsilon = 0.0;

    private int numOfStates;
    private int numOfActions;
    private double[][] qValue;
}
