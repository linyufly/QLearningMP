import java.util.Random;
import java.util.ArrayList;

public class qLearningAgent implements Agent {
	
	Random rand = new Random();
	
	private int numOfStates;
	private int numOfActions;
	private double[][] listOfQvalue;
	
	private int state;
	
	private double reward;
	private int action;
	private int oldState;
	private int newState;
	
	
	public void initialize(int numOfStates, int numOfActions) {
	    this.numOfStates=numOfStates;
	    this.numOfActions=numOfActions;
	    this.listOfQvalue=new double[numOfStates][numOfActions];
	    
	    for (int i=0;i<numOfStates;i++) {
	        for (int j=0;j<numOfActions;j++) {
	          this.listOfQvalue[i][j] = 0.0;
	        }
	      }
	    }//make every value in the Qvalue matrix equal to 0.0
	
	private double maxQvalue(int state) {
	    double max=this.listOfQvalue[state][0];
	    for (int m=0;m<this.numOfActions;m++) {
	      if (this.listOfQvalue[state][m]>max) {
	         max=this.listOfQvalue[state][m];
	      }
	    }
	    return max;
	  }//why an array like max[numOfState] does not work well and a method maxQvalue has to be applied?
	
	public int chooseAction(int state) {
		ArrayList<Integer> bestAction = new ArrayList<Integer>();
		double epsilon=0.0;
	
	 if(this.rand.nextDouble()<epsilon) {
		int anAction=rand.nextInt(numOfActions);
		return anAction;
	 }
	 
	 else  {
		for (int n=0;n<numOfActions;n++) {
			if(this.listOfQvalue[state][n]==this.maxQvalue(state)) {
				bestAction.add(n);
			}	
	 }
		//int aRandom=1+ (int) Math.random()*(bestAction.size()-1);
		//return bestAction.get(aRandom+1);	
		return bestAction.get(rand.nextInt(bestAction.size()));
	}
	}//return selected action//
	
	public void updatePolicy(double reward, int action, int oldState, int newState) {
		final double alpha=0.1;
		final double gamma=0.9;
		this.listOfQvalue[oldState][action]=this.listOfQvalue[oldState][action]*(1.0-alpha)+alpha*(reward+gamma*this.maxQvalue(newState));
	}//process the reward//
	
	public Policy getPolicy() {
		int[] actions;
		actions=new int[this.numOfStates];
		for(state=0;state<this.numOfStates;state++) {
		for(int p=0;p<this.numOfActions;p++)	{	
			if(this.listOfQvalue[state][p]==this.maxQvalue(state)) {
			actions[state]=p;
			break;
			}
		}				
		}
		return new Policy(actions);
	}//create a txt named Policy.txt which includes actions//
}
