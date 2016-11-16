package nelda.com.commonlywebsite.Tool;

import android.app.Activity;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/8.
 */

public class TransitionManager {
    Activity mActivity;
    public static final String KEY_SHARED_VIEW = "key_shared_view";
    public static final String KEY_SHARED_NAME = "key_shared_name";
    List<Map<String,Object>> mList_transitionView = new ArrayList<>();

    public TransitionManager(Activity activity){
        mActivity = activity;
    }

    public void addTransitionView(View view,String sharedName){
        if(view == null || sharedName.isEmpty()) return;
        Map<String,Object> map = new HashMap<>();
        map.put(KEY_SHARED_VIEW,view);
        map.put(KEY_SHARED_NAME,sharedName);
        mList_transitionView.add(map);
    }

    public List<Map<String,Object>> getTransitionViewList(){
        return mList_transitionView;
    }

}
