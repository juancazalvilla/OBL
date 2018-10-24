package com.apps.wikex.obl;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class LoadListActivity extends AppCompatActivity {

    RadioGroup filesRG = null;
    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_list);

        AppSettings.getCurrentList().destroyList();
        filesRG = findViewById(R.id.filesRG);
        filesRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                b.setEnabled(true);
            }
        });
        loadFiles();
        b = findViewById(R.id.loadList);
        b.setEnabled(false);
    }

    private void loadFiles(){
        for(String s : AppSettings.getListIndex()){
            RadioButton list = new RadioButton(filesRG.getContext());
            list.setText(s);
            filesRG.addView(list);
        }


    }

    private void loadDataFromFile(String file){
        try {
            FileInputStream fileInputStream = openFileInput(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String data;
            while((data = bufferedReader.readLine()) != null){
                Log.e("IOERROR", data);
                String[] listItem = data.split("-", 0);
                Log.e("IOERROR", listItem[0] + " " + listItem[1]);
                String code = listItem[0];
                int amount = Integer.parseInt(listItem[1]);
                Log.e("IOERROR", "Code " +   listItem[0]);
                ItemModel itemModel = AppSettings.getItemList().findItemByCode(code);
                itemModel.setAmount(amount);
                AppSettings.getCurrentList().addToCart(itemModel);

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


    public void loadList(View view){
        Intent intent = new Intent(this,ShowListActivity.class);
        intent.putExtra(AppSettings.listMode, true);
        RadioButton checked = findViewById(filesRG.getCheckedRadioButtonId());
        String fileName = checked.getText().toString() + ".list";
        loadDataFromFile(fileName);
        startActivity(intent);
    }
}
