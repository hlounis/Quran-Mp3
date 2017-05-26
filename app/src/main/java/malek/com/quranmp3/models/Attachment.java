
package malek.com.quranmp3.models;

import java.util.HashMap;
import java.util.Map;

public class Attachment extends ApiModel {


    private Integer order;
    private Double duration;
    private String size;
    private String extensionType;
    private Object description;
    private String url;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @Override
    public String toString() {
        return "Attachment{" +
                "id=" + id +
                ", order=" + order +
                ", title='" + title + '\'' +
                ", duration=" + duration +
                ", size='" + size + '\'' +
                ", extensionType='" + extensionType + '\'' +
                ", description=" + description +
                ", url='" + url + '\'' +
                ", additionalProperties=" + additionalProperties +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getExtensionType() {
        return extensionType;
    }

    public void setExtensionType(String extensionType) {
        this.extensionType = extensionType;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
