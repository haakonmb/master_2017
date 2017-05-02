package mordbad.master.dss;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mordbad.master.data.PlaceJSONParser;

/**
 * Created by haakon on 24/04/17.
 */

public class DayProblem {


    //Score lower for laybacking, ie going far away and coming back again, except distance between first and fifth
    //TODO: finish implementation
    final static DecisionConstraint<HashMap<String,String>[]> constraint1 = new DecisionConstraint<HashMap<String, String>[]>() {
        @Override
        public double constraint(HashMap<String, String>[] assignment) {
            double score = 0;
            List<double[]> coordinates = new ArrayList<>();

            double[] first = {  Double.parseDouble(assignment[0].get(PlaceJSONParser.strings.lat)),
                                Double.parseDouble(assignment[0].get(PlaceJSONParser.strings.lng))};
            coordinates.add(first);

            //finn distansen mellom hvert sted i listen og tidligere steder
            for(int i = 1; i < assignment.length-1 ; i++){
                double[] candidate = {  Double.parseDouble(assignment[i].get(PlaceJSONParser.strings.lat)),
                                        Double.parseDouble(assignment[i].get(PlaceJSONParser.strings.lng))};

                boolean penalize = findAndCompareCoords(candidate, coordinates);
                if(penalize){
                    score = score-1;
                }
                coordinates.add(candidate);
            }

            return score;
        }

        //Finner koordinater og sammenligner de med de tidligere stedene og returnerer true om det finnes en sti som er mer effektiv
        private boolean findAndCompareCoords(double[] candidate, List<double[]> coordinates) {


            for(double[] d: coordinates){

                double result = distanceInKmBetweenCoords(candidate,d);
            }

            return false;
        }

        private double distanceInKmBetweenCoords(double[] candidate, double[] d) {
           double earthRadiusKm= 6371;




//            Math.sin
            return 0;
        }
    };

    //Penalize repeats
    final static DecisionConstraint<HashMap<String,String>[]> constraint2 = new DecisionConstraint<HashMap<String, String>[]>() {


        @Override
        public double constraint(HashMap<String, String>[] assignment) {
            double score = 0;

//            boolean penalize = false;
            for(int i = 0; i< assignment.length;i++){
                for(int j=0; j < assignment.length; j++){
                    if(assignment[i].equals(assignment[j])){
                        score = score -1;

                    }

                }


            }




            return score;
        }

    };


        //Create the array of all the constraints
        final static DecisionConstraint<HashMap<String,String>[]>[] constraints = new DecisionConstraint[]{constraint1, constraint2};

}
