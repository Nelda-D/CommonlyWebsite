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
                if(mGankDateView != null) mGankDateView.showDatas(list);
                loadPic();
            }
        });
    }

    // need loadDate
    private void loadPic(){
        mGankModel.getRecentlyPicUrl(new IGankModel.OnRecentlyPicResultListener() {
            @Override
            public void onRecentlyPicResultListener(String url) {
                if(mGankDateView != null) mGankDateView.showCover(url);
            }
        });
    }

}
