package mordbad.master.dss;

import org.uncommons.watchmaker.framework.factories.AbstractCandidateFactory;

import java.util.Random;

/**
 * Created by haakon on 01/11/16.
 */

public class ActivityFactory<T> extends AbstractCandidateFactory<int[]> {

    private int bound = 90;

    public ActivityFactory(int candidates){
        bound = candidates;

    }


    @Override
    public int[] generateRandomCandidate(Random random) {
        //TODO: make size of array be configurable easily
        int[] candidate= {random.nextInt(bound),random.nextInt(bound),random.nextInt(bound),random.nextInt(bound),random.nextInt(bound)};

        


        return candidate;
    }
}
