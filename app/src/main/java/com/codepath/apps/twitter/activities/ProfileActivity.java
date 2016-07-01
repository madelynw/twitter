package com.codepath.apps.twitter.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.twitter.R;
import com.codepath.apps.twitter.TwitterApplication;
import com.codepath.apps.twitter.TwitterClient;
import com.codepath.apps.twitter.fragments.UserTimelineFragment;
import com.codepath.apps.twitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class ProfileActivity extends AppCompatActivity {

    TwitterClient client;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        client = TwitterApplication.getRestClient();

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        // Get the screen name from the activity
        String screenName = getIntent().getStringExtra("screen_name");

        //Toast.makeText(this, "1", Toast.LENGTH_SHORT).show();
        if (savedInstanceState == null) {
            loadUserInfo(screenName);

            UserTimelineFragment fragmentUserTimeline = UserTimelineFragment.newInstance(screenName);
            // Display the user fragment within the activity dynamically
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flContainer, fragmentUserTimeline);
            ft.commit();
        }
    }

    public void loadUserInfo(String screenName) {
        if (screenName != null && !screenName.isEmpty()) {
            // Trigger call to "users/show" endpoint
            // to load user profile data
            // populate the top of the profile view
            // Create the user timeline fragment

            client.getUserInfo(screenName, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    user = User.fromJSON(response);
                    populateProfileHeader(user);
                    //getSupportActionBar().setTitle("@" + user.getScreenName());
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.d("DEBUG", errorResponse.toString());
                }
            });

        } else {
            // no screenName was passed
            // Trigger call to "account/verifyCredentials" endpoint
            // to load current user profile data
            // populate the top of the profile view
            client.getMyInfo(new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    user = User.fromJSON(response);
                    populateProfileHeader(user);
                    //getSupportActionBar().setTitle("@" + user.getScreenName());
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.d("DEBUG", errorResponse.toString());
                }
            });
        }
    }

    private void populateProfileHeader(User user) {
        TextView tvName = (TextView) findViewById(R.id.tvName);
        TextView tvBody = (TextView) findViewById(R.id.tvBody);
        TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
        TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
        TextView tvNumberFollowers = (TextView) findViewById(R.id.tvNumberFollowers);
        TextView tvNumberFollowing = (TextView) findViewById(R.id.tvNumberFollowing);
        TextView tvUsername = (TextView) findViewById(R.id.tvUsername);
        ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        ImageView ivBackgroundImage = (ImageView) findViewById(R.id.ivBackgroundImage);

        tvName.setText(user.getName());
        tvBody.setText(user.getTagline());
        tvFollowers.setText(R.string.followers);
        tvFollowing.setText(R.string.following);
        tvUsername.setText("@" + user.getScreenName());
        tvNumberFollowers.setText(String.valueOf(user.getFollowersCount()));
        tvNumberFollowing.setText(String.valueOf(user.getFriendsCount()));

        Typeface gotham_light = Typeface.createFromAsset(getAssets(), "fonts/GothamNarrow-Light.otf");
        Typeface gotham_bold = Typeface.createFromAsset(getAssets(), "fonts/GothamNarrow-Medium.otf");

        tvName.setTypeface(gotham_bold);
        tvBody.setTypeface(gotham_light);
        tvUsername.setTypeface(gotham_light);
        tvFollowers.setTypeface(gotham_light);
        tvFollowing.setTypeface(gotham_light);
        tvNumberFollowers.setTypeface(gotham_bold);
        tvNumberFollowing.setTypeface(gotham_bold);

        ivProfileImage.setImageResource(android.R.color.transparent); // clear out the old image for a recycled view
        Picasso.with(this).load(user.getProfileImageUrl())
                //.resize(100, 0)
                .fit().centerCrop()
                .transform(new RoundedCornersTransformation(25, 25))
                .into(ivProfileImage);

        ivBackgroundImage.setImageResource(android.R.color.transparent);
        Picasso.with(this).load(user.getBackgroundImageUrl())
                .fit().centerCrop()
                .into(ivBackgroundImage);
    }

    /**
    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
    */
}
