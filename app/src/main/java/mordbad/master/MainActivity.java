package mordbad.master;

//import android.app.FragmentManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import mordbad.master.dss.Gatherer;
import mordbad.master.dss.Reasoner;

public class MainActivity extends AppCompatActivity implements PreferenceFragment.OnFragmentInteractionListener, TourFragment.OnFragmentInteractionListener, MapFragment.OnFragmentInteractionListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    String TAG = "mainactivity";

    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;
    //private Reasoner reasoner;
    private String[] events = null;
    private Wish wish;

    //Init Decision support system parts
    private Gatherer gatherer = null;
    private Reasoner reasoner = null;


    //fragments ohoy!
    FragmentManager fragmentManager = getSupportFragmentManager();
    PreferenceFragment prefFragment;
    TourFragment tourFragment;
    MapFragment mapFragment;

    //apiclient for location services
    GoogleApiClient mGoogleApiClient = null;



    String[] activites = {"Arts & Culture", "Business","Community","Education", };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            //PreferenceFragment prefFragment = new PreferenceFragment();
            tourFragment = new TourFragment();
            mapFragment = new MapFragment();
            prefFragment = new PreferenceFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
           // firstFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container, prefFragment).commit();
        }


        //Connecting the listview and populating it
        mDrawerList = (ListView)findViewById(R.id.navList);
        addDrawerItems();
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());


        //Enabling the hamburger-icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        setupDrawer();


        Log.d(TAG, "test");
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();


    }


    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (mDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void setupDrawer(){
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close){

            //Called when a drawer has settled in a completely open state
            public void onDrawerOpened(View drawerView){
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Navigation!");
                invalidateOptionsMenu();

            }

            //Called when a drawer has settled in a completely closed state
            public void onDrawerClosed(View view){
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu();


            }

        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

    }

    private void addDrawerItems(){
        String[] drawerItems = { "LevelTest", "Tour", "Map", "LevelTest"};
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, drawerItems);
        mDrawerList.setAdapter(mAdapter);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void passPreference(Wish wish) {
        //send wish to the recommender for processing
        this.wish = wish;
        events = reasoner.getEvents(wish);
        //TODO Change to loading screen while processing
        //


        //TODO Then change to tourfragment when you get input from Reasoner
    }

    @Override
    public void onConnected(Bundle bundle) {
        
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    private class DrawerItemClickListener implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }


    }

    private void selectItem(int position) {
        Log.d(TAG, ""+position);
        Fragment fragment = null;

        switch(position){
            case 0:
                fragment = prefFragment;
                break;

            case 1:
                fragment = tourFragment;
                break;

            case 2:
                fragment = mapFragment;
                break;

            case 4:
                prefFragment.addXp();
                break;

            default:
                Log.d(TAG, "That menu-item didnt exist! How do?");
               // fragment = prefFragment;
        }

        if(fragment != null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();

        }
    }

   /* @Override
    public void addXp() {

    }*/
}
