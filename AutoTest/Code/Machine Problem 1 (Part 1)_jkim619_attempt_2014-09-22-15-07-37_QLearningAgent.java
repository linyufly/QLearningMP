import java.util.Random;

/**
 * Created by Ji Hoon Kim on 9/20/2014.
 * Assignment: CS440-MP 1
 */
public class QLearningAgent implements Agent {
    private static final double discount = 0.9;
    private static final double rate = 0.1;
    private static final double epsilon = 0.00;

    private int numOfStates;
    private int numOfActions;
    private double[][] qValue;

    public QLearningAgent()
    {

    }

    @Override
    public void initialize(int numOfStates, int numOfActions) {
        this.numOfActions = numOfActions;
        this.numOfStates = numOfStates;
        this.qValue = new double[numOfStates][numOfActions];
    }

    @Override
    public int chooseAction(int state) {
        double availableActions[] = this.qValue[state];
        boolean tiebreak = false;
        int maxIndex = 0;
        double checkEpsilon = rand.nextDouble();

        if ( checkEpsilon < this.epsilon )
        {
            //System.out.println("test");
            maxIndex = rand.nextInt(availableActions.length);
        }
        else {
            for (int i = 0; i < availableActions.length; i++) {
                if (availableActions[maxIndex] == availableActions[i]) {
                    tiebreak = true;
                }
            }
            if (tiebreak)
            {
                maxIndex = rand.nextInt(availableActions.length);
            }
            else
            {
                maxIndex = maxQ(availableActions);
            }
        }

        return maxIndex;

    }

    private int maxQ(double actions[])
    {
        int maxIndex = 0;
        for (int i = 0; i < actions.length; i++) {
            if (actions[maxIndex] < actions[i]) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    /*
    Q(s,a) = Q(s,a) + rate(Reward(s,s') + discountmaxa'(maxQ(s',a')) - Q(s,a))
     */
    @Override
    public void updatePolicy(double reward, int action, int oldState, int newState) {
        int newAction = maxQ(this.qValue[newState]);
        this.qValue[oldState][action] = this.qValue[oldState][action] + (this.rate *
                        (reward + (this.discount * this.qValue[newState][newAction] -
                                this.qValue[oldState][action])));
    }

    Random rand = new Random();
    @Override
    public Policy getPolicy() {
        int[] actions = new int[this.numOfStates];
        for (int i = 0; i < numOfStates; i++)
        {
            actions[i] = maxQ(this.qValue[i]);
        }
        return new Policy(actions);
    }
}
