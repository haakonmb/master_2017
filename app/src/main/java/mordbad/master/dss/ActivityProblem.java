package mordbad.master.dss;

import java.lang.reflect.Array;
import java.util.HashMap;

/**
 * Created by haakon on 24/04/17.
 */

public class ActivityProblem{

    //Quick dirty contains check.
    public static boolean contains(int[] food, int i) {
        for(int check : food){
            if(check == i){
                return true;
            }
        }
        return false;
    }

    //Food should the third option
    final static DecisionConstraint<int[]> constraint1 = new DecisionConstraint<int[]>() {

        @Override
        public double constraint(HashMap<String, String>[] assignment) {
            return 0;
        }

        @Override
        public double constraint(int[] assignment) {

            if(contains(TouristEnums.food,assignment[2])){
                return 1;

            }

            return -1.0;
        }

    };

    //food as any
    final static DecisionConstraint<int[]> constraint2 = new DecisionConstraint<int[]>() {
        @Override
        public double constraint(HashMap<String, String>[] assignment) {
            return 0;
        }

        @Override
        public double constraint(int[] assignment) {
            for(int i: assignment){
                if(contains(TouristEnums.food, i)){
                    return 1.0;

                }

            }


            return -1.0;
        }
    };

    //No repeats
    final static DecisionConstraint<int[]> constraint3 = new DecisionConstraint<int[]>() {
        @Override
        public double constraint(HashMap<String, String>[] assignment) {
            return 0;
        }

        @Override
        public double constraint(int[] assignment) {
            double counter = 0;

            for(int i : assignment){
                if(contains(assignment, i)){
                    counter ++;

                }

            }

            if(counter > 1){
               counter = counter * -1;
               return counter;
            }

            return 0;
        }
    };


    //Penalize non-interesting activities
    final static DecisionConstraint<int[]> constraint4 = new DecisionConstraint<int[]>() {
        @Override
        public double constraint(HashMap<String, String>[] assignment) {
            return 0;
        }

        @Override
        public double constraint(int[] assignment) {
            return 0;
        }
    };

    //Add new constraints here.
    final static DecisionConstraint<int[]>[] constraints = new DecisionConstraint[] {constraint1, constraint2,constraint3,constraint4};

}
