import java.util.Random;
import java.util.ArrayList;

public class QLearningAgent implements Agent {
  private Random rand;
public QLearningAgent() {
    this.rand = new Random();
  }

  public void initialize(int numOfStates, int numOfActions) {
    this.qValue = new double[numOfStates][numOfActions];
    this.numOfStates = numOfStates;
    this.numOfActions = numOfActions;
  }

  private double bestUtility(int state) {
	  double bestAction=-9999;
	  for(int i=1; i< this.numOfActions; i++ ){
		  if(this.qValue[state][i]>bestAction){
			  bestAction=this.qValue[state][i];
		  }
	  }
	  return bestAction;
  }

  public int chooseAction(int state) {
	  if(this.rand.nextFloat()>epsilon){
		  return chooseBestAction(state);
	  }
	  else{
		  return this.rand.nextInt(this.numOfActions);
	  }
  }
  
  public int chooseBestAction(int state){
	  int bestAction=0;
	  for(int i=1; i< this.numOfActions; i++ ){
		  if(this.qValue[state][i]>this.qValue[state][bestAction]){
			  bestAction=i;
		  }
		  else if(this.qValue[state][i]==this.qValue[state][bestAction]){
			  if(this.rand.nextFloat()>.5){
				  bestAction=i;
			  }
		  }
	  }
	  return bestAction;
  }
  
  public void updatePolicy(double reward, int action,
                           int oldState, int newState) {
	  this.qValue[oldState][action]+=rate*(reward+discount*(bestUtility(newState))-this.qValue[oldState][action]);
  }

  public Policy getPolicy() {
	  int[] actions=new int[this.numOfStates];
	  for(int i=0; i<this.numOfStates; i++){
		  actions[i]=this.chooseBestAction(i);
	  }
	  Policy output=new Policy(actions);
	  return output;
  }

  private static final double discount = 0.9;
  private static final double rate = 0.1;
  private static final double epsilon = 0;

  private int numOfStates;
  private int numOfActions;
  private double[][] qValue;
}