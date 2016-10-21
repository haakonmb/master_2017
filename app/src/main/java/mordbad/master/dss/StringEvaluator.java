package mordbad.master.dss;

import org.uncommons.watchmaker.framework.FitnessEvaluator;

import java.util.List;

/**
 * Created by haakon on 21/10/16.
 */



public class StringEvaluator implements FitnessEvaluator<String> {

    private final String targetString = "HELLO WORLD";



    @Override
    public double getFitness(String s, List<? extends String> list) {
        int matches = 0;
        for(int i = 0; i< s.length(); i++){
            if(s.charAt(i) == targetString.charAt(i)){
                ++matches;
            }

        }
        return matches;




    }

    @Override
    public boolean isNatural() {
        return true;
    }
}
