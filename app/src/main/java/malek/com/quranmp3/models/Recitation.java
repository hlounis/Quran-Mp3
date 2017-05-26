
package malek.com.quranmp3.models;

import java.util.HashMap;
import java.util.Map;

public class Recitation extends ApiModel {

    private Integer addDate;
    private String description;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAddDate() {
        return addDate;
    }

    public void setAddDate(Integer addDate) {
        this.addDate = addDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Recitation{" +
                "id=" + id +
                ", addDate=" + addDate +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +

                ", additionalProperties=" + additionalProperties +
                '}';
    }


    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
