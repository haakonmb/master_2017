package mordbad.master.dss;

import org.uncommons.watchmaker.framework.FitnessEvaluator;

import java.util.List;

/**
 * Created by haakon on 20/10/16.
 */

public class PlaceEvaluator implements FitnessEvaluator<String> {
    @Override
    public double getFitness(String s, List<? extends String> list) {
        return 0;
    }

    @Override
    public boolean isNatural() {
        return false;
    }
}
