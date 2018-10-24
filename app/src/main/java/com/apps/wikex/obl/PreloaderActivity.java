package com.apps.wikex.obl;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PreloaderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disclamer);

        // load JSON
        loadJSONData();

        // prepare the screens
        Intent intent = null;
        boolean disclamerCheked = true;

        //load Shared Preference data
        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(this);
        AppSettings.setSavedList( sh.getBoolean(AppSettings.SP_SAVEDLIST, false));


        if (disclamerCheked){
            intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
        }





    }

    private void loadJSONData(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading data...");
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(AppSettings.URL_DATA, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Log.e("Volley", "Items to read: " + String.valueOf(response.length()));

                for (int i = 0; i< response.length(); i++){
                    try{
                        JSONObject jsonObject = response.getJSONObject(i);

                        ItemModel item = new ItemModel(jsonObject.getString("itemCode"),
                                jsonObject.getString("itemProduct"),
                                jsonObject.getString("itemCompareTo"),
                                jsonObject.getString("itemCount"),
                                Double.valueOf(jsonObject.getString("itemPrice")),
                                jsonObject.getString("itemImage"));

                        AppSettings.getItemList().addToList(item);
                        Log.e("Volley", "item " + item.getCode() + " added, list size: " + AppSettings.getItemList().getList().size() );
                    } catch (JSONException e){
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                }

                progressDialog.dismiss();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
                progressDialog.dismiss();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);


    }
}
