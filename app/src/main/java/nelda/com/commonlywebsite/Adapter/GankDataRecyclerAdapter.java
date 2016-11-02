package nelda.com.commonlywebsite.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import nelda.com.commonlywebsite.Bean.GankDayBean;
import nelda.com.commonlywebsite.R;

/**
 * Created by Administrator on 2016/9/22.
 */
public class GankDataRecyclerAdapter extends RecyclerView.Adapter<GankDataRecyclerAdapter.MyViewHolder> {
    Context mContext;
    GankDayBean.ResultsBean mResultsBean;
    List<LinkBean> list_LinkBeans = new ArrayList<>();

    public GankDataRecyclerAdapter(Context context){
        mContext = context;
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_gank_data,viewGroup,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int i) {
        viewHolder.mTv_Name.setText(list_LinkBeans.get(i).name);
        changeTextViewColor(viewHolder.mTv_Name, !(list_LinkBeans.get(i).link.isEmpty()));
    }

    @Override
    public int getItemCount() {
        return list_LinkBeans.size();
    }

    private void changeTextViewColor(TextView target,boolean isGreen){
        if(isGreen){
            target.setTextColor(mContext.getResources().getColor(R.color.white));
            target.setTextSize(14);
//            target.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
//            target.getPaint().setAntiAlias(true);//抗锯齿
//            target.setTranslationX(80);
            Drawable drawable = mContext.getResources().getDrawable(R.drawable.voal);
            drawable.setBounds(0, 0, 15, 15);
            target.setCompoundDrawables(drawable, null, null, null);
            target.setCompoundDrawablePadding(20);
        }else{
            target.setTextColor(mContext.getResources().getColor(R.color.green_primary));
            target.setTextSize(20);
            target.setTranslationX(0);
            target.setCompoundDrawables(null, null, null, null);
        }
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

    public LinkBean getItemLinkBean(int position){
       return list_LinkBeans.get(position);
    }

    public void setOnItemClickListener(OnRecyclerItemClickListener listener){
        mOnItemClickListener = listener;
    }
    OnRecyclerItemClickListener mOnItemClickListener;
    private void onItemClick(View view,int position){
        if(mOnItemClickListener != null){
            mOnItemClickListener.onItemClick(view,position);
        }
    }

    public interface OnRecyclerItemClickListener {
       void onItemClick(View view,int position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView mTv_Name;
        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClick(view,getPosition());
                    System.out.println("MyViewHolder getPosition() ->"+getPosition());
                }
            });
            mTv_Name = (TextView)itemView.findViewById(R.id.tv_item_gank_data);
        }
    }

    public class LinkBean{
        public String name;
        public String link;

        public LinkBean(String name,String link){
            this.name = name;
            this.link = link;
        }
    }
}
