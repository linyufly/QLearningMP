//import java.util.Arrays;
import java.util.Random;

public class QLearningAgent implements Agent{
	public QLearningAgent(){
		this.rand =  new Random();
	}
	public void initialize(int numOfStates, int numOfActions) {
		this.iDoNotKnowWhyINeedThisArray = new double[numOfStates][numOfActions];
		this.numOfStates = numOfStates;
		this.numOfActions = numOfActions;
	}
	public int chooseAction(int state) {
		// here we cannot simply draw a coin;
		// what I want to do is find an action with the highest Q value;
		// when there are several actions whose Q values are the same then I want to draw a coin and choose the random one;
		double[] temp = new double[this.numOfActions];
		int i;
		for(i = 0 ; i < 4 ; i++){
			temp[i] = this.iDoNotKnowWhyINeedThisArray[state][i];
		}
		// try to find the action with the highest utility;
		int index = findMax( temp ); // find the index of the maximum value;
		
		int s = 0;  // mark the ending of the matrix;
		int[] mem = new int[this.numOfActions]; // this array is used to store the indice of elements which are equal to the maximum value;
		mem[s] = index;
		
		for(i = 0 ; i < this.numOfActions ; i++ ){
			if( temp[i] == temp[index] && i != index ){
				s = s + 1;
				mem[s] = i;
			}
		}
		
		if( rand.nextFloat() < epsilon ){
			return this.rand.nextInt(this.numOfActions);
		}else{
			return (mem[this.rand.nextInt(s+1)]);
		}
	}
	public int findMax( double[] temp ){
		int index = 0;
		for( int i = 0; i < temp.length ; i++ ){
			if(temp[index] < temp[i] ){
				index = i;
			}
		}
		return index;
	}
	public void updatePolicy(double reward, int action, int oldState, int newState) {
		double Max = this.iDoNotKnowWhyINeedThisArray[newState][0]; // find the max Q[s'][a'] with a';
		for(int i = 1 ; i < this.numOfActions ; i++ ){
			if(this.iDoNotKnowWhyINeedThisArray[newState][i] > Max){
				Max=this.iDoNotKnowWhyINeedThisArray[newState][i];
			}
		}		
		this.iDoNotKnowWhyINeedThisArray[oldState][action]=(1-this.alpha)*this.iDoNotKnowWhyINeedThisArray[oldState][action]+this.alpha*(reward+this.gamma*Max);
		return;
	}
	public Policy getPolicy() {
		int[] actions = new int[this.numOfStates];
		for (int i = 0; i < this.numOfStates; i++) {
			actions[i] = findOptAct(i);
		}
		return new Policy(actions);
	}
	private int findOptAct(int state){
		double[] temp = new double[this.numOfActions]; // temp stores the Q value of each action of specific state;
		for(int i = 0 ; i < this.numOfActions ; i++){
			temp[i] = this.iDoNotKnowWhyINeedThisArray[state][i];
		}
		double Max = temp[0];
		int    index = 0; // index is equal to direction;
		for(int i = 1; i < 4 ; i++ ){
			if(Max < temp[i]){
				index = i;
				Max = temp[i];
			}
		}
		return index;
	}
	
	private double gamma   = 0.9;
	private double alpha   = 0.1;
	private double epsilon = 0.0;
	
	private Random rand;
	private int numOfStates;
	private int numOfActions;
	private double[][] iDoNotKnowWhyINeedThisArray;
}
