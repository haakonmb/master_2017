package mordbad.master.dss;

import org.uncommons.watchmaker.framework.FitnessEvaluator;

import java.util.HashMap;
import java.util.List;

/**
 * Created by haakon on 20/10/16.
 */

    public class ActivityEvaluator implements FitnessEvaluator<int[]> {


    private int length = 90;
    private HashMap<Integer,Integer> weights;
    private int[] data;


    public ActivityEvaluator(int length, int[] dataFromQuestions){
        this.length = length;
        this.data = dataFromQuestions;

    }

    public ActivityEvaluator(int length, HashMap<Integer,Integer> weight){
        this.length = length;
        this.weights = weight;
    }

    @Override
    public double getFitness(int[] ints, List<? extends int[]> list) {
        //TODO: implement actual fitness-score in a good way
        //ATM only scores based on difference between highest possible sum and actual sum of integers.
        //Should use generated constraints in a natural way instead
        int matches =0;
        int max = length*ints.length;
        int candidate = 0;
        for(int n: ints){
            candidate+=n;

        }
        matches = max - candidate;


        return matches;
    }

    @Override
    public boolean isNatural() {
        return false;
    }


    private double individualScore(){

       return 0;
    }

    private double constraintScore(){

        return 0;
    }


}
