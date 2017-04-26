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




    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {

    }


    @Test
    public void checkProbabilities() throws Exception {

        Probabilitator probabilitator =  Data.probabilitator;
        for(double d : probabilitator.probabilities){

            System.out.println(d);
        }

    }

}