

import java.util.Random;
import java.util.ArrayList;

public class QLearningAgent implements Agent {
  public QLearningAgent() {
    this.rand = new Random();
  }

  public void initialize(int numOfStates, int numOfActions) {
    this.numOfStates = numOfStates;
    this.numOfActions = numOfActions;
    this.qValue = new double[numOfStates][numOfActions];
    
    for (int i = 0; i < numOfStates; i++){
      for (int j = 0; j < numOfActions; j++){
           this.qValue[i][j] = 0.0;
          } 
         }
  }

  private double bestUtility(int state) {
     double r = this.qValue[state][0];
     for (int i =1; i<this.numOfActions;i++){
        if (this. qValue[state][i]>r){
            r = this.qValue[state][i];
    }

  }
     return r;
}

  public int chooseAction(int state) {
     if (this.rand.nextDouble() <=epsilon){
     return this.rand.nextInt(numOfActions);
     }
     double maximumQ=this.bestUtility(state);

     ArrayList<Integer> candidates=new ArrayList<Integer>();
     for (int i=0; i<this.numOfActions;i++){
     if (this.qValue[state][i]==maximumQ){
     candidates.add(i); 
       }
      }
 return candidates.get(this.rand.nextInt(candidates.size()));
}

  public void updatePolicy(double reward, int action,
                           int oldState, int newState) {
  this.qValue[oldState][action]*=(1.0-rate);
  this.qValue[oldState][action]+=rate*(reward+discount*this.bestUtility(newState));

  }

  public Policy getPolicy() {
   int[] actions = new int[this.numOfStates];

   for(int state=0;state<this.numOfStates;state++){
   double maximumQ=this.bestUtility(state);
   for(int i=0;i<this.numOfActions;i++){
     if(this.qValue[state][i]==maximumQ){
       actions[state]=i;
       break;}}}
   return new Policy(actions);
  }

  private static final double discount = 0.9;
  private static final double rate = 0.1;
  private static final double epsilon = 0;

  private int numOfStates;
  private int numOfActions;
  private double[][] qValue;
  private Random rand;

}
