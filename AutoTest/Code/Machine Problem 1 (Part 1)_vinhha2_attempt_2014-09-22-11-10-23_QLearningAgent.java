/* Author: Mingcheng Chen */

import java.util.Random;
import java.util.ArrayList;

public class QLearningAgent implements Agent {
  public QLearningAgent() {
    this.rand = new Random();
  }

  public void initialize(int numOfStates, int numOfActions) {
    this.qValue = new double[numOfStates][numOfActions];
    this.numOfStates = numOfStates;
    this.numOfActions = numOfActions;
  }

  private double bestUtility(int state) {
    int k = 0;
    double l = qValue[state][0];
    for(int i = 1; i < numOfActions; i++)
    {
      if(l < qValue[state][i])
      {
        l = qValue[state][i];
        k = i;
      }
    }
    return qValue[state][k];
  }

  public int chooseAction(int state) {
      if(rand.nextDouble() <= epsilon)
        return rand.nextInt(this.numOfActions);
      else
        return bestAction(state);
  }

  public void updatePolicy(double reward, int action,
                           int oldState, int newState) {
    qValue[oldState][action] = qValue[oldState][action] + rate*(reward + discount * bestUtility(newState) - qValue[oldState][action]);
  }

  public Policy getPolicy() {
    int [] bestPolicy = new int[this.numOfStates];
    for(int i = 0; i < this.numOfStates; i++)
      bestPolicy[i] = bestAction(i);
    return(new Policy(bestPolicy));
  }

  private int bestAction(int state)
  {
    double highQ = bestUtility(state);
    int counter = 0;
    int [] bestStuff = new int[4];
    for(int i = 0; i < this.numOfActions; i++)
    {
      if(highQ == qValue[state][i])
      {
        bestStuff[counter] = i;
        counter++;
      }
    }
    int k = rand.nextInt(counter);
    return bestStuff[k];
  }

  private static final double discount = 0.9;
  private static final double rate = 0.1;
  private static final double epsilon = 0.0;

  private int numOfStates;
  private int numOfActions;
  private double[][] qValue;
  private Random rand;
}