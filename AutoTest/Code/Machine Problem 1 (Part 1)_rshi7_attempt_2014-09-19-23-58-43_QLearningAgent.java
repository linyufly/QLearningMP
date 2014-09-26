import java.util.Random;
import java.util.ArrayList;

public class QLearningAgent implements Agent {
  public QLearningAgent() {
    this.randnum = new Random();
  }

  public void initialize(int nStates, int nActions) {
    // the default value of vQ is 0.0
    this.vQ = new double[nStates][nActions];
    this.nStates = nStates;
    this.nActions = nActions;
  }

  private double bestUtility(int state) {
    // calcualte the best Q value from current state
    double result = this.vQ[state][0];
    for (int i = 1; i < this.nActions; i++){
      if (this.vQ[state][i] > result) 
        result = this.vQ[state][i];
    }
    return result;
  }


  public int chooseAction(int state) {
    if (this.randnum.nextDouble() <= epsilon) {
      return this.randnum.nextInt(nActions);
    }
    // get the best possible Q value from current state
    double qMax = this.bestUtility(state);

    // this Arraylist saves all possible qMax actions and random choose one
    ArrayList<Integer> candidateActions = new ArrayList<Integer>();
    for (int i = 0; i < this.nActions; i++) {
      // if current action get the qMax, save it to possible list 
      if (this.vQ[state][i] == qMax) {
        candidateActions.add(i);
      }
    }
    // Random select one action from the save utility actions
    return candidateActions.get(this.randnum.nextInt(candidateActions.size()));
  }

  public void updatePolicy(double reward, int action,
                           int oldState, int newState) {
    // the equation of 21.8
    this.vQ[oldState][action] = (1.0 - rate)* this.vQ[oldState][action] + (rate * (reward + discount * this.bestUtility(newState)));
  }

  public Policy getPolicy() {
    // for each possible states, we have a list of 4 possible actions
    int[] actions = new int[this.nStates];

    for (int i = 0; i < this.nStates; i++) {
      double qMax = this.bestUtility(i);
      // save a possible action with same qMax, and choose random later
      ArrayList<Integer> candidateActions = new ArrayList<Integer>();
      for (int j = 0; j < this.nActions; j++) {
        if (this.vQ[i][j] == qMax) {
          candidateActions.add(j);
        }
      }
      actions[i] = candidateActions.get(this.randnum.nextInt(candidateActions.size()));
    }

    return new Policy(actions);
  }

  private static final double discount = 0.9;
  private static final double rate = 0.1;
  private static final double epsilon = 0.00;

  private double[][] vQ;
  private Random randnum;
  private int nStates;
  private int nActions;
}
