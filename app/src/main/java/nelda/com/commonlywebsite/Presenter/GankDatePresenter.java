package nelda.com.commonlywebsite.Presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.List;

import nelda.com.commonlywebsite.Bean.GankDayBean;
import nelda.com.commonlywebsite.Model.GankModel;
import nelda.com.commonlywebsite.Model.IGankModel;
import nelda.com.commonlywebsite.View.GankDayDatasActivity;
import nelda.com.commonlywebsite.View.IGankDateView;

/**
 * Created by Administrator on 2016/10/31.
 */
public class GankDatePresenter implements IGankDatePresenter {
    private IGankDateView mGankDateView;
    private IGankModel mGankModel;
    List<String> mList_date;
    private Context mContext;
    private String mTodayPicUrl;

    public GankDatePresenter(Context context, IGankDateView view){
        if(view instanceof  IGankDateView){
            mGankDateView = view;
            mContext = context;
            loadDate();
        }
    }

    public void loadDate(){
        mGankModel = new GankModel();
        mGankModel.getDate(new IGankModel.OnDateLoadedListener() {
            @Override
            public void onDateLoadedListener(List<String> list) {
                mList_date = list;
                if(mGankDateView != null) mGankDateView.showDatas(list);
                loadPic();
                getTodayTitle();
            }
        });
    }

    // need loadDate
    private void loadPic(){
        mGankModel.getRecentlyPicUrl(new IGankModel.OnRecentlyPicResultListener() {
            @Override
            public void onRecentlyPicResultListener(String url) {
                mTodayPicUrl = url;
                if (mGankDateView != null) mGankDateView.showCover(url);
            }
        });
    }

    private void getTodayTitle(){
        if(mList_date != null && mList_date.size() >0){
            mGankModel.getDayTitle(mList_date.get(0), new IGankModel.OnDayTitleLoadedListener() {
                @Override
                public void onDayTitleLoadedListener(String title) {
                    mGankDateView.showTitle(title);
                }
            });
        }
    }

    private void getDayDatas(String date){
        final String[] dates = GankDayDatasPresenter.parseDateString(date);
        mGankModel.getDayDatas(dates[0], dates[1], dates[2], new IGankModel.OnDayDatasLoadedListener() {
            @Override
            public void onDayDatasLoadedListener(GankDayBean.ResultsBean resultsBean) {
//                mIGankDayDatasView.showDatas(mResultsBean);
                startDayActivity(resultsBean);
            }
        });
//        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
//            @Override
//            public void call(Subscriber<? super String> subscriber) {
////                GankDayDatasPresenter mGankPresenter = new GankDayDatasPresenter(mCon);
////                mGankPresenter.loadDates(dates[0],dates[1],dates[2]);
//
//            }
//        });
    }


    private void startDayActivity(GankDayBean.ResultsBean resultsBean){
        Intent intent = new Intent(mContext,GankDayDatasActivity.class);
        String[] extraDatas = new String[]{mTodayPicUrl,mList_date.get(0)};
//        intent.putExtra(GankDayDatasPresenter.INTENT_DATA_GANK_DATE,mList_Date.get(0));
//        Bundle bundle = new Bundle();
//        bundle.putSerializable(IGankModel.KEY_GANK_DAY_DATAS,resultsBean);
//        intent.putExtra(IGankModel.KEY_GANK_DAY_DATAS,bundle);
        intent.putExtra(IGankModel.KEY_GANK_DAY_DATAS,extraDatas);

        mContext.startActivity(intent);
    }

    @Override
    public void onSelectedDay(String date) {
        getDayDatas(date);
    }
}
