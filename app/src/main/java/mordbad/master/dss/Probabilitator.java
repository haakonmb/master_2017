package mordbad.master.dss;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;

/**
 * Created by haakon on 10/04/17.
 */

public class Probabilitator {
    Double[] prior_probabilities;
    Map<String, Double[]> evidence_probabilities;
    int[] data;
    public double[] probabilities = new double[11];
    private Double[] probabilities_object = new Double[11];
    public Double[] probabilities_scaled = new Double[11];
    public DefaultHashMap<Integer, Double> map_activities_to_probability_for_yes = new DefaultHashMap<>(-1.0);


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

        //get the highest numbers
        Integer[] sortedIndex    =    getSortedIndexOfProbabilities();

        //Set the highest to 1 and scale the rest accordingly to diff between them and 1
        scaleNumbers(sortedIndex);
//        for(int i = sortedIndex.length-1 ; i> -1; i--){
//            System.out.println("Sorted " +probabilities_scaled[sortedIndex[i]]);
//        }


    }

    private void scaleNumbers(Integer[] sortedIndex) {

        //Om vi skal sette høyeste verdi til 0.9 må vi finne et tall å skalere den med.
        //(maks / x = 0.9) == (x = maks / 0.9)

        //angi maks på skalaen
        double highestScale = 0.9;

        double scaleAdjuster = 0.1 - probabilities[sortedIndex[0]];
        //finn skalaraen
        double scaleFactor = highestScale/probabilities[sortedIndex[sortedIndex.length-1]];

//        System.out.println("Scaleadjuster" + scaleAdjuster);
        //Bruk den på alle variablene
        for(int i =0 ; i < probabilities_object.length; i++){

            probabilities_scaled[i] = probabilities_object[i] * scaleFactor;
            probabilities_scaled[i] = probabilities_scaled[i] + scaleAdjuster;
        }


    }

    private Integer[] getSortedIndexOfProbabilities() {
        ArrayIndexComparator comparator = new ArrayIndexComparator(probabilities_object);
        Integer[] index = comparator.createIndexArray();

        Arrays.sort(index,comparator);
//        Arrays.

        return index;
    }

    private void convertProbabilitesToPositions() {
        int[][] allmaps = TouristEnums.all_maps;

        for(int map_value = 0; map_value< allmaps.length; map_value++){
            int[] map = allmaps[map_value];

            for(int map_position= 0; map_position< map.length; map_position++){
//                System.out.println(CheatData.place_candidates[map[map_position]] + ":"+ Double.toString(probabilities_scaled[map_value]));
                map_activities_to_probability_for_yes.put(map[map_position], probabilities_scaled[map_value]);

            }

        }

    }

    private void calculateProbabilities() {
        ArrayList<Double[]> everything = new ArrayList<>();

        //Hent ut sansynligheten for ja for alle aktivitetene for hvert datapunkt. Ie, få arrayet som tilsvarer sansynlighet for ja på alle aktiviteter gitt eksempelvis alder = 18-30 og putt dette arrayet i everything.
        //Oppsummert: hent ut de relevante sansynlighetene for personen som bruker appen og samle de i everything.
        for(int i = 0; i < data.length; i++){

            String key = Integer.toString(i+1) + Integer.toString(data[i]);
            everything.add(evidence_probabilities.get(key));

        }
        //Finn ut hvordan disse sansynligehetene forandrer priors når vi har en gitt variabel-verdi.
        for(int i =0 ;i < prior_probabilities.length; i++){
            double result = prior_probabilities[i];

            for(Double[] mult: everything){
                result = mult[i] * result;
            }

            probabilities[i] = result;
            probabilities_object[i] = result;
        }


    }


    public class ArrayIndexComparator implements Comparator<Integer>
    {
        private final Double[] array;

        public ArrayIndexComparator(Double[] array)
        {
            this.array = array;
        }

        public Integer[] createIndexArray()
        {
            Integer[] indexes = new Integer[array.length];
            for (int i = 0; i < array.length; i++)
            {
                indexes[i] = i; // Autoboxing
            }
            return indexes;
        }

        @Override
        public int compare(Integer index1, Integer index2)
        {
            // Autounbox from Integer to int to use as array indexes
            return array[index1].compareTo(array[index2]);
        }
    }


}
