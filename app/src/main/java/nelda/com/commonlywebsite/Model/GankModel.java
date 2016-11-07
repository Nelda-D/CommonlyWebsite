package nelda.com.commonlywebsite.Model;

import java.util.ArrayList;
import java.util.List;

import nelda.com.commonlywebsite.Bean.GankDateBean;
import nelda.com.commonlywebsite.Bean.GankDayBean;
import nelda.com.commonlywebsite.Bean.GankDayTitleBean;
import nelda.com.commonlywebsite.Bean.GankPicResponse;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Administrator on 2016/10/23.
 */
public class GankModel implements IGankModel {
    GankDayBean.ResultsBean mResultsBean;
    String baseUrl = "http://gank.io";
    List<String> mList_GankDates;


    @Override
    public void getDayDatas(String year, String mounth, String day, final OnDayDatasLoadedListener listenr) {
        getDayDatasByApi(year, mounth, day, listenr);
    }

//    public void getRecentlyDay() {
//        if (mList_GankDates != null) {
//            String date = mList_GankDates.get(0);
//            String[] dates = parseDateString(date);
//            getDayDatasByApi(dates[0], dates[1], dates[2], listenr);
//        } else {
//            getDateByApi(new OnDateApiLoadedListener() {
//                @Override
//                public void onDateApiLoadedListener(GankDateBean bean) {
//                    String date = bean.getResults().get(0);
//                    String[] dates = parseDateString(date);
//                    getDayDatasByApi(dates[0], dates[1], dates[2], listenr);
//                }
//            });
//        }
//    }

    @Override
    public void getDate(final OnDateLoadedListener listenr) {
        getDateByApi(new OnDateApiLoadedListener() {
            @Override
            public void onDateApiLoadedListener(GankDateBean bean) {
                List<String> list_dates = new ArrayList<String>();
                List<String> result = bean.getResults();
                for (int i = 0; i < result.size(); i++) {
                    String date = bean.getResults().get(i);
                    list_dates.add(date);
                }
                mList_GankDates = list_dates;
                if (listenr != null) listenr.onDateLoadedListener(list_dates);
            }
        });
    }

    @Override
    public void getRecentlyPicUrl(final OnRecentlyPicResultListener listener) {
        if (mList_GankDates != null) {
            String date = mList_GankDates.get(0);
            String[] dates = parseDateString(date);
            getDayDatasByApi(dates[0], dates[1], dates[2], new OnDayDatasLoadedListener() {
                @Override
                public void onDayDatasLoadedListener(GankDayBean.ResultsBean resultsBean) {
                    String url = resultsBean.getWelfare().get(0).getUrl();
                    if(listener != null) listener.onRecentlyPicResultListener(url);
                }
            });
        }
    }


    private void getDayDatasByApi(String year, String month, String day, final OnDayDatasLoadedListener listenr) {
        //create resetApdater
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(baseUrl).build();
        //create GankDayApi
        GankDayApi gankDayApi = restAdapter.create(GankDayApi.class);
        //getData by GankDayApi
        gankDayApi.getData(year, month, day, new Callback<GankDayBean>() {
            @Override
            public void success(GankDayBean gankDayModel, Response response) {
                mResultsBean = gankDayModel.getResults();
//                List<GankDayBean.ResultsBean.AndroidBean> list_AndroidBeans = mResultsBean.getAndroid();
//                for (int i=0; i<list_AndroidBeans.size();i++){
//                    System.out.println(mResultsBean.getAndroid().get(i).getDesc());
//                }
                if (listenr != null) listenr.onDayDatasLoadedListener(mResultsBean);
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
    }

    private void getDateByApi(final OnDateApiLoadedListener listenr) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(baseUrl).build();
        GankDateApi gankDateApi = restAdapter.create(GankDateApi.class);
        gankDateApi.getData(new Callback<GankDateBean>() {
            @Override
            public void success(GankDateBean gankDateBean, Response response) {
                GankDateBean bean = gankDateBean;
                List<String> result = bean.getResults();
//                for (int i = 0; i <result.size(); i++){
//                    System.out.println("date : - -> "+result.get(i));
//                }
                if (listenr != null) listenr.onDateApiLoadedListener(bean);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    @Override
    public void getDayTitle(String date,OnDayTitleLoadedListener onDayTitleLoadedListener) {
        String[] dates = parseDateString(date);
        getDayTitleByApi(dates[0], dates[1], dates[2], onDayTitleLoadedListener);
    }

    private void getDayTitleByApi(String year, String month, String day, final OnDayTitleLoadedListener listenr) {
        //create resetApdater
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(baseUrl).build();
        //create GankDayApi
        GankDayTitleApi gankDayTitleApi = restAdapter.create(GankDayTitleApi.class);
        //getData by GankDayApi
        gankDayTitleApi.getData(year, month, day, new Callback<GankDayTitleBean>() {
            @Override
            public void success(GankDayTitleBean gankDayTitleModel, Response response) {
                GankDayTitleBean.ResultsBean bean = gankDayTitleModel.getResults().get(0);
                String title = bean.getTitle();
                if (listenr != null) listenr.onDayTitleLoadedListener(title);
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
    }


    public static String[] parseDateString(String date) {
        String[] dates = date.split("-");
        return dates;
    }


    /**
     * http://gank.io/api/data/福利/10/1
     * 分类数据: http://gank.io/api/data/数据类型/请求个数/第几页
     */
    public interface GankApi {
        @GET("/api/data/福利/{count}/{page}")
        public void getData(@Path("count") int count, @Path("page") int page, Callback<GankPicResponse> gankPicModelCallback);
    }

    /**
     * http://gank.io/api/day/2015/08/06
     */
    public interface GankDayApi {
        @GET("/api/day/{year}/{month}/{day}")
        public void getData(@Path("year") String year, @Path("month") String month, @Path("day") String day, Callback<GankDayBean> gankPicModelCallback);
    }

    /**
     * http://gank.io/api/day/history
     */
    public interface GankDateApi {
        @GET("/api/day/history")
        void getData(Callback<GankDateBean> gankPicModelCallback);
    }

    /**
     * http://gank.io/api/history/content/day/2016/11/04
     *
     */
    public interface GankDayTitleApi {
        @GET("/api/history/content/day/{year}/{month}/{day}")
        void getData(@Path("year")String year,@Path("month") String month, @Path("day") String day,Callback<GankDayTitleBean> gankPicModelCallback);
    }



}
