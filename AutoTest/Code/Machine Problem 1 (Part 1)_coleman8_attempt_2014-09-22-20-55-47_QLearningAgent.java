/* Author: Mingcheng Chen */

import com.sun.jndi.url.corbaname.corbanameURLContextFactory;

import java.util.Random;
import java.util.Scanner;

import static java.lang.Math.pow;

public class QLearningAgent implements Agent {
  public QLearningAgent() {
    this.rand = new Random();
  }

  public void initialize(int numOfStates, int numOfActions) {
    this.Q = new double[numOfStates][numOfActions]; //expected utility of performing an action at a state
    this.numOfStates = numOfStates;
    this.numOfActions = numOfActions;
  }

  /* return the index of the maximum double in the array*/
  public int findMax(double [] action_rewards){
      double max = -10000000000.0;
      int max_index = 0;
      int [] tie_breaker_queue = new int [action_rewards.length];
      int ties = 0;
      for(int i = 0; i < action_rewards.length; i++)
      {
          if(action_rewards[i] > max)
          {
              max_index = i;
              max = action_rewards[i];
              ties = 0;
          }
          else if (action_rewards[i] == max)
          {
              //add to tie breaking queue
              ties++;
              tie_breaker_queue[ties - 1] = i;
          }
      }
      if(ties > 0)
      {
          int tie_breaker = rand.nextInt(ties);
          return tie_breaker_queue[tie_breaker];
      }

      return max_index;
  }

  public int chooseAction(int state) {
    double epsilon = 0.05; //probability of randomly exploring
    int nextAction;

    //we want to be greedy with probability 1 - epsilon
    boolean beGreedy = rand.nextDouble() > epsilon;

    if(!beGreedy)
    {
        nextAction = rand.nextInt(this.numOfActions);
        return nextAction;
    }
    else
    {
        nextAction =  findMax(Q[state]);
        return nextAction;
    }
  }

  public void updatePolicy(double reward, int action, int oldState, int newState) {
      double Gamma = 0.9;
      double alpha = 0.1;
      Q[oldState][action] =  Q[oldState][action] +  alpha * (reward + (Gamma * Q[newState][findMax(Q[newState])] - Q[oldState][action]));

      return;
  }

  public Policy getPolicy() {
    int[] actions = new int[this.numOfStates];
    for (int i = 0; i < this.numOfStates; i++) {
      actions[i] = findMax(Q[i]);
    }

    return new Policy(actions);
  }

  private Random rand;
  private int numOfStates;
  private int numOfActions;
  private double[][] Q; //we'll need this later to compute the highest expected utility
  private int[] stateVisits;
  private double[] stateTotalDiscountedRewards;
  private int [][][] transitionToStateCount;
  private int [][] transitionTotalCount;
}
