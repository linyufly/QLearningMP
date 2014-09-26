/* Author: Mingcheng Chen */

import java.util.Random;
import java.util.ArrayList;

public class QLearningAgent implements Agent {
  public QLearningAgent() {
    //this.rand = new Random();
  }

  public void initialize(int numOfStates, int numOfActions) {
    this.qValue = new double[numOfStates][numOfActions];
    for(int i = 0 ; i< numOfStates; i++){
      for(int j = 0; j< numOfActions; j++){
        this.qValue[i][j] = 0;
        
      }
      
    }
    this.policies = new int[numOfStates];
    for(int i = 0; i< numOfStates; i++){
	policies[i] = 0;
    }
    this.numOfStates = numOfStates;
    this.numOfActions = numOfActions;
  }

  private double bestUtility(int state) {
      //update the policy
      double max = -10000000.0;
        
      for(int i = 0; i < this.numOfActions; i++){
        if(this.qValue[state][i] > max){
            max = this.qValue[state][i];
        }
      }
      return max;
      
  }




  public int chooseAction(int state) {
      //if statements check surrounding states
      int [] best_actions = new int[this.numOfActions];
      int count = 0;
      int size = 0;
      int action_index;
      Random double_rand = new Random();
      Random int_rand = new Random();
      int action;
      
      double rand_num = double_rand.nextDouble();
      if(rand_num < epsilon){
        action = int_rand.nextInt(this.numOfActions);
      }
      
      else{
          double max;
          max = bestUtility(state);
          
          for(int i = 0; i < this.numOfActions; i++){
            if(this.qValue[state][i] == max){
                best_actions[count] = i;
                count++;
            }
            
          }
          
          size = count;
          if(size > 1){
            int rand_action = int_rand.nextInt(size);
            action_index = best_actions[rand_action];
            count = 0;
          }
          else{
            action_index = best_actions[0];
          }
          
          action = action_index; 
      }
       return action;
  }
  
  
  public void updatePolicy(double reward, int action, int oldState, int newState) {
      
      Random rand_num = new Random();
      double q = this.qValue[oldState][action];
      //update the policy
      double max = this.bestUtility(newState);



      //if(qValue[oldState][this.policies[oldState]] < qValue[oldState][chooseAction(oldState)]){
	//	this.policies[oldState] = chooseAction(oldState);
	//}	


      //upgrade	
      this.qValue[oldState][action] = q +  rate * (reward + discount*(max) - q);
      

      int chosen = chooseAction(oldState);
      if(qValue[oldState][this.policies[oldState]] < qValue[oldState][chosen]){
		this.policies[oldState] = chosen;
	}
   



  }

  public Policy getPolicy() {
      /*int [] states = new int[this.numOfStates];
      for(int i = 0; i < states.length; i++){
        states[i] = chooseAction(i);
      }
      
      return new Policy(states);*/

      return new Policy(this.policies);
  }

  private static final double discount = 0.9;
  private static final double rate = 0.1;
  private static final double epsilon = 0.0;

  private int numOfStates;
  private int numOfActions;
  private double[][] qValue;
  private int [] policies;

}
