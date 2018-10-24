package com.apps.wikex.obl;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.myViewHolder> {

    Context myContext;
    ItemList itemList;
    Dialog myDialog;

    public ItemAdapter(Context myContext, ItemList itemList) {
        this.myContext = myContext;
        this.itemList = itemList;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(myContext);
        View v = inflater.inflate(R.layout.item_card, parent, false);
        return new myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(myViewHolder myViewHolder, final int i) {
        myViewHolder.itemProduct.setText(itemList.list.get(i).getProduct());
        myViewHolder.itemPrice.setText("$ " + String.valueOf(itemList.list.get(i).getPrice()));
        myViewHolder.itemCount.setText(itemList.list.get(i).getCount());

        if(itemList.list.get(i).getCompareTo().compareToIgnoreCase("none") == 1){
            myViewHolder.itemCompareTo.setText("Compare to " + itemList.list.get(i).getCompareTo());
        } else {
            myViewHolder.itemCompareTo.setText("");

        }



        myViewHolder.itemCode.setText(itemList.list.get(i).getCode());

        Picasso.with(myContext).load(itemList.list.get(i).getImageSrc()).into(myViewHolder.itemImage);


        final ImageButton addB = myViewHolder.addButton;
        final ImageButton remB = myViewHolder.removeButton;

        remB.setEnabled(false);
        remB.setImageResource(R.drawable.ic_remove_circle_gray_24dp);

        myViewHolder.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppSettings.getCurrentList().addToCart(itemList.getList().get(i));
                ItemListActivity.changeBudget();
                Toast.makeText(myContext,"Item " + itemList.getList().get(i).getCode()+ " added", Toast.LENGTH_SHORT).show();
                remB.setImageResource(R.drawable.ic_remove_circle_black_24dp);
                remB.setEnabled(true);
            }
        });



        myViewHolder.removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppSettings.getCurrentList().removeFromCart(itemList.getList().get(i).getCode());
                ItemListActivity.changeBudget();
                if (itemList.getList().get(i).getAmount() < 1){
                    remB.setEnabled(false);
                    remB.setImageResource(R.drawable.ic_remove_circle_gray_24dp);

                }
                Toast.makeText(myContext,"Item " + itemList.getList().get(i).getCode()+ " removed", Toast.LENGTH_SHORT).show();
            }
        });

        myViewHolder.itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog = new Dialog(myContext);
                myDialog.setContentView(R.layout.picture_dialog);
                TextView dialogCloseB = myDialog.findViewById(R.id.closeText);
                ImageView imageView = myDialog.findViewById(R.id.imageDialog);
                Picasso.with(myContext).load(itemList.list.get(i).getImageSrc()).into(imageView);
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

    @Override
    public int getItemCount() {
        return AppSettings.getItemList().getList().size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder{

        TextView itemProduct;
        TextView itemCompareTo;
        TextView itemCode;
        TextView itemPrice;
        TextView itemCount;
        ImageView itemImage;
        ImageButton addButton;
        ImageButton removeButton;

        public myViewHolder(View  itemView){
            super(itemView);
            itemCode = itemView.findViewById(R.id.itemCode);
            itemCompareTo = itemView.findViewById(R.id.itemComparteTo);
            itemCount = itemView.findViewById(R.id.itemCount);
            itemPrice = itemView.findViewById(R.id.itemPrice);
            itemProduct = itemView.findViewById(R.id.itemProduct);
            itemImage = itemView.findViewById(R.id.productImage);
            addButton = itemView.findViewById(R.id.addButton);
            removeButton = itemView.findViewById(R.id.removeButton);
        }

    }


}
