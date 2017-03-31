package mordbad.master.dss;

import android.support.annotation.NonNull;

import com.activeandroid.annotation.Column;
import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by haakon on 30/03/17.
 */

public class PersonLikelihoodForActivities {

    private int[] data;

    private double yes_spise = 0.00;
    private double yes_shoppe = 0.00;
    private double yes_historisk = 0.00;
    private double yes_slappe_av = 0.00;
    private double yes_teater = 0.00;
    private double yes_kunst = 0.00;
    private double yes_moro = 0.00;
    private double yes_lokal_historie = 0.00;
    private double yes_sightseeing = 0.00;
    private double yes_lokal_kultur= 0.00;
    private double yes_restaurant = 0.00;

    private double[] allLikelihoods = {yes_spise,yes_shoppe,yes_historisk,yes_slappe_av,yes_teater,yes_kunst,yes_moro,yes_lokal_historie,yes_sightseeing,yes_lokal_kultur,yes_restaurant};


    private String[] categories =
            {
            "formal",
            "land",
            "kjønn",
            "alder",
            "utdanning",
            "status"

            };
    private int statisticSignificantSample = 300;

    public PersonLikelihoodForActivities(int[] data){
        this.data = data;
        generateLikelihoods();
    }

    private void generateLikelihoods() {
        List<PersonModel>[] allCases = new List[allLikelihoods.length];

        for(int i = 0; i < allLikelihoods.length; i++){
            allCases[i] = new Select()
                    .from(PersonModel.class)
                    .where(""+categories[i]+"="+data[i])
                    .execute();
        }

        List<PersonModel> results = adjustCasesUntilAboveSampleSizeMinimum(allCases);

        findLikelihooods(results);

    }

    private void findLikelihooods(List<PersonModel> results) {

        int sum = results.size();
        int probability[] = new int[]{1,1,1,1,1,1,1,1,1,1,1};
        int[] no_responses = new int[]{0,0,0,0,0,0,0,0,0,0,0};
        int preference_counter = 0;
        for(int active_person = 0; active_person <allLikelihoods.length; active_person++){

            for(int current_preference =0 ; active_person < PersonModel.NUMBER_OF_PREFERENCES-1; current_preference++){

                if(results.get(active_person).preferences[current_preference] == 2){
                    no_responses[current_preference]++;

                }
            }
        }
        for(int pref = 0;pref < no_responses.length; pref++){
            no_responses[pref] = no_responses[pref] / sum;
            allLikelihoods[pref] = probability[pref] - no_responses[pref];
        }
    }

    private List<PersonModel> adjustCasesUntilAboveSampleSizeMinimum(List<PersonModel>[] allCases) {
            List<PersonModel> finalResult;
            List<PersonModel> tempResult = allCases[0];


            for(int i=0; i< allCases.length; i++){
                List<PersonModel> tmp = tempResult;
                //Should return only those that have the same ID, ie primitive after-market SQL JOIN, with only diff being "dynamic".
                tmp = findIntersection(tempResult,allCases[i]);
                if(tmp.size() < statisticSignificantSample){
                    continue;
                }
                tempResult = tmp;
            }

            finalResult = tempResult;
        return finalResult;

    }

    private List<PersonModel> findIntersection(List<PersonModel> tempResult, List<PersonModel> allCase) {
        List<PersonModel> intersection = new ArrayList<PersonModel>() ;

        for(int i = 0; i <tempResult.size(); i++){
            if(tempResult.get(i).getId() == allCase.get(i).getId()){
                intersection.add(tempResult.get(i));

            }


            return intersection;

        }




        return null;
    }

    public int[] getData() {
        return data;
    }



    public double[] getAllLikelihoods() {
        return allLikelihoods;
    }

    public String[] getCategories() {
        return categories;
    }


}
