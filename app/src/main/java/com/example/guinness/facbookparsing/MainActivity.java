package com.example.guinness.facbookparsing;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.guinness.facbookparsing.HELPER.Adapter.FriendsAdapter;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.R.attr.data;
import static android.R.attr.id;
import static android.R.attr.name;

public class MainActivity extends AppCompatActivity {
    private CallbackManager callbackManager;
    private TextView info;
    private LoginButton loginButton;
    //    -------------------------------------
    public HashMap<String, String> userHashmap;
    String id;
    Userdata userdata;
    private FriendsAdapter adapter;

    protected static JSONArray friends = new JSONArray();
    private static final List<String> PERMISSIONS = new ArrayList<String>() {

        private static final long serialVersionUID = 1L;

        {
            add("user_friends");
            add("public_profile");
            add("email");
        }
    };
//    ------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_main);
        getKeyHash();
        singinWithFacbook();
        sharApp();
        userdata = new Userdata();


    }


    private void singinWithFacbook() {
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email", "public_profile","user_friends");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                System.out.println("onSuccess");

                String accessToken = loginResult.getAccessToken()
                        .getToken();
                Log.i("accessToken", accessToken);

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object,
                                                    GraphResponse response) {
                                Log.i("LoginActivity", response.toString());
                                Log.i("LoginActivity", object.toString());
                                try {
                                    if (object.isNull("id")) {

                                    }
                                    {
                                        id = object.getString("id");
                                        Log.i("LoginActivity", id.toString());
//                                    userHashmap.put("id",id);
                                        userdata.setId(id);
                                        try {
                                            URL profile_pic = new URL(
                                                    "http://graph.facebook.com/" + id + "/picture?type=large");
                                            Log.i("profile_pic",
                                                    profile_pic + "");
                                            Log.i("LoginActivity", profile_pic.toString());
//                                        userHashmap.put("profile_pic",profile_pic.toString());
                                            userdata.setUrl(profile_pic);

                                        } catch (MalformedURLException e) {
                                            e.printStackTrace();
                                        }

                                        if (object.isNull("name")) {
                                            Log.i("LoginActivity", "name is null ");

                                        } else {
                                            String name = object.getString("name");
                                            Log.i("LoginActivity", name.toString());
//                                        userHashmap.put("name",name);
                                            userdata.setName(name);
                                        }

                                        if (object.isNull("first_name")) {
                                            Log.i("LoginActivity", "first name is null ");

                                        } else {

                                            String first_name = object.getString("first_name");
                                            Log.i("LoginActivity", first_name.toString());
//                                        userHashmap.put("first_name",first_name);
                                            userdata.setFirst_name(first_name);
                                        }
                                        if (object.isNull("last_name")) {
                                            Log.i("LoginActivity", "last name is null");

                                        } else {

                                            String last_name = object.getString("last_name");
                                            Log.i("LoginActivity", last_name.toString());
//                                            userHashmap.put("last_name",last_name);
                                            userdata.setLast_name(last_name);
                                        }
                                        if (object.isNull("email")) {
                                            Log.i("LoginActivity", "email null ");


                                        } else {
                                            String email = object.getString("email");
                                            Log.i("LoginActivity", email.toString());
//                                            userHashmap.put("email",email);
                                            userdata.setEmail(email);
                                        }
                                        if (object.isNull("gender")) {
                                            Log.i("LoginActivity", "gender null");

                                        } else {
                                            String gender = object.getString("gender");
                                            Log.i("LoginActivity", gender.toString());
//                                        userHashmap.put("gender",gender);
                                            userdata.setGender(gender);
                                        }
                                        if (object.isNull("birthday")) {
                                            Log.i("LoginActivity", "null birthday");

                                        } else {
                                            String birthday = object.getString("birthday");
                                            Log.i("LoginActivity", birthday.toString());

                                            userdata.setBirthday(birthday);
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }







                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields",
                        "id,name,first_name,last_name,birthday,gender,email");
                request.setParameters(parameters);
                request.executeAsync();
            }


//                ------------------------------------------------------

            @Override
            public void onCancel() {
                info.setText("Login attempt cancelled.");
            }

            @Override
            public void onError(FacebookException e) {
                info.setText("Login attempt failed.");
            }
        });

    }




    protected void requestFacebookFriends1()
    {
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/"+id+"/friends",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
            /* handle the result */

                    Log.i("Guinness","  friends  "+response.toString());
                    }
                }
        ).executeAsync();

    }

//    --------------------------------------
protected void requestFacebookFriends(AccessToken token) {
    Bundle params = new Bundle();
    params.putString("fields", "id, name");

    GraphRequest req = GraphRequest.newMyFriendsRequest(
            token,
            new GraphRequest.GraphJSONArrayCallback() {
                @Override
                public void onCompleted(
                        JSONArray jsonArray,
                        GraphResponse response) {
                    friends = jsonArray;
                    Log.i("jsFriend",jsonArray.toString());
                    Intent obj_intent = new Intent(MainActivity.this, Contat_List.class);
                    Bundle b = new Bundle();
                    b.putString("Array",jsonArray.toString());
                    obj_intent.putExtras(b);
                    startActivity(obj_intent);

//                    onFriendsListReceived();

                }
            });

    req.setParameters(params);
    req.executeAsync();
}

//    protected void onFriendsListReceived() {
//        if (friends == null) {
//            return;
//        }
//
//        if (adapter != null) {
//
//            adapter.setData(friends);
//            adapter.notifyDataSetChanged();
//        }
//    }
public void GEtFacbookFriends(View view) {

    requestFacebookFriends(AccessToken.getCurrentAccessToken());
//    requestFacebookFriends1();

//    new GraphRequest(
//            AccessToken.getCurrentAccessToken(),
//            "/{friend-list-id}",
//            null,
//            HttpMethod.GET,
//            new GraphRequest.Callback() {
//                public void onCompleted(GraphResponse response) {
//            /* handle the result */
//                }
//            }
//    ).executeAsync();
}















//    -------------------------------------

    public void GETUSERINFORMATION(View view) {
        Intent intent = new Intent(this, User_info.class);
        intent.putExtra("obj",userdata);
        startActivity(intent);
    }

    private void getKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("Keyhash:",
                        Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
        } catch (NoSuchAlgorithmException e) {
        }
    }


    private void sharApp() {
        ShareButton fbShareButton = (ShareButton) findViewById(R.id.share_btn);
        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("https://developers.facebook.com"))
                .build();
        fbShareButton.setShareContent(content);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}