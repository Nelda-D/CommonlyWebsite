package nelda.com.commonlywebsite;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.content.ClipboardManager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import nelda.com.commonlywebsite.Bean.Website;
import nelda.com.commonlywebsite.Fragment.BaseFragment;



public class MainActivity extends BaseActivity implements View.OnClickListener {
    private Button mButton_JCodeCraeer;
    private Button mButton_ApkBus;
    private Button mButton_codekk;
    private Button mBUtton_V2ex;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private int mCurrentFragmentNum = 0;
    private ProgressBar mProgressBar;
    private List<Website> mList_website;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFragmentManager = getFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        initDatas();
        initClick();
        String extraUrl = getBundle();
        System.out.println("##########"+extraUrl);
        initFragment(extraUrl);
    }

    private String getBundle(){
        Bundle bundle = this.getIntent().getExtras();
        if(bundle == null) return "";
        String url = bundle.getString("url");
        return url;
    }

    private void initFragment(String url){
        if(url.isEmpty()){
            url = mList_website.get(mCurrentFragmentNum).getWebUrl();
        }
        mCurrentFragmentNum = 0;
//        BaseFragment fragment = new BaseFragment(mList_webUrl.get(mCurrentFragmentNum));
        mCurrentFragment = new BaseFragment(url);
//        mList_fragment.add(fragment);
        mFragmentTransaction.replace(R.id.main_content,mCurrentFragment);
        mFragmentTransaction.commit();
    }

    private void replaceFragment(int position){
        if(mCurrentFragmentNum == position){
            return;
        }
        mCurrentFragmentNum = position;
        mCurrentFragment.loadUrl(mList_website.get(position).getWebUrl());
//        BaseFragment fragment = new BaseFragment(mList_webUrl.get(position));
//        mList_fragment.add(fragment);
//        mFragmentTransaction.replace(R.id.main_content, mCurrentFragment);
//        mFragmentTransaction.commit();
//        mList_fragment.remove(0);
    }

    private void initDatas(){
        mList_website = new ArrayList<>();
        Website web1 = new Website("V2EX","http://www.v2ex.com/");
        Website web2 = new Website("APK BUS","http://www.apkbus.com/forum.php?forumlist=1&mobile=2");
        Website web3 = new Website("CODEKK","http://a.codekk.com/");
        Website web4 = new Website("CRAEER","http://www.jcodecraeer.com");

        mList_website.add(web1);
        mList_website.add(web2);
        mList_website.add(web3);
        mList_website.add(web4);


    }




    private void initClick(){
        mButton_JCodeCraeer = (Button)findViewById(R.id.button_pao);
        mButton_JCodeCraeer.setOnClickListener(this);
        mButton_JCodeCraeer.setText(mList_website.get(0).getName());
        mButton_ApkBus = (Button)findViewById(R.id.button_apkbus);
        mButton_ApkBus.setOnClickListener(this);
        mButton_ApkBus.setText(mList_website.get(1).getName());
        mButton_codekk = (Button)findViewById(R.id.button_codekk);
        mButton_codekk.setOnClickListener(this);
        mButton_codekk.setText(mList_website.get(2).getName());
        mBUtton_V2ex = (Button)findViewById(R.id.button_v2ek);
        mBUtton_V2ex.setOnClickListener(this);
        mBUtton_V2ex.setText(mList_website.get(3).getName());
    }

    private void initProgressbar(){
        mProgressBar = (ProgressBar)findViewById(R.id.main_progressbar);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            Boolean isTrue = mCurrentFragment.webviewGoBack();
            if(!isTrue){
                exitApp();
            }else{
                return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }

    private void exitApp(){
        System.exit(0);
    }

    @Override
    public void onClick(View view) {
        int position = 0;
        switch (view.getId()){
            case R.id.button_pao:
                position = 0;
                break;
            case R.id.button_apkbus:
                position = 1;
                break;
            case R.id.button_codekk:
                position = 2;
                break;
            case R.id.button_v2ek:
                position = 3;

                break;
        }
        replaceFragment(position);
    }


}
