package com.xuber_for_services.provider.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.xuber_for_services.provider.Constant.URLHelper;
import com.xuber_for_services.provider.Helper.ConnectionHelper;
import com.xuber_for_services.provider.Helper.CustomDialog;
import com.xuber_for_services.provider.Helper.SharedHelper;
import com.xuber_for_services.provider.R;
import com.xuber_for_services.provider.Utils.Utilities;
import com.xuber_for_services.provider.XuberServicesApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.xuber_for_services.provider.XuberServicesApplication.trimMessage;

public class OtpMatchActivity extends AppCompatActivity {

    EditText edit_otp;
    Button btnSendOTP;

    Boolean isInternet;
    ConnectionHelper helper;
    CustomDialog customDialog;
    Utilities utils = new Utilities();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_match);

        edit_otp= (EditText) findViewById(R.id.editOTP);
        btnSendOTP= (Button) findViewById(R.id.btnSendOTP);
        helper = new ConnectionHelper(OtpMatchActivity.this);
        isInternet = helper.isConnectingToInternet();

        btnSendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 if (edit_otp.getText().toString().equals("")) {
                     displayMessage(getString(R.string.otp_number_empty));
                 }else {
                     if (isInternet) {
                         registerOtp();
                     } else {
                         displayMessage(getString(R.string.something_went_wrong_net));
                     }

                 }


            }
        });


    }

    private void registerOtp() {


        customDialog = new CustomDialog(OtpMatchActivity.this);
        customDialog.setCancelable(false);
        customDialog.show();
        JSONObject object = new JSONObject();
        try {
//            object.put("device_type", "android");
//            object.put("device_id", device_UDID);
//            object.put("device_token", "" + device_token);
//            object.put("login_by", "manual");
//            object.put("first_name", first_name.getText().toString());
//            object.put("last_name", last_name.getText().toString());
//            object.put("email", email.getText().toString());
//            object.put("password", password.getText().toString());
//            object.put("password_confirmation", password.getText().toString());
            object.put("otp", edit_otp.getText().toString());
            utils.print("Register_OTP", "" + object);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URLHelper.Otp, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                customDialog.dismiss();
                Log.e("otp_RESponce", response.toString());
                utils.print("SignInResponse", response.toString());
                // Toast.makeText(context, "Registration Successful.", Toast.LENGTH_SHORT).show();
                Toast.makeText(OtpMatchActivity.this, "Registration Successfully", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(OtpMatchActivity.this, ActivityPassword.class);
                startActivity(intent);

//                SharedHelper.putKey(RegisterActivity.this, "email", email.getText().toString());
//                SharedHelper.putKey(RegisterActivity.this, "password", password.getText().toString());
//                signIn();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                customDialog.dismiss();
                String json = null;
                String Message;
                NetworkResponse response = error.networkResponse;

                if (response != null && response.data != null) {
                    utils.print("MyTest", "" + error);
                    utils.print("MyTestError", "" + error.networkResponse);
                    utils.print("MyTestError1", "" + response.statusCode);
                    try {
                        JSONObject errorObj = new JSONObject(new String(response.data));

                        if (response.statusCode == 400 || response.statusCode == 405 || response.statusCode == 500) {
                            try {
                                displayMessage(errorObj.optString("message"));
                            } catch (Exception e) {
                                displayMessage(getString(R.string.something_went_wrong));
                            }
                        } else if (response.statusCode == 401) {
                            try {
                                if (errorObj.optString("message").equalsIgnoreCase("invalid_token")) {
                                    //Call Refresh token
                                } else {
                                    displayMessage(errorObj.optString("message"));
                                }
                            } catch (Exception e) {
                                displayMessage(getString(R.string.something_went_wrong));
                            }

                        } else if (response.statusCode == 422) {

                            json = trimMessage(new String(response.data));
                            if (json != "" && json != null) {
                                displayMessage(json);
                            } else {
                                displayMessage(getString(R.string.please_try_again));
                            }

                        } /*else if (response.statusCode == 1000){

                        } */ else {
                            displayMessage(getString(R.string.please_try_again));
                        }

                    } catch (Exception e) {
                        displayMessage(getString(R.string.something_went_wrong));
                    }


                }
            }
        }) {
//            @Override
//             public Map<String, String> getHeaders() throws AuthFailureError {
//                 HashMap<String, String> headers = new HashMap<String, String>();
//                 headers.put("X-Requested-With", "XMLHttpRequest");
//                 headers.put("Authorization", "Bearer" + " " + SharedHelper.getKey(OtpMatchActivity.this, "access_token"));
//                 Log.e("Authoization", "Bearer" + " "
//                         + SharedHelper.getKey(OtpMatchActivity.this, "access_token"));
//                 return headers;
//             }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("X-Requested-With", "XMLHttpRequest");
                return headers;
            }
        };

        XuberServicesApplication.getInstance().addToRequestQueue(jsonObjectRequest);

    }

    private void displayMessage(String string) {
    }

}
