package nelda.com.commonlywebsite.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import nelda.com.commonlywebsite.Bean.GankDayBean;
import nelda.com.commonlywebsite.MainActivity;
import nelda.com.commonlywebsite.R;

/**
 * Created by Administrator on 2016/9/20.
 */
public class GankDataAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {
    GankDayBean.ResultsBean mResultsBean;
    List<LinkBean> list_LinkBeans = new ArrayList<>();
    Context mContext;

    public GankDataAdapter(Context context){
        mContext = context;
    }

    @Override
    public int getCount() {
        return list_LinkBeans.size();
    }

    public void setDatas(GankDayBean.ResultsBean resultsBean){
        mResultsBean = resultsBean;
        //init Android datas
        if(resultsBean.getAndroid() != null){
            list_LinkBeans.add(new LinkBean(resultsBean.getAndroid().get(0).getType(),""));
            for (int i=0; i<resultsBean.getAndroid().size(); i++){
                LinkBean bean = new LinkBean(resultsBean.getAndroid().get(i).getDesc(),resultsBean.getAndroid().get(i).getUrl());
                list_LinkBeans.add(bean);
            }
        }
        //init iOS datas
        if(resultsBean.getIOS() != null) {
            list_LinkBeans.add(new LinkBean(resultsBean.getIOS().get(0).getType(),""));
            for (int i=0; i<resultsBean.getIOS().size(); i++){
                LinkBean bean = new LinkBean(resultsBean.getIOS().get(i).getDesc(),resultsBean.getIOS().get(i).getUrl());
                list_LinkBeans.add(bean);
            }
        }

        // init ExtendResource datas
        if(resultsBean.getExtendResource() != null) {
            list_LinkBeans.add(new LinkBean(resultsBean.getExtendResource().get(0).getType(),""));
            for (int i=0; i<resultsBean.getExtendResource().size(); i++){
                LinkBean bean = new LinkBean(resultsBean.getExtendResource().get(i).getDesc(),resultsBean.getExtendResource().get(i).getUrl());
                list_LinkBeans.add(bean);
            }
        }

        // init Recommend datas
        if(resultsBean.getRecommend() != null) {
            list_LinkBeans.add(new LinkBean(resultsBean.getRecommend().get(0).getType(),""));
            for (int i=0; i<resultsBean.getRecommend().size(); i++){
                LinkBean bean = new LinkBean(resultsBean.getRecommend().get(i).getDesc(),resultsBean.getRecommend().get(i).getUrl());
                list_LinkBeans.add(bean);
            }
        }

        // init RestMovie datas
        if(resultsBean.getRestMovie() != null) {
            list_LinkBeans.add(new LinkBean(resultsBean.getRestMovie().get(0).getType(),""));
            for (int i=0; i<resultsBean.getRestMovie().size(); i++){
                LinkBean bean = new LinkBean(resultsBean.getRestMovie().get(i).getDesc(),resultsBean.getRestMovie().get(i).getUrl());
                list_LinkBeans.add(bean);
            }
        }

        // init Welfare datas
        if(resultsBean.getWelfare() != null) {
            list_LinkBeans.add(new LinkBean(resultsBean.getWelfare().get(0).getType(),""));
            for (int i=0; i<resultsBean.getWelfare().size(); i++){
                LinkBean bean = new LinkBean(resultsBean.getWelfare().get(i).getDesc(),resultsBean.getWelfare().get(i).getUrl());
                list_LinkBeans.add(bean);
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public Object getItem(int i) {
        return list_LinkBeans.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(mContext).inflate(R.layout.item_gank_data,null);
        TextView tv_name = (TextView)view.findViewById(R.id.tv_item_gank_data);
        changeTextViewColor(tv_name,!(list_LinkBeans.get(i).link.isEmpty()));
        tv_name.setText(list_LinkBeans.get(i).name);
        return view;
    }

    private void changeTextViewColor(TextView target,boolean isGreen){
        if(isGreen){
            target.setTextColor(mContext.getResources().getColor(R.color.green_primary));
            target.setTextSize(14);
//            target.setTranslationX(80);
            Drawable drawable = mContext.getResources().getDrawable(R.drawable.voal);
            drawable.setBounds(0, 0, 15, 15);
            Drawable drawable1 = mContext.getResources().getDrawable(R.drawable.abc_ab_share_pack_mtrl_alpha);
            target.setCompoundDrawables(drawable, null, null, null);
            target.setCompoundDrawablePadding(80);
        }else{
            target.setTextColor(mContext.getResources().getColor(R.color.white));
            target.setTextSize(20);
            target.setTranslationX(0);
        }
    }

    @Override
    public boolean isEnabled(int position) {
        if(list_LinkBeans.get(position).link.isEmpty()){
            return false;
        }
        return super.isEnabled(position);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String url = list_LinkBeans.get(i).link;
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        Intent intent = new Intent(mContext, MainActivity.class);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }


    class LinkBean{
        public String name;
        public String link;

         public LinkBean(String name,String link){
             this.name = name;
             this.link = link;
         }
    }
}
