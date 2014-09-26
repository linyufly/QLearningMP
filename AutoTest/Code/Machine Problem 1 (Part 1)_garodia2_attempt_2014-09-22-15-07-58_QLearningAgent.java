

import java.util.Random;
import java.util.ArrayList;

public class QLearningAgent implements Agent {
  public QLearningAgent() {
    this.rand = new Random();
  }

  public void initialize(int numOfStates, int numOfActions) {
    this.qmatrix = new double[numOfStates][numOfActions];
    this.numOfStates = numOfStates;
    this.numOfActions = numOfActions;
  }

  public int chooseAction(int state) {
    
   if(rand.nextDouble()<epsilon){
	  return rand.nextInt(this.numOfActions);
   }else{
 	  double best = bestUtility(state);
 	  ArrayList<Integer> bestactions = new ArrayList<Integer>();
  	  for(int i = 0;i<numOfActions;i++){
	  	 if(qmatrix[state][i]==best){
		   bestactions.add(i);
	  	 }
   	  }

  		return bestactions.get(rand.nextInt(bestactions.size()));
	}
	
  }

  public void updatePolicy(double reward, int action, int oldState, int newState) {
    
	  qmatrix[oldState][action]=qmatrix[oldState][action]+(rate*((reward)+
		  														 (discount*bestUtility(newState))-
															     (qmatrix[oldState][action])));
  }

  public Policy getPolicy() {
    int[] actions = new int[this.numOfStates];
    for (int i = 0; i < this.numOfStates; i++) {
		
		double best = qmatrix[i][0];
		int besta=0;
    	for(int a = 1;a<numOfActions;a++){
  	  	 	if(qmatrix[i][a]>best){
				best=qmatrix[i][a];
  		   		besta=a;
  	  	 	}
     	 }
		 actions[i]=besta;  
    }

    return new Policy(actions);
  }
  
  
  private double bestUtility(int state) {

	  double best = qmatrix[state][0];
	  
	  for(int i = 1;i<numOfActions;i++){
		  if(qmatrix[state][i]>best)
			best=qmatrix[state][i];
	  }
	  return best;
	   
  }
  
  private static final double discount = 0.9;
  private static final double rate = 0.1;
  private static final double epsilon = 0.0;
	


  private Random rand;
  private int numOfStates;
  private int numOfActions;
  private double[][] qmatrix;
}