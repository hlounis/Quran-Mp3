package mdweb.com.quranmp3.tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import mdweb.com.quranmp3.models.ApiModel;


public class DataParser {
    private static DataParser instance = null;


    private List<ApiModel> apiModels;


    private JSONObject listQuraJson;

    protected DataParser() {

    }

    public static DataParser getInstance() {
        if (instance == null) {
            instance = new DataParser();
        }
        return instance;
    }

    public static List<ApiModel> createList(JSONObject jsonObject, String cleGeneral, String cleUrl, boolean hasCount) {
        ArrayList<ApiModel> quars = new ArrayList<>();
        if (jsonObject != null)
            try {
                JSONArray qurayJsonArray = jsonObject.getJSONArray(cleGeneral);
                for (int i = 0; i < qurayJsonArray.length(); i++) {
                    JSONObject qurajsonObject = qurayJsonArray.getJSONObject(i);
                    ApiModel apiModel = new ApiModel();
                    apiModel.setTitle(qurajsonObject.getString("title"));
                    apiModel.setApi_url(qurajsonObject.getString(cleUrl));

                    if (hasCount) {
                        apiModel.setCount(qurajsonObject.getJSONObject("recitations_info").getInt("count"));
                        if (apiModel.getCount() == 1) {
                            JSONArray jsonArray = qurajsonObject.getJSONObject("recitations_info").getJSONArray("recitations_ids");
                            if (jsonArray.length() > 0) {
                                apiModel.setUniqueResitance(jsonArray.getInt(0));
                            }
                        }
                    }

                    quars.add(apiModel);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        return quars;
    }

    public JSONObject getListQuraJson() {
        return listQuraJson;
    }

    public void setListQuraJson(JSONObject listQuraJson) {
        this.listQuraJson = listQuraJson;
        apiModels = createList(listQuraJson, Urls.cle_Qura, Urls.cle_Qura_Url, true);
    }

    public List<ApiModel> getApiModels() {
        return apiModels;
    }

    public void setApiModels(List<ApiModel> apiModels) {
        this.apiModels = apiModels;
    }
}
