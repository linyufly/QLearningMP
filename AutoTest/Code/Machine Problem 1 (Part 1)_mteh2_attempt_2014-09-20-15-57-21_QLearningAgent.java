import java.util.Random;
import java.util.ArrayList;
import java.lang.Math;

public class QLearningAgent implements Agent {
  public QLearningAgent() {
    this.rand = new Random();
  }

  public void initialize(int numOfStates, int numOfActions) {
    this.qValue = new double[numOfStates][numOfActions];
    this.numOfStates = numOfStates;
    this.numOfActions = numOfActions;
  }

  private int bestUtilityAction(int state) {
	double max1 = Math.max(qValue[state][0], qValue[state][1]);
	double max2 = Math.max(qValue[state][2], qValue[state][3]);
	double max = Math.max(max1, max2);
	
	ArrayList<Integer> array = new ArrayList<Integer>();
	
	for(int i = 0; i < this.numOfActions; i++){
		if (qValue[state][i] == max){
			array.add(i);
		}
	}
	array.removeAll(null);
	
	int num = array.size();
	num = rand.nextInt(num);
	
	return array.get(num);
  }

  public int chooseAction(int state) {
	  if(rand.nextDouble() > epsilon){
		  return bestUtilityAction(state);
	  }
	  else {
		  return rand.nextInt(this.numOfActions);
	  }
  }

  public void updatePolicy(double reward, int action,
                           int oldState, int newState) {
	  qValue[oldState][action] = qValue[oldState][action] + rate * (reward + discount * qValue[newState][bestUtilityAction(newState)] - qValue[oldState][action]);
  }

  public Policy getPolicy() {
	int[] actions = new int[numOfStates];
	for(int i = 0; i< numOfStates; i++){
		actions[i] = bestUtilityAction(i);
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
