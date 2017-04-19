package mdweb.com.quranmp3.tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import mdweb.com.quranmp3.models.Qura;

/**
 * Created by lenovo on 14/05/2016.
 */
public class DataParser {
    public DataParser() {

    }

    public static ArrayList<Qura> getListQura(String file, String cleGeneral, String cleUrl,boolean hasCount) {
        ArrayList<Qura> quars = new ArrayList<>();
        if (file != null)
            try {
                JSONObject jsonObject = new JSONObject(file);
                JSONArray qurayJsonArray = jsonObject.getJSONArray(cleGeneral);
                for (int i = 0; i < qurayJsonArray.length(); i++) {
                    JSONObject qurajsonObject = qurayJsonArray.getJSONObject(i);
                    Qura qura = new Qura();
                    qura.setTitle(qurajsonObject.getString("title"));
                    qura.setApi_url(qurajsonObject.getString(cleUrl));

                    if (hasCount) {
                        qura.setCount(qurajsonObject.getJSONObject("recitations_info").getInt("count"));
                        if (qura.getCount()==1) {
                            JSONArray jsonArray = qurajsonObject.getJSONObject("recitations_info").getJSONArray("recitations_ids");
                            if (jsonArray.length() > 0) {
                                qura.setUniqueResitance(jsonArray.getInt(0));
                            }
                        }
                    }

                    quars.add(qura);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        return quars;
    }
}
