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

  public int chooseAction(int state) {
	  
	  int ACTION = rand.nextInt(this.numOfActions);
	  
	  rand.setSeed(System.nanoTime());
	  
	  for (int i = 0; i < this.numOfActions; i++) {
		  if (qValue[state][i] == qValue[state][ACTION]) {
			  ACTION =  (rand.nextBoolean()) ? i : ACTION;
		  }
		  else if (qValue[state][i] > qValue[state][ACTION]) {
			  ACTION = i;
		  }
	  }
	  
	  if (this.epsilon > rand.nextDouble()) {
		  ACTION = rand.nextInt(this.numOfActions);
	  }
	  
	  return ACTION;
	  
  }

  public void updatePolicy(double reward, int action,int oldState, int newState) {
	  
	  double MAXQVAL = qValue[newState][0];
	  
	  for (int i = 0; i < this.numOfActions; i++) {
		  if (qValue[newState][i] > MAXQVAL) {
			  MAXQVAL = qValue[newState][i];
		  }
	  }
	  qValue[oldState][action] = qValue[oldState][action] + this.rate*(reward + this.discount*MAXQVAL-qValue[oldState][action]);

  }

  public Policy getPolicy() {
	  
	  int[] actions = new int[this.numOfStates];
	  
	  rand.setSeed(System.nanoTime());
	  
	  for (int i = 0; i < this.numOfStates; i++) {
		  
		  actions[i] = 0;
		  for (int ii = 0; ii < this.numOfActions; ii++) {
 
			  if (qValue[i][ii] == qValue[i][actions[i]]) {
				  actions[i] = (rand.nextBoolean()) ? actions[i] : ii;
			  }
			  else if (qValue[i][ii] > qValue[i][actions[i]]) {
				  actions[i] = ii;
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
