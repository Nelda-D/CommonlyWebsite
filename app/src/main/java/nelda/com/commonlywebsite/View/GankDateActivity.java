package nelda.com.commonlywebsite.View;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.transition.Slide;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.LoadedFrom;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;

import java.util.List;

import nelda.com.commonlywebsite.BaseActivity;
import nelda.com.commonlywebsite.Model.GankModel;
import nelda.com.commonlywebsite.Presenter.GankDatePresenter;
import nelda.com.commonlywebsite.R;
import nelda.com.commonlywebsite.Tool.TransitionManager;

/**
 * Created by Administrator on 2016/10/31.
 */
public class GankDateActivity extends BaseActivity implements IGankDateView {
    private ImageView mImg_Cover;
    private List<String> mList_Date;
    private TextView mTv_Title;
    private GankDatePresenter mGankDatePresenter;
    private View mView_Shadow;
    private DisplayImageOptions mOptions;
    private FrameLayout mContainer_Title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_gank_dayselect);
        hideDecor();
        mGankDatePresenter = new GankDatePresenter(this,this);
        initUI();
        iniImageLoaderOptions();
        getWindow().setEnterTransition(new Slide());
        getWindow().setExitTransition(new Slide());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initUI(){
        mContainer_Title = (FrameLayout)findViewById(R.id.gank_dayselect_title_container);
        mImg_Cover = (ImageView)findViewById(R.id.gank_dayselect_cover);
        mTv_Title = (TextView)findViewById(R.id.gank_dayselect_title);
        mView_Shadow = findViewById(R.id.gank_dayselect_layout_shadow);
        mImg_Cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TransitionManager manager = new TransitionManager(GankDateActivity.this);
                manager.addTransitionView(mImg_Cover,"SharedView");
                manager.addTransitionView(mView_Shadow,"SharedShadow");
                mGankDatePresenter.setTransitionManager(manager);
                mGankDatePresenter.onSelectedDay(mList_Date.get(0));
            }
        });
    }

    private Animator getShadowAnimator(View view, boolean isShow){
        if(isShow){
            return ObjectAnimator.ofFloat(view, "alpha", 0f, 0.5F).setDuration(200);
        }else{
            return ObjectAnimator.ofFloat(view, "alpha", view.getAlpha(), 0F).setDuration(400);
        }
    }


    @Override
    public void showDatas(List<String> list_date) {
        mList_Date=list_date;
    }

    @Override
    public void showCover(String imageUrl) {
        ImageLoader.getInstance().displayImage(imageUrl,mImg_Cover,mOptions);
    }



    @Override
    public void showTitle(String title) {
        mTv_Title.setText(title);
        mContainer_Title.setVisibility(View.VISIBLE);
        ObjectAnimator.ofFloat(mContainer_Title, "translationY", 400f, 0).setDuration(200).start();
//        ValueAnimator animation = ValueAnimator.ofObject(new MyTypeEvaluator(), startPropertyValue, endPropertyValue);
//        animation.setDuration(1000);
//        animation.start();
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

    private void iniImageLoaderOptions(){
        mOptions = new DisplayImageOptions.Builder()
                .showStubImage(0)
                .showImageForEmptyUri(0)//����ͼƬΪ�ջ��Ǵ���ʱ��ʾ��ͼƬ
                .showImageOnFail(0)//����ͼƬ���ػ����Ĺ����з���������ʾ��ͼƬ
                .cacheInMemory().cacheOnDisc()
                .cacheInMemory(true) // default ��������IDEͼƬ�Ƿ񻺴����ڴ���
                .cacheOnDisk(false) //dafault �������ص�ͼƬ�Ƿ񻺴���SD����
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT) //default ����ͼƬ����εı��뷽ʽ��ʾ
                .bitmapConfig(Bitmap.Config.ARGB_8888)//default ����ͼƬ�Ľ�������
                .displayer(new BitmapDisplayer() {
                    @Override
                    public void display(Bitmap bitmap, ImageAware imageAware, LoadedFrom loadedFrom) {
                        GankModel.getInstance().setRecentlyPicBitmap(bitmap);
                        imageAware.setImageBitmap(bitmap);
                    }
                })
                .build();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        getShadowAnimator(mView_Shadow,true);
        ObjectAnimator.ofFloat(mContainer_Title, "translationY", 400f, 0).setDuration(200).start();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mContainer_Title.setVisibility(View.VISIBLE);
//        Animator colorAnimator = getShadowAnimator(mView_Shadow,true);
        Animator transionAnimator = ObjectAnimator.ofFloat(mContainer_Title, "translationY", 400f, 0).setDuration(200);
        transionAnimator.start();
        AnimatorSet animatorSet = new AnimatorSet();
//        animatorSet.playTogether(colorAnimator,transionAnimator);
//        animatorSet.start();
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
//        if(hasFocus){
//            ObjectAnimator.ofFloat(mContainer_Title, "translationY", 800f, 600F).setDuration(200).start();
//        }
    }
}
