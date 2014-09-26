import java.util.Random;


public class QLearningAgent implements Agent {

	
	
	

	  private int numOfStates;
	  private int numOfActions;
	  private Random rand = new Random();
	  private int updateState;
	  private double QArray[][];
	  private static final double discount = 0.9;
	  private static final double rate = 0.1;
	  private static final double epsilon= 0.0;
	  
	  
	  public QLearningAgent(){
		  
	  }
	  
	
	@Override
	public void initialize(int numOfStates, int numOfActions) {
		// TODO Auto-generated method stub
		
	    this.QArray = new double[numOfStates][numOfActions];
	    this.numOfStates = numOfStates;
	    this.numOfActions = numOfActions;
	    System.out.println("The number of States in this world is: "+numOfStates);
	}

	@Override
	public int chooseAction(int state) {
		int action=0;
		
		if (rand.nextInt(101)<(epsilon*100)){
			action= rand.nextInt(numOfActions);
			
		}
		else {

			if(this.QArray[state][0]>this.QArray[state][1]&&this.QArray[state][0]>this.QArray[state][2]&&this.QArray[state][0]>this.QArray[state][3]){
				action=0;
			}
			else if(this.QArray[state][1]>this.QArray[state][0]&&this.QArray[state][1]>this.QArray[state][2]&&this.QArray[state][1]>this.QArray[state][3]){
				action=1;
			}
			else if(this.QArray[state][2]>this.QArray[state][0]&&this.QArray[state][2]>this.QArray[state][1]&&this.QArray[state][2]>this.QArray[state][3]){
				action=2;
			}
			else if(this.QArray[state][3]>this.QArray[state][0]&&this.QArray[state][3]>this.QArray[state][1]&&this.QArray[state][3]>this.QArray[state][2]){
				action=3;
			}
			else if(this.QArray[state][0]==this.QArray[state][1]){
				if(rand.nextBoolean()){
					action=0;
				}
				else{
					action=1;
				}	
			}
			else if(this.QArray[state][0]==this.QArray[state][2]){
				if(rand.nextBoolean()){
					action=0;
				}
				else{
					action=2;
				}	
			}
			else if(this.QArray[state][0]==this.QArray[state][3]){
				if(rand.nextBoolean()){
					action=0;
				}
				else{
					action=3;
				}	
			}
			else if(this.QArray[state][1]==this.QArray[state][2]){
				if(rand.nextBoolean()){
					action=1;
				}
				else{
					action=2;
				}	
			}
			else if(this.QArray[state][1]==this.QArray[state][3]){
				if(rand.nextBoolean()){
					action=1;
				}
				else{
					action=3;
				}	
			}
			else if(this.QArray[state][2]==this.QArray[state][3]){
				if(rand.nextBoolean()){
					action=2;
				}
				else{
					action=3;
				}	
			}
			else {
				action=0;
				System.out.println("Forgot Choose State! Change Code!");
			}
			
			
			
		}
		return action;
		
		
	}

	@Override
	public void updatePolicy(double reward, int action, int oldState,
			int newState) {
		
		
		this.QArray[oldState][action]=(this.QArray[oldState][action]+rate*(reward+discount*this.QArray[newState][chooseAction(newState)]-this.QArray[oldState][action]));
		
		
	}

	@Override
	public Policy getPolicy() {
	
		int[] actions = new int[this.numOfStates];
		for(int state=0;state<this.numOfStates;state++){
		int action=0;
		if(this.QArray[state][0]>this.QArray[state][1]&&this.QArray[state][0]>this.QArray[state][2]&&this.QArray[state][0]>this.QArray[state][3]){
			action=0;
		}
		else if(this.QArray[state][1]>this.QArray[state][0]&&this.QArray[state][1]>this.QArray[state][2]&&this.QArray[state][1]>this.QArray[state][3]){
			action=1;
		}
		else if(this.QArray[state][2]>this.QArray[state][0]&&this.QArray[state][2]>this.QArray[state][1]&&this.QArray[state][2]>this.QArray[state][3]){
			action=2;
		}
		else if(this.QArray[state][3]>this.QArray[state][0]&&this.QArray[state][3]>this.QArray[state][1]&&this.QArray[state][3]>this.QArray[state][2]){
			action=3;
		}
		else if(this.QArray[state][0]==this.QArray[state][1]){
			if(rand.nextBoolean()){
				action=0;
			}
			else{
				action=1;
			}	
		}
		else if(this.QArray[state][0]==this.QArray[state][2]){
			if(rand.nextBoolean()){
				action=0;
			}
			else{
				action=2;
			}	
		}
		else if(this.QArray[state][0]==this.QArray[state][3]){
			if(rand.nextBoolean()){
				action=0;
			}
			else{
				action=3;
			}	
		}
		else if(this.QArray[state][1]==this.QArray[state][2]){
			if(rand.nextBoolean()){
				action=1;
			}
			else{
				action=2;
			}	
		}
		else if(this.QArray[state][1]==this.QArray[state][3]){
			if(rand.nextBoolean()){
				action=1;
			}
			else{
				action=3;
			}	
		}
		else if(this.QArray[state][2]==this.QArray[state][3]){
			if(rand.nextBoolean()){
				action=2;
			}
			else{
				action=3;
			}	
		}
		else {
			action=0;
			System.out.println("Forgot Choose State! Change Code!");
		}
		actions[state]=action;
		}
		
		
		
		
		return new Policy(actions);
	}
	


}
