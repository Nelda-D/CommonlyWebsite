package nelda.com.commonlywebsite;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import nelda.com.commonlywebsite.Fragment.BaseFragment;

/**
 * Created by Administrator on 2016/6/26 0026.
 */
public class BaseActivity extends Activity {

    private DisplayImageOptions options;

    protected BaseFragment mCurrentFragment;


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadImageLoader();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this,DrawActivity.class);
            startActivity(intent);
            return true;
        }else if(id == R.id.action_finish){
            finish();
            return true;
        }else if(id == R.id.action_copy_url){
            String path = mCurrentFragment.mWebView.getUrl();
            ClipboardManager cmb = (ClipboardManager)this.getSystemService(Context.CLIPBOARD_SERVICE);
            cmb.setText(path);
            return true;
        }else if(id == R.id.action_webview){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            return true;
        }else if(id == R.id.action_gankpic){
            Intent intent = new Intent(this,GankActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadImageLoader(){
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .memoryCache(new WeakMemoryCache())
                .build();
        ImageLoader.getInstance().init(config);

        options = new DisplayImageOptions.Builder()
                .showStubImage(0)
                .showImageForEmptyUri(0)//����ͼƬΪ�ջ��Ǵ���ʱ��ʾ��ͼƬ
                .showImageOnFail(0)//����ͼƬ���ػ����Ĺ����з���������ʾ��ͼƬ
                .cacheInMemory().cacheOnDisc()
                .cacheInMemory(false) // default ��������IDEͼƬ�Ƿ񻺴����ڴ���
                .cacheOnDisk(false) //dafault �������ص�ͼƬ�Ƿ񻺴���SD����
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT) //default ����ͼƬ����εı��뷽ʽ��ʾ
                .bitmapConfig(Bitmap.Config.ARGB_8888)//default ����ͼƬ�Ľ�������
                .build();
    }

    protected DisplayImageOptions getImageLoaderOptions(){
        return options;
    }



}
