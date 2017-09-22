package com.example.guinness.facbookparsing.HELPER.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.guinness.facbookparsing.R;

import org.json.JSONArray;
import org.json.JSONObject;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder> {
    private JSONArray friends;

    public FriendsAdapter(JSONArray friends) {
        this.friends = friends;
        

    }


    /**
     * Created by guinness on 24/07/17.
     */

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageButton callButton;
        ImageButton voiceCallButton;
        View item;


        public ViewHolder(View itiemView) {
            super(itiemView);
            name = (TextView) itiemView.findViewById(R.id.name);
            callButton = (ImageButton) itiemView.findViewById(R.id.call);
            voiceCallButton = (ImageButton) itiemView.findViewById(R.id.voice_call);
            item = itiemView;

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.contact_row, parent, false);
        return new ViewHolder(v);


    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        JSONObject user = friends.optJSONObject(position);
        String userId = user.optString("id");
        String name = user.optString("name");
        holder.name.setText(name);

//        holder.voiceCallButton.setOnClickListener(new AdapterView.OnItemClickListener(userId, false));
//        holder.item.setOnClickListener(new AdapterView.OnItemClickListener(userId, true));

    }


    @Override
    public int getItemCount() {
        if (friends != null) {
            return friends.length();
        }
        return 0;
    }
    public void setData(JSONArray users) {
        this.friends = users;
    }

class OnItemClickListener implements View.OnClickListener
{
    private String userId;
    private boolean isVideo;

    public OnItemClickListener(String userId, boolean isVideo) {
        this.userId = userId;
        this.isVideo = isVideo;
    }
    @Override
    public void onClick(View view) {

    }
}

}
