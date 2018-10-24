package com.apps.wikex.obl;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ItemListActivity extends AppCompatActivity {

    static TextView  totalBudget;
    static ImageButton shopButtom;
    ItemAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemslist);

        RecyclerView recyclerView = findViewById(R.id.items_panel);

        adapter = new ItemAdapter(this, AppSettings.getItemList());

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        totalBudget = findViewById(R.id.totalBudget);
        shopButtom = findViewById(R.id.checkOutButton);
        totalBudget.setText("$ 0.00");

        if(AppSettings.getCurrentList().getTotalAmount() > 0){
            shopButtom.setImageResource(R.drawable.ic_shopping_cart_black_24dp);
            shopButtom.setEnabled(true);
        } else{
            shopButtom.setImageResource(R.drawable.ic_remove_shopping_cart_black_24dp);
            shopButtom.setEnabled(false);
        }
    }

    public void showList(View v){
        Intent intent = new Intent(this, ShowListActivity.class);
        startActivity(intent);
    }

    public static void changeBudget(){

        String total = String.format("$ %.2f" , Float.valueOf(Double.toString(AppSettings.getCurrentList().total)));
        totalBudget.setText(total);

        if(AppSettings.getCurrentList().getTotalAmount() > 0){
            shopButtom.setImageResource(R.drawable.ic_shopping_cart_black_24dp);
            shopButtom.setEnabled(true);
        } else{
            shopButtom.setImageResource(R.drawable.ic_remove_shopping_cart_black_24dp);
            shopButtom.setEnabled(false);
        }


    }


}
