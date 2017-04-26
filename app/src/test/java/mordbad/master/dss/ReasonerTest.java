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

//        for(int i: TouristEnums.slappe_av){
//
//        System.out.println(           Data.place_candidates[i]);
//            System.out.println();
//        }
        int index = 0;

        for(int i = 0; i < Data.probabilitator.probabilities_scaled.length; i++){
            if(Data.probabilitator.probabilities_scaled[i] > 0.8){
                index = i;
            }

        }
//        Data.probabilitator.probabilities_scaled


        System.out.println("Index:" + index);
        for(int i: TouristEnums.all_maps[index]){
            System.out.println(Data.place_candidates[i]);
        }

        System.out.println();
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