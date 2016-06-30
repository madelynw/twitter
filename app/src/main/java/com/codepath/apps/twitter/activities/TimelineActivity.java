package com.codepath.apps.twitter.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.twitter.R;
import com.codepath.apps.twitter.TwitterApplication;
import com.codepath.apps.twitter.TwitterClient;
import com.codepath.apps.twitter.adapters.SmartFragmentStatePagerAdapter;
import com.codepath.apps.twitter.adapters.TweetsArrayAdapter;
import com.codepath.apps.twitter.fragments.HomeTimelineFragment;
import com.codepath.apps.twitter.fragments.MentionsTimelineFragment;
import com.codepath.apps.twitter.fragments.TweetsListFragment;
import com.codepath.apps.twitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity {

    private final int REQUEST_CODE = 20;

    ViewPager vpPager;

    private SmartFragmentStatePagerAdapter adapterViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        // Get the viewpager
        vpPager = (ViewPager) findViewById(R.id.viewpager);
        // Set the viewpager adapter for the pager
        adapterViewPager = new TweetsPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
        // Find the sliding tabstrip
        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        // Attach the tabstrip to the viewpager
        tabStrip.setViewPager(vpPager);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ImageButton btnProfile = (ImageButton) findViewById(R.id.btnProfile);

        /**
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TimelineActivity.this, ProfileActivity.class);
                startActivity(i);
            }
        });

         */
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        View mToolbar = getLayoutInflater().inflate(R.layout.custom_toolbar, null);
        getSupportActionBar().setCustomView(mToolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        TextView tvToolbarTitle = (TextView) findViewById(R.id.tvToolbarTitle);
        Typeface gotham_bold = Typeface.createFromAsset(getAssets(), "fonts/GothamNarrow-Medium.otf");
        tvToolbarTitle.setTypeface(gotham_bold);
        tvToolbarTitle.setText(R.string.title_activity_timeline);
    }

    public void composeMessage(View view) {
        Intent i = new Intent(this, ComposeActivity.class);
        startActivityForResult(i, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            Tweet tweet = data.getParcelableExtra("tweet");

            HomeTimelineFragment fragmentHomeTweets =
                    (HomeTimelineFragment) adapterViewPager.getRegisteredFragment(0);
            fragmentHomeTweets.appendTweet(tweet);
            vpPager.setCurrentItem(0);
        }
    }


    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getLayoutInflater().inflate(R.layout.custom_toolbar, null);
        //getMenuInflater().inflate(R.menu.menu_timeline, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void onProfileView2(MenuItem item) {
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);

    }

    public void onProfileView(ImageButton btnProfile) {
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);
    }


    // Return the order of the fragments in the view pager
    public class TweetsPagerAdapter extends SmartFragmentStatePagerAdapter implements PagerSlidingTabStrip.IconTabProvider {
        private String tabTitles[] = { "Home", "Mentions", "Notifications", "Messages" };
        private int tabIcons[] = {R.drawable.ic_home, R.drawable.ic_lightning, R.drawable.ic_bell, R.drawable.ic_message};

        // Adapter gets the manager to insert or remove fragment from activity
        public TweetsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        // The order and creation of fragments within the pager
        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new HomeTimelineFragment();
            }
            else if (position == 1) {
                return new MentionsTimelineFragment();
            }
            else if (position == 2) {
                return new HomeTimelineFragment();
            }
            else if (position == 3) {
                return new MentionsTimelineFragment();
            }
            else {
                return null;
            }
        }

        // Return the tab title
        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

        // How many fragments there are to swipe between
        @Override
        public int getCount() {
            return tabTitles.length;
        }

        @Override
        public int getPageIconResId(int position) {
            return tabIcons[position];
        }
    }
}
