package com.codepath.apps.twitter.adapters;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.twitter.R;
import com.codepath.apps.twitter.models.Tweet;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by madelynw on 6/27/16.
 */
// Takes the tweet objects and turns them into views
public class TweetsArrayAdapter extends ArrayAdapter<Tweet> {

    public TweetsArrayAdapter(Context context, List<Tweet> tweets) {
        super(context, android.R.layout.simple_list_item_1, tweets);
    }

    // Later, we want to override simple_list_item_1 and setup custom template

    // Implement ViewHolder pattern later
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 1. Get the tweet
        Tweet tweet = getItem(position);
        // 2. Find and inflate the template
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
        }

        // 3. Find the subviews to fill with data in the template
        ImageView ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvBody = (TextView) convertView.findViewById(R.id.tvBody);
        TextView tvUsername = (TextView) convertView.findViewById(R.id.tvUsername);
        TextView tvLongAgo = (TextView) convertView.findViewById(R.id.tvLongAgo);

        // 4. Populate data into the subviews
        tvName.setText(tweet.getUser().getName());
        tvBody.setText(tweet.getBody());
        tvUsername.setText("@" + tweet.getUser().getScreenName());
        tvLongAgo.setText(tweet.getLongAgo());

        Typeface helvetica_light = Typeface.createFromAsset(getContext().getAssets(), "fonts/HelveticaLight.ttf");
        Typeface helvetica_bold = Typeface.createFromAsset(getContext().getAssets(), "fonts/HelveticaBold.otf");
        tvName.setTypeface(helvetica_bold);
        tvBody.setTypeface(helvetica_light);
        tvUsername.setTypeface(helvetica_light);
        tvLongAgo.setTypeface(helvetica_light);

        //ivProfileImage.setImageResource(0);
        ivProfileImage.setImageResource(android.R.color.transparent); // clear out the old image for a recycled view
        Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl())
                .transform(new RoundedCornersTransformation(25,25))
                .resize(150, 0)
                .into(ivProfileImage);

        // 5. Return the view to be inserted into the list
        return convertView;
    }
}
