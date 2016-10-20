package mordbad.master.dss;



import com.google.android.gms.location.places.Place;

import org.uncommons.watchmaker.framework.EvolutionEngine;
import org.uncommons.watchmaker.framework.GenerationalEvolutionEngine;
import org.uncommons.watchmaker.framework.factories.BitStringFactory;
import org.uncommons.watchmaker.framework.factories.ObjectArrayPermutationFactory;
import org.uncommons.watchmaker.framework.operators.ObjectArrayCrossover;
import org.uncommons.watchmaker.framework.selection.RouletteWheelSelection;
import org.uncommons.math.random.MersenneTwisterRNG;
//import mordbad.master.R;
import mordbad.master.data.Gatherer;


/**
 * Created by haakon on 12/05/16.
 */
public class Reasoner {

    private Gatherer gatherer = new Gatherer();
    private Wish wish = null;
    private PlaceEvaluator pe;


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

        //Candidate factory
        ObjectArrayPermutationFactory<String> factory = new ObjectArrayPermutationFactory<>(candidates);

        //Evolutionary Operator
        ObjectArrayCrossover<String> operator = new ObjectArrayCrossover<>();

        //Fitness Evaluator
        pe = new PlaceEvaluator();


        //Selection Strategy
        RouletteWheelSelection rouletteWheelSelection = new RouletteWheelSelection();

        //Random number generator



        //private EvolutionEngine<BitStringFactory> test = new GenerationalEvolutionEngine<BitStringFactory>();






    };




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
