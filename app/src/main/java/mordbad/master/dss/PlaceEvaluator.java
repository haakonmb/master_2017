package mordbad.master.dss;

import org.uncommons.watchmaker.framework.FitnessEvaluator;

import java.util.HashMap;
import java.util.List;

/**
 * Created by haakon on 20/10/16.
 */

public class PlaceEvaluator implements FitnessEvaluator<int[]> {


    private int length = 90;
    private HashMap<Integer,Integer> weights;


    public PlaceEvaluator(int length){
        this.length = length;

    }

    public PlaceEvaluator(int length, HashMap<Integer,Integer> weight){
        this.length = length;
        this.weights = weight;
    }

    @Override
    public double getFitness(int[] ints, List<? extends int[]> list) {
        //TODO: implement actual fitness-score in a good way
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
}