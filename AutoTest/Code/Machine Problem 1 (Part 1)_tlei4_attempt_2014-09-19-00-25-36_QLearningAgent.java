/* Author: Mingcheng Chen */

import java.util.Random;
import java.util.ArrayList;

public class QLearningAgent implements Agent {
  public QLearningAgent() {
    this.rand = new Random();
  }

  public void initialize(int numOfStates, int numOfActions) {
    this.qValue = new double[numOfStates][numOfActions];
    int i,j;
    for(i=0;i<numOfStates;i++)
    {
        for(j=0;j<numOfActions;j++)
        {
            this.qValue[i][j]=0;
        }
    }
    this.numOfStates = numOfStates;
    this.numOfActions = numOfActions;
  }

  private double bestUtility(int state){
    int i;
    double max_qValue=this.qValue[state][0];
    for(i=0;i<numOfActions;i++)
        if(this.qValue[state][i]>max_qValue) 
            max_qValue=this.qValue[state][i];
    return max_qValue;
  }
  
  public int BestWay(int state){
    int i;
    double max_qValue=this.qValue[state][0];
    int best_action=0;
    for(i=0;i<numOfActions;i++)
    {
        if(this.qValue[state][i]>max_qValue)
        {        
            max_qValue=this.qValue[state][i];
            best_action=i;
        }
    }
    int count=0;
    for(i=0;i<numOfActions;i++)
        if(this.qValue[state][i]==max_qValue) count++;
    if(count==1) return best_action;
    else
    {
        while(true)
        {
            int r=rand.nextInt(numOfActions);
            if(this.qValue[state][r]==max_qValue) return r ;
        }
    }
  }

  public int chooseAction(int state) {
    int ran_num=rand.nextInt(10000)+1;
    if(ran_num<=10000*this.epsilon)
        return rand.nextInt(this.numOfActions);
    else
        return BestWay(state);
  }
    
  public void updatePolicy(double reward, int action,
                           int oldState, int newState) {
    this.qValue[oldState][action]=this.qValue[oldState][action]+rate*(reward+discount*this.bestUtility(newState)-this.qValue[oldState][action]);
  }

  public Policy getPolicy() {
    int[] actions = new int[this.numOfStates];
    for (int i = 0; i < this.numOfStates; i++) {
        actions[i] = BestWay(i);
    }
    return new Policy(actions);
  }

  private static final double discount = 0.9;
  private static final double rate = 0.1;
  private static final double epsilon = 0.0;

  private Random rand;
  private int numOfStates;
  private int numOfActions;
  private double[][] qValue;
}