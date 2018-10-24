package com.apps.wikex.obl;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ShowListActivity extends AppCompatActivity {

    Context myContext = this;
    Dialog myDialog;
    ImageButton saveB;
    String fileName = "sample";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list);

        for (ItemModel itemModel : AppSettings.getItemList().getList()) {
            String msg = String.valueOf(itemModel.getAmount());
            Log.v("ITEMMODEL", itemModel.getCode() + " " + msg);
        }

        ArrayAdapter<ItemModel> itemsShop = new ArrayAdapter<ItemModel>(this, android.R.layout.simple_list_item_1, AppSettings.getCurrentList().getList());


        ListView itemList = findViewById(R.id.shopItemList);
        itemList.setAdapter(itemsShop);
        saveB = findViewById(R.id.saveButton);
        saveB.setEnabled(true);
        saveB.setImageResource(R.drawable.ic_save_black_24dp);

        if(getIntent().hasExtra(AppSettings.listMode)){
            saveB.setEnabled(false);
            saveB.setImageResource(R.drawable.ic_save_gray_24dp);
        }

        TextView totalShopping = findViewById(R.id.totalShopping);
        String total = String.format("Total $ %.2f", Float.valueOf(Double.toString(AppSettings.getCurrentList().getTotal())));
        totalShopping.setText(total);


        itemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                myDialog = new Dialog(myContext);
                myDialog.setContentView(R.layout.picture_dialog);
                TextView dialogCloseB = myDialog.findViewById(R.id.closeText);
                ImageView imageView = myDialog.findViewById(R.id.imageDialog);
                Picasso.with(myContext).load(AppSettings.getCurrentList().list.get(position).getImageSrc()).into(imageView);
                myDialog.show();

                dialogCloseB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog.dismiss();
                    }
                });
            }
        });

    }

    public void saveList(View v) {
        launcheDialog();


    }

    public void starOver(View v) {
        AppSettings.getCurrentList().destroyList();
        Intent intent = new Intent(myContext, MenuActivity.class);
        startActivity(intent);
    }

    private void saveFile(String filename) {
        String data;
        String fileName = filename + ".list";
        for (ItemModel im : AppSettings.getCurrentList().list) {
            data = im.getCode() + "-" + im.getAmount();
            try {
                FileOutputStream file = openFileOutput(fileName, MODE_APPEND);
                file.write(data.getBytes());
                file.write("\n".getBytes());
                file.close();
            } catch (FileNotFoundException f) {
                Log.e("TAG", "file not available for writing");
                f.printStackTrace(); //help with debugging
            } catch (IOException i) {
                Log.e("TAG", "IO Exception");
            }

        }
    }

    private void saveListName(String fileName) {



            try {
                FileOutputStream file = openFileOutput(AppSettings.FILEINDEX, MODE_APPEND);
                file.write(fileName.getBytes());
                file.write("\n".getBytes());
                file.close();
            } catch (FileNotFoundException f) {
                Log.e("TAG", "file not available for writing");
                f.printStackTrace(); //help with debugging
            } catch (IOException i) {
                Log.e("TAG", "IO Exception");
            }


        }


    private void changeSP(Context context){

        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor shEditor = sh.edit();

        shEditor.putBoolean(AppSettings.SP_SAVEDLIST, true);
        shEditor.commit();

    }

        private void launcheDialog () {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Enter name for the list");

            // Set up the input
            final EditText input = new EditText(this);
            // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
            builder.setView(input);

            // Set up the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    fileName = input.getText().toString();
                    saveListName(fileName);
                    saveFile(fileName);
                    if(AppSettings.getSavedList() == false){
                        AppSettings.setSavedList(true);
                        changeSP(myContext);
                    }
                    Toast.makeText(myContext, "List saved", Toast.LENGTH_SHORT).show();
                    saveB.setEnabled(false);
                    saveB.setImageResource(R.drawable.ic_save_gray_24dp);
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();
        }
    }


