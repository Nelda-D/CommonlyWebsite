package nelda.com.commonlywebsite.Bean;

/**
 * Created by Administrator on 2016/7/11 0011.
 */

import java.util.ArrayList;
import java.util.List;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class GankPicResponse {

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("results")
    @Expose
    private List<GankPicModel> results = new ArrayList<GankPicModel>();

    /**
     * @return The error
     */
    public Boolean getError() {
        return error;
    }

    /**
     * @param error The error
     */
    public void setError(Boolean error) {
        this.error = error;
    }

    /**
     * @return The results
     */
    public List<GankPicModel> getResults() {
        return results;
    }

    /**
     * @param results The results
     */
    public void setResults(List<GankPicModel> results) {
        this.results = results;
    }

}






