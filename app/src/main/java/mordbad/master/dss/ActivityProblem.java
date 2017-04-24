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

            return 0;
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



            return 0;
        }
    };


    //Add new constraints here.
    final static DecisionConstraint<int[]>[] constraints = new DecisionConstraint[] {constraint1, constraint2};

}
