package nelda.com.commonlywebsite;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import lib.homhomlib.design.SlidingLayout;
import nelda.com.commonlywebsite.Bean.GankPicModel;
import nelda.com.commonlywebsite.Bean.GankPicResponse;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Administrator on 2016/7/11 0011.
 */
public class GankActivity extends BaseActivity {
    String mPicUrl = "http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/1";

    String baseUrl = "http://gank.io";
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_gank_pic);
       // textView = (TextView) findViewById(R.id.textview);
        //getData(baseUrl);
        ViewGroup container = (SlidingLayout) findViewById(R.id.container_gank);
        setView(container);
        getData(baseUrl);
    }

    private void getData(String url){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(url).build();

        GankApi gankApi = restAdapter.create(GankApi.class);

        gankApi.getData(10, 1, new Callback<GankPicResponse>() {
            @Override
            public void success(GankPicResponse gankPicModel, Response response) {
                List<GankPicModel> list_result = gankPicModel.getResults();
                List<String> datas = new ArrayList<>();
                for (int i = 0; i<list_result.size();i++ ){
//                    Log.e("GankActivity", list_result.get(i).getUrl());
                    datas.add(list_result.get(i).getUrl());
                }
                gridAdapter.setDatas(datas);
                gridAdapter.notifyDataSetChanged();
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

    GridViewAdapter gridAdapter;
    private void setView(ViewGroup rootView){
        GridView gridView = getGridView();
        gridAdapter = new GridViewAdapter(this);
        gridView.setAdapter(gridAdapter);
        rootView.addView(gridView);
    }

//    private List<String> getDatas(){
//        List<String> datas = new ArrayList<>();
//        for (int i=0;i<20;i++){
//            datas.add("1");
//        }
//        return datas;
//    }


    private GridView getGridView(){
        GridView gridView = new GridView(this);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        gridView.setLayoutParams(params);
        gridView.setNumColumns(2);
        gridView.setBackgroundColor(getResources().getColor(R.color.white));
        return gridView;
    }

    class GridViewAdapter extends BaseAdapter {
        List<String> list_image_url = new ArrayList<>();
        Context mContext;

        public GridViewAdapter(Context context){
            mContext = context;
        }
        public void setDatas(List<String> datas){
            list_image_url = datas;
        }

        @Override
        public int getCount() {
            return list_image_url.size();
        }

        @Override
        public Object getItem(int i) {
            return list_image_url.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = LayoutInflater.from(mContext).inflate(R.layout.items_gridview_gank_pic,null);
            ImageView pic = (ImageView) view.findViewById(R.id.item_gridview_pic);
           // pic.setImageResource(R.drawable.ic_launcher);
            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.displayImage(list_image_url.get(i),pic);
            return view;
        }
    }


}
