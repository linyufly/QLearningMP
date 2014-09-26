/* Author: Mingcheng Chen */

import java.util.Random;
import java.util.ArrayList;
import java.lang.*;

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
    double best_utility = 0.0;
    for (int j = 0; j < numOfActions; ++j) {
      if(qValue[state][j] > best_utility) {
        best_utility = qValue[state][j];
      }
    }

    return best_utility;
  }

  public int chooseAction(int state) {
    //choose the action with the best utility
    ArrayList<Integer> possActions = new ArrayList<Integer>();

    double rand_double = rand.nextDouble();
    if (rand_double < epsilon) {
      return rand.nextInt(numOfActions);
    }

    double best_utility = this.bestUtility(state);

    for (int i = 0; i < numOfActions; ++i) {
      if (Double.compare(qValue[state][i],best_utility) == 0) {
        possActions.add(i);
      }
    }
    if (possActions.size() < 1 ) {
        int randaction = rand.nextInt(4);
        possActions.add(randaction);
    }
    int randomint = rand.nextInt(possActions.size());
    return possActions.get(randomint);
  }

  public void updatePolicy(double reward, int action,
                           int oldState, int newState) {
    qValue[oldState][action] = qValue[oldState][action] + rate * (reward + discount * this.bestUtility(newState) - qValue[oldState][action]);
  }

 public Policy getPolicy() {
    int [] actions = new int[numOfStates];
    for (int i = 0; i < numOfStates; ++i) {
      actions[i] = this.chooseAction(i);
    }

    return new Policy(actions);
  }

  private static final double discount = 0.9;
  private static final double rate = 0.1;
  private static final double epsilon = 0.00;

  private Random rand;
  private int numOfStates;
  private int numOfActions;
  private double[][] qValue;
}
