package mordbad.master.dss;



import com.google.android.gms.location.places.Place;

import org.uncommons.maths.random.Probability;
import org.uncommons.watchmaker.framework.CandidateFactory;
import org.uncommons.watchmaker.framework.EvolutionEngine;
import org.uncommons.watchmaker.framework.EvolutionaryOperator;
import org.uncommons.watchmaker.framework.FitnessEvaluator;
import org.uncommons.watchmaker.framework.GenerationalEvolutionEngine;
import org.uncommons.watchmaker.framework.SelectionStrategy;
import org.uncommons.watchmaker.framework.factories.BitStringFactory;
import org.uncommons.watchmaker.framework.factories.ObjectArrayPermutationFactory;
import org.uncommons.watchmaker.framework.factories.StringFactory;
import org.uncommons.watchmaker.framework.operators.EvolutionPipeline;
import org.uncommons.watchmaker.framework.operators.ObjectArrayCrossover;
import org.uncommons.watchmaker.framework.operators.StringCrossover;
import org.uncommons.watchmaker.framework.operators.StringMutation;
import org.uncommons.watchmaker.framework.selection.RouletteWheelSelection;
import org.uncommons.maths.random.MersenneTwisterRNG;
import org.uncommons.watchmaker.framework.termination.TargetFitness;

//import mordbad.master.R;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

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
    EvolutionEngine<String> engine;


    public Reasoner(){};




    public Reasoner(String[] candidates){
        /*
        What watchmaker needs:
    A Candidate Factory
    An Evolutionary Operator
    A Fitness Evaluator
    A Selection Strategy
    A Random Number Generator

     */


        /*       //Candidate factory
        ObjectArrayPermutationFactory<String> factory = new ObjectArrayPermutationFactory<>(candidates);

        //Evolutionary Operator
        EvolutionaryOperator<String> operator = new ObjectArrayCrossover<T>();

        List<EvolutionaryOperator<String>> test = new LinkedList<>();
        //test.add()

        //Fitness Evaluator
        pe = new PlaceEvaluator();


        //Selection Strategy
        RouletteWheelSelection rouletteWheelSelection = new RouletteWheelSelection();

        //Random number generator
        mRng = new MersenneTwisterRNG();

       // EvolutionEngine<String> engine = new GenerationalEvolutionEngine<String>(factory,pe,rouletteWheelSelection,mRng);
        //private EvolutionEngine<BitStringFactory> test = new GenerationalEvolutionEngine<BitStringFactory>();
*/


        char[] chars = new char[27];

        for(char c ='A'; c<= 'Z';c++){
            chars[c-'A'] = c;

        }
        chars[26] = ' ';
        CandidateFactory<String> factory = new StringFactory(chars, 11);

        //Create a pieline that apllies cross-over mutation
        List<EvolutionaryOperator<String>> operators= new LinkedList<>();

        operators.add(new StringMutation(chars, new Probability(0.22)));

        operators.add(new StringCrossover());

        EvolutionaryOperator<String> pipeline = new EvolutionPipeline<>(operators);

        FitnessEvaluator<String> fitnessEvaluator = new StringEvaluator();
        SelectionStrategy<Object> selection = new RouletteWheelSelection();
        Random rng = new MersenneTwisterRNG();

        engine = new GenerationalEvolutionEngine<>(   factory,
                                                      pipeline,
                                                      fitnessEvaluator,
                                                      selection,
                                                      rng);







    }


    public String getResult(int generations, int targetfitness){

        result = engine.evolve(generations,0, new TargetFitness(targetfitness, true));

        return result;
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
