/* Author: rkelch2 */

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

 private int bestAction(int state)
  {
      ArrayList<Integer> bestActions = new ArrayList<Integer>();
      bestActions.add(0);
      for (int i=1;i<numOfActions;i++)
      {
          if(qValue[state][i] > qValue[state][bestActions.get(0)])
          {
              bestActions.clear();
              bestActions.add(i);
          }
          else if(qValue[state][i] == qValue[state][bestActions.get(0)])
          {
              bestActions.add(i);
          }
      }
      int randIndex = rand.nextInt(bestActions.size());
      return bestActions.get(randIndex);
  }

  public int chooseAction(int state) {

      int bestAction = bestAction(state);

     double greedyChance = rand.nextDouble();
      if(greedyChance < epsilon)
      {
          //choose random action
          int randomAction = rand.nextInt(numOfActions);
          return randomAction;
      }
      else
      {
          return bestAction;
      }
  }
    
  

  public void updatePolicy(double reward, int action,
                           int oldState, int newState) {
      if(reward != 0)
      {
          System.out.print("");
      }
      double maxQ = qValue[newState][bestAction(newState)];
      double newQValue = qValue[oldState][action] + rate*(reward + discount*maxQ  - qValue[oldState][action]);
      qValue[oldState][action] = newQValue;
  }

  public Policy getPolicy() {
      int actions[] = new int[numOfStates];
      for(int state=0;state<numOfStates;state++)
      {
          actions[state] = bestAction(state);
      }
      return new Policy(actions);
  }

  Random rand;

  private static final double discount = 0.9;
  private static final double rate = 0.1;
  private static final double epsilon = 0.05;

  private int numOfStates;
  private int numOfActions;
  private double[][] qValue;
}
