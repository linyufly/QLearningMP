/* Author: Mingcheng Chen */

import java.util.Random;

public class Tester {
  public Tester() {
    this.rand = new Random();
    this.solution = new StandardQLearningAgent();
  }

  private void runTest(Agent agent, int numOfStates, int numOfActions, int numOfUpdates) {
    agent.initialize(numOfStates, numOfActions);
    this.solution.initialize(numOfStates, numOfActions);

    for (int i = 0; i < numOfUpdates; i++) {
      double reward = rand.nextDouble() * 2 - 1.0;
      int action = rand.nextInt(numOfActions);
      int oldState = rand.nextInt(numOfStates);
      int newState = rand.nextInt(numOfStates);

      agent.updatePolicy(reward, action, oldState, newState);
      this.solution.updatePolicy(reward, action, oldState, newState);
    }

    Policy policy = agent.getPolicy();

    for (int i = 0; i < numOfStates; i++) {
      int bestAction = policy.getAction(i);

      for (int j = 0; j < numOfActions; j++) {
        if (this.solution.getQValue(i, j) > this.solution.getQValue(i, bestAction) + epsilon) {
          System.out.println("Test Failed.");
          System.exit(1);
        }
      }
    }

    System.out.println("Test Passed!");
  }

  public static void main(String[] args) {
    if (args.length != 4) {
      System.out.println("Usage: java Tester <agent> <num_states> <num_actions> <num_updates>");
      System.exit(-1);
    }

    Agent agent = null;

    try {
      Class c = Class.forName(args[0]);
      agent = (Agent)c.newInstance();
    } catch (Exception e) {
      System.out.println("Error: invalid agent class");
      System.exit(-1);
    }

    int numOfStates = 0, numOfActions = 0, numOfUpdates = 0;

    try {
      numOfStates = Integer.parseInt(args[1]);
    } catch (Exception e) {
      System.out.println("Error: Invalid num_states");
      System.exit(-1);
    }

    try {
      numOfActions = Integer.parseInt(args[2]);
    } catch (Exception e) {
      System.out.println("Error: Invalid num_actions");
      System.exit(-1);
    }

    try {
      numOfUpdates = Integer.parseInt(args[3]);
    } catch (Exception e) {
      System.out.println("Error: Invalid num_updates");
      System.exit(-1);
    }

    (new Tester()).runTest(agent, numOfStates, numOfActions, numOfUpdates);
  }

  private Random rand;
  private StandardQLearningAgent solution;

  private static final double epsilon = 1e-6;
}
