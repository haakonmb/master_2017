package mordbad.master.dss;



import org.uncommons.watchmaker.framework.EvolutionEngine;
import org.uncommons.watchmaker.framework.EvolutionaryOperator;
import org.uncommons.watchmaker.framework.FitnessEvaluator;
import org.uncommons.watchmaker.framework.GenerationalEvolutionEngine;
import org.uncommons.watchmaker.framework.SelectionStrategy;
import org.uncommons.watchmaker.framework.operators.IntArrayCrossover;
import org.uncommons.watchmaker.framework.selection.RouletteWheelSelection;
import org.uncommons.maths.random.MersenneTwisterRNG;
import org.uncommons.watchmaker.framework.termination.ElapsedTime;

//import mordbad.master.R;

import java.util.HashMap;
import java.util.Random;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.functions.Action;
import mordbad.master.data.Gatherer;


/**
 * Created by haakon on 12/05/16.
 */
public class Reasoner {

    private String result = "";
    private Gatherer gatherer = new Gatherer();
    private Wish wish = null;
    private PlaceEvaluator pe;
    private MersenneTwisterRNG mRng;
    private EvolutionEngine<int[]> engine;
    private String[] candidates;
    private int length = 90;


    public Reasoner(){}


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
    public int[] getActivities(int population,int[] activities){
       int[] candidate = {0,1} ;


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
