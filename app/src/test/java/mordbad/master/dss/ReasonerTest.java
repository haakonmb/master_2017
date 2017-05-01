package mordbad.master.dss;

import android.location.Location;

import org.junit.Test;

import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import mordbad.master.data.PlaceJSONParser;

import static org.junit.Assert.*;

/**
 * Created by haakon on 25/04/17.
 */
public class ReasonerTest {

    Reasoner reasoner = new Reasoner(Data.place_candidates,Data.probabilitator.map_activities_to_probability_for_yes,new MockGatherer());


    HashMap<String,String>[] result;


    Observer<HashMap<String,String>[]> subscriber = new Observer<HashMap<String, String>[]>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(HashMap<String, String>[] hashMaps) {
            result = hashMaps;
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {
            for(HashMap h: result){
                System.out.println(h.get(PlaceJSONParser.strings.place_name));

            }
        }

    };

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
        reasoner.setSubscriber(subscriber);
        reasoner.getActivities(100,Data.stats,Data.location);

    }

    @Test
    public void setSubscriber() throws Exception {

    }


    @Test
    public void getDay() throws Exception {
        int population = 100;
        int timeToBreak = 5000;

        Observer<HashMap<String,String>[]> subscriber = new Observer<HashMap<String, String>[]>() {
         @Override
         public void onSubscribe(Disposable d) {

         }

         @Override
         public void onNext(HashMap<String, String>[] hashMaps) {
            result = hashMaps;
         }

         @Override
         public void onError(Throwable e) {

         }

         @Override
         public void onComplete() {
                for(HashMap h: result){
                    System.out.println(h.get(PlaceJSONParser.strings.place_name));

                }
         }

        };

        reasoner.setSubscriber(subscriber);
        reasoner.getActivities(population,reasoner.getCategories(population,timeToBreak),Data.location);
    }

}