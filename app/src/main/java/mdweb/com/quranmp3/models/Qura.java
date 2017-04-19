package mdweb.com.quranmp3.models;

/**
 * Created by lenovo on 14/05/2016.
 *
 *
 */
public class Qura {
    public Qura() {
    }
    private String title ;
    private String api_url ;


    public int getUniqueResitance() {
        return uniqueResitance;
    }

    public void setUniqueResitance(int uniqueResitance) {
        this.uniqueResitance = uniqueResitance;
    }

    private int uniqueResitance ;


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    private int count ;

    public Qura(String title, String api_url) {
        this.title = title;
        this.api_url = api_url;
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
