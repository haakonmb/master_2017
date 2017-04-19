package mordbad.master.dss;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by haakon on 10/04/17.
 */

public class Probabilitator {
    Double[] prior_probabilities;
    Map<String, Double[]> evidence_probabilities;
    int[] data;
    double[] probabilities = new double[11];

    public Probabilitator(Double[] priors, Map<String,Double[]> evidence, int[] data){
        this.prior_probabilities = priors;
        this.evidence_probabilities = evidence;
        this.data= data;
        calculateProbabilities();

    }

    private void calculateProbabilities() {
        ArrayList<Double[]> everything = new ArrayList<>();
//        everything.add(prior_probabilities);
        for(int i = 0; i < data.length; i++){

            String key = Integer.toString(i+1) + Integer.toString(data[i]);
            everything.add(evidence_probabilities.get(key));

        }
        for(int i =0 ;i < prior_probabilities.length; i++){
            double result = prior_probabilities[i];

            for(Double[] mult: everything){
                result = mult[i] * result;
            }

            probabilities[i] = result;
        }


    }


}
