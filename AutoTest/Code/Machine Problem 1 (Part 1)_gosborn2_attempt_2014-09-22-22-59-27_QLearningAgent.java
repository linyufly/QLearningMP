import java.util.ArrayList;
import java.util.Random;

/**
 */
public class QLearningAgent implements Agent {

    private static final double GAMMA = 0.9d;
    private static final double EPSILON = 0.0d;
    private static final double ALPHA = 0.1d;

    private int _numOfActions;
    private int _numOfStates;
    private double[][] _N;
    private double[][] _Q;
    private double[][] _R;
    private Random _random = new Random();

    @Override
    public void initialize(int numOfStates, int numOfActions) {
        _numOfStates = numOfStates;
        _numOfActions = numOfActions;
        _N = new double[numOfStates][numOfActions];
        _R = new double[numOfStates][numOfActions];
        _Q = new double[numOfStates][numOfActions];
    }

    @Override
    public int chooseAction(int state) {
        int answer;
        double greedy = _random.nextDouble();
        if (greedy < EPSILON) {
            answer = _random.nextInt(_numOfActions);
        } else {
            answer = bestAction(state);
        }
        return answer;
    }

    @Override
    public void updatePolicy(double reward, int action, int s1, int s2) {
        _N[s1][action] = _N[s1][action] + 1.0d;
        _R[s1][action] = _R[s1][action] + reward;
        double r_s = _R[s1][action] / _N[s1][action];
        _Q[s1][action] = _Q[s1][action] + ALPHA * (r_s + GAMMA * maxQ(s2) - _Q[s1][action]);
        //_Q[s1][action] = (1.0 - ALPHA) * _Q[s1][action] + ALPHA * (reward + GAMMA * maxQ(s2));
    }

    @Override
    public Policy getPolicy() {
        int[] actions = new int[_numOfStates];
        for (int s=0; s < _numOfStates; s++) {
            actions[s] = bestAction(s);
        }
        return new Policy(actions);
    }

    private int bestAction(int state) {
        ArrayList<Integer> bestActions = new ArrayList<Integer>();
        double bestValue = Double.NEGATIVE_INFINITY;
        for (int i=0; i < _numOfActions; i++) {
            double nextValue = _Q[state][i];
            if (Double.compare(nextValue, bestValue) > 0) {
                bestActions.clear();
                bestActions.add(i);
                bestValue = nextValue;
            } else if (Double.compare(nextValue, bestValue) == 0) {
                bestActions.add(i);
            }
        }
        int tieBreaker = _random.nextInt(bestActions.size());
        return bestActions.get(tieBreaker);
    }

    private double maxQ(int state) {
        double answer = 0.0d;
        for (int i=0; i < _numOfActions; i++) {
            answer = Math.max(answer, _Q[state][i]);
        }
        return answer;
    }
}
