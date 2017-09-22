package com.example.guinness.facbookparsing.HELPER.Adapter;

import android.util.Log;

import com.example.guinness.facbookparsing.Storage.FacebookFriendsData;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by guinness on 24/07/17.
 */

public class FacebookFriendsJSONUtils {

    public static ArrayList<FacebookFriendsData> formatFriendsData(String sb){

        ArrayList<FacebookFriendsData> data = null;
        try{
            JSONArray root = new JSONArray(sb);
            data = new ArrayList<>();
            FacebookFriendsData temp;
            for(int i = 0; i<root.length(); ++i){
                Log.d("jsonUtils", root.toString());
                temp = new FacebookFriendsData();
                temp.setId(Long.parseLong(root.getJSONObject(i).getString("id")));
                temp.setName(root.getJSONObject(i).getString("name"));
                data.add(temp);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

}