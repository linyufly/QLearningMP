import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class QLearningAgent implements Agent {
  private static final double DISCOUNT = 0.9;
  private static final double RATE     = 0.1;
  private static final double EPSILON  = 0.0;
  private static final int[]  ACTIONS  = {0, 1, 2, 3};

  private int        numOfStates;
  private int        numOfActions;
  private double[][] qValue;
  private double[]   totalRewards;
  private int[]      visitedRewards;
  private Random     rand;

  public QLearningAgent() {}

  public void initialize(int numOfStates, int numOfActions) {
    this.qValue         = new double[numOfStates][numOfActions];
    this.totalRewards   = new double[numOfStates];
    this.visitedRewards = new int[numOfStates];
    this.numOfStates    = numOfStates;
    this.numOfActions   = numOfActions;
    this.rand           = new Random();
  }

  public int chooseAction(int state) {
    if (this.rand.nextDouble() <= EPSILON) {
      return ACTIONS[this.rand.nextInt(ACTIONS.length)];
    }

    if (anyActionsEqual(state)) {
      return getRandomBestEqualAction(state);
    }

    return getBestAction(state);
  }

  public void updatePolicy(double reward, int action, int oldState, int newState) {
    double maxQ = 0.0;
    if (anyActionsEqual(newState)) {
      maxQ = qValue[newState][getRandomBestEqualAction(newState)];
    } else {
      for (int a: ACTIONS) {
        maxQ = qValue[newState][a] > maxQ ? qValue[newState][a] : maxQ;
      }
    }
    double update = reward + DISCOUNT * maxQ;
    qValue[oldState][action] = (1.0 - RATE) * qValue[oldState][action] + RATE * update;
  }

  public Policy getPolicy() {
    int[] policy = new int[this.numOfStates];
    for (int i = 0; i < this.numOfStates; i++) {
      if (anyActionsEqual(i)) {
        policy[i] = getRandomBestEqualAction(i);
      } else {
        policy[i] = getBestAction(i);
      }
    }
    return new Policy(policy);
  }

  private boolean anyActionsEqual(int state) {
    for (int i = 0; i < this.numOfActions; i++) {
      for (int j = i + 1; j < this.numOfActions; j++) {
        if (this.qValue[state][i] == this.qValue[state][j]) {
          return true;
        }
      }
    }
    return false;
  }

  private int getBestAction(int state) {
    int bestAction = 0;
    double bestQ = 0.0;
    for (int a: ACTIONS) {
      if (qValue[state][a] > bestQ) {
        bestQ = qValue[state][a];
        bestAction = a;
      }
    }
    return bestAction;
  }

  private int getRandomBestEqualAction(int state) {
    Map<Double, ArrayList<Integer>> map = new HashMap<Double, ArrayList<Integer>>();
    double maxQ = 0.0;

    for (int a: ACTIONS) {
      if (map.get(this.qValue[state][a]) == null) {
        map.put(this.qValue[state][a], new ArrayList<Integer>());
      }
      if (this.qValue[state][a] > maxQ) {
        maxQ = this.qValue[state][a];
      }
      map.get(this.qValue[state][a]).add(a);
    }

    List<Integer> bestActions = map.get(maxQ);
    return bestActions.get(this.rand.nextInt(bestActions.size()));
  }
}
