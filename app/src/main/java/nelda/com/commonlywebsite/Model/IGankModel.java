package nelda.com.commonlywebsite.Model;

import java.util.List;

import nelda.com.commonlywebsite.Bean.GankDateBean;
import nelda.com.commonlywebsite.Bean.GankDayBean;

/**
 * Created by Administrator on 2016/10/23.
 */
public interface IGankModel {
    public static final String KEY_GANK_DAY_DATAS = "key_gank_day_datas";

    void getDayDatas(String year,String mounth,String day,OnDayDatasLoadedListener listenr);
    void getDate(OnDateLoadedListener listenr);
    void getRecentlyPicUrl(OnRecentlyPicResultListener listener);
    void getDayTitle(String date,OnDayTitleLoadedListener onDayTitleLoadedListener);

    public interface OnDayDatasLoadedListener{
        void onDayDatasLoadedListener(GankDayBean.ResultsBean resultsBean);
    }

    public interface  OnDateApiLoadedListener{
        void onDateApiLoadedListener(GankDateBean bean);
    }

    public interface OnDateLoadedListener{
        void onDateLoadedListener(List<String> list);
    }

    public interface OnRecentlyPicResultListener{
        void onRecentlyPicResultListener(String url);
    }

    public interface OnDayTitleLoadedListener{
        void onDayTitleLoadedListener(String title);
    }


}
