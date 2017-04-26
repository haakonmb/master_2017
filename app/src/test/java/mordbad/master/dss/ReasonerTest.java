package mordbad.master.dss;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by haakon on 25/04/17.
 */
public class ReasonerTest {
    Reasoner reasoner = new Reasoner(Data.place_candidates,Data.probabilitator.map_activities_to_probability_for_yes);

    @Test
    public void getCategories() throws Exception {
        int[] result = reasoner.getCategories(50,5000);
        for(int i: result){
            System.out.println(Data.place_candidates[i]);

        }
    }

    @Test
    public void getActivities() throws Exception {

    }

    @Test
    public void setSubscriber() throws Exception {

    }



    public void getDay() throws Exception {

    }

}