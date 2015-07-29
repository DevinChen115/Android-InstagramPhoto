package com.example.daiyan.instagramclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by daiyan on 7/25/15.
 */
public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {
    // what data do we need from the activity
    // context, Data srouce
    public InstagramPhotosAdapter(Context context, List<InstagramPhoto> objects) {
        super(context, android.R.layout.simple_expandable_list_item_1, objects);
    }

    // what our item looks like
    // Use the template to display each photo


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        InstagramPhoto photo = getItem(position);
        // Check if we are using a recycled view, if not we need to inflate
        if (convertView == null) {
            // create a new view from template
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
        }
        // Lookup the views for populating the data (image, caption)
        TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
        TextView tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
        TextView tvLike = (TextView) convertView.findViewById(R.id.tvLike);
        ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);
        // Insert the model data into each of the view items
        tvCaption.setText(photo.caption);
        tvUserName.setText("@"+photo.username);
        tvLike.setText("Likes:" + Integer.toString(photo.likesCount));
        // clear out the imageview
        ivPhoto.setImageResource(0);
        // Insert the image using picasso
        Picasso.with(getContext()).load(photo.imageURL).into(ivPhoto);
        // Return the created item as a view
        return convertView;
    }
}
