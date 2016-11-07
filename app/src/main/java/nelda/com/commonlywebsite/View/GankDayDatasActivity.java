package nelda.com.commonlywebsite.View;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import nelda.com.commonlywebsite.Adapter.GankDataRecyclerAdapter;
import nelda.com.commonlywebsite.BaseActivity;
import nelda.com.commonlywebsite.Bean.GankDayBean;
import nelda.com.commonlywebsite.MainActivity;
import nelda.com.commonlywebsite.Model.IGankModel;
import nelda.com.commonlywebsite.Presenter.GankDayDatasPresenter;
import nelda.com.commonlywebsite.R;


/**
 * Created by Administrator on 2016/7/11 0011.
 */
public class GankDayDatasActivity extends BaseActivity implements IGankDayDatasView {
    String mPicUrl = "http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/1";


    TextView textView;
    ImageView mImg_Cover;
    TextView mTV_Head_Title;
    RecyclerView mListView_Gank;

//    GankDataAdapter mAdapter;
    GankDataRecyclerAdapter mRecylerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_gank_day);
        hideDecor();
        initUI();
//        setView(container);
//        getPicData(baseUrl);
//        getDayDatas(baseUrl,2016,"09",18);
        Intent intent = getIntent();
        String[] extraDatas = intent.getStringArrayExtra(IGankModel.KEY_GANK_DAY_DATAS);
//        GankDayBean.ResultsBean resultsBean = (GankDayBean.ResultsBean) intent.getSerializableExtra(IGankModel.KEY_GANK_DAY_DATAS);
        String[] dates = GankDayDatasPresenter.parseDateString(extraDatas[1]);

        GankDayDatasPresenter mGankPresenter = new GankDayDatasPresenter(this);
//        mGankPresenter.setDayDatas(resultsBean);
        showCover(extraDatas[0]);
        mGankPresenter.loadDates(dates[0],dates[1],dates[2]);
    }

    private void initUI(){
//        ViewGroup container = (SlidingLayout) findViewById(R.id.container_gank);
        mImg_Cover = (ImageView)findViewById(R.id.gank_banner_cover);
        mTV_Head_Title = (TextView) findViewById(R.id.gank_head_title);
        mListView_Gank = (RecyclerView)findViewById(R.id.gank_listview);
//        mAdapter = new GankDataAdapter(this);
        mRecylerAdapter = new GankDataRecyclerAdapter(this);
        mListView_Gank.setAdapter(mRecylerAdapter);
        mListView_Gank.setLayoutManager(new LinearLayoutManager(this));
        mRecylerAdapter.setOnItemClickListener(new GankDataRecyclerAdapter.OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                GankDataRecyclerAdapter.LinkBean bean = mRecylerAdapter.getItemLinkBean(position);
                startAcitivity(bean.link);
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void hideDecor(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // Translucent navigation bar
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.green_primary));


        }
    }



//    private void getPicData(String url){
//        RestAdapter restAdapter = new RestAdapter.Builder()
//                .setLogLevel(RestAdapter.LogLevel.FULL)
//                .setEndpoint(url).build();
//
//        GankApi gankApi = restAdapter.create(GankApi.class);
//
//        gankApi.getData(10, 1, new Callback<GankPicResponse>() {
//            @Override
//            public void success(GankPicResponse gankPicModel, Response response) {
//                List<GankPicModel> list_result = gankPicModel.getResults();
//                List<String> datas = new ArrayList<>();
//                for (int i = 0; i < list_result.size(); i++) {
////                    Log.e("GankDateActivity", list_result.get(i).getUrl());
//                    datas.add(list_result.get(i).getUrl());
//                }
//                ImageLoader.getInstance().displayImage(datas.get(0), mImg_Cover);
////                gridAdapter.setDatas(datas);
////                gridAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//
//            }
//        });
//    }

    private void startAcitivity(String url){
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void showDatas(GankDayBean.ResultsBean mResultsBean) {

//        mTV_Head_Title.setText(mResultsBean.getAndroid().get(0).getPublishedAt());
//        mTV_Head_Title.append("-\nAndroid / iOS / ExtendResource/ Recommend ");
        mRecylerAdapter.setDatas(mResultsBean);
    }

    public void showCover(String picUrl){
        ImageLoader.getInstance().displayImage(picUrl, mImg_Cover);
    }

//    private void setView(ViewGroup rootView){
//        imageView = new ImageView(this);
//        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        imageView.setLayoutParams(params);
//        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//        rootView.addView(imageView);
//    }




}
