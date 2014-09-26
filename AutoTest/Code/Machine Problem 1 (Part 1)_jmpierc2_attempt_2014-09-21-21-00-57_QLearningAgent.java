/* Author: Jonathan Pierce */

import java.util.Random;
import java.lang.Math;

public class QLearningAgent implements Agent {
  public QLearningAgent() {
    this.rand = new Random();
    this.discounting = 0.9;
    this.learningrate = 0.1;
    this.e = 0.0;
  }

  public void initialize(int numOfStates, int numOfActions) {
    this.numOfStates = numOfStates;
    this.numOfActions = numOfActions;
    this.qual = new double[numOfStates][numOfActions];
    
    // Initalize the qual table to 0
    for(int i = 0; i < numOfStates; i++) {
        for(int j = 0; j < numOfActions; j++) {
            this.qual[i][j] = 0.0;
        }
    }
  }

  public int chooseAction(int state) {
    // Should we just be completely random?
    if(this.rand.nextDouble() < this.e) {
        return this.rand.nextInt(this.numOfActions);
    }
  
    // Figure out what action is best
    int max = maxAction(state);
    double bestVal = this.qual[state][max];
    
    // If there is a tie, break it randomly
    int[] ties = new int[this.numOfActions];
    int numTies = 0;
    for(int i = 0; i < this.numOfActions; i++) {
        // Use a tolerance to account for floating point error
        if(Math.abs(this.qual[state][i] - bestVal) <= 0.00001) {
            ties[numTies] = i;
            numTies++;
        }
    }
    
    return ties[this.rand.nextInt(numTies)];
  }
  
  public int maxAction(int state) {
    int best = 0;
    double bestVal = this.qual[state][0];
    for(int i = 1; i < this.numOfActions; i++) {
        double current = this.qual[state][i];
        if(current > bestVal) {
            best = i;
            bestVal = current;
        }
    }
    return best;
  }

  public void updatePolicy(double reward, int action, int oldState, int newState) {
    int max = maxAction(newState);
    double learned = reward + (this.discounting * this.qual[newState][max]);
    this.qual[oldState][action] = (1.0 - this.learningrate)*(this.qual[oldState][action]) + (this.learningrate)*learned;
  }

  public Policy getPolicy() {
    int[] actions = new int[this.numOfStates];
    for (int i = 0; i < this.numOfStates; i++) {
      actions[i] = maxAction(i);
    }

    return new Policy(actions);
  }

  private Random rand;
  private int numOfStates;
  private int numOfActions;
  private double[][] qual; // [state][action]
  private double discounting;
  private double learningrate;
  private double e;
}
