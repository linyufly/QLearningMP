import java.util.Random;
import java.util.Arrays;

public class QLearningAgent implements Agent{

	private static final double discount = 0.9;
	private static final double rate = 0.1;
	private static final double epsilon = 0.0;

	private int numOfStates;
	private int numOfActions;
	private double[][] qValue;

	private Random rand;

	public void initialize(int numOfStates, int numOfActions) {
		this.numOfStates = numOfStates;
		this.numOfActions = numOfActions;
		this.rand = new Random();
		this.qValue = new double[this.numOfStates][this.numOfActions];
	}

	public int chooseAction(int state) {
		double temp = this.rand.nextDouble();
		if (temp < epsilon) { return (int)(this.numOfActions*this.rand.nextDouble()); }
		double maxQ = -Double.MAX_VALUE;
		int[] multipleActions = new int[this.numOfActions];
		int idx = 0;
		Arrays.fill(multipleActions, -1);
		//System.out.println(this.numOfActions);
		for (int k = 0; k < this.numOfActions; k++) {
			/*System.out.print("----");
			System.out.println(k);
			System.out.println(maxQ);
			System.out.println(this.qValue[state][k]);*/
			if (this.qValue[state][k] > maxQ) {
				idx = 0;
				Arrays.fill(multipleActions, -1);
				maxQ = this.qValue[state][k]; 
				multipleActions[idx] = k;
				/*System.out.print("set maximum value to ");
				System.out.println(multipleActions[idx]);
				System.out.println(maxQ);*/
				idx++;
			}
			else if (this.qValue[state][k] == maxQ) {
				multipleActions[idx] = k;
				idx++;
			}
		}
		int outIdx = (int)(this.rand.nextDouble() * idx);
		//System.out.println(outIdx);
		return multipleActions[outIdx];
	}

	public void updatePolicy(double reward, int action,
			int oldState, int newState) {
		double maxQ = this.qValue[newState][0];
		for (int k = 0; k < this.numOfActions; k++) {
			if (this.qValue[newState][k] > maxQ) { maxQ = this.qValue[newState][k]; }
		}
		this.qValue[oldState][action] = this.qValue[oldState][action] + rate*(reward + discount*maxQ - this.qValue[oldState][action]);
	}

	public Policy getPolicy() {
		int[] actions = new int[this.numOfStates];
		for (int k = 0; k < this.numOfStates; k++) {
			actions[k] = this.chooseAction(k);
		}
		return new Policy(actions);
	}
}
