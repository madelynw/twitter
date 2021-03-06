package com.codepath.apps.twitter.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.twitter.R;
import com.codepath.apps.twitter.TwitterClient;
import com.codepath.oauth.OAuthLoginActionBarActivity;

public class LoginActivity extends OAuthLoginActionBarActivity<TwitterClient> {

	// where the user will sign in

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

        TextView tvWelcome = (TextView) findViewById(R.id.tvWelcome);
        TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
        Button btnConnect = (Button) findViewById(R.id.btnConnect);
        ImageView ivBird = (ImageView) findViewById(R.id.ivBird);

        Typeface gotham_light = Typeface.createFromAsset(getAssets(), "fonts/GothamNarrow-Light.otf");
        Typeface gotham_bold = Typeface.createFromAsset(getAssets(), "fonts/GothamNarrow-Medium.otf");

        tvWelcome.setTypeface(gotham_light);
        tvTagline.setTypeface(gotham_light);
        btnConnect.setTypeface(gotham_bold);

        ivBird.setImageResource(R.drawable.ic_twitter_bird);
	}

	/**
	// Inflate the menu; this adds items to the action bar if it is present.
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
	*/

    // OAuth authenticated successfully, launch primary authenticated activity
	// i.e Display application "homepage"
	@Override
	public void onLoginSuccess() {
		Intent i = new Intent(this, TimelineActivity.class);
		startActivity(i);
    }

	// OAuth authentication flow failed, handle the error
	// i.e Display an error dialog or toast
	@Override
	public void onLoginFailure(Exception e) {
		e.printStackTrace();
	}

	// Click handler method for the button used to start OAuth flow
	// Uses the client to initiate OAuth authorization
	// This should be tied to a button used to login
	public void loginToRest(View view) {
		getClient().connect();
	}

}
