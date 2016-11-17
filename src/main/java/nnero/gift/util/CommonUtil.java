package nnero.gift.util;

import nnero.gift.bean.Pic;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * **********************************************
 * <p>
 * Author NNERO
 * <p>
 * Time : 16/11/15 下午4:42
 * <p>
 * Function: json to obj or obj to json
 * <p>
 * ************************************************
 */
public class CommonUtil {

    public static Pic jsonToPic(JSONObject json){
        if(json != null){
            Pic pic = new Pic();
            pic.setTarget_id(json.optString("target_id"));
            pic.setPic_url(json.optString("pic_url"));
            pic.setTarget_name(json.optString("target_name"));
            pic.setTarget_dir(json.optString("target_dir"));
            pic.setPlatform(json.optString("platform"));
            return pic;
        }
        return null;
    }

    public static List<Pic> jsonListToPicList(List<String> jsonList){
        if(jsonList != null && jsonList.size() > 0) {
            List<Pic> sources = new ArrayList<>();
            for (String jsonStr : jsonList) {
                JSONObject object = new JSONObject(jsonStr);
                String code = object.optString("code");
                if("1000".equals(code)) {
                    JSONObject json = object.optJSONObject("pic");
                    Pic pic = new Pic();
                    pic.setTarget_id(json.optString("target_id"));
                    pic.setPic_url(json.optString("pic_url"));
                    pic.setTarget_name(json.optString("target_name"));
                    pic.setTarget_dir(json.optString("target_dir"));
                    pic.setPlatform(json.optString("platform"));
                    sources.add(pic);
                } else {
                    NLog.e(object.optString("info"));
                }
            }
            return sources;
        } else {
            NLog.e("jsonList is null or size 0");
            return null;
        }
    }

    public static boolean isEmpty(String str){
        return str == null || "".equals(str.trim());
    }
}
