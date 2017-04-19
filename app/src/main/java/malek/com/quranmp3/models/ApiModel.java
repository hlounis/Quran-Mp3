package malek.com.quranmp3.models;

/**
 * Created by lenovo on 14/05/2016.
 */
public class ApiModel {
    private String title;
    private String api_url;
    private int uniqueResitance;
    private int count;

    public ApiModel() {
    }

    public ApiModel(String title, String api_url) {
        this.title = title;
        this.api_url = api_url;
    }

    public int getUniqueResitance() {
        return uniqueResitance;
    }

    public void setUniqueResitance(int uniqueResitance) {
        this.uniqueResitance = uniqueResitance;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getApi_url() {
        return api_url;
    }

    public void setApi_url(String api_url) {
        this.api_url = api_url;
    }
}
