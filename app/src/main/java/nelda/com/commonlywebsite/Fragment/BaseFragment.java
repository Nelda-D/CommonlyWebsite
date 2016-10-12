package nelda.com.commonlywebsite.Fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

//import com.gc.materialdesign.views.ProgressBarCircularIndeterminate;


import nelda.com.commonlywebsite.R;

/**
 * Created by Administrator on 2016/2/14 0014.
 */
public class BaseFragment extends Fragment {
    public WebView mWebView;
//    private ProgressBarCircularIndeterminate mProgress;
private ProgressBar mProgress;
    private String mWebViewPat;  //��ַ·��
    private String mUrlHead ="http://";

    public BaseFragment(){}

    @SuppressLint("ValidFragment")
    public BaseFragment(String webUrl){
        if(!webUrl.startsWith(mUrlHead)){
            mWebViewPat = mUrlHead + webUrl;
        }
        mWebViewPat = webUrl;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_web,container,false);
        initWebView(view);
//        mProgress = (ProgressBarCircularIndeterminate)view.findViewById(R.id.progressBarCircularIndetermininate);
        mProgress = (ProgressBar)view.findViewById(R.id.progressBarCircularIndetermininate);

        return view;
    }

    public void initWebView(View view){
        mWebView =(WebView) view.findViewById(R.id.webview_jcodecraeer);
        mWebView.loadUrl(mWebViewPat);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    mProgress.setVisibility(View.GONE);
                }
            }
        });
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mProgress.setVisibility(View.GONE);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mProgress.setVisibility(View.VISIBLE);
            }
        });
    }

    public boolean webviewGoBack() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }else{
            return false;
        }
    }
    public void setWebViewPath(String path){
        this.mWebViewPat = path;
    }
    public String getWebViewPath(){
        return mWebViewPat;
    }

    public void loadUrl(String url){
        mWebView.loadUrl(url);
    }
}
