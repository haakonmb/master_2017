package mordbad.master.dss;

import java.util.HashMap;

/**
 * Created by haakon on 19/04/17.
 */

public class DefaultHashMap<K,V> extends HashMap<K,V> {
    protected V defaultValue;
    public DefaultHashMap(V defaultValue){
        this.defaultValue = defaultValue;
    }

    @Override
    public V get(Object k){
        return containsKey(k) ? super.get(k) : defaultValue;

    }


}
