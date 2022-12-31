package com.kevin.dev.farmer.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.kevin.dev.farmer.R;
import com.kevin.dev.farmer.Resources.URLs;
import com.kevin.dev.farmer.authentication.RequestHandler;
import com.kevin.dev.farmer.authentication.SharedPrefManager;
import com.kevin.dev.farmer.model.Farmer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ApplyA extends AppCompatActivity {
    EditText name,rem,used;
    Button apply;
    Farmer farmer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply);
        name=findViewById(R.id.name);
        used=findViewById(R.id.used);
        rem=findViewById(R.id.rem);
        apply=findViewById(R.id.apply);
        farmer = SharedPrefManager.getInstance(this).getUser();
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApplyFert();
            }
        });


    }
    private void ApplyFert() {
        SweetAlertDialog regProgress= new SweetAlertDialog(ApplyA.this,SweetAlertDialog.PROGRESS_TYPE);
        regProgress.setTitleText("submitting ");
        regProgress.setContentText("Please wait...");


        //if it passes all the validations

        class ApplyFretilizer extends AsyncTask<Void, Void, String> {

            private ProgressBar progressBar;

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object

                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("name", name.getText().toString());
                params.put("quantity_used", used.getText().toString());
                params.put("quantity_rem", rem.getText().toString());
                params.put("supplier_id", "3");
                params.put("farmer_id",farmer.getId() );
                params.put("cost", "400");


                //returing the response
                return requestHandler.sendPostRequest(URLs.apply_fertilizer, params);
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
                        Toast.makeText(ApplyA.this, "submitted", Toast.LENGTH_SHORT).show();

                    } else {
                        //Toast.makeText(getApplicationContext(), "Some error occurred"+obj.getString("error"), Toast.LENGTH_LONG).show();
                        new SweetAlertDialog(ApplyA.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("ERROR !")
                                .setContentText("errorr")
                                .show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                regProgress.dismissWithAnimation();
            }
        }

        //executing the async task
        ApplyFretilizer af = new ApplyFretilizer();
        af.execute();
    }
}