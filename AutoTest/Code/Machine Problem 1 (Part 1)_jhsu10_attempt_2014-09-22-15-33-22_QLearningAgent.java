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
    double curr_max = 0;
    for(int i = 0; i < numOfActions; i++)
    {
        if(this.qValue[state][i] > curr_max)
            curr_max = this.qValue[state][i];
    }
    return curr_max;
  }

  public int chooseAction(int state) {
    boolean isGreedy = this.rand.nextDouble() >= this.epsilon;
    if(isGreedy)
    {
        double curr_max = 0;
        int curr_max_index = 0;
        for(int i = 0; i < numOfActions; i++)
        {
            if(this.qValue[state][i] > curr_max)
            {
                curr_max = this.qValue[state][i];
                curr_max_index = i;
            }
            // tie-breaker condition: when two equally largest values exist, add randomness.
            else if(this.qValue[state][i] == curr_max)
            {
                if(this.rand.nextBoolean())
                {
                    curr_max = this.qValue[state][i];
                    curr_max_index = i;
                }
            }
        }
        return curr_max_index;
    }
    else 
        return this.rand.nextInt(4);
  }

  public void updatePolicy(double reward, int action,
                           int oldState, int newState) {
      this.qValue[oldState][action] = this.qValue[oldState][action] + this.rate*(reward + discount*bestUtility(newState) - this.qValue[oldState][action]);
  }

  public Policy getPolicy() {
      int[] actions = new int[numOfStates];
      for(int i = 0; i < numOfStates; i++)
      {
          actions[i] = getBestAction(i);
      }
      return new Policy(actions);
  }

  private int getBestAction(int state) {
      double curr_max = 0;
      int curr_max_index = 0;
      for(int i = 0; i < numOfActions; i++)
      {
          if(this.qValue[state][i] > curr_max)
          {
              curr_max = this.qValue[state][i];
              curr_max_index = i;
          }
      }
      return curr_max_index;
  }

  private static final double discount = 0.9;
  private static final double rate = 0.1;
  private static final double epsilon = 0;

  private int numOfStates;
  private int numOfActions;
  private double[][] qValue;
  private Random rand;
}
