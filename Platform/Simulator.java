/* Author: Mingcheng Chen */

public class Simulator {
  public static void main(String[] args) {
    if (args.length != 6) {
      System.println("Usage: java Simulator <world> <agent> <knows_thief> <steps> <episodes> <policy_output>");
      return;
    }

    String worldClassName = args[0];
    String agentClassName = args[1];
    int numOfEpisodes = Integer.parseInt(args[2]);
    int numOfSteps = Integer.parseInt(args[3]);
  }
}
