package nelda.com.commonlywebsite.Presenter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.view.View;

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
    private Activity mActivity;
    private String mTodayPicUrl;

    public GankDatePresenter(Activity context, IGankDateView view){
        if(view instanceof  IGankDateView){
            mGankDateView = view;
            mActivity = context;
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

    private void getDayDatas(String date, final View sharedView){
        final String[] dates = GankDayDatasPresenter.parseDateString(date);
        mGankModel.getDayDatas(dates[0], dates[1], dates[2], new IGankModel.OnDayDatasLoadedListener() {
            @Override
            public void onDayDatasLoadedListener(GankDayBean.ResultsBean resultsBean) {
//                mIGankDayDatasView.showDatas(mResultsBean);
                startDayActivity(resultsBean,sharedView);
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


    private void startDayActivity(GankDayBean.ResultsBean resultsBean,View sharedView){
        Intent intent = new Intent(mActivity,GankDayDatasActivity.class);
        String[] extraDatas = new String[]{mTodayPicUrl,mList_date.get(0)};
//        intent.putExtra(GankDayDatasPresenter.INTENT_DATA_GANK_DATE,mList_Date.get(0));
//        Bundle bundle = new Bundle();
//        bundle.putSerializable(IGankModel.KEY_GANK_DAY_DATAS,resultsBean);
//        intent.putExtra(IGankModel.KEY_GANK_DAY_DATAS,bundle);
        intent.putExtra(IGankModel.KEY_GANK_DAY_DATAS,extraDatas);
//        mIntent.setClass(this, ShareElementsActivity.class);
        ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(mActivity, sharedView, "SharedView");
        mActivity.startActivity(intent, transitionActivityOptions.toBundle());
//        mActivity.startActivity(intent);
    }

    @Override
    public void onSelectedDay(String date,View sharedView) {
        getDayDatas(date,sharedView);
    }
}
