package com.apps.wikex.obl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ItemList {
    public ArrayList<ItemModel> list;
    public double total;
    public int totalAmount;

    public ItemList() {
        this.list = new ArrayList<>();
        this.total = 0;
        this.totalAmount = 0;
    }

    public List<ItemModel> getList() {
        return list;
    }

    public void destroyList(){
        list.clear();
        this.total = 0;
        this.totalAmount = 0;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public ItemModel findItemByCode(String code){
        return getList().get(findItemIndex(code));
    }

    private int findItemIndex(String code){
        boolean found = false;
        int index = 0 , itemFound = -1;
        while (!found && index < list.size()){
            if(list.get(index).getCode().compareToIgnoreCase(code) == 0){
                itemFound = index;
                found = true;
                }
            index++;
        }
        return itemFound;
    }

    public void addToList(ItemModel itemModel){
        int index = findItemIndex(itemModel.getCode());
            if( index == -1){
                list.add(itemModel);
            }

        }

    public void addToCart(ItemModel item){
        int foundItem = findItemIndex(item.getCode());
        if (foundItem == -1){
            list.add(item);
            list.get(list.size()-1).setAmount(1);
        } else {
            list.get(findItemIndex(item.getCode())).changeAmount(1);
        }
        this.total += item.getPrice();
        this.totalAmount += 1;
    }

    public void removeFromCart(String code){
        int index = findItemIndex(code);
        if(index != -1){
            this.total -= list.get(index).getPrice();
            this.totalAmount -= 1;
            list.get(index).changeAmount(-1);
            if(list.get(index).getAmount() == 0){
                list.remove(index);
            }
        }


    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }
}
