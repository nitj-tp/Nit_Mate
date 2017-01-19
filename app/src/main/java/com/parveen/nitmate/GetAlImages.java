package com.parveen.nitmate;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Belal on 9/19/2015.
 */
public class GetAlImages {

    public static String[] imageURLs;
    public static Bitmap[] bitmaps;
    public static String[] names;
    public static String[] jobs;
    public static String[] contacts;

    public static String[] emails;
    public static String[] descriptions;

    public static final String JSON_ARRAY="result";
    public static final String IMAGE_PATH = "path";
    public static final String IMAGE_NAME = "name";
    public static final String IMAGE_CONTACT="contact";
    public static final String IMAGE_EMAIL="email";
    public static final String IMAGE_JOB="job";

    public static final String IMAGE_DESCRIPTION="description";

    private String json;
    private JSONArray urls;

    public GetAlImages(String json){
        this.json = json;
        try {
            JSONObject jsonObject = new JSONObject(json);
            urls = jsonObject.getJSONArray(JSON_ARRAY);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private Bitmap getImage(JSONObject jo){
        URL url = null;
        Bitmap image = null;
        try {
            url = new URL(jo.getString(IMAGE_PATH));
            image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return image;
    }

    public void getAllImages() throws JSONException {
        bitmaps = new Bitmap[urls.length()];
        names = new String[urls.length()];
        imageURLs = new String[urls.length()];
        contacts= new String[urls.length()];
        jobs = new String[urls.length()];
        emails = new String[urls.length()];
        descriptions = new String[urls.length()];

        for(int i=0;i<urls.length();i++){
            imageURLs[i] = urls.getJSONObject(i).getString(IMAGE_PATH);
            names[i]=urls.getJSONObject(i).getString(IMAGE_NAME);
            jobs[i] = urls.getJSONObject(i).getString(IMAGE_JOB);
            emails[i]=urls.getJSONObject(i).getString(IMAGE_EMAIL);
            contacts[i]=urls.getJSONObject(i).getString(IMAGE_CONTACT);
            descriptions[i]=urls.getJSONObject(i).getString(IMAGE_DESCRIPTION);

            JSONObject jsonObject = urls.getJSONObject(i);
            bitmaps[i]=getImage(jsonObject);

        }
    }
}