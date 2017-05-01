package mordbad.master.dss;


import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import mordbad.master.data.Gatherer;

/**
 * Created by haakon on 01/05/17.
 */

public class MockGatherer extends Gatherer {

    @Override
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
}
