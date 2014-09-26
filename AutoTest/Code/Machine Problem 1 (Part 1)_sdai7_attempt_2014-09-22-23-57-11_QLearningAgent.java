import java.util.Random;
import java.util.ArrayList;



public class QLearningAgent implements Agent {


	public void initialize(int numOfStates, int numOfActions) {
		this.qValue = new double[numOfStates][numOfActions];
    	this.numOfStates = numOfStates;
    	this.numOfActions = numOfActions;
    	this.rand = new Random();

	}
	private double bestUtility(int state) {
		this.bestUtility = qValue[state][0];
		for(int i =0; i<4; i++) {
			if(this.bestUtility < qValue[state][i])
				this.bestUtility = qValue[state][i];
		}
		return this.bestUtility;
  	}

	public int chooseAction(int state) {
		double random_action = rand.nextDouble();
		if(random_action <= epsilon) {
			return rand.nextInt(4);
		} else {
			double max_qvalue = qValue[state][0];
			for (int i =0; i<4 ; i++) {
				if (max_qvalue < qValue[state][i]) {
					max_qvalue = qValue[state][i]; //find max_qvalue
									
				}	
			}

			int count = 0;
			for (int i =0; i<4 ; i++) {
				if (max_qvalue == qValue[state][i]) {
					count++; //count the number of max_qvalue
									
				}	
			}

			int[] action_array = new int[count];
			int action_array_index = 0;
			for (int i =0; i<4 ; i++) {
				if (max_qvalue == qValue[state][i]) {
					action_array[action_array_index] = i;
					action_array_index++;				
				}	
			}
			return action_array[rand.nextInt(action_array_index)];
		}



	}

	public void updatePolicy(double reward, int action, int oldState, int newState) {
		qValue[oldState][action] = qValue[oldState][action] + rate * (reward + discount * (bestUtility(newState) - qValue[oldState][action]));


	}

	public Policy getPolicy() {
		int []actions = new int[this.numOfStates];
		double statevalue = 0;
		for (int i = 0;i < this.numOfStates ; i++) {
			statevalue = qValue[i][0];
			int action_value = 0;	
			for(int j =0; j<4; j++) {
				if(statevalue < qValue[i][j]) {
					statevalue = qValue[i][j];
					action_value = j;
				}
					
			}
			actions[i] = action_value;	
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
	  private double bestUtility;



}