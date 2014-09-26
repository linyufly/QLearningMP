/* Author: yangsu4 */
import java.util.Random;
import java.util.*;

public class QLearningAgent implements Agent {

	public void initialize(int numOfStates, int numOfActions) {
	    this.qValue = new double[numOfStates][numOfActions];
	    this.numOfStates = numOfStates;
	    this.numOfActions = numOfActions;
	  }


	  public int chooseAction(int state) {
		 // int[] actions = new int[this.numOfStates];
		//  double max=-1; int[] maxValue={-1,-1,-1,-1};int k=0;
		  /*  for (int i = 0; i < this.numOfStates; i++) {
		    	for(int j=0;j<4;j++){
                                if(qValue[i][j]==max){
                                k++;
                                maxValue[k]=j;
                                }
		    		if(qValue[i][j]>max){
		    		    for(k=1;k<maxValue.length-1;k++){
		    		    	maxValue[k]=-1;
		    		    }
		    		    k=0;
                        max=qValue[i][j];
		    		    maxValue[k]=j;
		    	}
		    }
		    	for(k=0;k<4;k++){
		    		if(maxValue[k]==-1)
		    			break;
		    	}
		    	Random rand = new Random();
				  actions[i]=rand.nextInt(k-1); 
		    	}
		    	*/
		  int j=0;
		  double values[]={-1,-1,-1,-1};
		  for(j=0;j<this.numOfActions;j++){
			  values[j]=qValue[state][j];
		  }
		  Arrays.sort(values);
		  for( j=3; j>=0;j--){
			  if(values[j]!=values[3])
				  break;
		  }
		 // Random rand=new Random();
		  //since the epsilon=0, the random number x:[0,1] could not be smaller than epsilon, so we just have to take greedy method.
		  return (int)(j+1+Math.random()*(this.numOfActions-1-j-1+1));
	  }

	  
	  public void updatePolicy(double reward, int action,
	                           int oldState, int newState) {
		/*  for(i=0;i<this.numOfActions;i++){
			 value=qValue[newState][action]-qValue[oldState][action];
			  if(value>max){
				  max=value;
				  for(j=0;j<maxAction.length-1;j++){
					  maxAction[j]=-1;
				  }
				  j=0;
				  maxAction[j]=i;
				  j++;
			  }
			  if(value==max){
				  maxAction[j]=i;
				  j++;
			  }
		  }
		  Random rand = new Random();
		  i=rand.nextInt(j); 

		 qValue[oldState][i]=qValue[oldState][i]+
				 discount*(reward+rate*max);
         oldState=newState;
         */
		
		  int newAction=0;
		  if(oldState!=newState){
			newAction=chooseAction(newState);}
			 qValue[oldState][action]=(1-rate)*qValue[oldState][action]+rate*(reward+discount*qValue[newState][newAction]);
	  }
		  
		  
	 
	  public Policy getPolicy() {
		  int j=0;
		int[] actions = new int[this.numOfStates];
			  double max=-1;
			    for (int i = 0; i < this.numOfStates; i++) {
			    	for(j=0;j<4;j++){
			    		if(qValue[i][j]>max){
			    		  max=qValue[i][j];
			    	}
			    }
			    	actions[i]=j;
			    }
		    return new Policy(actions);
	  }


	  private static final double discount = 0.9;
	  private static final double rate = 0.1;
	  private static final double epsilon = 0;
	  
	  private Random rand;
	  private int numOfStates=100;
	  private int numOfActions=4;
	  private double[][] qValue;
}


