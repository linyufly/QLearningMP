import java.util.Random;


public class QLearningAgent implements Agent {
	  public QLearningAgent() {
	    this.rand = new Random();
	  }

	  public void initialize(int numOfStates, int numOfActions) {
	    this.qValue = new double[numOfStates][numOfActions];
	    this.numOfStates = numOfStates;
	    this.numOfActions = numOfActions;
	    actions = new int[this.numOfStates];
	    for (int i = 0; i < numOfStates; i++) {
	    	actions[i] = rand.nextInt(this.numOfActions);
	    }
	  }

	  private double bestUtility(int state) {
		  double highest = 0;
		  highest = qValue[state][0];
		  for (int i = 0; i < this.numOfActions; i++) {
			  if (qValue[state][i] > highest ) {
				  highest = qValue[state][i];
			  }
		  }
		  return highest;
	  }

	  public int chooseAction(int state) {
		  //given state, choose action with the highest q value, with a chance of a random action
		  int bestAction = 0;
		  double highest = 0;
		  double x = Math.random();
		  if ( x < epsilon) {
			  int randomAction = rand.nextInt((3) + 1);
			  return randomAction;
		  }
		  highest = qValue[state][0];
		  for (int i = 0; i < this.numOfActions; i++) {
			  if (qValue[state][i] > highest ) {
				  highest = qValue[state][i];
				  bestAction = i;
			  }
			  else if (qValue[state][i] == highest) {
				  if (rand.nextBoolean() ) {
					  bestAction = i;
				  }
			  }
		  }
		  
		  return bestAction;
	  }

	  public void updatePolicy(double reward, int action,
	                           int oldState, int newState) {
		  double oldQValue = qValue[oldState][action];
		  double highest;
		  int bestAction = 0;
		  qValue[oldState][action] = oldQValue + alpha * (reward + discount*bestUtility(newState) - oldQValue );
		  //highest utility for a state is the action done for the policy
		  
		  highest = qValue[oldState][0];
		  for (int i = 0; i < this.numOfActions; i++) {
			  if (qValue[oldState][i] > highest ) {
				  highest = qValue[oldState][i];
				  bestAction = i;
			  }
		  }
		  actions[oldState] = bestAction;
		  
		  //actions[oldState] = chooseAction(oldState);
	  }

	  public Policy getPolicy() {
		  Policy policy = new Policy(actions);
		  return policy;
	  }

	  private static final double discount = 0.9;
	  private static final double alpha = 0.1;
	  private static final double epsilon = 0.0;
	  
	  Random rand;
	  int[] actions;
	  private int numOfStates;
	  private int numOfActions;
	  private double[][] qValue;
	}
