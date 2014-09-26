/* Author: Mingcheng Chen */

import java.util.Random;
import java.util.ArrayList;



public class QLearningAgent implements Agent {

  public void initialize(int numOfStates, int numOfActions) {
    this.qValue = new double[numOfStates][numOfActions];
    this.numOfStates = numOfStates;
    this.numOfActions = numOfActions;
	this.rand = new Random();
  }

  public int chooseAction(int state) {
    gulu = rand.nextDouble();
//System.out.println("random double is " + gulu);
    if (gulu <= epsilon){
//System.out.println("random int is " + rand.nextInt(4));
      return rand.nextInt(4);
    }
    else {
//System.out.println("reached here");
		max = qValue[state][0];
        for (int i = 0; i < 4; i++){
			if (qValue[state][i]>max){
				max = qValue[state][i];
			}
		}
//System.out.println("max is " + max);
		int counter = 0;
		for (int i = 0; i < 4; i++){
            if (qValue[state][i] == max){
                counter = counter + 1;
            }
		}
//System.out.println("counter is " + counter);
        int[] choice;
		choice = new int[counter];
        int wired = 0;
//		System.out.println("state" + state);
        for (int i = 0; i < 4; i++){
            if  (qValue[state][i] == max) {
//				System.out.println("same values" + i);
                choice[wired] = i;
                wired ++;
            }
        }
//System.out.println("random counter" + rand.nextInt(counter));
//System.out.println("action" + choice[rand.nextInt(counter)]);
        return choice[rand.nextInt(counter)];
    }
  }

  public void updatePolicy(double reward, int action,
                           int oldState, int newState) {
//System.out.println("reward" + reward);
//System.out.println("newstate" +discount*(bestUtility(newState)));
//System.out.println("oldstate" +qValue[oldState][action]);
	qValue[oldState][action] = qValue[oldState][action] +  rate*(reward + discount*(bestUtility(newState))-qValue[oldState][action]);
  }

  public double bestUtility(int newState){
	com = qValue[newState][0];
	for (int i = 0; i < 4; i++){
		if (qValue[newState][i] > com){
			com = qValue[newState][i];
		}
	}
	return com;
  }

  public Policy getPolicy() {
	int[] actions = new int[this.numOfStates];
    temp = 0;
    for (int i = 0; i < this.numOfStates; i++){
        temp = qValue[i][0];
        comp = 0;
        for (int j = 0; j < 4; j++){
            if (temp < qValue[i][j]){
                temp = qValue[i][j];
                comp = j;
            }
        }
        actions[i] = comp;
    }
	return new Policy(actions);
  }

  private static final double discount = 0.9;
  private static final double rate = 0.1;
  private static final double epsilon = 0.0;

  private Random rand;
  private int numOfStates;
  private int numOfActions;
  private double[][] qValue;
  private double max;
  private int act;
//  private double choice;
  private double temp;
  private int comp;
  private int count;
  private double com;
  private double gulu;
}
