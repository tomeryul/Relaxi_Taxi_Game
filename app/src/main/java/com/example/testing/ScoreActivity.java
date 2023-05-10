package com.example.testing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.testing.Fragments.ListFragment;
import com.example.testing.Fragments.MapFragment;
import com.example.testing.Halpers.Item;
import com.example.testing.Halpers.ItemList;
import com.example.testing.Interfaces.CallBack_SendClick;
import com.example.testing.Utilities.MySPv1;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Objects;

public class ScoreActivity extends AppCompatActivity {


    private ListFragment listFragment;
    private MapFragment mapFragment;
    private FloatingActionButton score_FAB_return;
    public static final String KEY_STATUS = "KEY_STATUS";


    private CallBack_SendClick callBack_sendClick = new CallBack_SendClick() {
        @Override
        public void coordinatesChosen(String coordinates) {
            mapFragment.zoomOnRecord(coordinates);
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        initFragments();
        myIntent();
        beginTransactions();
        initView();

    }
    private void initView() {
        score_FAB_return.setOnClickListener(v -> backToSetting());

        }

    private void backToSetting() {
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
        finish();
    }

    private void myIntent() {
        Intent previousIntent = getIntent();
        String status = previousIntent.getStringExtra(KEY_STATUS);
//        converting the String into an ItemList
        ItemList theItemList = new Gson().fromJson(status,ItemList.class);
        listFragment.setScores(theItemList);

    }

    private void beginTransactions() {
        getSupportFragmentManager().beginTransaction().add(R.id.score_FRAME_list, listFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.score_FRAME_map, mapFragment).commit();
    }

    private void initFragments() {
        score_FAB_return = findViewById(R.id.score_FAB_return);
        listFragment = new ListFragment();
        listFragment.setCallBack_sendClick(callBack_sendClick);
        mapFragment = new MapFragment();
    }

}