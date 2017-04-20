package mordbad.master.dss;



import org.uncommons.watchmaker.framework.EvolutionEngine;
import org.uncommons.watchmaker.framework.EvolutionaryOperator;
import org.uncommons.watchmaker.framework.FitnessEvaluator;
import org.uncommons.watchmaker.framework.GenerationalEvolutionEngine;
import org.uncommons.watchmaker.framework.SelectionStrategy;
import org.uncommons.watchmaker.framework.operators.IntArrayCrossover;
import org.uncommons.watchmaker.framework.operators.ObjectArrayCrossover;
import org.uncommons.watchmaker.framework.selection.RouletteWheelSelection;
import org.uncommons.maths.random.MersenneTwisterRNG;
import org.uncommons.watchmaker.framework.termination.ElapsedTime;


import java.util.HashMap;
import java.util.List;
import java.util.Random;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import mordbad.master.data.Gatherer;

import android.location.Location;
import android.util.Log;


/**
 * Created by haakon on 12/05/16.
 */
public class Reasoner {

    private static final String TAG = "REASONER";
    private String result = "";
    private Gatherer gatherer = new Gatherer();
    private Wish wish ;
    // --Commented out by Inspection (03/03/17 09:58):private ActivityEvaluator pe;
    // --Commented out by Inspection (03/03/17 09:59):private MersenneTwisterRNG mRng;
    private EvolutionEngine<int[]> engine;
    private String[] candidates;
    private int length = 90;
    private Observable<List<HashMap<String,String>>> activityObservable;
    private boolean allFinished = false;
    private Question[] questions;
    private DecisionConstraint[] constraints;

    public Observable<List<HashMap<String,String>>[]> allResults;
    private Observer subscriber;
    private int[] dataFromQuestions = {1,1,1,1,1,1};

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

//        Probabilitator probabilitator = new Probabilitator()
    }


    public Reasoner(String[] candidates, DefaultHashMap<Integer, Double> weights) {
        this.candidates = candidates;
        length = candidates.length;

//        this.questions = question;

//        constraints = new DecisionConstraint[questions.length];
//        for(int i = 0; i < questions.length; i++){
//            constraints[i] = questions[i].generateConstraint();
//        }

        initiateEvolutionEngine(weights);
    }




    private void initiateEvolutionEngine(DefaultHashMap<Integer,Double> weights) {
         /*
        What watchmaker needs:
    A Candidate Factory
    An Evolutionary Operator
    A Fitness Evaluator
    A Selection Strategy
    A Random Number Generator
     */
        ActivityFactory factory = new ActivityFactory<int[]>(length);
//      Create a pieline that apllies cross-over mutation
//         List<EvolutionaryOperator<String>> operators= new LinkedList<>();
//        operators.add(new StringMutation(chars, new Probability(0.22)));
//        operators.add(new StringCrossover());
//        EvolutionaryOperator<String> pipeline = new EvolutionPipeline<>(operators);


        EvolutionaryOperator<int[]> pipeline = new IntArrayCrossover();
        FitnessEvaluator<int[]> fitnessEvaluator = new ActivityEvaluator(length, weights);
        SelectionStrategy<Object> selection = new RouletteWheelSelection();
        Random rng = new MersenneTwisterRNG();

        engine = new GenerationalEvolutionEngine<int[]>(   factory,
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

        return engine.evolve(population,0, new ElapsedTime(5000));
    }


    /**
     * Gets the concrete activities we recommend using the activity-types from getCategories()
     *
     * @param population The size of the possibilities for each generation.
     * @param activities The set of ints getCategories generated from type-array defined in strings.xml. In the design-spec this should be an int[] of five elements, because we are looking for five places.
     * @return concrete candidate activities we recommend to the user.
     */
    //TODO: Design, change signature and implement
    public int[] getActivities(int population, int[] activities, Location location){
        //PLACEHOLDER
       int[] candidate = {0,1} ;
        List<HashMap<String,String>>[] allofit =new List[activities.length];
        boolean[] finished = new boolean[activities.length];
        allFinished = false;
        Log.d(TAG, ""+activities.length);

        Observable<List<HashMap<String,String>>> observable;
        for(int i =0; i<activities.length; i++){
            observable = gatherer.getObservable(gatherer.contructUrl(this.candidates[activities[i]],location));

            final int ii = i;
            observable.subscribe(new Observer<List<HashMap<String,String>>>() {

                @Override
                public void onSubscribe(Disposable d) {
                    
                }

                @Override
                public void onNext(List<HashMap<String, String>> hashMaps) {
                    Log.d(TAG, "Allofitsize: "+ allofit.length + " iterator nr:" + ii);
//                    allofit.ensureCapacity(ii);

                    allofit[ii] = hashMaps;
//                    allofit.add(ii, hashMaps);
                    finished[ii] = true;
                    checkAllFinished(finished);
                }


                @Override
                public void onError(Throwable t) {
                    Log.d(TAG, ":"+t );
                }

                @Override
                public void onComplete() {
                    if(allFinished){
                        //TODO: should start second-round generation of paths. Extract to own method and call it here for memory-purposes
                        generateDay(population, activities,allofit);
                        Log.d(TAG,"All done");
                        allResults = Observable.just(allofit);
                        allResults.subscribe(subscriber);

                        //Once everything is done start generation of stuff.
//                        allResults.subscribe();

                    }
                }
            });

        }


        return candidate;
    }

    private void generateDay(int population, int[] activities, List<HashMap<String, String>>[] allofit) {
//        int[] candidates = {0,1};

//        instantiateGeneticStuff();
        //stuff for evolutionengine
        DayFactory dayFactory = new DayFactory(allofit,activities.length);


        FitnessEvaluator<HashMap<String,String>[]> dayEvaluator = new DayEvaluator(constraints);

        EvolutionaryOperator hashArrayCrossover = new ObjectArrayCrossover<HashMap<String,String>>();


        SelectionStrategy<Object> selection = new RouletteWheelSelection();
        Random rng = new MersenneTwisterRNG();


        EvolutionEngine<HashMap<String, String>[]> engine = new GenerationalEvolutionEngine<HashMap<String, String>[]>(
                dayFactory,
                hashArrayCrossover,
                dayEvaluator,
                selection,
                rng
                );


//        Observable<HashMap<String,String>[]> obs;

        Observable.defer(
                () -> Observable.just(engine.evolve(population, 0, new ElapsedTime(5000)))
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);


    }


    //Checks if this was the last one, if so flips allFinished to signal method-call in onComplete
    private void checkAllFinished(boolean[] finished) {
        boolean everyone = true;
        for(boolean i: finished){
            if(!i){
                everyone = false;
            }
        }
        allFinished = everyone;
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


    public void setSubscriber(Observer subscriber) {
        this.subscriber = subscriber;
    }


}
