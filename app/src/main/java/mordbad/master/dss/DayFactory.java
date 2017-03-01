package mordbad.master.dss;

import org.uncommons.watchmaker.framework.factories.AbstractCandidateFactory;

import java.util.List;
import java.util.Random;

/**
 * Created by haakon on 01/03/17.
 */

class DayFactory<T> extends AbstractCandidateFactory {
    private List<T>[] possibilities;
    private int size = 0;

    public DayFactory(List<T>[] allofit, int size) {
        this.possibilities = allofit;
        this.size = size;
    }

    @Override
    public T[] generateRandomCandidate(Random random) {
        T[] candidate;
        candidate = (T[]) new Object[size];

        //Constructs a candidate by taking a random place from each of the possible categories of places and returning an array of equal size to the number of categories
        for(int i = 0; i < size; i++){
            candidate[i] = possibilities[i].get(random.nextInt(possibilities[i].size()));


        }


        return candidate;
    }
}
