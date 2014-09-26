//David Taylor djtaylr2@illinois.edu

import java.util.Random;
import java.util.Vector;

public class QLearningAgent implements Agent {
  
	private int numOfStates;
	private int numOfActions;
	private double[][] valueGrid;
	private Random rand;
	public static final double EPSILON = 0.0;
	public static final double LEARNING_RATE = 0.1;
	public static final double DISCOUNT_FACTOR = 0.9;

	public QLearningAgent() {
    	this.rand = new Random();
  	}

	public void initialize(int numOfStates, int numOfActions){
	  	this.valueGrid = new double[numOfStates][numOfActions];
	    this.numOfStates = numOfStates;
	    this.numOfActions = numOfActions;
	}

	public int chooseAction(int state){
		//allow a EPSILON_var percent chance of disvcovery
		if(this.rand.nextDouble() <= EPSILON) return this.rand.nextInt(this.numOfActions);
		//get the most optimal state value
		double max = this.greedy(state);

		//create a structure to hold the states with that value
		Vector<Integer> optimalActions = new Vector<Integer>();
		//for each of optimal values, considering some may be equal add to vector
		for (int action = 0; action < this.numOfActions; action++){
			if(this.valueGrid[state][action] == max)
				optimalActions.add(action);
		}	
		//choose randomly between the optimal values
		int winner = this.rand.nextInt(optimalActions.size());
		return optimalActions.get(winner);
	}

	public void updatePolicy(double reward, int action, int oldState, int newState){

		this.valueGrid[oldState][action] *= DISCOUNT_FACTOR;
		double oldValue = this.valueGrid[oldState][action];
		double optimalFutureValue = this.greedy(newState);
    	this.valueGrid[oldState][action] = oldValue + LEARNING_RATE * (reward + DISCOUNT_FACTOR * optimalFutureValue);

	}

	public Policy getPolicy(){
		//public Policy(int[] actions) : array for the policy constructor
		int[] actions = new int[this.numOfStates];

		for(int state = 0; state < this.numOfStates; state++){
			//double optimal = this.greedy(state);
			Vector<Double> actionValues = new Vector<Double>();
			for (double value : this.valueGrid[state]) actionValues.add(value);
			//get the first action with the most optimal value	
			actions[state] = actionValues.indexOf(this.greedy(state));
		}

		return new Policy(actions);
	}

	// ==================== HELPER METHODS ====================== //

	public double[][] getValues(){
		return this.valueGrid;
	}

	public void setValues(int state, int action, double value){
		this.valueGrid[state][action] = value;
	}

	public double greedy(int state){
		double max = this.valueGrid[state][0]; 
		//iterate through all actions for a state and get optimal value
		for (double value : this.valueGrid[state]) if(value > max) max = value;

		return max;
	}

	// //test my class
	// public static void main(String[] args) {

	// 	GreedyQAgent agent = new GreedyQAgent();
	// 	agent.initialize(10, 4);
	// 	agent.setValues(0, 0, 5.0);
	// 	agent.setValues(0, 1, 2.0);
	// 	agent.setValues(0, 2, 5.0);
	// 	agent.setValues(0, 3, 4.0);

	// 	for (int x = 0; x < 25; x++)
	// 		System.out.println(agent.chooseAction(0));

	// }

}
