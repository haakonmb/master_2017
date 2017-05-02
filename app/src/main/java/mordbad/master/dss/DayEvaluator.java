package mordbad.master.dss;

import org.uncommons.watchmaker.framework.FitnessEvaluator;

import java.util.HashMap;
import java.util.List;

/**
 * Created by haakon on 01/03/17.
 */

class DayEvaluator implements FitnessEvaluator<HashMap<String,String>[]> {
    private DecisionConstraint[] decisionConstraint = DayProblem.constraints;


    public DayEvaluator(){

    }

    @Override
    public double getFitness(HashMap<String, String>[] hashMaps, List<? extends HashMap<String, String>[]> list) {
        double score = 0;

        //How well does this assignment fulfill all the generated constraints?
        score += constraintFulfillment(hashMaps);


        //in the absence of constraint-fulfillment, how good is this assignment?
        score += baseCaseFulfillment(hashMaps);

        //Initial thought: we dont need negative numbers because this fitness-function is natural
        //Follow-up thought: that has nothing to do with it and you lose differential information about assignments by assigning a score.
        //Dont throw away information
//        if(score < 0)
//            score = 0;




        return score;
    }

    private double baseCaseFulfillment(HashMap<String, String>[] hashMaps) {
        double result =5;

        for(HashMap<String, String> hm: hashMaps){

            //something about adding up scores here
        }

        if(result < 0){
            result =0;
        }

        return result;
    }

    private double constraintFulfillment(HashMap<String, String>[] hashMaps) {
        double result =5;
        for(DecisionConstraint c: decisionConstraint){
            //how to check for fulfillment?
            result += c.constraint(hashMaps);

        }
        if(result < 0){
            result = 0;
        }

        return result;
    }

    @Override
    public boolean isNatural() {
        return false;
    }
}
