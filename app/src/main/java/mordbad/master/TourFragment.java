package mordbad.master;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import mordbad.master.dss.Reasoner;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TourFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TourFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TourFragment extends android.support.v4.app.Fragment implements Button.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "TourFragment";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private Reasoner mReasoner;
    JSONObject results = null;

    TextView nameView;
    TextView adressView;
    TextView phoneView;
    TextView openView;
    TextView openTimeView;
    TextView websiteView;
    TextView typesView;

    Button mButton;

    Spinner one;
    Spinner two;
    Spinner three;
    Spinner four;
    Spinner five;
    Spinner six;

    Spinner[] content;


    String placeDetails = "";

    String[] jsonAttrib = new String[]{
            "name",
            "formatted_address",
            "international_phone_number",
            "open_now",
            "weekday_text",
            "website",
            "types"
    };


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TourFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TourFragment newInstance(String param1, String param2) {
        TourFragment fragment = new TourFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public TourFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tour, container, false);

        nameView = (TextView) view.findViewById(R.id.jsonView);
        adressView = (TextView) view.findViewById(R.id.textView);
        phoneView = (TextView) view.findViewById(R.id.textView2);
        openView = (TextView) view.findViewById(R.id.textView3);
        openTimeView = (TextView) view.findViewById(R.id.textView4);
        websiteView = (TextView) view.findViewById(R.id.textView5);
        typesView = (TextView) view.findViewById(R.id.textView6);

        // Getting reference to the Spinner
        one = (Spinner) view.findViewById(R.id.spinner);
        two = (Spinner) view.findViewById(R.id.spinner2);
        three = (Spinner) view.findViewById(R.id.spinner3);
        four= (Spinner) view.findViewById(R.id.spinner4);
        five = (Spinner) view.findViewById(R.id.spinner5);
        six = (Spinner) view.findViewById(R.id.spinner6);

        mButton = (Button) view.findViewById(R.id.button);
        mButton.setOnClickListener( this);


        Integer[] generations = {50,80,100,200,400,500,1000};


        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(getContext(), android.R.layout.simple_spinner_dropdown_item, generations);


        one.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.d(TAG, "Spinner clicked");


                one.setSelection(position);
                Log.d(TAG, "spinnerPos: " + one.getSelectedItemPosition());

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                Log.d(TAG, "Spinner nothing selected");
            }
        });
        one.setAdapter(adapter);

        String[] allPlaceTypes = getResources().getStringArray(R.array.all_place_types);

        content = new Spinner[] {two, three, four, five, six};


        // Setting adapter on Spinner to set question options
        spinnerSetClickAndContent(content,allPlaceTypes);

        mReasoner = new Reasoner(allPlaceTypes);

        populate();



//        nameView.setText("test");
//        nameView.setText(placeDetails);
//        nameView.setMovementMethod(new ScrollingMovementMethod());

        return view;
    }

    private void spinnerSetClickAndContent(Spinner[] spinners, String[] contents) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, contents);

        for(final Spinner s: spinners){
            s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {
                    Log.d(TAG, "Spinner clicked");


                    s.setSelection(position);
                    Log.d(TAG, "spinnerPos: " + s.getSelectedItemPosition());

                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    Log.d(TAG, "Spinner nothing selected");
                }
            });

            s.setAdapter(adapter);
        }


    }

    private void populate() {
        String name = "";
        String adr = "";
        String phone = "";
        String open = "";
//        String[] openTime = {};
        String web = "";
//        String[] types = {};

        JSONObject jplace = null;


        try{
            jplace = results.getJSONObject("result");

            int i = 0;
//            if(!jplace.isNull("name"){
            name = jplace.getString(jsonAttrib[i++]);


//            if(!jplace.isNull(jsonAttrib[i])){
                adr = jplace.getString(jsonAttrib[i++]);

//            }
            phone = jplace.getString(jsonAttrib[i++]);


            JSONArray jtime = jplace.getJSONArray("opening_hours");
            Log.d(TAG,""+ jtime.get(0));
//            JSONObject jtim = jtime.get(0);
//            open = jtime.get(0).getString(jsonAttrib[i++]);
//            openTime = jplace.getString(jsonAttrib[i++]);



//            }


        }
        catch(Exception e){
            Log.d(TAG,""+e);


        }
        nameView.setText(name);
        adressView.setText(adr);
        phoneView.setText(phone);
        openView.setText(open);


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

    public void presentDetails(String result) {

        String vicinity = "";
        try{
            results = new JSONObject(result);

//            // Extracting Place Vicinity, if available
//            if(!results.isNull("vicinity")){
//                vicinity = results.getString("vicinity");
//            }

        }
        catch(Exception e){
            Log.d(TAG, ""+e);

        }


        Log.d(TAG,""+result);
//        Log.d(TAG, "" + vicinity);
        Log.d(TAG, "presentDetials got called");
        placeDetails = result;

//        nameView.setText(result);


    }


    @Override
    public void onClick(View v){

        switch(v.getId()){
            case R.id.button:
                printResult();
                break;






        }


    }

    private void printResult() {
//        String result;
        int gen = (int) one.getSelectedItem();

        Log.d(TAG, "gen :" + gen );
        //TODO: move getCategories out of the print-function and into its own method for clarity of functionality.
        int[] result = mReasoner.getCategories(gen);
//        result = mReasoner.getCategories(gen);

        StringBuilder build = new StringBuilder();

        for(int count =0; count < result.length; count ++){
            build.append(result[count]+ ", ");
            content[count].setSelection(result[count]);


        }

        String numbers = build.toString();
        Log.d(TAG,""+numbers);
        nameView.setText(numbers);
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
    }

}
