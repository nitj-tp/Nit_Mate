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
public class GetAlImagesy {

    public static String[] imageURLs;
    public static Bitmap[] bitmaps;
    public static String[] keywords;
    public static String[] headings;
    public static String[] descriptions;

    public static final String JSON_ARRAY="result";
    public static final String IMAGE_URL = "url";
    public static final String IMAGE_KEYWORD = "keyword";
    public static final String IMAGE_HEADING="heading";
    public static final String IMAGE_DESCRIPTION="description";

    private String json;
    private JSONArray urls;

    public GetAlImagesy(String json){
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
            url = new URL(jo.getString(IMAGE_URL));
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
        keywords = new String[urls.length()];
        imageURLs = new String[urls.length()];
        headings = new String[urls.length()];
        descriptions = new String[urls.length()];

        for(int i=0;i<urls.length();i++){
            imageURLs[i] = urls.getJSONObject(i).getString(IMAGE_URL);
            keywords[i]=urls.getJSONObject(i).getString(IMAGE_KEYWORD);
            headings[i]=urls.getJSONObject(i).getString(IMAGE_HEADING);
            descriptions[i]=urls.getJSONObject(i).getString(IMAGE_DESCRIPTION);


            JSONObject jsonObject = urls.getJSONObject(i);
            bitmaps[i]=getImage(jsonObject);

        }
    }
}