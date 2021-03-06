package mordbad.master;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import mordbad.master.data.PlaceJSONParser;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MapFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends android.support.v4.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "MapFragment";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    private MapView mMapView;
    private GoogleMap mMap;
    private Bundle mBundle;

    Spinner mSprPlaceType;

    String[] mPlaceType = null;
    String[] mPlaceTypeName = null;

    Location mLocation = null;

    double mLatitude = 0;
    double mLongitude = 0;

    Button btnFind = null;

    HashMap<Marker, String> markerID;

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBundle = savedInstanceState;
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_map, container, false);


        mPlaceType = getResources().getStringArray(R.array.place_type);

        mPlaceTypeName = getResources().getStringArray(R.array.place_type_name);

        // Creating an array adapter with an array of Place types
        // to populate the spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, mPlaceTypeName);

        // Getting reference to the Spinner
        mSprPlaceType = (Spinner) inflatedView.findViewById(R.id.spr_place_type);

        // Setting adapter on Spinner to set place types
        mSprPlaceType.setAdapter(adapter);

        mSprPlaceType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.d(TAG,"Spinner clicked");


                mSprPlaceType.setSelection(position);


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                Log.d(TAG,"Spinner nothing selected");
            }
        });

        //mSprPlaceType.setSelection(3);
        mSprPlaceType.bringToFront();

        // Getting reference to Find Button
        btnFind = (Button) inflatedView.findViewById(R.id.btn_find);

        mMapView = (MapView) inflatedView.findViewById(R.id.map);
        mMapView.onCreate(mBundle);
        setUpMapIfNeeded(inflatedView);


        // Getting Google Play availability status
        boolean status = checkPlayServices();


        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            Log.d(TAG,"Something fucked with location this way comes");
            return inflatedView;
        }
        mMap.setMyLocationEnabled(true);

        Log.d(TAG,"Location success? After setMyLocationEnabled at least");


        // Setting click event lister for the find button
        btnFind.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                mListener.getLocation();

                int selectedPosition = mSprPlaceType.getSelectedItemPosition();
                String type = mPlaceType[selectedPosition];

                StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
                sb.append("location=" + mLatitude + "," + mLongitude);
                sb.append("&radius=5000");
                sb.append("&types=" + type);
                sb.append("&sensor=true");
                sb.append("&key="+"AIzaSyBDJ5xrcLftFy_VC0ZoRc2j2Jn-oTLPXvc");


                Log.d(TAG, ""+sb.toString());
                // Creating a new non-ui thread task to download json data
                mListener.getPlaces(sb.toString());
//                PlacesTask placesTask = new PlacesTask();
//
//                // Invokes the "doInBackground()" method of the class PlaceTask
//                placesTask.execute(sb.toString());

            }
        });

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Log.d(TAG, "mark: " + markerID.get(marker));

//                String infoget = "https://maps.googleapis.com/maps/api/place/details/json?placeid="+markerID.get(marker)+"&key=AIzaSyBDJ5xrcLftFy_VC0ZoRc2j2Jn-oTLPXvc";
//
//                PlacesDetails placesDetails= new PlacesDetails();
//                placesDetails.execute(infoget);
                mListener.placeDetails(markerID.get(marker));
            }
        });


        // Inflate the layout for this fragment
        return inflatedView;
    }

    public void onLocationChanged(Location location) {
        mLatitude = location.getLatitude();
        mLongitude = location.getLongitude();
        LatLng latLng = new LatLng(mLatitude, mLongitude);

    //    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
     //   mMap.animateCamera(CameraUpdateFactory.zoomTo(12));
        Log.d(TAG, "lat: " + mLatitude);
        Log.d(TAG,"lon: "+mLongitude);

    }

    private boolean checkPlayServices() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(getContext());
        if (result != ConnectionResult.SUCCESS) {
            if (googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(getActivity(), result,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            }

            return false;
        }

        return true;
    }

   /* private int checkSelfPermission(String accessFineLocation) {
        return 1;
    }*/

    private void setUpMapIfNeeded(View inflatedView) {
        if (mMap == null) {
            //TODO change to non-deprecated method
            mMap = ((MapView) inflatedView.findViewById(R.id.map)).getMap();
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }


    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    //TODO finish up so mainactivity can pass location to this fragment, consider another designs-choice / refactor
    public void SetLocation(Location mLastLocation) {

        Log.d(TAG,"setlocation activated");
        this.mLocation = mLastLocation;
        onLocationChanged(mLastLocation);


    }


    public void setPlaces(String result){
//        ParserTask pt= new ParserTask();
//
//        pt.execute(result);
        List<HashMap<String,String>> list = null;
        list = mListener.parsePlaces(result);
        Log.d(TAG, "Got called by gatherer");

        // Clears all the existing markers
        mMap.clear();
        markerID = new HashMap<Marker, String>();

        for(int i=0;i<list.size();i++){

            // Creating a marker
            MarkerOptions markerOptions = new MarkerOptions();

            // Getting a place from the places list
            HashMap<String, String> hmPlace = list.get(i);

            // Getting latitude of the place
            double lat = Double.parseDouble(hmPlace.get("lat"));

            // Getting longitude of the place
            double lng = Double.parseDouble(hmPlace.get("lng"));

            // Getting name
            String name = hmPlace.get("place_name");

            // Getting vicinity
            String vicinity = hmPlace.get("vicinity");
//                String icon = hmPlace.get("icon");
//                try{
//
//                    URL url = new URL(icon);
//                    Bitmap image = BitmapFactory.decodeStream(url.openStream());
//                    BitmapDescriptor bmd = BitmapDescriptorFactory.fromBitmap(image);
//                    markerOptions.icon(bmd);
//                }
//                catch (Exception e){
//                    Log.d(TAG, ""+e);
//                }
//
//                Log.d(TAG,""+icon);

            LatLng latLng = new LatLng(lat, lng);

            markerOptions.snippet("test");
            //markerOptions.icon(icon);

            // Setting the position for the marker
            markerOptions.position(latLng);

            // Setting the title for the marker.
            //This will be displayed on taping the marker
            markerOptions.title(name + " : " + vicinity);

            // Placing a marker on the touched position
            Marker mark = mMap.addMarker(markerOptions);
            markerID.put(mark, hmPlace.get("place_id"));

        }
    }

    //TODO: Fix. wtf myself? Use-case?
    public void presentDetails(String result) {
        mListener.presentDetails(result);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);

        //TODO add requestlocation-method for getting location from mainCativity
        public Location getLocation();

        public void getPlaces(String s);

        void placeDetails(String s);

        void presentDetails(String result);

        List<HashMap<String,String>> parsePlaces(String result);
    }

    //TODO: move to just using JSONObject as hashmap directly instead of needlessly making one yourself.
    /** A class to parse the Google Places in JSON format */



}