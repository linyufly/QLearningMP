import java.util.ArrayList;
import java.util.Random;

public class QLearningAgent implements Agent {
	
	int nStates, nActions; // actions should be 4
	Random r;
	double discount = 0.9;
	double learningRate = 0.1;
	double epsRate = 0.0; 
	
	// keeps track of states (integers) and the action(integer)'s Q value(double)
	double[][] stateQValuesArr;
	
	@Override
	public void initialize(int numOfStates, int numOfActions) {
		nStates = numOfStates;
		nActions = numOfActions;
		stateQValuesArr = new double[numOfStates][numOfActions]; // init array
		// fill all initial state/action Q values to 0
		for (int i = 0; i < nStates; i++) {
			for (int j = 0; j < nActions; j++) {
				stateQValuesArr[i][j] = 0.0;
			}
		}
		r = new Random();
	}

	@Override
	public int chooseAction(int state) {
		// make a choice from the learned items so far about where to go, or maybe a random new spot for learning
		ArrayList<Integer> bestActionsList = new ArrayList<Integer>();
		double valueToBeat = -9999.0;
		int actionPick = -1;

		// if the random number is within the specified range, run normally and pick optimal action, otherwise, pick random
		double rRate = Math.random();
		if (rRate > epsRate) {
			// check for highest q value action
			for (int j = 0; j < nActions; j++) {
				if (stateQValuesArr[state][j] > valueToBeat) {
					bestActionsList.clear();
					bestActionsList.add(j);
					valueToBeat = stateQValuesArr[state][j];
				} else if(stateQValuesArr[state][j] == valueToBeat){
					bestActionsList.add(j);
				}
			}
		} else { 
			// learning time... do a random action!
			return r.nextInt(nActions);
		}
		
		// check for ties in "best" action selection, if there is, pick at random
		if (bestActionsList.size() > 1) {
			actionPick = bestActionsList.get(r.nextInt(bestActionsList.size()));
		} else if (bestActionsList.size() == 1) {
			actionPick = bestActionsList.get(0);
		}
		
		return actionPick;		
	}

	@Override
	public void updatePolicy(double reward, int action, int oldState, int newState) {
		// pick the best action possible from newState to go back and update Q value of oldState
		double maxFutureQValue = -9999.99;
		for (int j = 0; j < nActions; j++) {
			if(stateQValuesArr[newState][j] > maxFutureQValue) {
				maxFutureQValue = stateQValuesArr[newState][j]; 
			}
		}
		
		double oldValue = stateQValuesArr[oldState][action]; 
		// Q learning formula. update old Q value for action we selected to get to newState
		stateQValuesArr[oldState][action] = oldValue + learningRate*(reward + discount*maxFutureQValue - oldValue);
	}

	@Override
	public Policy getPolicy() {
		// return the final policy, for each state, action to take
		int[] bestActionPerState = new int[nStates]; 
		
		double bestScore;
		for (int i = 0; i < nStates; i++) {
			bestScore = -9999;
			for (int j = 0; j < nActions; j++) {
				if (stateQValuesArr[i][j] > bestScore) {
					bestActionPerState[i] = j;
					bestScore = stateQValuesArr[i][j];
				}
			}
			
		}
		
		return new Policy(bestActionPerState);
	}

}
