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
    int rand_action = rand.nextInt(4);
    double max = qValue[state][rand_action];

    for(int i=0; i<4; i++)
    {
        if(qValue[state][i] > max)
        {
            max = qValue[state][i];
        }
    }
    return max;
  }

  public int chooseAction(int state) {
    //get 4 rewards possible for this state, and choose the highest one
    int rand_action = rand.nextInt(4);
    double reference = qValue[state][rand_action];
    int result = rand_action;

    int rand_num = rand.nextInt(100);
    if(rand_num > epsilon*100)
    {
        //first, find the biggest one
        for(int i=0; i<4; i++)
        {
            if(qValue[state][i] > reference)
            {
                reference = qValue[state][i];
                result = i;
            }
        }
        //Then, check if there are duplicated ones
        int count = 0;
        int[] duplicated;
        duplicated = new int[4];
        for(int j=0; j<4; j++)
        {
            if(qValue[state][j] == reference)
            {
                duplicated[count] = j;
                count++;
            }
        }

        if(count > 0)
        {
            result = duplicated[rand.nextInt(count)];
        }

    }
    return result;
  }

  public void updatePolicy(double reward, int action,
                           int oldState, int newState) {
    //should be plug in formular and get result
    double old_value = qValue[oldState][action];
    qValue[oldState][action] = old_value +
        rate*(reward+discount*this.bestUtility(newState)-old_value);
    //System.out.printf("Reward is: $%,.2f%n", reward);
  }

  public Policy getPolicy() {
    int[] actions = new int[this.numOfStates];
    for(int i=0; i<numOfStates; i++)
    {
        actions[i] = this.chooseAction(i);
    }
    return new Policy(actions);
  }

  private static final double discount = 0.9;
  private static final double rate = 0.1;
  private static final double epsilon = 0.02;

  private Random rand;
  private int numOfStates;
  private int numOfActions;
  private double[][] qValue;
}

