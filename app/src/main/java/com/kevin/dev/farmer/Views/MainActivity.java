package com.kevin.dev.farmer.Views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kevin.dev.farmer.R;
import com.kevin.dev.farmer.Resources.ClickListener;
import com.kevin.dev.farmer.Resources.URLs;
import com.kevin.dev.farmer.adapter.FertilizerAdapter;
import com.kevin.dev.farmer.authentication.SharedPrefManager;
import com.kevin.dev.farmer.model.Farmer;
import com.kevin.dev.farmer.model.Fertilizer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity implements ClickListener {
    private Farmer farmer;
    TextView name;
    RecyclerView recyclerView;
    Toolbar toolbar;
    ArrayList <Fertilizer> usagelist;
    LinearLayout profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        farmer = SharedPrefManager.getInstance(this).getUser();
        recyclerView=findViewById(R.id.historyRecyclerView);
        toolbar=findViewById(R.id.toolbar);
        name=findViewById(R.id.who);
        Log.e("user", farmer.getId());
        name.setText("Hi "+ farmer.getPhone());

        recyclerView.setHasFixedSize(true);
        usagelist= new ArrayList<>();

        recyclerView.setLayoutManager(new GridLayoutManager(this,1));


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        findViewById(R.id.applyCard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent apply= new Intent(MainActivity.this,ApplyA.class);
                startActivity(apply);
            }
        });


        loadHistory();


    }



    private void loadHistory() {
        final SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(MainActivity.this,SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.setTitle("Fetching Records");
        sweetAlertDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.URL_FERTILIZER_USAGE+farmer.getId(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        sweetAlertDialog.dismiss();
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject comp = array.getJSONObject(i);

                                //adding the product to product list
                                usagelist.add(new Fertilizer(
                                        comp.getString("id"),
                                        comp.getString("quantity_used"),
                                        comp.getString("quantity_rem"),
                                        farmer.getId(),
                                        comp.getString("supplier_id"),
                                        comp.getString("name")



                                ));

                            }

                            //creating adapter object and setting it to recyclerview
                            FertilizerAdapter adapter = new FertilizerAdapter(MainActivity.this, usagelist,MainActivity.this);
                            recyclerView.setAdapter(adapter);
                            Log.e("usagelist", String.valueOf(usagelist));

                        } catch (JSONException e) {
                            sweetAlertDialog.dismiss();
                            e.printStackTrace();
                            new SweetAlertDialog(MainActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("ZERO RECORDS FOUND")
                                    .setContentText("No record")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            sDialog.dismissWithAnimation();
                                        }
                                    })
                                    .show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });


        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }

   

}

