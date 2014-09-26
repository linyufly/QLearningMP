import java.lang.Math;

public class QLearningAgent implements Agent {

    private static final double discount = 0.9; // gamma
    private static final double rate = 0.1;     // alpha
    private static final double epsilon = 0.0; // prob we take random action

    private int numStates;
    private int numActions;
    private double[][] qValue;

    public QLearningAgent() {
    }

    public void initialize(int numOfStates, int numOfActions) {
        this.numStates = numOfStates;
        this.numActions = numOfActions;

        qValue = new double[numOfStates][numOfActions];
        for (int i = 0; i < numOfStates; i++) {
            for (int j = 0; j < numOfActions; j++) {
                qValue[i][j] = 0.0;
            }
        }
    }

    public int chooseAction(int state) {
        double doRandom = Math.random();
        if (doRandom <  QLearningAgent.epsilon) 
		{
            return (int) (Math.random() * 4);
        }
        double bestValue = -Double.MAX_VALUE;
		int[] tieActions = new int[numActions];
		int counter = 0;
        for (int i = 0; i < numActions; i++)
        {
            if(qValue[state][i] == bestValue)
            {	
				counter++;
				tieActions[counter] = i;
            }

            if(qValue[state][i] > bestValue)
            {
                bestValue = qValue[state][i];
				counter = 0;
				tieActions[counter] = i;
            }
        }
		return tieActions[(int) ((double)(counter + 1) * Math.random())];
		
    }

    public void updatePolicy(double reward, int action, int oldState, int newState) {
        double bestValue = -Double.MAX_VALUE;
        for (int j = 0; j < numActions; j++) {
            if(qValue[newState][j] > bestValue) {
                bestValue= qValue[newState][j];
            }
        }

        qValue[oldState][action] = qValue[oldState][action]
            + QLearningAgent.rate * (reward
                    + QLearningAgent.discount * bestValue
                    - qValue[oldState][action]);

    }

    public Policy getPolicy() {
        int[] actions = new int[this.numStates];
        for (int i = 0; i < this.numStates; i++) {

            double bestValue = -Double.MAX_VALUE;
			int[] tieActions = new int[numActions];
			int counter = 0;
        	for (int j = 0; j < numActions; j++)
        	{
            	if(qValue[i][j] == bestValue)
            	{	
					counter++;
					tieActions[counter] = j;
        	    }

         	 	if(qValue[i][j] > bestValue)
            	{
                	bestValue = qValue[i][j];
					counter = 0;
					tieActions[counter] = j;
            	}
        	}
            actions[i] = tieActions[(int) ((double)(counter + 1) * Math.random())];
        }
        return new Policy(actions);
    }
}
