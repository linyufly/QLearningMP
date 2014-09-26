/**
 Michael(Zhaochuan) Qiu
 zqiu5
 ECE 448
 */
import java.lang.Math.*; 
import java.util.Random;
import java.util.ArrayList;

public class QLearningAgent implements Agent{

    public QLearningAgent() {
    	this.rand = new Random();
    }
    
    public void initialize(int numOfStates, int numOfActions) {
    	this.qValue = new double[numOfStates][numOfActions];
    	this.numOfStates = numOfStates;
    	this.numOfActions = numOfActions;
  	}
  	public int chooseAction(int state){
  		double prob = Math.random();
  		int chosenAction = 0; //this is the number corresponding to the chosen action
  		double qMax = qValue[state][0]; //this is the maximum Q value from the current state to the next state
  		ArrayList<Integer> optimalActions = new ArrayList<Integer>();
  		int randIndex;
  		if(prob <= epsilon){
  			//Explore. Choose random action. Probability is epsilon
  			chosenAction = (int)(numOfActions*Math.random());
  			return chosenAction;
  		}else{
  			//Exploit. Be greedy. Probability is 1-epsilon
  			for(int i = 1; i < numOfActions; i++){ //find the action that yields the most rewards in a given state
  				if(qValue[state][i]>=qMax){
  					qMax = qValue[state][i];
  				}
  			}
  			for(int i = 0; i < numOfActions; i++){ //put all the optimal actions into an ArrayList
  				if(qValue[state][i] == qMax){
  					optimalActions.add(i);
  				}	
  			}
  			//choose among optimal actions randomly
  			randIndex = (int)(Math.random()*optimalActions.size());
  			chosenAction = optimalActions.get(randIndex);
  			return chosenAction;
  		}
  	}
  	
  	public int chooseActionExploit(int state){
  		double prob = Math.random();
  		int chosenAction = 0; //this is the number corresponding to the chosen action
  		double qMax = qValue[state][0]; //this is the maximum Q value from the current state to the next state
  		ArrayList<Integer> optimalActions = new ArrayList<Integer>();
  		int randIndex;
  		//Exploit. Be greedy. Probability is 1-epsilon
  			for(int i = 1; i < numOfActions; i++){ //find the action that yields the most rewards in a given state
  				if(qValue[state][i]>=qMax){
  					qMax = qValue[state][i];
  				}
  			}
  			for(int i = 0; i < numOfActions; i++){ //put all the optimal actions into an ArrayList
  				if(qValue[state][i] == qMax){
  					optimalActions.add(i);
  				}
  			}
  			//choose among optimal actions randomly
  			randIndex = (int)(Math.random()*optimalActions.size());
  			chosenAction = optimalActions.get(randIndex);
  			return chosenAction;
  	}
  	
  	private double bestUtility(int state) {
		double[] actionRewards = new double[4];
		double highestReward;
		//put the utilites for each action from the state into an array
		for(int i =0; i < actionRewards.length; i++){
			actionRewards[i] = qValue[state][i];
		}
		//find the action that yields the highest rewards
		highestReward = actionRewards[0];
		for(int i = 1; i < actionRewards.length; i++){
		
			if(actionRewards[i]>highestReward){
				highestReward = actionRewards[i];
			}
			
		}
		return highestReward;
  	}
  	
  	public void updatePolicy(double reward, int action,
                           int oldState, int newState){
    	qValue[oldState][action] = qValue[oldState][action] + rate * (reward + discount*bestUtility(newState)-qValue[oldState][action]);             	
    }
    
  	public Policy getPolicy(){
  		actions = new int[this.numOfStates];
  		for(int i = 0; i < this.numOfStates; i++){
  			actions[i] = chooseActionExploit(i); //This creates an array of the chosen action for each state
  		}
  		Policy policy = new Policy(actions);
  		return policy;
  	}
  	
  	private static final double discount = 0.9;
  	private static final double rate = 0.1;
  	private static final double epsilon = 0.0;
  
  	private Random rand;
  	private int numOfStates;
  	private int numOfActions;
  	private int[] actions; 
  	private double[][] qValue;
}