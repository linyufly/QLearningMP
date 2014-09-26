import java.util.Random;
import java.util.ArrayList;

class QLearningAgent implements Agent {
    private static final double epsilon  = 0.0;
    private static final double rate     = 0.1;
    private static final double discount = 0.9;

    private int numOfActions;
    private int numOfStates;

    private Random rand;

    private int[]      actionsInState; //For each state, which is the best action?
    private double[][] Q; //Sorry, here it is stored as Q(state, action)
    
    public void initialize(int numOfStates, int numOfActions) {
        this.numOfActions = numOfActions;
        this.numOfStates  = numOfStates;
        actionsInState    = new int[this.numOfStates];
        Q                 = new double[this.numOfStates][this.numOfActions];
        rand              = new Random(); //Should be in constructor; don't want to write a constructor.
    }

    public int chooseAction(int state) {
        int maxAction  = 0;
        double maxQ    = Q[state][0];
        ArrayList<Integer> list = new ArrayList<Integer>();

        //Find the maxQ
        for (int j = 1; j < numOfActions; j++) {
            if (Q[state][j] > maxQ) {
                maxQ = Q[state][j];
            }
        }

        //Find all actions for the given state that have the same maxQ
        for (int j = 0; j < numOfActions; j++) {
            if (Q[state][j] == maxQ) {
                list.add(new Integer(j));
            }
        }

        if (list.size() > 1) { //Pick randomly
            int index    = rand.nextInt(list.size());
            Integer item = (Integer) list.get(index);
            maxAction    = item.intValue();
        } else if (list.size() == 1) {               //Pick the only one available
            Integer item = (Integer) list.get(0);
            maxAction    = item.intValue();
        } else {
            System.out.println("Did not find a maximum value!!!");
            System.exit(0);
        }

        if (rand.nextDouble() < epsilon) { //Choose a step different from the current chosen best.
            int newRandom;
            do {
                newRandom = rand.nextInt(numOfActions);
            } while (newRandom == maxAction);
            maxAction = newRandom;
        }
        
        return maxAction;
    }
    
    public void updatePolicy(double reward, int action, int oldState, int newState) {
        double maxQ = Q[newState][0];
        for (int i = 1; i < numOfActions; i++) {
            if (Q[newState][i] > maxQ) {
                maxQ = Q[newState][i];
            }
        }

        Q[oldState][action] = Q[oldState][action] + rate * (reward + discount * maxQ - Q[oldState][action]); //Q-update
    }

    public Policy getPolicy() {
        updateActionsInState();
        Policy p = new Policy(actionsInState);
        return p;
    }

    private void updateActionsInState() { //Choose the best action for each state and store it in actionsInState
        for (int i = 0; i < numOfStates; i++) {
            int maxAction = 0;
            for (int j = 1; j < numOfActions; j++) {
                if (Q[i][j] > Q[i][maxAction]) {
                    maxAction = j;
                }
            }
            actionsInState[i] = maxAction;
        }
    }
}
