package com.codepath.apps.twitter.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.DateUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by madelynw on 6/27/16.
 */
public class User implements Parcelable {
    // list attributes
    private String name;
    private String uid;
    private String screenName;
    private String profileImageUrl;
    private int friendsCount;
    private int followersCount;
    private String tagline;
    private String backgroundImageUrl;

    public String getName() {
        return name;
    }

    public String getUid() {
        return uid;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public int getFriendsCount() {
        return friendsCount;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public String getTagline() {
        return tagline;
    }

    public String getBackgroundImageUrl() { return backgroundImageUrl; }

    // deserialize the user json -> User
    public static User fromJSON(JSONObject json) {
        User u = new User();
        // Extract and fill the values
        try {
            u.name = json.getString("name");
            u.uid= json.getString("id");
            u.screenName = json.getString("screen_name");
            u.profileImageUrl = json.getString("profile_image_url");
            u.followersCount = json.getInt("followers_count");
            u.friendsCount = json.getInt("friends_count");
            u.tagline = json.getString("description");
            u.backgroundImageUrl = json.getString("profile_background_image_url");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Return a user
        return u;
    }

    public User() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.uid);
        dest.writeString(this.screenName);
        dest.writeString(this.profileImageUrl);
        dest.writeInt(this.friendsCount);
        dest.writeInt(this.followersCount);
        dest.writeString(this.tagline);
        dest.writeString(this.backgroundImageUrl);
    }

    protected User(Parcel in) {
        this.name = in.readString();
        this.uid = in.readString();
        this.screenName = in.readString();
        this.profileImageUrl = in.readString();
        this.friendsCount = in.readInt();
        this.followersCount = in.readInt();
        this.tagline = in.readString();
        this.backgroundImageUrl = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
