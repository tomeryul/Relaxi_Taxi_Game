package com.example.testing.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.testing.Halpers.Item;
import com.example.testing.Halpers.ItemAdapter;
import com.example.testing.Halpers.ItemList;
import com.example.testing.Interfaces.CallBack_SendClick;
import com.example.testing.R;

public class ListFragment extends Fragment {

    private ItemList myItemList;
    private CallBack_SendClick callBack_sendClick;

    public void setCallBack_sendClick(CallBack_SendClick callBack_sendClick) {
        this.callBack_sendClick = callBack_sendClick;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        ListView listView = view.findViewById(R.id.listView);

        ItemAdapter adapter = new ItemAdapter(getContext(), this.myItemList.getTop10Items());


        listView.setAdapter(adapter);

        myOnClick(view,listView);


        return view;
    }

    private void myOnClick(View view,ListView listView ) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item item = (Item) parent.getItemAtPosition(position);
                double getCoordinateN = item.getCoordinateN();
                double getCoordinateE = item.getCoordinateE();
                callBack_sendClick.coordinatesChosen(getCoordinateN+""+","+getCoordinateE+"");
            }
        });
    }

    public void setScores(ItemList items){
        this.myItemList = items;
    }
}