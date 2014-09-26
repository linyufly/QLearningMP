import java.util.Random;
import java.util.ArrayList;

public class QLearningAgent implements Agent
{
  private static final double discount=.9;
  private static final double rate=.1;
  private static final double epsilon=.05;

  private Random rand;
  private int numOfStates;
  private int numOfActions;
  private double[][] qValues;

  public QLearningAgent()
  {
    this.rand=new Random();
  }

  public void initialize(int numOfStates,int numOfActions)
  {
    this.numOfStates=numOfStates;
    this.numOfActions=numOfActions;
    this.qValues=new double[numOfStates][numOfActions];
  }

  public int chooseAction(int state)
  {
    double maxAction=this.qValues[state][0];
    int[] maxActionIdx=new int[this.numOfActions];
    int maxElem=1;

    if(this.rand.nextDouble()<epsilon)
      return this.rand.nextInt(this.numOfActions);

    for(int i=1;i<this.numOfActions;i++)
    {
      if(this.qValues[state][i]>maxAction)
      {
        maxAction=this.qValues[state][i];
        maxActionIdx[0]=i;
        maxElem=1;
      }
      else if(this.qValues[state][i]==maxAction)
      {
        maxActionIdx[maxElem]=i;
        maxElem+=1;
      }
    }
    
    return maxActionIdx[this.rand.nextInt(maxElem)];
  }  

  public void updatePolicy(double reward,int action,int oldState,int newState)
  {
    int maxAction=this.chooseAction(newState);
    this.qValues[oldState][action]=this.qValues[oldState][action]+rate*(reward+discount*this.qValues[newState][maxAction]-this.qValues[oldState][action]);
  }
  
  public Policy getPolicy()
  {
    int[] actions=new int[this.numOfStates];
    for(int i=0;i<this.numOfStates;i++)
      actions[i]=this.chooseAction(i);
    return new Policy(actions);
  }
}

