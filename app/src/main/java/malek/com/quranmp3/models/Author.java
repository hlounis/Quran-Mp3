
package malek.com.quranmp3.models;

import java.util.HashMap;
import java.util.Map;

public class Author extends ApiModel {

    private Integer addDate;
    private String type;
    private RecitationsInfo recitations_info;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getAddDate() {
        return addDate;
    }

    public void setAddDate(Integer addDate) {
        this.addDate = addDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", addDate=" + addDate +
                ", type='" + type + '\'' +
                ", recitations_info=" + recitations_info +
                ", additionalProperties=" + additionalProperties +
                '}';
    }

    public RecitationsInfo getRecitations_info() {
        return recitations_info;
    }

    public void setRecitations_info(RecitationsInfo recitations_info) {
        this.recitations_info = recitations_info;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
