package nelda.com.commonlywebsite.View;

import java.util.List;

import nelda.com.commonlywebsite.Bean.GankDayBean;

/**
 * Created by Administrator on 2016/10/23.
 */
public interface IGankDateView {
    void showDatas(List<String> list_date);
    void showCover(String imageUrl);
}
