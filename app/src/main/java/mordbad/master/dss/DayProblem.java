package mordbad.master.dss;

import java.util.HashMap;

/**
 * Created by haakon on 24/04/17.
 */

public class DayProblem {

    final static DecisionConstraint<HashMap<String,String>[]> constraint1 = new DecisionConstraint<HashMap<String, String>[]>() {
        @Override
        public double constraint(HashMap<String, String>[] assignment) {


            return 0;
        }
    };



    final static DecisionConstraint<HashMap<String,String>[]>[] constraints = new DecisionConstraint[]{constraint1};

}
