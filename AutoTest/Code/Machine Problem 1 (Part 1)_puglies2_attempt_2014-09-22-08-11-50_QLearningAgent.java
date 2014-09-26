import java.util.Random;
import java.util.ArrayList;

public class QLearningAgent implements Agent {
  private Random rand;
  
  public QLearningAgent() 
  {
    this.rand = new Random();
  }

  public void initialize(int numOfStates, int numOfActions) 
  {
    this.qValue = new double[numOfStates][numOfActions];
    this.numOfStates = numOfStates;
    this.numOfActions = numOfActions;
  }

  private double bestUtility(int state) {
    double bestUtility = 0;
    
    for (int action = 0; action < this.numOfActions; action++) {
      if (this.qValue[state][action] > bestUtility) {
        bestUtility = this.qValue[state][action];
      }
    }
    
    return bestUtility;
  }

  public int chooseAction(int state) 
  {
    if (rand.nextDouble() > epsilon) {
      int bestAction = 0;
      double bestUtility = 0;
      
      for (int action = 0; action < this.numOfActions; action++) {
        if (this.qValue[state][action] > bestUtility) {
          bestAction = action;
          bestUtility = this.qValue[state][action];
        }
        else if (this.qValue[state][action] == bestUtility) {
          if (rand.nextBoolean()) {
            bestAction = action;
          }
        }
      }
      
      return bestAction;
    }
    else {
      return rand.nextInt(this.numOfActions);
    }
  }

  public void updatePolicy(double reward, int action, int oldState, int newState) 
  {
    this.qValue[oldState][action] = this.qValue[oldState][action] +
        this.rate * (reward + (this.discount * this.bestUtility(newState)) - this.qValue[oldState][action]);
  }

  public Policy getPolicy() 
  {
    int actions[] = new int[this.numOfStates];
    
    for (int state = 0; state < this.numOfStates; state++) {
      double bestUtility = 0;
      actions[state] = 0;
      
      for (int action = 0; action < this.numOfActions; action++) {
        if (this.qValue[state][action] > bestUtility) {
          actions[state] = action;
          bestUtility = this.qValue[state][action];
        }
      }
    }
    
    return new Policy(actions);
  }

  private static final double discount = 0.9;
  private static final double rate = 0.1;
  private static final double epsilon = 0.00;

  private int numOfStates;
  private int numOfActions;
  private double[][] qValue;
}
