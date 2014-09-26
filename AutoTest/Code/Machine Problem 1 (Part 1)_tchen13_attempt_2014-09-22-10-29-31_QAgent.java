/* Author: Mingcheng Chen */

import java.util.Random;
import java.util.ArrayList;

public class QAgent implements Agent {
    public QAgent() {
	this.rand = new Random();
    }

    public void initialize(int numOfStates, int numOfActions) {
	this.qValue = new double[numOfStates][numOfActions];
	this.numOfStates = numOfStates;
	this.numOfActions = numOfActions;
    }

    private double bestUtility(int state) {
	double max=qValue[state][0];
	for (int i=0;i<numOfActions;i++)
	    if (max<qValue[state][i]){
		max=qValue[state][i];
	    }
	return max;
    }

    private int bestChoice(int state){
	int best=rand.nextInt(numOfActions);
   	double max=qValue[state][best];
	for (int i=0;i<numOfActions;i++)
	    if (max<qValue[state][i]){
		max=qValue[state][i];
		best=i;
	    }
	return best;
    }

    public int chooseAction(int state) {
	if (rand.nextDouble()>epsilon)
	    return bestChoice(state);
	else
	    return rand.nextInt(numOfActions);
    }

    public void updatePolicy(double reward, int action,
			     int oldState, int newState) {
	qValue[oldState][action]=qValue[oldState][action]+rate*(reward+discount*bestUtility(newState)-qValue[oldState][action]);
    }

    public Policy getPolicy() {
	actions=new int[numOfStates];
	for (int i=0;i<numOfStates;i++)
	    actions[i]=bestChoice(i);
	Policy q_policy=new Policy(actions);
	return q_policy;
    }

    private static final double discount = 0.9;
    private static final double rate = 0.1;
    private static final double epsilon = 0.0;
    private int[] actions;
    private int position_actions;
    private int num_actions;
    private int numOfStates;
    private int numOfActions;
    private double[][] qValue;
    private Random rand;
}
