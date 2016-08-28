package nelda.com.commonlywebsite.Bean;

/**
 * Created by Administrator on 2016/5/14 0014.
 */
public class Website {
    public String name;
    public String webUrl;

    public Website(String name,String webUrl){
        this.name = name;
        this.webUrl = webUrl;
    }


    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }

    public void setWebUrl(String webUrl){
        this.webUrl = webUrl;
    }
    public String getWebUrl(){
        return webUrl;
    }


}
