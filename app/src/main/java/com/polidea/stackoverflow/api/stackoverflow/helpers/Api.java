package com.polidea.stackoverflow.api.stackoverflow.helpers;

import android.content.Context;
import android.provider.Settings;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Api {

    protected JSONObject getJsonObject(JSONObject jObject, String mappedText){
        try{
            if(jObject.has(mappedText) && !jObject.isNull(mappedText)){
                return jObject.getJSONObject(mappedText);
            }
        } catch (JSONException je){
            je.printStackTrace();
        }
        return null;
    }

	protected String getString(JSONObject jObject, String mappedText){
		try{
			if(jObject.has(mappedText) && !jObject.isNull(mappedText)){
				return jObject.getString(mappedText);
			}
		} catch (JSONException je){
			je.printStackTrace();
		}
		return "";
	}

    protected JSONArray getJsonArray(JSONObject jObject, String mappedText){
        try{
            if(jObject.has(mappedText) && !jObject.isNull(mappedText)){
                return jObject.getJSONArray(mappedText);
            }
        } catch (JSONException je){
            je.printStackTrace();
        }
        return null;
    }

    protected int getInt(JSONObject jObject, String mappedText){
        try{
            if(jObject.has(mappedText) && !jObject.isNull(mappedText)){
                return jObject.getInt(mappedText);
            }
        } catch (JSONException je){
            je.printStackTrace();
        }
        return -1;
    }

    protected long getLong(JSONObject jObject, String mappedText){
        try{
            if(jObject.has(mappedText) && !jObject.isNull(mappedText)){
                return jObject.getLong(mappedText);
            }
        } catch (JSONException je){
            je.printStackTrace();
        }
        return -1;
    }

    protected boolean getBoolean(JSONObject jObject, String mappedText){
        try{
            if(jObject.has(mappedText) && !jObject.isNull(mappedText)){
                return jObject.getBoolean(mappedText);
            }
        } catch (JSONException je){
            je.printStackTrace();
        }
        return false;
    }

	
}
