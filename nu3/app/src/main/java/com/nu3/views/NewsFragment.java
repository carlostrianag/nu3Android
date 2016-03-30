package com.nu3.views;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nu3.R;
import com.nu3.views.adapters.NewsAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import models.News;
import utils.RestClient;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public NewsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_news, container, false);

        this.mRecyclerView = (RecyclerView) view.findViewById(R.id.newsRecyclerView);

        this.mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        this.mRecyclerView.setLayoutManager(mLayoutManager);

        RestClient.get("/Z678310f?category=51", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                ArrayList<News> newsList = new ArrayList<News>();

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        News news = new News();
                        news.setId(object.getInt("id"));
                        news.setName(object.getString("name").trim());
                        news.setDescriptionSpanish(object.getString("desc_es").trim());
                        newsList.add(news);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                mAdapter = new NewsAdapter(newsList);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(statusCode, headers, responseString);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
        return view;
    }

}
