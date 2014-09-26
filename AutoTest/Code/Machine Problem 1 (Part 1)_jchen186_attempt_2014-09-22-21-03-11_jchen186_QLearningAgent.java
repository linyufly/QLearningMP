/* Author: Mingcheng Chen */
import java.util.Random;
import java.util.ArrayList;

public class QLearningAgent implements Agent {

    //ArrayList<Integer> all_action;//紀錄每次走什麼路
    //int all_action[]; //紀錄每次走什麼路
    public QLearningAgent() {
        this.rand = new Random();
    }

    public void initialize(int numOfStates, int numOfActions) {
        this.qValue = new double[numOfStates][numOfActions];
        this.numOfStates = numOfStates;
        this.numOfActions = numOfActions;

        for (int i = 0; i < numOfStates; i++) {
            for (int j = 0; j < numOfActions; j++) {
                this.qValue[i][j] = 0.0;
            }
        }
        //all_action = new int[this.numOfStates];//初始陣列,有這麼多的states
        //all_action = new ArrayList<Integer>();

    }

    private double bestUtility(int state) {
        double rlt = this.qValue[state][0];
        for (int i = 1; i < this.numOfActions; i++) {
            if (this.qValue[state][i] > rlt) {
                rlt = this.qValue[state][i];//取得在這個state中選擇的action中最大的reward
            }
        }

        return rlt;
    }

    public int chooseAction(int state) {

        int rlt; //紀錄給policy

        if (this.rand.nextDouble() <= epsilon) { // rand=0.1x~0.9x
            /*
             當epsilon=0的時候不會進來, agent不敢嘗試
             epsilon越大,跑進來機率越高
             */
            //System.out.println("random");
            rlt = this.rand.nextInt(numOfActions);

            //this.all_action[state] = rlt;//random要被記錄到嗎??
            return rlt;
        }

        double maxQ = this.bestUtility(state); //最大的this.qValue[state][i]

        ArrayList<Integer> maxQ_list = new ArrayList<Integer>();

        for (int i = 0; i < this.numOfActions; i++) { //0-4
            //System.out.println("qValue: " + this.qValue[state][i]);
            if (this.qValue[state][i] == maxQ) {   
                maxQ_list.add(i);//加入這個路
            }

        }

        rlt = maxQ_list.get(this.rand.nextInt(maxQ_list.size()));

        //System.out.println("choose=" + rlt);
        //this.all_action[state] = rlt;
        //this.all_action.add(rlt);//紀錄這次走這步arraylist用
        return rlt; //有4個選擇就選0-3,有3個選擇就選0-2
    }

    public void updatePolicy(double reward, int action, int oldState, int newState) {
        //rate = alpha, discount = gamma
        this.qValue[oldState][action] *= (1.0 - rate);
        this.qValue[oldState][action] += rate * (reward + discount * this.bestUtility(newState));
    }

    /*
     The getPolicy method should return a Policy object, which species
     the action for each state. More details can be found in Policy.java.
     */
    public Policy getPolicy() {
        int[] actions = new int[this.numOfStates];

        for (int state = 0; state < this.numOfStates; state++) {
            double maxQ = this.bestUtility(state); 
            for (int i = 0; i < this.numOfActions; i++) {
                if (this.qValue[state][i] == maxQ) {
                    actions[state] = i;
                    break;
                }
            }
        }
        return new Policy(actions);

        //轉arraylist到int array
//        int[] rlt = new int[this.all_action.size()];
//        for (int i = 0; i < rlt.length; i++) {
//            rlt[i] = this.all_action.get(i);
//        }
//        return new Policy(rlt);
        //(global)all_action int[]用
//        return new Policy(this.all_action);
    }

    private static final double discount = 0.9;
    private static final double rate = 0.1; 
    private static final double epsilon = 0;

    private int numOfStates;
    private int numOfActions;
    private double[][] qValue;
    private Random rand;
}
