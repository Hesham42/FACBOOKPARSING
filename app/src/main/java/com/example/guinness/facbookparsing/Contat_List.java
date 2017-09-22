package com.example.guinness.facbookparsing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.guinness.facbookparsing.HELPER.Adapter.FriendsAdapter;
import com.facebook.AccessToken;

import org.json.JSONArray;
import org.json.JSONException;

import static com.example.guinness.facbookparsing.MainActivity.friends;

public class Contat_List extends AppCompatActivity {
    private FriendsAdapter adapter;

    protected static JSONArray friends ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_contat__list);
        Bundle b = getIntent().getExtras();
        String Array=b.getString("Array");
        Log.i("jsFriend","contact "+Array.toString());

        try {
            friends=new JSONArray(Array);
            Log.i("jsFriend","contact "+friends.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new FriendsAdapter(friends);
        recyclerView.setAdapter(adapter);

    }
}
