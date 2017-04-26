package mordbad.master.dss;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by haakon on 25/04/17.
 */
public class ProbabilitatorTest {

    Probabilitator probabilitator = Data.probabilitator;


    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {

    }


    @Test
    public void checkProbabilities() throws Exception {

        for(double d : probabilitator.probabilities){

            System.out.println(d);
        }

    }

    @Test
    public void getSortedIndexOfHighestNumber() throws Exception{
        Data.generateProbabilitator(new int[] {1,1,1,4,1,1});

        for(int i= 0; i < probabilitator.probabilities.length; i++){
            Double orig = probabilitator.probabilities[i];
            Double scaled = probabilitator.probabilities_scaled[i];
            Double relation = orig / scaled;


            System.out.println("orig:" +orig +" scaled:"+scaled);
            System.out.println("Relation:" + relation);
            if(probabilitator.probabilities_scaled[i] > 0.8)
            System.out.println("Best thing:"+ probabilitator.probabilities_scaled[i]+ " "+ i);
        }

    }

}