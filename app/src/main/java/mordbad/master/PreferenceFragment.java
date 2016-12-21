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

import mordbad.master.dss.Question;
import mordbad.master.dss.Wish;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PreferenceFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PreferenceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PreferenceFragment extends android.support.v4.app.Fragment implements Button.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "PreferenceFragment";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private Wish wish = new Wish();
    private Question[] questions ;
    private int currentQNum =0;
    private String currentQ;
    private String[] currentQOpts;

    private Button mNext;
    private Button mClear;
    private TextView mqView;
    private TextView mLevelView;

    private Spinner mSpinner;
    private boolean questionareDone = false;
    private boolean showingAnswer = false;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PreferenceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PreferenceFragment newInstance(String param1, String param2) {
        PreferenceFragment fragment = new PreferenceFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public PreferenceFragment() {
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
        View view = inflater.inflate(R.layout.fragment_preference, container, false);

        //TODO: oppdater for henting av spm fra databasen, istedenfor test-spm
        //Get the questions for css
        questions = new Question[]{
                new Question("Er du sulten?",new String[]{"Ja","Nei","Fåglarne vet"}),
                new Question("Liker du fisk?",new String[]{"Ja","Nei","Eplekake"}),
                new Question("Hvilken nasjonalitet er du?",new String[]{"Norsk","Kinesisk","Eplekake"}),
                new Question("Burde denne spørsmålsrunden vært på engelsk?",new String[]{"Ja",})};

        //Find all the things
        mNext = (Button) view.findViewById(R.id.next);
        mClear =(Button) view.findViewById(R.id.Clear);
        mqView = (TextView) view.findViewById(R.id.qView);
        mLevelView = (TextView) view.findViewById(R.id.levelTextView);


        mSpinner = (Spinner) view.findViewById(R.id.spinner);
        updateQuestions();


        //Make them listen!
        mNext.setOnClickListener(this);
        mClear.setOnClickListener(this);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.d(TAG, "Spinner clicked");


                mSpinner.setSelection(position);
                Log.d(TAG, "spinnerPos: " + mSpinner.getSelectedItemPosition());

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                Log.d(TAG, "Spinner nothing selected");
            }
        });


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    //this is where app should save important info. Call mListener and pass it on.
    @Override
    public void onPause(){
        super.onPause();
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

    @Override
    public void onClick(View v) {
//        mListener.passPreference(wish);


        switch(v.getId()){
            //case
            //TODO Legg til knapper og funksjonalitet for fragmentet


            case R.id.next:
                Log.d(TAG,"Question answered presumably");
                nextQuestion();
                mListener.gathererTest();
                break;

            case R.id.Clear:
                Log.d(TAG,"Clear pressed");
                clearQuestion();
                break;

          //  default:
            //    throw new RuntimeException("Unknown Button ID");

        }
    }


    //resets the questionare
    private void clearQuestion() {
        questionareDone = false;
        showingAnswer = false;
        currentQNum = 0;
        mLevelView.setText("");
        updateQuestions();
    }

    private void nextQuestion() {
        //Update answer on current q
        boolean successful =false;
        successful = questions[currentQNum].setAnswer(mSpinner.getSelectedItemPosition());
        Log.d(TAG, "Success?: " + successful);
        //Question answered successfully?
        if(successful){

            Log.d(TAG, "Inside successful if");
            //Are there questions left?
            if(currentQNum+1 < questions.length){
                //Go to next question
                currentQNum++;
                updateQuestions();
                Log.d(TAG, "there are questions left num: "+currentQNum + " "+ questions.length);

            }
            //Finished with questions
            else{
                questionareDone = true;
                Log.d(TAG, "finished with questions" + questionareDone);
                //TODO: getDayPlan(withAnswers);
            }
            updateQuestions();

        }
        else{
            Log.d(TAG,"You up shit creek without a paddle, son. Also known as: problems with nextQuestion-method and spinner.selectedItemPos");
        }


    }

    private void updateQuestions() {

        if(questionareDone && !showingAnswer){
            String newStr = "";
            for(Question q : questions){
                String currentStr = (String)mLevelView.getText();
                String concatStr = q.getAnswer();
                newStr = currentStr+"\n"+concatStr;
                mLevelView.setText(newStr);

            }

            Log.d(TAG, newStr);

            mqView.setVisibility(TextView.INVISIBLE);
            mSpinner.setVisibility(Spinner.INVISIBLE);
            mLevelView.setVisibility(TextView.VISIBLE);
            mNext.setVisibility(Button.INVISIBLE);

            showingAnswer = true;

        }
        else if(!questionareDone){
            if(showingAnswer){
                mLevelView.setText("");
            }
            currentQ = questions[currentQNum].getQuestion();
            currentQOpts = questions[currentQNum].getOptions();
            mqView.setText(currentQ);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, currentQOpts);

            // Getting reference to the Spinner

            // Setting adapter on Spinner to set question options
            mSpinner.setAdapter(adapter);

            mqView.setVisibility(TextView.VISIBLE);
            mSpinner.setVisibility(Spinner.VISIBLE);
            mLevelView.setVisibility(TextView.INVISIBLE);
            mNext.setVisibility(Button.VISIBLE);


            Log.d(TAG,"Qs have been upped. " + currentQ);
        }

        //toggleVisibility(questionareDone);


    }

    //TODO: needs debug for use-cases. THings not visible
    //Change visibility based on status.
    //True makes questions visible
    //False makes invisible
    private void toggleVisibility(boolean status){
        if(status){
            //Change visibility
            mqView.setVisibility(TextView.VISIBLE);
            mSpinner.setVisibility(Spinner.VISIBLE);
            mLevelView.setVisibility(TextView.INVISIBLE);

        }
        else{
            //Change visibility
            mqView.setVisibility(TextView.INVISIBLE);
            mSpinner.setVisibility(Spinner.INVISIBLE);
            mLevelView.setVisibility(TextView.VISIBLE);

        }


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


        public void gathererTest();
        //public void addXp();
        public void passPreference(Wish wish );
    }
}
