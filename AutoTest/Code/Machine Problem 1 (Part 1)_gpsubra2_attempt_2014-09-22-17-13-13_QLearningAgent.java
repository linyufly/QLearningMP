import java.util.Random;
import java.util.ArrayList;

public class QLearningAgent implements Agent {
  public QLearningAgent() {
    this.rand = new Random();
  }

  public void initialize(int numOfStates, int numOfActions) {
    this.qValue = new double[numOfStates][numOfActions];
    this.numOfStates = numOfStates;
    this.numOfActions = numOfActions;
  }

  public int chooseAction(int state) {
    // Find the action which maximizes the QValue given the state
    Random r = new Random();
	double zeroToOne = r.nextInt(1001)/1000;
	
	// Using epsilon-greedy search
	if (zeroToOne < epsilon){
		return r.nextInt(this.numOfActions);
	}	
	else{
		
		double[][] qvalues = this.qValue;
		double[] state_qvalues = qvalues[state];
		ArrayList<Integer> max_indices = new ArrayList<Integer>();
		
		// Looping through the array to find the maximum value
		double max = 0;
		int max_index = 0;	
		for (int i = 0; i < state_qvalues.length; i++){
			if (state_qvalues[i] > max){
				max_indices.clear();		// Clear the existing max values
				max = state_qvalues[i];
				max_index = i;
				max_indices.add(max_index);	// Add current max value to the list
				}
			
			// If values are the same, add it to an array to randomly decide later
			else if (state_qvalues[i] == max) {	
				max_indices.add(i);			
			}
		}	
		
		Random generator = new Random(); 
		max_index = generator.nextInt(max_indices.size());
	
		return max_index;
	}
  }

  public void updatePolicy(double reward, int action, int oldState, int newState) {
    // Finding the max qvalue of the new state
    double[][] qvalues = this.qValue;
    double[] state_qvalues = qvalues[newState];
    
    double max = 0;	
	for (int i = 0; i < state_qvalues.length; i++){
		if (state_qvalues[i] > max){			
			max = state_qvalues[i];						
		}
	}
	
	// Updating the current qvalue using the Q-Learning update formula
    this.qValue[oldState][action] = this.qValue[oldState][action] + rate*(reward + discount*max - this.qValue[oldState][action]);
    
    return;
  }

  public Policy getPolicy() {
    int[] actions = new int[this.numOfStates];    
    double[][] qvalues = this.qValue;
    
    for (int i = 0; i < this.numOfStates; i++) {		
		double[] state_qvalues = qvalues[i];
    
		double max = 0;
		int max_index = 0;	
		for (int j = 0; j < state_qvalues.length; j++){
			if (state_qvalues[j] > max){			
				max = state_qvalues[j];	
				max_index = j;					
			}
		}
		
      actions[i] = max_index;
    }

    return new Policy(actions);
  }
	
  private static final double discount = 0.9;
  private static final double rate = 0.1;
  private static final double epsilon = 0;
  
  private Random rand;
  private int numOfStates;
  private int numOfActions;
  private double[][] qValue;
}

