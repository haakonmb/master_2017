package mordbad.master.dss;

import org.uncommons.watchmaker.framework.FitnessEvaluator;

import java.util.List;

/**
 * Created by haakon on 20/10/16.
 */

    public class ActivityEvaluator implements FitnessEvaluator<int[]> {


    private int length = 90;
    private int[] data;
    private DefaultHashMap<Integer, Double> weights;

    private DecisionConstraint<int[]>[] constraints = ActivityProblem.constraints;


    public ActivityEvaluator(int length, int[] dataFromQuestions){
        this.length = length;
        this.data = dataFromQuestions;

    }

    public ActivityEvaluator(int length, DefaultHashMap<Integer, Double> weight){
        this.length = length;
        this.weights = weight;
    }

    @Override
    public double getFitness(int[] ints, List<? extends int[]> list) {
        double score = 0;

        //How well does this assignment fulfill all the generated constraints?
        score += constraintFulfillment(ints);


        //in the absence of constraint-fulfillment, how good is this assignment?
        score += baseCaseFulfillment(ints);

        //Initial thought: we dont need negative numbers because this fitness-function is natural
        //Follow-up thought: that has nothing to do with it and you lose differential information about assignments by assigning a score.
        //Dont throw away information
//        if(score < 0)
//            score = 0;




        return score;
    }

    private double baseCaseFulfillment(int[] ints) {
        double score = 0;

        for(int i: ints){
           score = score + weights.get(i);

        }
        return score;
    }

    private double constraintFulfillment(int[] ints) {
        double result =0;
        for(DecisionConstraint<int[]> constraint : constraints){
            result =  result + constraint.constraint(ints);



        }
        return result;
    }

    @Override
    public boolean isNatural() {
        return false;
    }



}
