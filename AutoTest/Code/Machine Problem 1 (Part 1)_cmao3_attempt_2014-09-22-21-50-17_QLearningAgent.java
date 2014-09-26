/* Author: Mingcheng Chen */

import java.util.Random;
import java.util.ArrayList;

public class QLearningAgent implements Agent {
  public QLearningAgent() {
    this.rand = new Random(); // Initialize a new Random() object
  }

  public void initialize(int numOfStates, int numOfActions) {
    this.qValue = new double[numOfStates][numOfActions];
    this.numOfStates = numOfStates;
    this.numOfActions = numOfActions;
  }
  
  /*  private int max_act(int state)
  *  input: state - the state whose maximum utility to be found 
  * output: the action which can give the maximum utility given the state
  */
  private int max_act(int state) {
    ArrayList<Integer> max_act_list = new ArrayList<Integer>(); // a list keeps all the actions which can give the maximum utility
    double[] candidates = qValue[state]; // all the actions' utility of the given state
    double max = candidates[0];  
    max_act_list.add(0);
    for (int i = 1; i < numOfActions; i++){
        int retval = Double.compare(candidates[i], max);
        if (retval >= 0){
            if (retval > 0){ // if the current utility is bigger than the current max
                max = candidates[i]; // update the max
                max_act_list.clear(); // clear the current list
            }
            max_act_list.add(i); // add the current action to the list
        }
    }
    return max_act_list.get(rand.nextInt(max_act_list.size())); // draw a random one from the list
  }
  
  private double bestUtility(int state) {
    int act_idx = max_act(state); // get the action with the best utility
    return qValue[state][act_idx]; // return the best utlity
  }

  public int chooseAction(int state) {
    if (Double.compare(rand.nextDouble(), epsilon)<0)
        return rand.nextInt(this.numOfActions); // choose a random action with the probability of epsilon
    else return max_act(state); // return the action with maximum utility with the probability of (1 - epsilon)
  }

  public void updatePolicy(double reward, int action,
                           int oldState, int newState) {
    qValue[oldState][action] = qValue[oldState][action] + rate * (reward + discount * bestUtility(newState)- qValue[oldState][action]);
    // update the policy according to the Q learning formula
  }

  public Policy getPolicy() {
    int [] actions = new int[numOfStates];
    for (int i = 0; i < numOfStates; i++)
        actions[i] = max_act(i);    // generate the array with optimal action for each state
    Policy optimal_policy = new Policy(actions);
    return optimal_policy;
  }

  private static final double discount = 0.9;
  private static final double rate = 0.1;
  private static final double epsilon = 0.0;

  private int numOfStates;
  private int numOfActions;
  private double[][] qValue;
  
  private Random rand;
}
