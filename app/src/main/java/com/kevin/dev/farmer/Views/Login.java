package com.kevin.dev.farmer.Views;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.kevin.dev.farmer.R;
import com.kevin.dev.farmer.Resources.URLs;
import com.kevin.dev.farmer.authentication.HttpParse;
import com.kevin.dev.farmer.authentication.SharedPrefManager;
import com.kevin.dev.farmer.model.Farmer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Login extends AppCompatActivity implements View.OnClickListener {
    Button login;
    EditText Phone, Password;
    TextView register;

    String userPhone, userPassword;
    String finalResult;
    Boolean CheckEditText;
    HttpParse httpParse = new HttpParse();
    HashMap<String, String> hashMap = new HashMap<>();
    public static final String phone = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login=findViewById(R.id.loginButton);
        register=findViewById(R.id.login_toSignup);
        Phone=findViewById(R.id.login_phoneNumber);
        Password=findViewById(R.id.login_password);

        login.setOnClickListener(this);
        register.setOnClickListener(this);

    }

    public void CheckEditTextIsEmptyOrNot() {

        userPhone = Phone.getText().toString();
        userPassword = Password.getText().toString();

        if (TextUtils.isEmpty(userPassword) || TextUtils.isEmpty(userPhone)) {
            CheckEditText = false;
        } else {

            CheckEditText = true;
        }
    }




    public void UserLoginFunction(final String email, final String password) {

        class UserLoginClass extends AsyncTask<String, Void, String> {
            SweetAlertDialog n = new SweetAlertDialog(Login.this, SweetAlertDialog.PROGRESS_TYPE);


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                n.setTitleText("VALIDATING");
                n.setContentText("Please wait...");
                n.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                });
                n.show();


            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);
                try {
                    JSONObject obj = new JSONObject(httpResponseMsg);

                    if (!httpResponseMsg.equalsIgnoreCase("failed")) {
                        JSONObject userJson = obj.getJSONObject("farmer");

                        //creating a new user object
                        Farmer farmer = new Gson().fromJson(userJson.toString(),Farmer.class);

                        //storing the user in shared preferences
                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(farmer);
                        finish();

                        Intent intent = new Intent(Login.this, MainActivity.class);

                        intent.putExtra(phone, userPhone);

                        startActivity(intent);


                    }

                } catch (JSONException e) {
                    new SweetAlertDialog(Login.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("ERROR")
                            .setContentText("Invalid details")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                    n.dismissWithAnimation();
                                }
                            })
                            .show();
                    Log.e("JsonError",e.getMessage());

                }



            }
            @Override
            protected String doInBackground(String... params) {

                hashMap.put("phone",params[0]);

                hashMap.put("password",params[1]);

                finalResult = httpParse.postRequest(hashMap, URLs.loginurl);

                return finalResult;
            }
        }

        UserLoginClass userLoginClass = new UserLoginClass();

        userLoginClass.execute(email,password);
    }

    @Override
    public void onClick(View v) {
        if(v==login){

            CheckEditTextIsEmptyOrNot();

            if (CheckEditText) {

                UserLoginFunction(userPhone, userPassword);

            } else {

                Toast.makeText(Login.this, "Please fill all form fields.", Toast.LENGTH_LONG).show();


            }
        }
        if(v==register){
            Intent e= new Intent(Login.this, SignUp.class);
            startActivity(e);
        }
    }
}