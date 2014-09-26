
import java.util.Random;
import java.util.ArrayList;

public class QLearningAgent implements Agent {
  public QLearningAgent() {
    this.rand = new Random();
  }

  public void initialize(int numOfStates, int numOfActions) {
    this.qValue = new double[numOfStates][numOfActions];
    this.numOfStates = numOfStates;
    this.numOfActions = numOfActions;
  }

  public int chooseAction(int state) {
    int nextAction = 0;
	if (rand.nextDouble() < epsilon) {
	  nextAction = rand.nextInt(this.numOfActions);
	  return nextAction;
	}
	
	int maxQ = qValue[state][0];
	for (int i = 1; i < numOfActions; i++) {
	  if (qValue[state][i] > maxQ) {
	    maxQ = qValue[state][i];
		nextAction = i;
		}
      }
	return nextAction;
  }

  public void updatePolicy(double reward, int action,
                           int oldState, int newState) {
	int maxQ = qValue[newState][0];
	for (int i = 1; i < numOfActions; i++) {
	  if (qValue[newState][i] > maxQ) {
	    maxQ = qValue[newState][i];
      }
	}
    this.qValue[oldState][action] = (1-rate) * this.qValue[oldState][action] + rate * (reward + discount * maxQ);
  }

  public Policy getPolicy() {
    int[] actions = new int[this.numOfStates];
    for (int i = 0; i < this.numOfStates; i++) {
	  int k = qValue[i][0];
	  actions[i] = 0;
	  for (int j = 1; j < this.numOfActions; j++) {
	    if (qValue[i][j] > k) {
		  k = qValue[i][j];
		  actions[i] = j;
		}
      }
	}

    return new Policy(actions);
  }

  private static final double discount = 0.9;
  private static final double rate = 0.1;
  private static final double epsilon = 0.0;

  private int numOfStates;
  private int numOfActions;
  private double[][] qValue;
}
