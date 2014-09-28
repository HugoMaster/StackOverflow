package com.polidea.stackoverflow.listeners;

import com.polidea.stackoverflow.api.stackoverflow.models.SearchResultModel;

import java.util.ArrayList;

/**
 * Created by Hubert on 27.09.2014.
 */
public interface SearchListener extends ApiListener {
    public void onSuccess(ArrayList<SearchResultModel> searchResultModels);
}
