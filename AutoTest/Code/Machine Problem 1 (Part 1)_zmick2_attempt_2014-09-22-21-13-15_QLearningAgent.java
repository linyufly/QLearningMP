import java.lang.Math;
import java.util.ArrayList;

public class QLearningAgent implements Agent {

    private static final double discount = 0.9;
    private static final double rate = 0.1;
    private static final double epsilon = 0.00;

    private int numOfStates;
    private int numOfActions;
    private double[][] qValue;

    public QLearningAgent() {
    }

    public void initialize(int numOfStates, int numOfActions) {
        this.numOfStates = numOfStates;
        this.numOfActions = numOfActions;

        qValue = new double[numOfStates][numOfActions];
        for (int s = 0; s < numOfStates; s++) {
            for (int a = 0; a < numOfActions; a++) {
                qValue[s][a] = 0.0;
            }
        }
    }

    public int chooseAction(int state) {
        double chance = Math.random();

        if (chance <  QLearningAgent.epsilon)
            return (int) (Math.random() * numOfActions);

        double bestValue = qValue[state][0];
        int bestAction = 0;

        int ties[] = new int[numOfActions];
        int counter = 0;

        for (int a = 0; a < numOfActions; a++)
        {
            if(qValue[state][a] == bestValue)
            {
                counter++;
                ties[counter] = a;
            }

            if(qValue[state][a] > bestValue)
            {
                bestValue = qValue[state][a];
                bestAction = a;
                counter = 0;
                ties[counter] = a;
            }
        }

        if (counter != 0) {
            return ties[ (int)(Math.random() * (counter+1)) ];
        }
        return bestAction;
    }

    public void updatePolicy(double reward, int action, int oldState, int newState) {
        double outside_come_on_dz = -Double.MAX_VALUE;
        for (int j = 0; j < numOfActions; j++) {
            if ( qValue[newState][j] > outside_come_on_dz ) {
                outside_come_on_dz = qValue[newState][j];
            }
        }

        qValue[oldState][action] = qValue[oldState][action]
            + QLearningAgent.rate * ( reward
                    + QLearningAgent.discount * outside_come_on_dz
                    - qValue[oldState][action] );
    }

    public Policy getPolicy() {
        int[] actions = new int[this.numOfStates];
        for (int s = 0; s < this.numOfStates; s++) {

            double outside_come_on_dz = qValue[s][0];
            int best_action = 0;
            for (int a = 0; a < this.numOfActions; a++) {
                if (qValue[s][a] > outside_come_on_dz) {
                    outside_come_on_dz = qValue[s][a];
                    best_action = a;
                }
            }

            actions[s] = best_action;
        }
        return new Policy(actions);
    }
}
