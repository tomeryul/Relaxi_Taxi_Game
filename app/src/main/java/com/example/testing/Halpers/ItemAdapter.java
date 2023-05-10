package com.example.testing.Halpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.testing.R;

import java.util.ArrayList;

public class ItemAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Item> items = new ArrayList<>();;

    public ItemAdapter(Context context, ArrayList<Item> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);
        }

        TextView tvScore = convertView.findViewById(R.id.tvScore);
        TextView tvCoordinateN = convertView.findViewById(R.id.tvCoordinateN);
        TextView tvCoordinateE = convertView.findViewById(R.id.tvCoordinateE);

        Item item = (Item) getItem(position);

        tvScore.setText("Score: "+Integer.toString(item.getScore()));
        tvCoordinateN.setText(Double.toString(item.getCoordinateN()));
        tvCoordinateE.setText(Double.toString(item.getCoordinateE()));

        return convertView;
    }
}



