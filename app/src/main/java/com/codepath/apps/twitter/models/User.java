package com.codepath.apps.twitter.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by madelynw on 6/27/16.
 */
public class User {
    // list attributes
    private String name;
    private String uid;
    private String screenName;
    private String profileImageUrl;
    private int friendsCount;
    private int followersCount;
    private String tagline;

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
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Return a user
        return u;
    }

}
