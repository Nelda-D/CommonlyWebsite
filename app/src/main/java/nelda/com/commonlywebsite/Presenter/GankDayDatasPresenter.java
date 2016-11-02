package nelda.com.commonlywebsite.Presenter;

import nelda.com.commonlywebsite.Bean.GankDayBean;
import nelda.com.commonlywebsite.Model.GankModel;
import nelda.com.commonlywebsite.Model.IGankModel;
import nelda.com.commonlywebsite.View.IGankDayDatasView;

/**
 * Created by Administrator on 2016/10/23.
 */
public class GankDayDatasPresenter {
    IGankModel mIGankModel;
    IGankDayDatasView mIGankDayDatasView;
    public final static String INTENT_DATA_GANK_DATE = "intent_data_gank_date";


    public GankDayDatasPresenter(IGankDayDatasView view){
        if(view instanceof IGankDayDatasView){
            mIGankDayDatasView = view;
        }
        mIGankModel = new GankModel();
    }

    public void loadDates(String year,String month,String day){
        getDayDatas(year, month, day);
    }


    private void getDayDatas(String year,String month,String day){
        mIGankModel.getDayDatas(year, month, day, new IGankModel.OnDayDatasLoadedListener() {
            @Override
            public void onDayDatasLoadedListener(GankDayBean.ResultsBean mResultsBean) {
                mIGankDayDatasView.showDatas(mResultsBean);
            }
        });
    }

}
