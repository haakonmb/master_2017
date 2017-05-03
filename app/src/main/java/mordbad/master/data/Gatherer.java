package mordbad.master.data;


import android.app.Activity;
import android.location.Location;
import android.os.AsyncTask;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import mordbad.master.MapFragment;
import mordbad.master.dss.Wish;




/**
 * Created by haakon on 12/05/16.
 */
public class Gatherer {

    String TAG = "GATHERER";
    String result;
    String[] allPlaceTypes;

    MapFragment mapFragment;
    protected PlaceJSONParser placeJSONParser = new PlaceJSONParser();

    private OnGathererInteractionListener mListener;
    private String api_key = "AIzaSyBDJ5xrcLftFy_VC0ZoRc2j2Jn-oTLPXvc";

    Boolean places= true;




    public Gatherer(){}

    //Takes as input the canonical arry of place-types.
    public Gatherer(String[] placeTypes){
        this.allPlaceTypes = placeTypes;

//        Fragment.getResources().
    }

    //TODO Interface with APIs and get data. Save it locally for a limited time.
    public String[] getEvents(Wish wish) {


        return new String[0];

    }

    public Observable getObservable(String s){
        Observable<List<HashMap<String,String>>> observable =
                //Wait until subscription to do stuff
                Observable.defer(
                //Do this
                () -> Observable.just(exceptionHandlerForDownloadUrl(s)))
                //Do it on this thread
                .subscribeOn(Schedulers.io())
                // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread())
                //make it into a JSONObject and send it to parsing
                .map(s1 -> {
            JSONObject jsonObject = new JSONObject(s1);

            return placeJSONParser.parse(jsonObject);
        });


        return observable;
    }


    protected String exceptionHandlerForDownloadUrl(String s){
        String data = "";
        try{
            data = downloadUrl(s);

        }
        catch (IOException e){
            Log.d(TAG, e.toString());
        }
        return data;
    }
    //This should combine the database and web-interface and supply information to the Reasoner

    public void setup(Activity activity){
        try {
            mListener = (OnGathererInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public void gather(){
        mListener.finished("This was a great success");

    }

    public String contructUrl(String type, Location location){

        StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        sb.append("location=" + location.getLatitude() + "," + location.getLongitude());
        sb.append("&radius=6000");
        sb.append("&types=" + type);
        sb.append("&sensor=true");
        sb.append("&key="+ api_key);

        Log.d(TAG,sb.toString());
        return sb.toString();
    }

    /** A method to download json data from url */
    private String downloadUrl(String strUrl) throws IOException{
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb  = new StringBuffer();

            String line = "";
            //TODO: fix hack / mem-leak
            while( ( line = br.readLine())  != null){
                sb.append(line);
//                Log.d(TAG, "dURL " + line);
            }

            data = sb.toString();

            br.close();

        }catch(Exception e){
            Log.d(TAG, "Exception while downloading url " +e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }

        return data;
    }

    public void getPlaces(String s) {
        JSONGet jsonGet = new JSONGet();


        places = true;
        jsonGet.execute(s);
    }

    public void placeDetails(String markerID) {
        StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/details/json?placeid=");
        sb.append(markerID);
        sb.append("&key=");
        sb.append(api_key);

        Log.d(TAG, sb.toString());
        places = false;
        JSONGet jsonGet = new JSONGet();

        jsonGet.execute(sb.toString());



    }

    public List<HashMap<String,String>> parsePlaces(String result) {

        List<HashMap<String,String>> list = null;
//        ParserTask derp = new ParserTask();
//        derp.execute(result);


        return list;
    }

    /**
     * A class, to download Google Places
     */
    private class JSONGet extends AsyncTask<String, Integer, String> {

        String data = null;

        // Invoked by execute() method of this object
        @Override
        protected String doInBackground(String... url) {
            try {
                data = downloadUrl(url[0]);

                // data = mListener.getPlaces();
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        // Executed after the complete execution of doInBackground() method
        @Override
        protected void onPostExecute(String result) {
           // setup(result);
            if(places){
                mapFragment.setPlaces(result);
                Log.d(TAG, "places yes");
                places = false;

            }
            else{
//                mapFragment.setPlaces(result);
                Log.d(TAG, "places no");
                mapFragment.presentDetails(result);
            }
        }

    }


    public interface OnGathererInteractionListener {
        public void finished(String result);
    }
}

