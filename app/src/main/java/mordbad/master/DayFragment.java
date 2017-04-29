package mordbad.master;

import android.content.Context;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import mordbad.master.data.PlaceJSONParser;
import mordbad.master.dss.Reasoner;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DayFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DayFragment extends Fragment implements Button.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "DAYFRAGMENT";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private Button button;


    private int[] activities =  {13,13,13,13,13};
//    private ArrayList<List<HashMap<String,String>>> result;
    private Reasoner reasoner;
    private TextView view1;
    private TextView view2;
    private TextView view3;
    private TextView view4;
    private TextView view5;

    private Observer<HashMap<String,String>[]> subscriber;
    private HashMap<String,String>[] result;
    private TextView maplink;

    public DayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DayFragment newInstance(String param1, String param2) {
        DayFragment fragment = new DayFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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

        View view = inflater.inflate(R.layout.fragment_day, container, false);
        button = (Button) view.findViewById(R.id.startTheApocalypse);
        view1 = (TextView) view.findViewById(R.id.tulleteksten);

        view2 = (TextView) view.findViewById(R.id.textView7);
        view3 = (TextView) view.findViewById(R.id.textView8);
        view4 = (TextView) view.findViewById(R.id.textView9);
        view5 = (TextView) view.findViewById(R.id.textView10);

        maplink = (TextView) view.findViewById(R.id.textView11);

        button.setOnClickListener(this);

        subscriber = new Observer<HashMap<String,String>[]>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(HashMap<String, String>[] hashMaps) {

                view1.setText("Look daddy, Im recieving stuff in: " + TAG);
                result = hashMaps;

            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete triggerued :O");
                try{


                    view1.setText(result[0].get(PlaceJSONParser.strings.place_name.toString()));
                    view1.setTag(result[0]);
                    view2.setText(result[1].get(PlaceJSONParser.strings.place_name.toString()));
                    view2.setTag(result[1]);
                    view3.setText(result[2].get(PlaceJSONParser.strings.place_name.toString()));
                    view3.setTag(result[2]);
                    view4.setText(result[3].get(PlaceJSONParser.strings.place_name.toString()));
                    view4.setTag(result[3]);
                    view5.setText(result[4].get(PlaceJSONParser.strings.place_name.toString()));
                    view5.setTag(result[4]);

                    Log.d(TAG,""
                            + result[0] + "\n"
                            + result[1] + "\n"
                            + result[2] + "\n"
                            + result[3] + "\n"
                            + result[4] + "\n"
                    );
                    makeLink();

                }
                catch(Exception e){
                    Log.d(TAG, ""+e);
                }
            }
        };

        //Instantiate the reasoner and give it access to the canonical array of place types so that the fragment and the reasoner are sure to talk about the same kinds of places
        reasoner = new Reasoner(getResources().getStringArray(R.array.all_place_types));

        return view;
    }

    private void makeLink() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("https://www.google.com/maps/dir/Current+Location");
        for(HashMap<String,String> place : result){

            stringBuilder.append("/");
            stringBuilder.append(place.get(PlaceJSONParser.strings.lat.toString())+",");
            stringBuilder.append(place.get(PlaceJSONParser.strings.lng.toString()));

        }

        maplink.setText(stringBuilder.toString());
        Log.d(TAG,stringBuilder.toString());

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case(R.id.startTheApocalypse):
                Log.d(TAG,"I Got Pressed :O");
                reasoner.setSubscriber(subscriber);
               reasoner.getActivities(100,activities,mListener.getLocation());
//                view1.setText(result[0].get(1).);a
                break;


            case(R.id.textView7):

                break;
            case(R.id.textView8):

                break;
            case(R.id.textView9):

                break;
            case(R.id.textView10):

                break;
            case(R.id.textView11):

                break;

        }
    }

    public void setResult(int[] result) {
        this.activities = result;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
        Location getLocation();
    }
}
