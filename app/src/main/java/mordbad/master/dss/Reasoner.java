package mordbad.master.dss;

import mordbad.master.data.Gatherer;

/**
 * Created by haakon on 12/05/16.
 */
public class Reasoner {

    private Gatherer gatherer = new Gatherer();
    private Wish wish = null;


    public Reasoner(){};





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
