package com.codepath.apps.twitter.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.apps.twitter.R;
import com.codepath.apps.twitter.activities.ComposeActivity;
import com.codepath.apps.twitter.activities.TimelineActivity;
import com.codepath.apps.twitter.adapters.TweetsArrayAdapter;
import com.codepath.apps.twitter.models.Tweet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by madelynw on 6/28/16.
 */
public class TweetsListFragment extends Fragment {

    private TweetsArrayAdapter adapter;
    private ArrayList<Tweet> tweets;
    private ListView lvTweets;

    // inflation logic

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tweets_list, parent, false);
        // Find the list
        lvTweets = (ListView) v.findViewById(R.id.lvTweets);
        // Connect adapter to ListView
        lvTweets.setAdapter(adapter);
        return v;
    }


    // creation life cycle

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Create the arraylist (data source)
        tweets = new ArrayList<>();
        // Construct the adapter from data source
        adapter = new TweetsArrayAdapter(getActivity(), tweets);
    }

    public void addAll(List<Tweet> tweets) {
        adapter.addAll(tweets);
    }


    public void appendTweet(Tweet tweet) {
        tweets.add(0, tweet);
        adapter.notifyDataSetChanged();
        lvTweets.setSelection(0);
    }

}
