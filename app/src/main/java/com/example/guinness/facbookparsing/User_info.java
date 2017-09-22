package com.example.guinness.facbookparsing;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.HashMap;

public class User_info extends AppCompatActivity {
    private String userId;

    private LinearLayout llUserName, llFirstName, llLastName, llBirthDate,
            llEmailId;
    private TextView tvUserId, tvUserName, tvFirstNAme, tvLastName, tvBirthday,
            tvEmail;
    private ImageView ivProfileImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        Intent intent = getIntent();
        Userdata userdata = (Userdata) getIntent().getSerializableExtra("obj");
        Log.i("Guinness", userdata.getId());
        getReferences();

        displayProfileImage(userdata);
        displayUserInfo(userdata);
    }

    private void displayProfileImage(Userdata userdata) {

        if (userdata.getUrl()!=null)
        {   String url=userdata.getUrl().toString();
           Log.i("Guinness",url);
            Picasso.with(this).load(url).into(ivProfileImage);

        }
    }

    private void displayUserInfo(Userdata userdata) {
        if (userdata != null) {
            if (userdata.getId() != null) {
                tvUserId.setText(userdata.getId());

                if (userdata.getName() != null) {
                    tvUserName.setText(userdata.getName());
                } else {
                    tvUserName.setVisibility(View.GONE);

                }
                if (userdata.getFirst_name() != null) {
                    tvFirstNAme.setText(userdata.getFirst_name());

                } else {
                    tvFirstNAme.setVisibility(View.GONE);

                }


                if (userdata.getLast_name() != null) {
                    tvLastName.setText(userdata.getLast_name());
                } else {
                    tvLastName.setVisibility(View.GONE);

                }

                if (userdata.getBirthday() != null) {
                    tvBirthday.setText(userdata.getBirthday());
                } else {
                    tvBirthday.setVisibility(View.GONE);
                }


                if (userdata.getEmail() != null) {
                    tvEmail.setText(userdata.getEmail());
                } else {
                    tvEmail.setVisibility(View.GONE);
                }

            } else {
                tvUserId.setVisibility(View.GONE);
                tvUserName.setVisibility(View.GONE);
                tvFirstNAme.setVisibility(View.GONE);
                tvLastName.setVisibility(View.GONE);
                tvBirthday.setVisibility(View.GONE);
                tvEmail.setVisibility(View.GONE);
            }
        }

    }


    private void getReferences() {
        llUserName = (LinearLayout) findViewById(R.id.llUserName);
        llFirstName = (LinearLayout) findViewById(R.id.llFirstName);
        llLastName = (LinearLayout) findViewById(R.id.llLastName);
        llBirthDate = (LinearLayout) findViewById(R.id.llBirthDate);
        llEmailId = (LinearLayout) findViewById(R.id.llEmailId);

        tvUserId = (TextView) findViewById(R.id.tvUserId);
        tvUserName = (TextView) findViewById(R.id.tvUserName);
        tvFirstNAme = (TextView) findViewById(R.id.tvFirstNAme);
        tvLastName = (TextView) findViewById(R.id.tvLastName);
        tvBirthday = (TextView) findViewById(R.id.tvBirthday);
        tvEmail = (TextView) findViewById(R.id.tvEmail);
        ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);

     }


}
