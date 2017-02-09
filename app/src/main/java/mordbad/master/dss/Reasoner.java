package mordbad.master.dss;



import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.uncommons.watchmaker.framework.EvolutionEngine;
import org.uncommons.watchmaker.framework.EvolutionaryOperator;
import org.uncommons.watchmaker.framework.FitnessEvaluator;
import org.uncommons.watchmaker.framework.GenerationalEvolutionEngine;
import org.uncommons.watchmaker.framework.SelectionStrategy;
import org.uncommons.watchmaker.framework.operators.IntArrayCrossover;
import org.uncommons.watchmaker.framework.selection.RouletteWheelSelection;
import org.uncommons.maths.random.MersenneTwisterRNG;
import org.uncommons.watchmaker.framework.termination.ElapsedTime;


import java.util.HashMap;
import java.util.List;
import java.util.Random;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import mordbad.master.R;
import mordbad.master.data.Gatherer;
import android.Manifest;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;


/**
 * Created by haakon on 12/05/16.
 */
public class Reasoner {

    private String result = "";
    private Gatherer gatherer = new Gatherer();
    private Wish wish ;
    private PlaceEvaluator pe;
    private MersenneTwisterRNG mRng;
    private EvolutionEngine<int[]> engine;
    private String[] candidates;
    private int length = 90;
    private Observable<List<HashMap<String,String>>> activityObservable;


    public Reasoner(){
//        getContext().getResources().getStringArray(R.array.all_place_types);

    }


//Observable

    public Reasoner(String[] candidates){
        /*
        What watchmaker needs:
    A Candidate Factory
    An Evolutionary Operator
    A Fitness Evaluator
    A Selection Strategy
    A Random Number Generator
     */
        this.candidates = candidates;
        length = candidates.length;
//        activityObservable = gatherer.getObservable()

        PlaceFactory factory = new PlaceFactory<int[]>(length);
//      Create a pieline that apllies cross-over mutation
//         List<EvolutionaryOperator<String>> operators= new LinkedList<>();
//        operators.add(new StringMutation(chars, new Probability(0.22)));
//        operators.add(new StringCrossover());
//        EvolutionaryOperator<String> pipeline = new EvolutionPipeline<>(operators);


        EvolutionaryOperator<int[]> pipeline = new IntArrayCrossover();
        FitnessEvaluator<int[]> fitnessEvaluator = new PlaceEvaluator(length);
        SelectionStrategy<Object> selection = new RouletteWheelSelection();
        Random rng = new MersenneTwisterRNG();

        engine = new GenerationalEvolutionEngine<int[]>(   factory,
                                                      pipeline,
                                                      fitnessEvaluator,
                                                      selection,
                                                      rng);
    }


    public Reasoner(String[] candidates, HashMap<Integer,Integer> weights) {
        /*
        What watchmaker needs:
    A Candidate Factory
    An Evolutionary Operator
    A Fitness Evaluator
    A Selection Strategy
    A Random Number Generator
     */
        this.candidates = candidates;
        length = candidates.length;

        PlaceFactory factory = new PlaceFactory<int[]>(length);
//      Create a pieline that apllies cross-over mutation
//         List<EvolutionaryOperator<String>> operators= new LinkedList<>();
//        operators.add(new StringMutation(chars, new Probability(0.22)));
//        operators.add(new StringCrossover());
//        EvolutionaryOperator<String> pipeline = new EvolutionPipeline<>(operators);


        EvolutionaryOperator<int[]> pipeline = new IntArrayCrossover();
        FitnessEvaluator<int[]> fitnessEvaluator = new PlaceEvaluator(length);
        SelectionStrategy<Object> selection = new RouletteWheelSelection();
        Random rng = new MersenneTwisterRNG();

        engine = new GenerationalEvolutionEngine<int[]>(factory,
                pipeline,
                fitnessEvaluator,
                selection,
                rng);
    }


    /**
     * Gets the categories of activities we recommend.
     *
     * @param population
     * @return
     */
    public int[] getCategories(int population){
        int[] candidate = engine.evolve(population,0, new ElapsedTime(5000));

        return candidate;
    }


    /**
     * Gets the concrete activities we recommend using the activity-types from getCategories()
     *
     * @param population The size of the population for each generation.
     * @param activities The set of ints getCategories generated from type-array defined in strings.xml.
     * @return concrete candidate activities we recommend to the user.
     */
    //TODO: Design, change signature and implement
    public int[] getActivities(int population,int[] activities,Location location){
        //PLACEHOLDER
       int[] candidate = {0,1} ;

        Observable<List<HashMap<String,String>>> observable;
        for(int i: activities){
            observable = gatherer.getObservable(gatherer.contructUrl(this.candidates[i],location));
            observable.subscribe(new Observer<List<HashMap<String,String>>>() {

                @Override
                public void onSubscribe(Disposable d) {
                    
                }

                @Override
                public void onNext(List<HashMap<String, String>> hashMaps) {

                }

                @Override
                public void onError(Throwable t) {

                }

                @Override
                public void onComplete() {

                }
            });

        }


     return candidate;
    }

    public String[] getEvents(Wish wish){
        //Get possible events according to time and location
        String[] str = gatherer.getEvents(wish);
        this.wish = wish;
        //TODO Process them for relevancy
        str = process(str);

        return str;
    }


    private String[] process(String[] events){

        //TODO Do DSS stuff here and in sub-methods


        return new String[0];
    }


}
