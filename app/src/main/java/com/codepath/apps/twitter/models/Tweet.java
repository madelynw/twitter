package com.codepath.apps.twitter.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.Toast;

import com.codepath.apps.twitter.TimeFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by madelynw on 6/27/16.
 */

// Parse the json and store the data, encapsulate state logic or display logic
public class Tweet implements Parcelable {
    private String body;
    private long uid; // unique id for the tweet
    private User user; // store embedded user object
    private String createdAt;
    private String longAgo;
    private String mediaUrl;
    private String favoritesCount;
    private String retweetsCount;

    public String getFavoritesCount() {
        return favoritesCount;
    }

    public String getRetweetsCount() {
        return retweetsCount;
    }

    public User getUser() {
        return user;
    }

    public String getBody() {
        return body;
    }

    public long getUid() {
        return uid;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getLongAgo() {
        String formattedTime = TimeFormatter.getTimeDifference(getCreatedAt());
        return formattedTime;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    // Deserialize the json and build tweet objects
    // Tweet.fromJSON("{...}") -> <Tweet>
    public static Tweet fromJSON(JSONObject jsonObject) {
        // Extract values from the json, store them, and return a tweet object
        Tweet tweet = new Tweet();

        try {
            tweet.body = jsonObject.getString("text");
            tweet.uid = jsonObject.getLong("id");
            tweet.createdAt = jsonObject.getString("created_at");
            tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
            tweet.favoritesCount = jsonObject.getString("favorite_count");
            tweet.retweetsCount = jsonObject.getString("retweet_count");

            JSONObject entities = jsonObject.getJSONObject("entities");
            if (entities.length() > 0) {
                tweet.mediaUrl = entities.getJSONArray("media").getJSONObject(0).getString("media_url");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tweet;
    }

    // Tweet.fromJsonArray( [{...}, {...}] ) -> List<Tweet>
    public static ArrayList<Tweet> fromJsonArray(JSONArray jsonArray) {
        ArrayList<Tweet> tweets = new ArrayList<>();
        // Iterate the json array and create tweets
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject tweetJson = jsonArray.getJSONObject(i);
                Tweet tweet = Tweet.fromJSON(tweetJson);
                if (tweet != null) {
                    tweets.add(tweet);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
        }

        // Return the finished list
        return tweets;
    }

    // getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }

    public Tweet() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.body);
        dest.writeLong(this.uid);
        dest.writeParcelable(this.user, flags);
        dest.writeString(this.createdAt);
        dest.writeString(this.longAgo);
        dest.writeString(this.mediaUrl);
        dest.writeString(this.favoritesCount);
        dest.writeString(this.retweetsCount);
    }

    protected Tweet(Parcel in) {
        this.body = in.readString();
        this.uid = in.readLong();
        this.user = in.readParcelable(User.class.getClassLoader());
        this.createdAt = in.readString();
        this.longAgo = in.readString();
        this.mediaUrl = in.readString();
        this.favoritesCount = in.readString();
        this.retweetsCount = in.readString();
    }

    public static final Creator<Tweet> CREATOR = new Creator<Tweet>() {
        @Override
        public Tweet createFromParcel(Parcel source) {
            return new Tweet(source);
        }

        @Override
        public Tweet[] newArray(int size) {
            return new Tweet[size];
        }
    };
}
