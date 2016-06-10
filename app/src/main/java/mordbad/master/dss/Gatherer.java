package mordbad.master.dss;


import android.os.AsyncTask;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import mordbad.master.MapFragment;
import mordbad.master.PlaceJSONParser;
import mordbad.master.TourFragment;
import mordbad.master.Wish;




/**
 * Created by haakon on 12/05/16.
 */
public class Gatherer {

    String TAG = "GATHERER";
    String result;


    MapFragment mapFragment;
    private String api_key = "AIzaSyBDJ5xrcLftFy_VC0ZoRc2j2Jn-oTLPXvc";

    Boolean places= true;




    public Gatherer(){};


    //TODO Interface with APIs and get data. Save it locally for a limited time.
    public String[] getEvents(Wish wish) {


        return new String[0];

    }


    //This should combine the database and web-interface and supply information to the Reasoner

    public void setup(MapFragment fragment){
       // this.result = result;
        mapFragment = fragment;
//        mapFragment.setPlaces("test");
    }


    /** A method to download json data from url */
    private String downloadUrl(String strUrl) throws IOException {
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
            while( ( line = br.readLine())  != null){
                sb.append(line);
                Log.d(TAG, "dURL " + line);
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



}

