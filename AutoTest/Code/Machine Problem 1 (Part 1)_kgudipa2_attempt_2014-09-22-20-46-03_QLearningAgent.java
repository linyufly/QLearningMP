/* Author: Mingcheng Chen */

import java.util.Random;
import java.util.ArrayList;

public class QLearningAgent implements Agent {
  public QLearningAgent() {
    //this.rand = new Random();
  }

  public void initialize(int numOfStates, int numOfActions) {
    this.qValue = new double[numOfStates][numOfActions];
    this.numOfStates = numOfStates;
    this.numOfActions = numOfActions;
  }

  private double bestUtility(int state) {
    double bestUtil = this.qValue[state][0];

    for(int i = 1; i < 4; i++) {
      // compare the rewards to choose best one
      if(this.qValue[state][i] > bestUtil) {
        bestUtil = this.qValue[state][i];
      }
    }

    return bestUtil;

  }

  public int chooseAction(int state) {
    // temporary array to hold best reward from every action of given state
    ArrayList<Integer> bestRew = new ArrayList<Integer>();
    double bestUtil = this.bestUtility(state);

    // use epsilon to check whether agent should learn or choose best utility
    Random rnd = new Random();
    double rnd_int = rnd.nextDouble();

    if(rnd_int < epsilon){
      return rnd.nextInt(numOfActions);
    }

    for(int j = 0; j < 4; j++) {
      if(bestUtil == this.qValue[state][j]) {
        bestRew.add(j);
      }
    }

    if(bestRew.size() == 1) {
      return (int)bestRew.get(0);
    }
    else {
      Random rand = new Random();
      int randIdx = rand.nextInt(bestRew.size());

      return (int)bestRew.get(randIdx);
    }


  }

  public void updatePolicy(double reward, int action,
                           int oldState, int newState) {
    double gamma = discount;
    double r = reward;
    double alpha = rate;
    double v = gamma + r;

    this.qValue[oldState][action] = this.qValue[oldState][action] + (alpha*(r + gamma*(this.bestUtility(newState)) - this.qValue[oldState][action]));
  }

  public Policy getPolicy() {
    // find best action per states
    int[] actions = new int[numOfStates];

    for(int i = 0; i < this.numOfStates; i++) {
      actions[i] = chooseAction(i);
    }

    Policy pol = new Policy(actions);

    return pol;
  }

  private static final double discount = 0.9;
  private static final double rate = 0.1;
  private static final double epsilon = 0.0;

  private int numOfStates;
  private int numOfActions;
  private double[][] qValue;
}
