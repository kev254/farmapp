package com.kevin.dev.farmer.Views;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.kevin.dev.farmer.R;
import com.kevin.dev.farmer.Resources.URLs;
import com.kevin.dev.farmer.authentication.RequestHandler;
import com.kevin.dev.farmer.authentication.SharedPrefManager;
import com.kevin.dev.farmer.model.Farmer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
    Button signUpButton;
    TextView toLogin;
    EditText phone,password,confirmPassword;
   
    boolean CheckEditText;
   
    TextView grade_id_holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        toLogin=findViewById(R.id.signUp_toLogin);
        signUpButton=findViewById(R.id.signUpButton);
        phone=findViewById(R.id.signUp_phone);
        password=findViewById(R.id.signUp_password);
        confirmPassword=findViewById(R.id.signUp_confirmPassword);
       


        //calling butttons actions based on views
        toLogin.setOnClickListener(this);
        signUpButton.setOnClickListener(this);



      

    }
    private void registerUser() {
        SweetAlertDialog regProgress= new SweetAlertDialog(SignUp.this,SweetAlertDialog.PROGRESS_TYPE);
        regProgress.setTitleText("Creating Account");
        regProgress.setContentText("Please wait...");

        final String Uname = phone.getText().toString().trim();
        final String Password = password.getText().toString().trim();
        final String ConfirmPassword = confirmPassword.getText().toString().trim();
       


      
        if(!Password.equalsIgnoreCase(ConfirmPassword)){
            Toast.makeText(this, "Password does not match", Toast.LENGTH_SHORT).show();
            return;
        }


        //if it passes all the validations

        class RegisterUser extends AsyncTask<Void, Void, String> {

            private ProgressBar progressBar;

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("phone", Uname);
                params.put("password", Password);

                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_REGISTER, params);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //displaying the progress bar while user registers on the server
                regProgress.show();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);


                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        //Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                        new SweetAlertDialog(SignUp.this, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("ACOUNT CREATED")
                                .setContentText("Proceed to login")
                                .setConfirmText("OK")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismissWithAnimation();
                                        Intent lo=new Intent(SignUp.this, Login.class);
                                        startActivity(lo);
                                        finish();
                                    }
                                })
                                .show();
                        //getting the user from the response
                        JSONObject userJson = obj.getJSONObject("farmer");

                        //creating a new user object
                        Farmer farmer = new Farmer(
                                userJson.getString("id"),
                                userJson.getString("phone")

                        );

                        //storing the user in shared preferences
                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(farmer);

                        //starting the profile activity
                        finish();
                        startActivity(new Intent(getApplicationContext(), Login.class));
                    } else {
                        //Toast.makeText(getApplicationContext(), "Some error occurred"+obj.getString("error"), Toast.LENGTH_LONG).show();
                        new SweetAlertDialog(SignUp.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("ERROR !")
                                .setContentText("Farmer Exist")
                                .setConfirmText("Ok")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismissWithAnimation();
                                    }
                                })
                                .show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                regProgress.dismissWithAnimation();
            }
        }

        //executing the async task
        RegisterUser ru = new RegisterUser();
        ru.execute();
    }


    @Override
    public void onClick(View v) {

        if(v== toLogin){
            startActivity(new Intent(SignUp.this,Login.class));
        }

        if(v==signUpButton){

            CheckEditTextIsEmptyOrNot();

            if (CheckEditText) {
                registerUser();
            } else {

                Toast.makeText(SignUp.this, "Please fill all form fields.", Toast.LENGTH_LONG).show();
            }

        }

    }
    public void CheckEditTextIsEmptyOrNot() {

        final String Uname = phone.getText().toString().trim();
        final String Password = password.getText().toString().trim();
        final String ConfirmPassword = confirmPassword.getText().toString().trim();
       

        if (TextUtils.isEmpty(Uname) || TextUtils.isEmpty(Password)|| TextUtils.isEmpty(ConfirmPassword))  {
            CheckEditText = false;
        } else {

            CheckEditText = true;
        }
    }

}