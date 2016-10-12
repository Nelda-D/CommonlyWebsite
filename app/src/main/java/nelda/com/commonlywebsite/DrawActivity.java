package nelda.com.commonlywebsite;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2016/6/25 0025.
 */
public class DrawActivity extends BaseActivity {
    private LinearLayout rootView;

    private int mRadius = 400;
    private int mStartLeft = 0;
    private Paint mYellowPaint;
    private Paint mGreenPaint;
    private Paint mRedPaint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView  = new LinearLayout(this);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rootView.setLayoutParams(params);
//        rootView.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
        setContentView(rootView);
        initPaint();
        ImageView imageView = initImageView();
        rootView.addView(imageView);

    }

    private ImageView initImageView(){
        Bitmap bitmap = Bitmap.createBitmap(400,400, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawChromeArc(canvas);
        ImageView imageView = new ImageView(this);
        imageView.setImageBitmap(bitmap);
        return imageView;
    }

    private void initPaint(){
        mYellowPaint = new Paint();
        mYellowPaint.setColor(getResources().getColor(android.R.color.holo_orange_dark));
        mGreenPaint = new Paint();
        mGreenPaint.setColor(getResources().getColor(android.R.color.holo_green_dark));
        mRedPaint = new Paint();
        mRedPaint.setColor(getResources().getColor(android.R.color.holo_red_dark));
    }

    private void drawChromeArc(Canvas canvas){
        RectF rectf = new RectF(mStartLeft,mStartLeft,mStartLeft+mRadius,mStartLeft+mRadius);
        canvas.drawArc(rectf,-30,120,true,mYellowPaint);
        canvas.drawArc(rectf,90,120,true,mGreenPaint);
        canvas.drawArc(rectf,210,120,true,mRedPaint);
    }



}
