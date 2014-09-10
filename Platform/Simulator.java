/* Author: Mingcheng Chen */

public class Simulator {
  public static void main(String[] args) {
    if (args.length != 6) {
      System.out.println("Usage: java Simulator <world> <agent> <knows_thief> <steps> <episodes> <policy_output>");
      return;
    }

    World world;

    try {
      Class c = Class.forName(args[0]);
      world = (World)c.newInstance();
    } catch (Exception e) {
      System.out.println("Error: invalid world class");
      return;
    }

    Agent agent;

    try {
      Class c = Class.forName(args[1]);
      agent = (Agent)c.newInstance();
    } catch (Exception e) {
      System.out.println("Error: invalid agent class");
      return;
    }

    boolean thiefKnown;

    if (args[2].equals("y")) {
      thiefKnown = true;
    } else if (args[2].equals("n")) {
      thiefKnown = false;
    } else {
      System.out.println("Error: invalid knows_thief");
      return;
    }

    int steps;

    try {
      steps = Integer.parseInt(args[3]);
    } catch (Exception e) {
      System.out.println("Error: invalid steps");
      return;
    }

    int episodes;

    try {
      episodes = Integer.parseInt(args[4]);
    } catch (Exception e) {
      System.out.println("Error: invalid episodes");
      return;
    }

    String policyOutput = args[5];

    Simulator(world, agent, thiefKnown, steps, episodes, policyOutput).simulate();
  }

  public Simulator(World world, Agent agent, boolean thiefKnown, int steps, int episodes, String policyOutput) {
    this.world = world;
    this.agent = agent;
    this.thiefKnown = thiefKnown;
    this.steps = steps;
    this.episodes = episodes;
    this.policyOutput = policyOutput;
  }

  public void simulate() {
    if (this.thiefKnown) {
      this.world.beAwareOfThief();
    } else {
      this.world.beIgnorantOfThief();
    }

    this.agent.initialize(this.world.getNumberOfStates(), numOfActions);

    for (int episode = 0; episode < this.episodes; episode++) {
      this.world.initialize();

      int initialState = this.world.getInitialState();

      int robotRow = this.world.getNumberOfRows() - 1;
      int robotCol = 0;

      int thiefRow = -1;

      if (this.world.knowsThief()) {
        thiefRow = this.world.getThiefRow();
      }

      boolean hasP1 = true, hasP2 = true;

      int currState = initialState;

      for (int step = 0; step < this.steps; step++) {
        int action = this.agent.chooseAction(currState);

        int nextRobotRow = robotRow + directions[action][0];
        int nextRobotCol = robotCol + directions[action][1];

        if (nextRobotRow < 0 || nextRobotCol < 0 || nextRobotRow >= this.world.getNumberOfRows() ||
                                                    nextRobotCol >= this.world.getNumberOfCols()) {
          nextRobotRow = robotRow;
          nextRobotCol = robotCol;
        }

        robotRow = nextRobotRow;
        robotCol = nextRobotCol;

        this.world.evolve();

        if (this.world.knowsThief()) {
          thiefRow = this.world.getThiefRow();
        }

        double reward;

        // has nothing (needs to go back to the company)
        if (!hasP1 && !hasP2) {
          reward = 0.0;

          if (this.world.inCompany(robotRow, robotCol)) {  // load new packages
            hasP1 = true;
            hasP2 = true;
          }
        } else {  // has at least one package (successfully delivered one)
          if (hasP1 && this.world.hasCustomer(robotRow, robotCol) == 1) {
            hasP1 = false;
          }
          if (hasP2 && this.world.hasCustomer(robotRow, robotCol) == 2) {
            hasP2 = false;
          }

          if (!hasP1 && !hasP2) {  // successful delivery
            reward = rewardBySuccessfulDelivery;
          } else if (this.world.meetsThief(robotRow, robotCol)) {
            hasP1 = false;
            hasP2 = false;
            reward = -lossByThief;
          } else if (this.world.slipperiness(robotRow, robotCol) > 0.0) {
          }
        }
      }
    }
  }

  private static final int numOfActions = 4;
  private static final int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};  // NESW
  private static final double lossBySlipperiness = 0.5;
  private static final double lossByThief = 2.0;
  private static final double rewardBySuccessfulDelivery = 1.0;

  private World world;
  private Agent agent;
  private boolean thiefKnown;
  private int steps;
  private int episodes;
  private String policyOutput;
}
