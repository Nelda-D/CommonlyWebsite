package nelda.com.commonlywebsite;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import nelda.com.commonlywebsite.Adapter.GankDataRecyclerAdapter;
import nelda.com.commonlywebsite.Bean.GankDayModel;
import nelda.com.commonlywebsite.Bean.GankPicModel;
import nelda.com.commonlywebsite.Bean.GankPicResponse;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Path;


/**
 * Created by Administrator on 2016/7/11 0011.
 */
public class GankActivity extends BaseActivity {
    String mPicUrl = "http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/1";

    String baseUrl = "http://gank.io";
    TextView textView;
    ImageView mImg_Cover;
    TextView mTV_Head_Title;
    RecyclerView mListView_Gank;
    GankDayModel.ResultsBean mResultsBean;
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
        getDayDatas(baseUrl,2016,"09",18);
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
//        mListView_Gank.setOnItemClickListener(mAdapter);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.gank_collapsing);
//        collapsingToolbarLayout.
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

    private void onResponse(){
        ImageLoader.getInstance().displayImage(mResultsBean.getWelfare().get(0).getUrl(), mImg_Cover);
//        mTV_Head_Title.setText(mResultsBean.getAndroid().get(0).getPublishedAt());
//        mTV_Head_Title.append("-\nAndroid / iOS / ExtendResource/ Recommend ");
        mRecylerAdapter.setDatas(mResultsBean);
    }

    private void getPicData(String url){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(url).build();

        GankApi gankApi = restAdapter.create(GankApi.class);

        gankApi.getData(10, 1, new Callback<GankPicResponse>() {
            @Override
            public void success(GankPicResponse gankPicModel, Response response) {
                List<GankPicModel> list_result = gankPicModel.getResults();
                List<String> datas = new ArrayList<>();
                for (int i = 0; i < list_result.size(); i++) {
//                    Log.e("GankActivity", list_result.get(i).getUrl());
                    datas.add(list_result.get(i).getUrl());
                }
                ImageLoader.getInstance().displayImage(datas.get(0), mImg_Cover);
//                gridAdapter.setDatas(datas);
//                gridAdapter.notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    private void startAcitivity(String url){
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void getDayDatas(String url,int year,String month ,int day){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(url).build();

        GankDayApi gankDayApi = restAdapter.create(GankDayApi.class);

        gankDayApi.getData(year, month, day, new Callback<GankDayModel>() {
            @Override
            public void success(GankDayModel gankDayModel, Response response) {
                 mResultsBean = gankDayModel.getResults();
                List<GankDayModel.ResultsBean.AndroidBean> list_AndroidBeans = mResultsBean.getAndroid();
//                for (int i=0; i<list_AndroidBeans.size();i++){
//                    System.out.println(mResultsBean.getAndroid().get(i).getDesc());
//                }
                onResponse();
            }
            @Override
            public void failure(RetrofitError error) {
            }
        });
    }


    /**
     http://gank.io/api/data/福利/10/1
     分类数据: http://gank.io/api/data/数据类型/请求个数/第几页
     */
    public interface GankApi {
        @GET("/api/data/福利/{count}/{page}")
        public void getData(@Path("count")int count,@Path("page")int page, Callback<GankPicResponse> gankPicModelCallback);
    }

    public interface GankDayApi{
//        http://gank.io/api/history/content/day/2016/05/11
        @GET("/api/day/{year}/{month}/{day}")
        public void getData(@Path("year")int year,@Path("month")String month,@Path("day")int day, Callback<GankDayModel> gankPicModelCallback);
    }

//    private void setView(ViewGroup rootView){
//        imageView = new ImageView(this);
//        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        imageView.setLayoutParams(params);
//        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//        rootView.addView(imageView);
//    }




}
