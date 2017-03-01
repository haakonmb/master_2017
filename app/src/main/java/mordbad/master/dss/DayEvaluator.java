package mordbad.master.dss;

import org.uncommons.watchmaker.framework.FitnessEvaluator;

import java.util.HashMap;
import java.util.List;

/**
 * Created by haakon on 01/03/17.
 */

class DayEvaluator implements FitnessEvaluator<HashMap<String,String>[]> {
    @Override
    public double getFitness(HashMap<String, String>[] hashMaps, List<? extends HashMap<String, String>[]> list) {
        return 0;
    }

    @Override
    public boolean isNatural() {
        return false;
    }
}
