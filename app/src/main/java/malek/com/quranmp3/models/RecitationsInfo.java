
package malek.com.quranmp3.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecitationsInfo {

    private Integer count;
    private List<Integer> recitations_ids = null;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<Integer> getRecitations_ids() {
        return recitations_ids;
    }

    public void setRecitations_ids(List<Integer> recitations_ids) {
        this.recitations_ids = recitations_ids;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return "RecitationsInfo{" +
                "count=" + count +
                ", recitations_ids=" + recitations_ids +
                ", additionalProperties=" + additionalProperties +
                '}';
    }
}
