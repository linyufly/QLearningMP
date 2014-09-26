/* Author: Mingcheng Chen */

import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import static java.lang.System.out;

public class QLearningAgent implements Agent {
  public void initialize(int numOfStates, int numOfActions) {
    this.qValue = new double[numOfStates][numOfActions];
    this.numOfStates = numOfStates;
    this.numOfActions = numOfActions;

    for(int i=0; i<numOfStates; i++){
        for(int j=0; j<numOfActions; j++){
            qValue[i][j] = 0.0;
        }
    }
  }

  public int chooseAction(int state) {
    List<Integer> index = new ArrayList<Integer>();
    double maxQ = -Double.MAX_VALUE; 
    
    if(Math.random() < epsilon){
        return (int) (numOfActions * Math.random());
    }

    for(int i=0; i<numOfActions; i++){
        if(qValue[state][i] > maxQ){
            maxQ = qValue[state][i];
        }
    }
   
    for(int i=0; i<numOfActions; i++){
        if(qValue[state][i] == maxQ){
            index.add(i);
        }
    }

    if(index.size() > 1){
        return index.get((int)(index.size()*Math.random()));
    }
    
    return index.get(0);
  }
  
  public int chooseAction(int state, boolean b) {
    List<Integer> index = new ArrayList<Integer>();
    double maxQ = -Double.MAX_VALUE;

    for(int i=0; i<numOfActions; i++){
        if(qValue[state][i] > maxQ){
            maxQ = qValue[state][i];
        }
    }

   
    for(int i=0; i<numOfActions; i++){
        if(qValue[state][i] == maxQ){
            index.add(i);
        }
    }

    if(index.size() > 1){
        return index.get((int)(index.size()*Math.random()));
    }
  
    if(index.size() > 0){
        return index.get(0);
    }
    return (int) (numOfActions * Math.random());
  }

  public void updatePolicy(double reward, int action,
                           int oldState, int newState) {
    double maxQ = -Double.MAX_VALUE;
    for(int i=0; i<numOfActions; i++){
        if(qValue[newState][i] > maxQ){
            maxQ = qValue[newState][i];
        }
    }

    qValue[oldState][action] = qValue[oldState][action] + rate * 
        (reward + discount * maxQ - qValue[oldState][action]);
  }

  public Policy getPolicy() {
    int[] output = new int[this.numOfStates];
    
    for(int i=0; i<numOfStates; i++){
        output[i] = chooseAction(i, true);
    }

    Policy p = new Policy(output);
    return p;
  }

  private static final double discount = 0.9;
  private static final double rate = 0.1;
  private static final double epsilon = 0.0;

  private int numOfStates;
  private int numOfActions;
  private double[][] qValue;
}

