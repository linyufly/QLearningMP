import java.util.Random;
import java.util.ArrayList;

public class QLearningAgent implements Agent {

    private static final Random random = new Random();
    
    private double[][] qdata;
    private static final double discountRate = 0.9;
    private static final double learningRate = 0.1;
    private static final double epsilon = 0.0;
    
    public void initialize(int numOfStates, int numOfActions) {
        qdata = new double[numOfStates][numOfActions];
    }
    
    private int getMaxAction(int state) {
        ArrayList<Integer> maxActions = new ArrayList<Integer>();
        maxActions.add(0);
        for (int i = 1;i < 4;i ++) {
            if (qdata[state][i] == qdata[state][maxActions.get(0)]) {
                maxActions.add(i);
            } else if (qdata[state][i] > qdata[state][maxActions.get(0)]) {
                maxActions.clear();
                maxActions.add(i);
            }
        }
        
        return maxActions.get(random.nextInt(maxActions.size()));
    }
    
    public int chooseAction(int state) {
        if (epsilon > random.nextDouble()) {
            return random.nextInt(qdata[state].length);
        }

        return getMaxAction(state);
    }
    
    public void updatePolicy(double reward, int action,
                           int oldState, int newState) {
        qdata[oldState][action] = qdata[oldState][action] + learningRate * (reward + discountRate * qdata[newState][getMaxAction(newState)] - qdata[oldState][action]);
    }
    
    public Policy getPolicy() {
        int[] actions = new int[qdata.length];
        for (int i = 0; i < qdata.length;i ++) {
            actions[i] = getMaxAction(i);
        }
        return new Policy(actions);
    }
}
