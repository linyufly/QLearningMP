
import java.util.Random;
import java.util.ArrayList;

public class QAgent implements Agent {
  public QAgent() {
    this.rand = new Random();
  }

  public void initialize(int numOfStates, int numOfActions) {
    this.qValue = new double[numOfStates][numOfActions];
    this.numOfStates = numOfStates;
    this.numOfActions = numOfActions;
    for(int i = 0;i<numOfStates;i++)
    	for(int j = 0; j<numOfActions;j++){
    		qValue[i][j]=0;
    	}
  }

  /*private double bestUtility(int state) {

  }*/
  

  
  public int chooseAction(int state) {
	  int randNum = rand.nextInt(100);
	  if(randNum<=epsilon*100)
		  return rand.nextInt(this.numOfActions);
	  double highestQ = qValue[state][0];
	  int chosenAction = 0;
	  for(int i = 1;i<numOfActions;i++){
		  if(qValue[state][i]>highestQ || (qValue[state][i]==highestQ && rand.nextInt(100) > 50))
		  {
				  highestQ = qValue[state][i];
				  chosenAction = i;
		  }
	  }
	  return chosenAction;
			  
  }

  private double findNextStepBest(int state){
	  double bestQ = qValue[state][0];
	  for(int i = 1;i<numOfActions;i++){
		  if(bestQ<qValue[state][i])
			  bestQ = qValue[state][i];
	  }
	  return bestQ;
  }
  
  public void updatePolicy(double reward, int action,
                           int oldState, int newState) {
	  double QbeforeUpdate = qValue[oldState][action];
	  qValue[oldState][action] = QbeforeUpdate  + rate*(reward + discount*findNextStepBest(newState) - QbeforeUpdate);
  }

  public Policy getPolicy() {
	  int[] actions = new int[numOfStates];
	  for(int i=0;i<numOfStates;i++){
		  actions[i] = chooseAction(i);
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
