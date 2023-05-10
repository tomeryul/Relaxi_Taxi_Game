package com.example.testing.Halpers;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;



public class ItemList {

    private ArrayList<Item> items = new ArrayList<>();

    public ItemList(ArrayList<Item> items) {
        this.items=items;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public ArrayList<Item> getTop10Items(){
        Collections.sort(this.items, new Comparator<Item>() {
            @Override
            public int compare(Item item1, Item item2) {
                return Integer.compare(item2.getScore(), item1.getScore());

            }
        });
        ArrayList<Item> top10 = new ArrayList<>();
        for(int i = 0 ; i < items.size() ; i++){
            top10.add(items.get(i));
            if(top10.size() == 10)
                break;
        }
        return top10;
    }

    public ItemList setMovies(ArrayList<Item> items) {
        this.items = items;
        return this;
    }


}
