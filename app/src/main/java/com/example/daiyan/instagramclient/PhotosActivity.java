package com.example.daiyan.instagramclient;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class PhotosActivity extends ActionBarActivity {

    public static final String CLIENT_ID = "6a442dfcc68040d5a7d8bae1d3f164a5";
    private ArrayList<InstagramPhoto> photos;
    private InstagramPhotosAdapter aPhotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        // Send out api requests to popular photos
        photos = new ArrayList<>();
        // 1. Create the adapter linking it to the source
        aPhotos = new InstagramPhotosAdapter(this, photos);
        // 2. Find the listview from the layout
        ListView lvPhotos = (ListView) findViewById(R.id.lvPhotos);
        // 3. Set the adapter binding it to the ListView
        lvPhotos.setAdapter(aPhotos);
        // Fetch the popular photos
        fechPopularPhotos();


    }

    // Trigger api request
    private void fechPopularPhotos() {
        /*
        - popular: https://api.instagram.com/v1/media/popular?access_token=ACCESS-TOKEN
        - Response
            - Type: { “data” => [x] => “type” } (“image” OR “video")
            - URL: { “data” => [x] => “images” => “standard_resolution” => “url" }
            - Caption: { “data” => [x] => “caption” => “text" }
            - Author Name: { “data” => [x] => “user” => “username" }
         */

        String url = "https://api.instagram.com/v1/media/popular?client_id=" + CLIENT_ID;

        // create network client
        AsyncHttpClient client = new AsyncHttpClient();
        // Trigger the GET request
        client.get(url,null, new JsonHttpResponseHandler(){
           // on success

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray photosJSON = null;
                try {
                    photosJSON = response.getJSONArray("data");

                    for (int i=0; i< photosJSON.length(); i++){
                        JSONObject photoJSON = photosJSON.getJSONObject(i);
                        InstagramPhoto photo = new InstagramPhoto();
                        photo.username = photoJSON.getJSONObject("user").getString("username");
                        photo.caption = photoJSON.getJSONObject("caption").getString("text");
                        photo.imageURL = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
                        photo.imageHight = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
                        photo.likesCount = photoJSON.getJSONObject("likes").getInt("count");
                        photos.add(photo);
                    }

                } catch (JSONException e){
                    e.printStackTrace();
                }
                // callback
                aPhotos.notifyDataSetChanged();
            }


            // on failure

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_photos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
