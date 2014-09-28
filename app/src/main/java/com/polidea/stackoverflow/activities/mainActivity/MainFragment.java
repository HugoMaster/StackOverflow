package com.polidea.stackoverflow.activities.mainActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.polidea.stackoverflow.R;
import com.polidea.stackoverflow.activities.detailsActivity.DetailsActivity;
import com.polidea.stackoverflow.api.stackoverflow.SearchApi;
import com.polidea.stackoverflow.api.stackoverflow.models.SearchResultModel;
import com.polidea.stackoverflow.listeners.SearchListener;

import java.util.ArrayList;


public class MainFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener, SearchListener, SwipeRefreshLayout.OnRefreshListener {

    private EditText searchEditText;
    private Button searchButton;
    private ListView searchListView;
    private SwipeRefreshLayout listRefresh;

    private ArrayList<SearchResultModel> searchResultModels;
    private SearchListAdapter searchListAdapter;
    private String lastSearchText;

    private DisplayImageOptions options;
    private SearchApi searchApi;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        initVariables();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        initInterface(view);
        initListeners();

        return view;
    }

    private void initInterface(View view) {
        searchEditText = (EditText) view.findViewById(R.id.searchEditText);
        searchButton = (Button) view.findViewById(R.id.searchButton);
        searchListView = (ListView) view.findViewById(R.id.searchListView);
        listRefresh = (SwipeRefreshLayout) view.findViewById(R.id.searchRefresh);

        searchListView.setAdapter(searchListAdapter);
        listRefresh.setColorSchemeColors(
                getResources().getColor(R.color.refresh_1),
                getResources().getColor(R.color.refresh_2),
                getResources().getColor(R.color.refresh_3),
                getResources().getColor(R.color.refresh_4));
    }

    private void initListeners() {
        searchButton.setOnClickListener(this);
        listRefresh.setOnRefreshListener(this);
        searchListView.setOnItemClickListener(this);
    }

    private void initVariables() {
        searchApi = new SearchApi(getActivity(), this);

        options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_launcher)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();

        searchResultModels = new ArrayList<SearchResultModel>();
        searchListAdapter = new SearchListAdapter(getActivity(), searchResultModels,options);
    }

    //=============================================================================
    // Interface Listeners
    //=============================================================================

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.searchButton:
                if(searchEditText.getText().toString().length()>0) {
                    lastSearchText = searchEditText.getText().toString();
                    searchApi.getSearch(lastSearchText);
                } else {
                    lastSearchText = "";
                    Toast.makeText(getActivity(), "You need to type smth in search box!", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        SearchResultModel searchResultModel = searchResultModels.get(i);
        Intent detailsIntent = new Intent(getActivity(), DetailsActivity.class);
        detailsIntent.putExtra("url", searchResultModel.getLink());
        startActivity(detailsIntent);
    }

    @Override
    public void onRefresh() {
        if(lastSearchText != null && lastSearchText.length()>0) {
            searchApi.getSearch(lastSearchText);
        } else {
            listRefresh.setRefreshing(false);
        }
    }

    //=============================================================================
    // Search Api Listeners
    //=============================================================================
    @Override
    public void onSuccess(ArrayList<SearchResultModel> searchResultModels) {
        this.searchResultModels.clear();
        this.searchResultModels.addAll(searchResultModels);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                searchListAdapter.notifyDataSetChanged();
                searchListView.smoothScrollToPosition(0);
                listRefresh.setRefreshing(false);
            }
        });
    }

    @Override
    public void onError(String errorCode, String errorMsg) {

    }

    @Override
    public void onErrorConnection(String msg) {

    }

}
