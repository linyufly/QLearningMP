import java.lang.Math;

public class QLearningAgent implements Agent
{
	private static final double discount = 0.9;
	private static final double rate = 0.1;
	private static final double epsilon = 0.0;

	private int numOfStates;
	private int numOfActions;
	private double[][] qValue;

	/*
	* Default constructor for Q Learning Agent.
	*/
	public QLearningAgent()
	{
	}

	/*
	* Initializes the Q-Learning Agent.
	*/
	public void initialize(int numOfStates, int numOfActions)
	{
		this.numOfStates = numOfStates;
		this.numOfActions = numOfActions;
		this.qValue = new double [numOfStates][numOfActions];

    //Initialize the QValue array.
    for(int i = 0; i < numOfStates; i++)
    {
      for (int j = 0; j < numOfActions; j++)
      {
        qValue[i][j] = 0.0;
      }
    }

	}


	/*
	* Returns the selected action, given the state as an argument.
	*/
  	public int chooseAction(int state)
  	{
  		// if < epsilon, be greedy, else pick randomly.
  		if ((double)Math.random() < QLearningAgent.epsilon)
  		{
        return (int)(Math.random() * this.numOfActions);	
  		}
  		else
  		{
        return chooseBestAction(state);
  		}
  	}

    private int chooseBestAction(int state)
    {
        double maxValue = - Double.MAX_VALUE;
        int maxIndex = 0;

        for(int i = 0; i < numOfActions; i++)
        {

          if (maxValue == qValue[state][i])
          {
            if((double)Math.random() < 0.5)
            {
              maxIndex = i;
            }
          }

          if (maxValue < qValue[state][i])
          {
            maxValue = qValue[state][i];
            maxIndex = i;
          }
        }

        return maxIndex;
    }

  	public void updatePolicy(double reward, int action,
                           int oldState, int newState)
  	{
  		double optimal = Double.MIN_VALUE;
  		for(int i = 0; i < this.numOfActions; i++)
  		{
  			optimal = Math.max(optimal, qValue[newState][i]);
  		}

  		this.qValue[oldState][action] = qValue[oldState][action] 
      + QLearningAgent.rate * (reward + QLearningAgent.discount*optimal
      - qValue[oldState][action]);
  	}

  	/*
	* Returns the policy, which specifies the state for each object.
  	*/
  	public Policy getPolicy()
  	{
  	  int[] actions = new int[this.numOfStates];

  	  for (int s = 0; s < this.numOfStates; s++)
		  {
  		  double maxValue = qValue[s][0];
  		  int maxIndex = 0;
  		
  		  for (int a = 0; a < this.numOfActions; a++)
  			{
  				if (qValue[s][a] > maxValue)
  				{
            maxValue = qValue[s][a];
  					maxIndex = a;
  				}
  			}

  			actions[s] = maxIndex;
  	  }

  		return new Policy(actions);
  	}
}
