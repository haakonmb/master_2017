package mordbad.master.dss;

import java.util.Date;

/**
 * Created by haakon on 11/05/16.
 */
public class Wish {
    private boolean[] activities = new boolean[]{};
    private Date start;
    private Date stop;


    public Wish(){
        start = new Date();
        stop = new Date();
        setActivities(new boolean[]{false});

    }

    public Wish(Date start, Date stop, boolean[] activities){
        this.start = start;
        this.stop = stop;
        this.activities = activities;

    }

    public Wish getWish(){

        return this;
    }

    public void setWish(Wish wish){
        setStart(wish.getStart());
        setStop(wish.getStop());
        setActivities(getActivities());

    }


    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }


    public Date getStop() {
        return stop;
    }

    public void setStop(Date stop) {
        this.stop = stop;
    }



    public boolean[] getActivities() {
        return activities;
    }

    public void setActivities(boolean[] activities) {
        this.activities = activities;
    }

    /*
            date time start
            date time end

            preferences from etrigg as array


             */









}
