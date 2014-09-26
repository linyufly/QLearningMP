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
    double max = qValue[state][0];
    for(int i=0;i<numOfActions;i++){
      if(qValue[state][i]>max){
        max = qValue[state][i];
      }
    }
	  return max;
  }

  public int chooseAction(int state) {
    double ething = rand.nextDouble();
    if(epsilon>ething){
      return rand.nextInt(numOfActions);
    }
    double max = qValue[state][0];
    int item = 0;
    for(int i=0;i<numOfActions;i++){
      if(qValue[state][i]>max){
        max = qValue[state][i];
        item = i;
      }
      else if(qValue[state][i]==max){
        if(rand.nextBoolean())
          item = i;
      }
    }
	  return item;
  }

  public void updatePolicy(double reward, int action, int oldState, int newState) {
    qValue[oldState][action] = qValue[oldState][action]+rate*(reward+discount*bestUtility(newState)-qValue[oldState][action]);
  }

  public Policy getPolicy() {
	  int [] cake = new int[this.numOfStates];
    for(int i=0;i<numOfStates;i++)
      cake[i] = chooseAction(i);
	  return new Policy(cake);
  }

  private static final double discount = 0.9;
  private static final double rate = 0.1;
  private static final double epsilon = 0.00;

  private Random rand;
  private int numOfStates;
  private int numOfActions;
  private double[][] qValue;
}
