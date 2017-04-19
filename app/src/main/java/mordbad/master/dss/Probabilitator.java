package mordbad.master.dss;

import java.util.Map;

/**
 * Created by haakon on 10/04/17.
 */

public class Probabilitator {
    double[] prior_probabilities;
    Map<Integer, Double> evidence_probabilities;
    int[] data;
    double[] probabilities = new double[11];

    public Probabilitator(double[] priors, Map<Integer,Double> evidence, int[] data){
        this.prior_probabilities = priors;
        this.evidence_probabilities = evidence;
        this.data= data;
        calculateProbabilities();

    }

    private void calculateProbabilities() {
        
    }


}
