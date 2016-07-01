package com.codepath.apps.twitter.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.twitter.R;
import com.codepath.apps.twitter.TwitterApplication;
import com.codepath.apps.twitter.TwitterClient;
import com.codepath.apps.twitter.models.Tweet;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class ComposeActivity extends AppCompatActivity {
    private TwitterClient client;
    Tweet tweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient(); // singleton client
        setContentView(R.layout.activity_compose);
    }

    public void onTweet(View v) {
        EditText etTweet = (EditText) findViewById(R.id.etTweet);
        final TextView tvDisplay = (TextView) findViewById(R.id.tvDisplay);
        ImageView ivExit = (ImageView) findViewById(R.id.ivExit);
        ImageView ivProfile = (ImageView) findViewById(R.id.ivProfileImage);
        String profileUrl = getIntent().getStringExtra("profile_url");

        Picasso.with(getApplicationContext()).load(profileUrl)
                //.transform(new RoundedCornersTransformation(15, 15))
                .fit()//.centerCrop()
                .into(ivProfile);

        ivExit.setImageResource(R.drawable.ic_exit);

        /**
        etTweet.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Fires right as the text is being changed (even supplies the range of text)
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // Fires right before text is changing
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Fires right after the text has changed
                tvDisplay.setText(s.toString());
            }
        });
         */

        // Prepare data intent
        // Pass relevant data back as a result
        String status = etTweet.getText().toString();
        client.postTweet(status, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                tweet = Tweet.fromJSON(response);
                Intent data = new Intent();
                data.putExtra("tweet", tweet);
                // Activity finished ok, return the data
                setResult(RESULT_OK, data); // set result code and bundle data for response
                finish(); // closes the activity, pass data to parent
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
            }
        });
    }

    public void onClick(View view) {
        finish();
    }
}
