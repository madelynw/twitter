package com.codepath.apps.twitter.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.codepath.apps.twitter.TwitterApplication;
import com.codepath.apps.twitter.TwitterClient;
import com.codepath.apps.twitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by madelynw on 6/28/16.
 */
public class FavoriteTweetFragment extends TweetsListFragment {

    private TwitterClient client;

    public static FavoriteTweetFragment newInstance(Long id) {
        FavoriteTweetFragment searchTweetsFragment = new FavoriteTweetFragment();
        Bundle args = new Bundle();
        args.putLong("id", id);
        searchTweetsFragment.setArguments(args);
        return searchTweetsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the client
        client = TwitterApplication.getRestClient(); // singleton client
        //favorite();
    }

    /**
    public void favorite() {
        Long id = getArguments().getLong("id");
        client.createFavorite(id, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                try {

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
            }
        });
    };
     */
}
