import java.util.Random;
import java.util.ArrayList;

public class QLearningAgent implements Agent{
  public QLearningAgent() {
    this.rand = new Random();
  }

  public void initialize(int numOfStates, int numOfActions){
  	this.numOfStates = numOfStates;
  	this.numOfActions = numOfActions;
  	this.qVal = new double[numOfStates][numOfActions];
  }

  public int chooseAction(int state){
    ArrayList<Integer> tie = new ArrayList<Integer>();
    int bestAction = 0;
    if(rand.nextFloat() < epsilon){
      return rand.nextInt(4);
    }
    else{
      for(int i  = 0; i < 4; i = i + 1){
        if(this.qVal[state][i] >= this.qVal[state][bestAction])
          bestAction = i;
      }
      for(int i = 0; i < 4; i = i + 1){
        if(this.qVal[state][i] == this.qVal[state][bestAction] && i != bestAction){
          tie.add(i);
        }
      }
      tie.add(bestAction);
      if(tie.size() > 1){
        bestAction = tie.get(rand.nextInt(tie.size()));
      }
      return bestAction;
    }
  }

  public void updatePolicy(double reward, int action, int oldState, int newState){
    this.qVal[oldState][action] = this.qVal[oldState][action] + (rate*(reward + (discount*bestUtility(newState)) - this.qVal[oldState][action]));
  }

  public Policy getPolicy(){
  	int[] actions = new int[this.numOfStates];
  	for(int i = 0; i < this.numOfStates; i = i+1){
  		actions[i] = chooseAction(i);
  	}
  	return new Policy(actions);
  }

  private double bestUtility(int state) {
    if(this.qVal[state][0] >= this.qVal[state][1] && this.qVal[state][0] >= this.qVal[state][2] && this.qVal[state][0] >= this.qVal[state][3])
      return this.qVal[state][0];
    else if(this.qVal[state][1] >= this.qVal[state][2] && this.qVal[state][1] >= this.qVal[state][3])
      return this.qVal[state][1];
    else if(this.qVal[state][2] >= this.qVal[state][3])
      return this.qVal[state][2];
    else
      return this.qVal[state][3];
  }

  private Random rand;
  private int numOfStates;
  private int numOfActions;
  private double[][] qVal;
  private static final double discount = 0.9;
  private static final double rate = 0.1;
  private static final double epsilon = 0.0;
}