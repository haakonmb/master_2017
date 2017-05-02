package mordbad.master.dss;

import java.util.HashMap;

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

            double[][] allCoordinates = new double[assignment.length][];

            for(int i = 0; i < assignment.length; i++){
                double[] place = new double[2];

                place[0] = Double.parseDouble(assignment[i].get(PlaceJSONParser.strings.lat.toString()));
                place[1] =  Double.parseDouble(assignment[i].get(PlaceJSONParser.strings.lng.toString()));
                allCoordinates[i] = place;
            }


            double toTilTre =   distanceInKmBetweenCoords(allCoordinates[1], allCoordinates[2]);
            double enTilTre =   distanceInKmBetweenCoords(allCoordinates[0], allCoordinates[2]);
            double treTilFire = distanceInKmBetweenCoords(allCoordinates[2], allCoordinates[3]);
            double toTilFire =  distanceInKmBetweenCoords(allCoordinates[1], allCoordinates[3]);
            double enTilFire=   distanceInKmBetweenCoords(allCoordinates[0], allCoordinates[3]);
            double fireTilFem=  distanceInKmBetweenCoords(allCoordinates[3], allCoordinates[4]);
            double treTilFem=   distanceInKmBetweenCoords(allCoordinates[2], allCoordinates[4]);
            double toTilFem=    distanceInKmBetweenCoords(allCoordinates[1], allCoordinates[4]);

            if(     distanceCheck(toTilTre,enTilTre) ||

                    distanceCheck(treTilFire,toTilFire) ||
                    distanceCheck(treTilFire, enTilFire) ||

                    distanceCheck(fireTilFem, treTilFem) ||
                    distanceCheck(fireTilFem, toTilFem)
                    ){
                score = -5;
            }


            return score;
        }
        //Burde dette vært i en annen rekkefølge?
        private boolean distanceCheck(double arg1, double arg2) {
            return ((arg1 > 0.2) && (arg1 > 2*arg2));
        }


        private double distanceInKmBetweenCoords(double[] candidate, double[] d) {
           double earthRadiusKm= 6371;

            double dLat = Math.toRadians(d[0]-candidate[0]);
            double dLng = Math.toRadians(d[1]-candidate[1]);

            double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                    Math.cos(Math.toRadians(candidate[0])) * Math.cos(Math.toRadians(d[0])) +
                    Math.sin(dLng/2) * Math.sin(dLng/2);

            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
            double dist = earthRadiusKm * c;

            return dist;
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
