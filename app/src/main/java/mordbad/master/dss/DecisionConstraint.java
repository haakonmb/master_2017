package mordbad.master.dss;

import java.util.HashMap;

/**
 * Created by haakon on 07/03/17.
 */

public interface DecisionConstraint<T> {

    double constraint(HashMap<String, String>[] assignment);

    double constraint(T assignment);
}
