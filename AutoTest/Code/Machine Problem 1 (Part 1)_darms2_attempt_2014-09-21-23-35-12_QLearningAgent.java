/* Submission from Dohn Arms (darms2) */

import java.util.Random;
import java.util.ArrayList;

public class QLearningAgent implements Agent 
{
    public QLearningAgent() 
    {
        this.rand = new Random();
    }

    public void initialize(int numOfStates, int numOfActions) 
    {
        this.qValue = new double[numOfStates][numOfActions];
        this.numOfStates = numOfStates;
        this.numOfActions = numOfActions;
    }

    /*
      This finds the next action for a given state, resulting in the 
      highest qValue.  If there are multiple highest values, one is 
      randomly found.
     */
    private int bestAction(int state) 
    {
        int cnt;
        int matches[];
        double best;
        int i;

        matches = new int[this.numOfActions];

        matches[0] = 0;
        cnt = 1;
        best = this.qValue[state][0];
        for( i = 1; i < this.numOfActions; i++)
            {
                if( this.qValue[state][i] == best)
                    {
                        matches[cnt] = i;
                        cnt++;
                    }
                else if( this.qValue[state][i] > best)
                    {
                        best = qValue[state][i];
                        matches[0] = i;
                        cnt = 1;
                    }
            }

        if( cnt == 1)
            return matches[0];

        return matches[this.rand.nextInt(cnt)];
    }

    public int chooseAction(int state) 
    {
        int i;
        int index;
        double best;

        if( this.rand.nextFloat() < this.epsilon)
            return this.rand.nextInt(this.numOfActions);

        return this.bestAction( state);
    }
    
    public void updatePolicy(double reward, int action, int oldState, 
                             int newState) 
    {
        this.qValue[oldState][action] = 
            (1.0-this.rate)*this.qValue[oldState][action] +
            this.rate*(reward + this.discount*
                       this.qValue[newState][this.bestAction(newState)]);
    }

    public Policy getPolicy() 
    {
        int[] policyArray;
        int i;

        policyArray = new int[this.numOfStates];

        for( i = 0; i < this.numOfStates; i++)
            policyArray[i] = this.bestAction( i);

        return new Policy( policyArray);
    }

    private static final double discount = 0.9;
    private static final double rate = 0.1;
    private static final double epsilon = 0.00;

    private int numOfStates;
    private int numOfActions;
    private double[][] qValue;

    private Random rand;
}
