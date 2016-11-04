package nelda.com.commonlywebsite.Presenter;

import java.util.List;

import nelda.com.commonlywebsite.Model.GankModel;
import nelda.com.commonlywebsite.Model.IGankModel;
import nelda.com.commonlywebsite.View.IGankDateView;

/**
 * Created by Administrator on 2016/10/31.
 */
public class GankDatePresenter {
    private IGankDateView mGankDateView;
    private IGankModel mGankModel;
    List<String> mList_date;

    public GankDatePresenter(IGankDateView view){
        if(view instanceof  IGankDateView){
            mGankDateView = view;
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
                getTodayPic();
            }
        });
    }

    // need loadDate
    private void loadPic(){
        mGankModel.getRecentlyPicUrl(new IGankModel.OnRecentlyPicResultListener() {
            @Override
            public void onRecentlyPicResultListener(String url) {
                if (mGankDateView != null) mGankDateView.showCover(url);
            }
        });
    }

    private void getTodayPic(){
        if(mList_date != null && mList_date.size() >0){
            mGankModel.getDayTitle(mList_date.get(0), new IGankModel.OnDayTitleLoadedListener() {
                @Override
                public void onDayTitleLoadedListener(String title) {
                    mGankDateView.showTitle(title);
                }
            });
        }
    }

}
