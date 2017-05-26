
package malek.com.quranmp3.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecitationsContainer {

    private Integer id;
    private String title;
    private String type;
    private String orginalItem;
    private String translationLanguage;
    private String sourceLanguage;
    private RecitationsInfo recitationsInfo;
    private List<Recitation> recitations = null;
    private List<String> locales = null;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrginalItem() {
        return orginalItem;
    }

    public void setOrginalItem(String orginalItem) {
        this.orginalItem = orginalItem;
    }

    public String getTranslationLanguage() {
        return translationLanguage;
    }

    public void setTranslationLanguage(String translationLanguage) {
        this.translationLanguage = translationLanguage;
    }

    public String getSourceLanguage() {
        return sourceLanguage;
    }

    public void setSourceLanguage(String sourceLanguage) {
        this.sourceLanguage = sourceLanguage;
    }

    public RecitationsInfo getRecitationsInfo() {
        return recitationsInfo;
    }

    public void setRecitationsInfo(RecitationsInfo recitationsInfo) {
        this.recitationsInfo = recitationsInfo;
    }

    public List<Recitation> getRecitations() {
        return recitations;
    }

    public void setRecitations(List<Recitation> recitations) {
        this.recitations = recitations;
    }

    public List<String> getLocales() {
        return locales;
    }

    public void setLocales(List<String> locales) {
        this.locales = locales;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return "RecitationsContainer{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", orginalItem='" + orginalItem + '\'' +
                ", translationLanguage='" + translationLanguage + '\'' +
                ", sourceLanguage='" + sourceLanguage + '\'' +
                ", recitationsInfo=" + recitationsInfo +
                ", recitations=" + recitations +
                ", locales=" + locales +
                ", additionalProperties=" + additionalProperties +
                '}';
    }
}
