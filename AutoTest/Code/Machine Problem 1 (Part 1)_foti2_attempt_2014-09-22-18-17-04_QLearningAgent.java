

public class QLearningAgent implements Agent {
	
	private int numStates, numActions;
	private double[][] qValue;
	private static final double discount=.9;
	private static final double rate=.1;
	private static final double epsilon=0;

	@Override
	public void initialize(int numOfStates, int numOfActions) {
		numStates=numOfStates;
		numActions=numOfActions;
		this.qValue= new double[numStates][numActions];
	}

	@Override
	public int chooseAction(int state) {
		
		double x = Math.random();
		if(x<=epsilon){
			return (int)(Math.random()*4);				//if exploring return a random action
		}
		else{
			int best=0;
			int size=1;
			int actions[] = new int[numActions];
			actions[0]=0;
			
			for(int i=1; i<numActions;i++){
				if(qValue[state][i]>qValue[state][best]){			//reset optimal array with new best value
					best=i;
					size=1;
					actions[0]=i;
				}
				
				else if(qValue[state][i]==qValue[state][best]){		// add another optimal value to array
					size++;
					actions[size-1]=i;
				}
			}
			
			int random =  actions[(int)(Math.random()*size)];				//return a random action from all optimal actions 
			//System.out.println("Randomly choose action " + random + "out of " + size + " optimal actions");
			return random;
		}
	}

	@Override
	public void updatePolicy(double reward, int action, int oldState,
			int newState) {
		
		qValue[oldState][action] = qValue[oldState][action] + (rate*(reward + (discount*bestUtility(newState)) - qValue[oldState][action]));

		//System.out.println("Reward:" + reward + " action:" + action + " oldState:" + oldState + " newState:" + newState);

	}
	
	private double bestUtility(int state){				//returns highest action qValue for a state
		
		double max = qValue[state][0];
		
		for(int i=0; i<numActions;i++){
			if(max<qValue[state][i]){
				max = qValue[state][i];
			}
		}
		
		return max;
	}
	
	private int bestAction(int state){
		int best = 0;
		
		for(int i=0; i<numActions;i++){
			if(qValue[state][best]<qValue[state][i]){
				best = i;
			}
		}
		
		return best;
	}
	

	@Override
	public Policy getPolicy() {
		int actions[] = new int[numStates];
		for(int i=0;i<numStates;i++){
			actions[i] = bestAction(i);
		}
		
		return new Policy(actions);
	}
 
		
	
	
}
