package mordbad.master.dss;

import android.util.Log;

import org.uncommons.watchmaker.framework.factories.AbstractCandidateFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by haakon on 01/03/17.
 */

class DayFactory extends AbstractCandidateFactory {
    private static final String TAG = "DAYFACTORY";
    private List<HashMap<String,String>>[] possibilities;
    private int size = 0;
    private HashMap<String,String>[] candidate;


    public DayFactory(List<HashMap<String,String>>[] allofit, int size) {
        this.possibilities = allofit;
        this.size = size;

        candidate = (HashMap<String,String>[]) new HashMap[this.size];
    }

    @Override
    public HashMap<String,String>[] generateRandomCandidate(Random random) {
//        candidate = (HashMap<String,String[]) new Object[size];

        //Constructs a candidate by taking a random place from each of the possible categories of places and returning an array of equal size to the number of categories
        for(int i = 0; i < size; i++){
            if(possibilities[i].size() != 0){

                candidate[i] = possibilities[i].get(random.nextInt(possibilities[i].size()));
            }
            else{
                Log.d(TAG,"why is possitbilties.size 0? :" + possibilities[i].size());
            }


        }


        return candidate;
    }
}
