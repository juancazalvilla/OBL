package com.apps.wikex.obl;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button showListB = findViewById(R.id.seeListButton);
        showListB.setEnabled(AppSettings.getSavedList());

        // load Filelist content
        AppSettings.getListIndex().clear();
        if(AppSettings.getSavedList()){
            try {
                FileInputStream fileInputStream = openFileInput(AppSettings.FILEINDEX);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String data;
                while((data = bufferedReader.readLine()) != null){
                    AppSettings.getListIndex().add(data);
                }
            }
            catch (FileNotFoundException f){
                f.printStackTrace();
                Log.e("FILEIO", "file can not be opened");
            }
            catch (IOException i){
                Log.e("FILEIO", "IO EXCEPTION READING FILE");
            }
        }
    }

    public void makeNewList(View v){
        Intent intent = new Intent(this, ItemListActivity.class);
        startActivity(intent);
    }

    public void seeOldList(View v){
        Intent intent = new Intent(this, LoadListActivity.class);
        startActivity(intent);
    }
}
