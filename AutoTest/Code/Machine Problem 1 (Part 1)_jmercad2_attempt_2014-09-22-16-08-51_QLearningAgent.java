import java.util.List;
import java.util.Random;
import java.util.ArrayList;

public class QLearningAgent implements Agent {
  public QLearningAgent() {
    this.rand = new Random();
  }

  public void initialize(int numOfStates, int numOfActions) {
    this.numOfStates = numOfStates;
    this.numOfActions = numOfActions;

    this.qValue = new double[numOfStates][numOfActions];
  }

  public double bestUtility(int state) {
	return qValue[state][bestAction(state)];
  }

  public int bestAction(int state) {
	List<Integer> list = new ArrayList<Integer>();
	double maxQ = Double.NEGATIVE_INFINITY;

	for (int aIdx = 0; aIdx < this.numOfActions; aIdx++) {
	  double curQ = qValue[state][aIdx];
	  int cmp = Double.compare(maxQ, curQ);

	  if (cmp < 0) {
		maxQ = curQ;

		list.clear();
		list.add(aIdx);
	  } 

	  if (cmp == 0) {
		list.add(aIdx);
	  }
	}

	int size = list.size();
	return size > 1 ? list.get(rand.nextInt(size)) : list.get(0);
  }

  public int chooseAction(int state) {
	if (rand.nextDouble() < epsilon) {
	  return rand.nextInt(this.numOfActions);
	} else {
	  return bestAction(state);
	}
  }

  public void updatePolicy(double reward, int action,
                           int oldState, int newState) {
	double curQ, upVal;

	curQ = qValue[oldState][action];
	upVal = curQ + rate*(reward + discount*bestUtility(newState) - curQ);

	qValue[oldState][action] = upVal;
  }

  public Policy getPolicy() {
	int[] actions = new int[this.numOfStates];

	for (int stateIdx = 0; stateIdx < this.numOfStates; stateIdx++) {
	  actions[stateIdx] = bestAction(stateIdx);
	}

	return new Policy(actions);
  }

  private static final double discount = 0.9;
  private static final double rate = 0.1;
  private static final double epsilon = 0.0;

  private Random rand;
  private int numOfStates;
  private int numOfActions;
  private double[][] qValue;
}
