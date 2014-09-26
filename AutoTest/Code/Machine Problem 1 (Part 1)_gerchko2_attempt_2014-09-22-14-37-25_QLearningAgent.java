import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QLearningAgent implements Agent {
	private final static double EPSILON=0.0;
	private final static double DOUBLE_EQUAL_MARGIN=0.000001; 
	private final static double LEARNING_RATE=0.1;
	private final static double DISCOUNT_RATE=0.9;
	private Random rand;
	private int numOfStates;
	private int numOfActions;
	//private int timesVisited[];
	//private double totalReward[];
	private double Q[][];
	//private int Ttimes[][][];
	//private int Ttotal[][];
	  
	public QLearningAgent() {
		this.rand = new Random();
	}
	
	public void initialize(int numOfStates, int numOfActions) {
		this.numOfStates = numOfStates;
		this.numOfActions = numOfActions;
	    //this.timesVisited=new int[this.numOfStates];
	    //this.totalReward=new double[this.numOfStates];
	    this.Q=new double[this.numOfStates][];
	    for(int i=0;i<this.numOfStates;i++) this.Q[i]=new double[this.numOfActions];
	    
	    /*this.Ttimes=new int[this.numOfStates][][];
	    this.Ttotal=new int[this.numOfStates][];
	    for(int i=0;i<this.numOfStates;i++){
	    	this.Ttimes[i]=new int[this.numOfActions][];
	    	this.Ttotal[i]=new int[this.numOfActions];
	    	for(int j=0;j<this.numOfActions;j++){
	    		this.Ttimes[i][j]=new int[this.numOfStates];
	    	}
	    }*/
	}
	
	public int chooseAction(int state) {
		double dice= rand.nextDouble();
		if(dice <= EPSILON){
			return rand.nextInt(this.numOfActions);
		}
		List<Integer> bestActions=getListOfBestActions(state);
		//System.out.println(bestActions.size());
		return bestActions.get(rand.nextInt(bestActions.size()));
	}
	
	private List<Integer> getListOfBestActions(int state){
		double bestQ=Q[state][0];
		List<Integer> bestOptions=new ArrayList<Integer>();
		bestOptions.add(0);
		for(int i=1;i<this.numOfActions;i++){
			if(-DOUBLE_EQUAL_MARGIN < bestQ-Q[state][i] && bestQ-Q[state][i] < DOUBLE_EQUAL_MARGIN){ //are equal
				bestOptions.add(i);
			}else if(Q[state][i] > bestQ){
				bestOptions=new ArrayList<Integer>();
				bestOptions.add(i);
				bestQ=Q[state][i];
			}
		}
		return bestOptions;
	}
	private int getBestAction(int state){
		return getListOfBestActions(state).get(0);
	}
	
	public void updatePolicy(double reward, int action, int oldState, int newState) {
		/*totalReward[newState]+=reward;
		timesVisited[newState]++;
		Ttotal[oldState][action]++;
		Ttimes[oldState][action][newState]++;*/
		
		Q[oldState][action]+=LEARNING_RATE*( reward + DISCOUNT_RATE*Q[newState][getBestAction(newState)] - Q[oldState][action]);
		//Q[oldState][action]=expectedReward(oldState) + DECAY*expectationQSA(oldState,action);
	}
	/*private double expectedReward(int state){
		if(timesVisited[state]==0) return 0;
		return totalReward[state]*1.0/timesVisited[state];
	}
	private double expectationQSA(int state,int action){
		if(Ttotal[state][action]==0) return 0;
		double total=0;
		for(int i=0;i<this.numOfStates;i++){
			total+=Ttimes[state][action][i]*1.0/Ttotal[state][action] * Q[i][getBestAction(i)];
		}
		return total;
	}*/
	
	public Policy getPolicy() {
		int actions[]=new int[this.numOfStates];
		for(int i=0;i<this.numOfStates;i++){
			actions[i]=this.getBestAction(i);
		}
		return new Policy(actions);
	}
}