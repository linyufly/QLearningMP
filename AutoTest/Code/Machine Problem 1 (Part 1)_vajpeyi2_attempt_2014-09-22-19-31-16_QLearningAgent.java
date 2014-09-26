import java.util.Random;


public class QLearningAgent implements Agent {

	  private static final double discount = 0.9;
	  private static final double rate = 0.1;
	  private static final double epsilon = 0.05;

	  private Random rand;
	  private int numOfStates;
	  private int numOfActions;
	  private double[][] qValue;
	  
	public QLearningAgent() {
	    this.rand = new Random();
	    
	}
	@Override
	public void initialize(int numOfStates, int numOfActions) {
	    this.qValue = new double[numOfStates][numOfActions];
	    this.numOfStates = numOfStates;
	    this.numOfActions = numOfActions;

	}

	@Override
	public int chooseAction(int state) {
		if(rand.nextDouble()<epsilon)
			return rand.nextInt(4);
		return bestAction(state);
	}

	@Override
	public void updatePolicy(double reward, int action, int oldState,
			int newState) {
		double max = 0;
		for(int i=0; i < numOfActions; i++)
			if(qValue[newState][i] > max)
				max = qValue[newState][i];
		qValue[oldState][action] += rate*(reward + discount*max - qValue[oldState][action]);
	}

	@Override
	public Policy getPolicy() {
		int[] actions = new int[numOfStates];
		for(int i=0; i<numOfStates; i++){
			actions[i] = bestAction(i);
		}
		Policy policy = new Policy(actions);
		return policy;
	}

	private int bestAction(int state){
		double max = 0;
		int[] optAction = new int[numOfActions];
		int count = 0;
		for(int i=0; i < numOfActions; i++)
			if(qValue[state][i] > max){
				max = qValue[state][i];
				optAction[0] = i;
				count = 1;
			}
			else
				if(qValue[state][i]==max){
					optAction[count++] = i;
				}
		return optAction[rand.nextInt(count)];
	}

}
