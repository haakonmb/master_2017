package mordbad.master.dss;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by haakon on 25/04/17.
 */
public class ProbabilitatorTest {

    int[] stats = {1,1,2,1,1,1};
    HashMap<String, Double[]> lookup_probability;
    Double[] priors_from_data = new Double[11];

    private void loadDataFromAsset() {
//        ActiveAndroid.beginTransaction();
        String cvsSplitBy = ",";
        lookup_probability = new HashMap<>();
       // priors_from_data = new Double[11];

        String[] apriori = Data.apriori;

        String[] evidence = Data.evidence;


        Double[] apriori_double= new Double[11]               ;

        for(int i=0; i< apriori.length; i++){
            apriori_double[i] = Double.parseDouble( apriori[i]);
//            System.out.println(apriori_double[i]);

        }

        for(String s: evidence){
            String[] data = s.split(cvsSplitBy);
            String key = data[0] + data[1];


            List<Double> tmp = new ArrayList<>();
            for(int i = 2; i < data.length; i++){
                tmp.add(Double.parseDouble(data[i]));

            }


//              Making the value into an array of the correct type and size automagically and putting it in the hashmap
            lookup_probability.put(key,tmp.toArray(new Double[tmp.size()]));
        }

        priors_from_data = apriori_double;

    }


    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {

    }


    @Test
    public void checkProbabilities() throws Exception {

        loadDataFromAsset();
        for(Double d: priors_from_data){

        }


        Probabilitator probabilitator = new Probabilitator(priors_from_data, lookup_probability, stats);
        for(double d : probabilitator.probabilities){

            System.out.println(d);
        }

    }

}