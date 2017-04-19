package mordbad.master.dss;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by haakon on 10/04/17.
 */

public class Probabilitator {
    Double[] prior_probabilities;
    Map<String, Double[]> evidence_probabilities;
    int[] data;
    public double[] probabilities = new double[11];
    public DefaultHashMap<Integer, Double> map_activities_to_probability_for_yes = new DefaultHashMap<>(0.00001);


    public Probabilitator(Double[] priors, Map<String,Double[]> evidence, int[] data){
        this.prior_probabilities = priors;
        this.evidence_probabilities = evidence;
        this.data= data;
        calculateProbabilities();
        normalizeProbabilites();
        convertProbabilitesToPositions();

    }

    private void normalizeProbabilites() {
        //TODO: implement normalization so things arent out of wack.
    }

    private void convertProbabilitesToPositions() {
        int[][] allmaps = TouristEnums.all_maps;

        for(int map_value = 0; map_value< allmaps.length; map_value++){
            int[] map = allmaps[map_value];

            for(int map_position= 0; map_position< map.length; map_position++){
                map_activities_to_probability_for_yes.put(map[map_position], probabilities[map_position]);

            }

        }

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
