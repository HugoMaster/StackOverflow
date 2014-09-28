package com.polidea.stackoverflow.api.stackoverflow;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;

import com.polidea.stackoverflow.api.stackoverflow.helpers.Api;
import com.polidea.stackoverflow.api.stackoverflow.helpers.ApiAsyncTask;
import com.polidea.stackoverflow.api.stackoverflow.helpers.ApiParams;
import com.polidea.stackoverflow.api.stackoverflow.helpers.Parser;
import com.polidea.stackoverflow.api.stackoverflow.models.OwnerModel;
import com.polidea.stackoverflow.api.stackoverflow.models.SearchResultModel;
import com.polidea.stackoverflow.listeners.SearchListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchApi extends Api {

    private static final String TAG = "StackOverflow";

	private Context context;
	private SearchListener searchListener;

	public SearchApi(Context context, SearchListener searchListener){
		this.searchListener = searchListener;
		this.context = context;
	}

	public void getSearch(String intitle) {
        ApiParams apiParams = new ApiParams();
		apiParams.setParser(parser);

        String url = ApiParams.SERVER + "search?";
        url+="order=desc&";
        url+="sort=activity&";
        url+="intitle="+intitle+"&";
        url+="site=stackoverflow";
        apiParams.setUrl(url);

		new ApiAsyncTask(searchListener).execute(apiParams);
	}


	private Parser parser = new Parser() {

		@Override
		public void parse(String string) {
			try {
                Log.d(TAG,"response :"+string);

                JSONObject objectJson = new JSONObject(string);
                JSONArray itemsJson = getJsonArray(objectJson, "items");

                ArrayList<SearchResultModel> searchResultModels = new ArrayList<SearchResultModel>();

                for(int i = 0; i<itemsJson.length(); i++){
                    JSONObject itemJson = itemsJson.getJSONObject(i);
                    JSONObject ownerJson = itemJson.getJSONObject("owner");

                    OwnerModel ownerModel = new OwnerModel();
                    ownerModel.setUserID(getString(ownerJson, "user_id"));
                    ownerModel.setUserName(getString(ownerJson, "display_name"));
                    ownerModel.setImageURL(getString(ownerJson, "profile_image"));

                    SearchResultModel searchResultModel = new SearchResultModel();
                    searchResultModel.setQuestionID(getString(itemJson, "question_id"));
                    searchResultModel.setLink(getString(itemJson, "link"));
                    searchResultModel.setTitle(getString(itemJson, "title"));
                    searchResultModel.setIsAnswered(getBoolean(itemJson, "is_answered"));
                    searchResultModel.setAnswerCount(getString(itemJson, "answer_count"));
                    searchResultModel.setOwnerModel(ownerModel);

                    searchResultModels.add(searchResultModel);
                }

                searchListener.onSuccess(searchResultModels);

			} catch (JSONException e) {
                if(searchListener!=null)
                    searchListener.onError("404", "Server wrong answer");
				e.printStackTrace();
			}
		}
	};
}
