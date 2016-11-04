package nelda.com.commonlywebsite.View;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import nelda.com.commonlywebsite.BaseActivity;
import nelda.com.commonlywebsite.Bean.GankDayBean;
import nelda.com.commonlywebsite.Model.GankModel;
import nelda.com.commonlywebsite.Presenter.GankDatePresenter;
import nelda.com.commonlywebsite.Presenter.GankDayDatasPresenter;
import nelda.com.commonlywebsite.R;

/**
 * Created by Administrator on 2016/10/31.
 */
public class GankDateActivity extends BaseActivity implements IGankDateView {
    ImageView mImg_Cover;
    List<String> mList_Date;
    TextView mTv_Title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_gank_dayselect);
        hideDecor();
        GankDatePresenter presenter = new GankDatePresenter(this);
        initUI();
    }

    private void initUI(){
        mImg_Cover = (ImageView)findViewById(R.id.gank_dayselect_cover);
        mTv_Title = (TextView)findViewById(R.id.gank_dayselect_title);
        mImg_Cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GankDateActivity.this,GankDayDatasActivity.class);
                intent.putExtra(GankDayDatasPresenter.INTENT_DATA_GANK_DATE,mList_Date.get(0));
                startActivity(intent);
            }
        });
    }



    @Override
    public void showDatas(List<String> list_date) {
        mList_Date=list_date;
    }

    @Override
    public void showCover(String imageUrl) {
        ImageLoader.getInstance().displayImage(imageUrl,mImg_Cover);
    }

    @Override
    public void showTitle(String title) {
        mTv_Title.setText(title);
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
}
