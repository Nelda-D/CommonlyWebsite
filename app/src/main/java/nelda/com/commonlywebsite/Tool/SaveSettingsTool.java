package nelda.com.commonlywebsite.Tool;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Objects;

/**
 * Created by Administrator on 2015/10/25 0025.
 */
public class SaveSettingsTool {
    private Context mContext;

    public SaveSettingsTool(Context context){
        mContext = context;
    }

    private void saveSetting(String key ,String path){
        SharedPreferences.Editor editor = mContext.getSharedPreferences("websitepaths",Context.MODE_PRIVATE).edit();
        editor.putString(key,path);
        editor.commit();
    }

    private String getSetting(String key){
        SharedPreferences sharedPreferences =mContext.getSharedPreferences("websitepaths",Context.MODE_WORLD_READABLE);
        return sharedPreferences.getString(key,"");
    }

}
