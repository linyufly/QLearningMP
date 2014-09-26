import java.util.Random;

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
      double retVal = qValue[state][0];
      for (int i = 1; i < numOfActions; i++){
              double newVal = qValue[state][i];
              if (retVal < newVal){
                      retVal = newVal;
              }
      }
	  return retVal;
  }

  public int chooseAction(int state) {
	  if (rand.nextDouble() < epsilon){
		  return rand.nextInt(numOfActions);
	  }
	  double best = qValue[state][0];
	  int retVal[] = new int[numOfActions];
	  int tieNum = 1;
	  retVal[0] = 0;
	  for (int a = 1; a < this.numOfActions; a++){
		  if (qValue[state][a] > best){
			  best = qValue[state][a];
			  retVal[0] = a;
			  tieNum = 1;
		  }
		  else{
			  if (qValue[state][a] == best){
				  retVal[tieNum] = a;
				  tieNum++;
			  }
		  }
	  }
	  return retVal[rand.nextInt(tieNum)];
	  
  }

  public void updatePolicy(double reward, int action,
                           int oldState, int newState) {
	  qValue[oldState][action] = qValue[oldState][action] + rate * (reward + discount*bestUtility(newState)-qValue[oldState][action]);		  
	 
  }

  public Policy getPolicy() {
	  int[] actions = new int[this.numOfStates];
	  for (int i = 0; i < this.numOfStates; i++) {
		  actions[i] = chooseAction(i);		  
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
