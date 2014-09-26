
/* Author: Mingcheng Chen */

import java.util.Random;
import java.util.ArrayList;

public class QLearningAgent implements Agent {
  public QLearningAgent() {

  }

  public void initialize(int numOfStates, int numOfActions) {
    this.qValue = new double[numOfStates][numOfActions];
    this.numOfStates = numOfStates;
    this.numOfActions = numOfActions;

    for(int i=0, ii=this.qValue.length; i<ii; ++i) {
      for(int j=0, jj=this.qValue[i].length; j<jj; ++j) {
        this.qValue[i][j] = 1000;
      }
    }
  }

  private int chooseBestAction(int state) {
    double maxVal = -Double.MAX_VALUE;
    int bestAction = -1;

    for(int i=0, ii=this.qValue[state].length; i<ii; ++i) {
      if(this.qValue[state][i] > maxVal) {
        maxVal = qValue[state][i];
        bestAction = i;
      }
    }

    return bestAction;
  }

  public int chooseAction(int state) {
    double randomValue = new Random().nextDouble();

    if(randomValue > 1.0 - this.epsilon) {
      return this.chooseActionGreedily(state);
    }
    else {
      return this.chooseActionRandomly();
    }
  }

  private int chooseActionGreedily(int state) {
    double maxVal = -Double.MAX_VALUE;
    int[] maxActions = new int[this.numOfStates];
    int ties = 0;

    for(int i=0, ii=this.qValue[state].length; i<ii; ++i) {
      if(this.qValue[state][i] > maxVal) {
        maxVal = qValue[state][i];
        maxActions[0] = i;
        ties = 0;
      }
      else if(this.qValue[state][i] == maxVal) {
        ties = ties + 1;
        maxActions[ties] = i;
      }
    }

    if(ties == 0) {
      return maxActions[0];
    }
    else {
      return maxActions[new Random().nextInt(ties + 1)];
    }
  }

  private int chooseActionRandomly() {
    return new Random().nextInt(this.numOfActions);
  }

  private double bestUtility(int state) {
    double maxVal = -Double.MAX_VALUE;

    for(int i=0, ii=this.qValue[state].length; i<ii; ++i) {
      if(this.qValue[state][i] > maxVal) {
        maxVal = qValue[state][i];
      }
    }

    return maxVal;
  }

  public void updatePolicy(double reward, int action,
                           int oldState, int newState) {

    qValue[oldState][action] =
      qValue[oldState][action] +
      this.rate *
      (reward + this.discount * this.bestUtility(newState) - qValue[oldState][action]);
  }

  public Policy getPolicy() {
    int[] actions = new int[this.numOfStates];

    // System.out.print("Policy: ");

    for(int i=0, ii=actions.length; i<ii; ++i) {
      actions[i] = chooseBestAction(i);
      // System.out.print(actions[i]);
    }

    // System.out.println("");

    return new Policy(actions);
  }

  private static final double discount = 0.9;
  private static final double rate = 0.1;
  private static final double epsilon = 0; // 0.2 worked well

  private int numOfStates;
  private int numOfActions;
  private double[][] qValue;
}
