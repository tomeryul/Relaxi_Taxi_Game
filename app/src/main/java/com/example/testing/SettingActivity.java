package com.example.testing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.bumptech.glide.Glide;
import com.example.testing.Halpers.Item;
import com.example.testing.Halpers.ItemList;
import com.example.testing.Utilities.MySPv1;
import com.example.testing.Utilities.StepDetector;
import com.google.gson.Gson;

import java.util.ArrayList;

public class SettingActivity extends AppCompatActivity {

    private StepDetector stepDetector;
    private AppCompatImageView main_IMG_background;
    public static final String NEWSCORE = "NEWSCORE";
    public static final String CoordinateN = "CoordinateN";
    public static final String CoordinateE = "CoordinateE";
    int fast_speed = 0;
    int Sensors = 0;
    Switch Switch_speed;
    Switch Switch_controls;
    Button button_score_screen;
    Button button_start_game;
    private ItemList myItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        findViews();
        setActions();
        updateScores();

        Glide
                .with(this)
                .load(R.drawable.ic_icon_relaxi_taxi)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(main_IMG_background);
    }

    private void updateScores() {
        Intent previousIntent = getIntent();
        int theScore = previousIntent.getIntExtra(NEWSCORE, 0);
        double latitude = previousIntent.getDoubleExtra(CoordinateN, 0.0);
        double longitude = previousIntent.getDoubleExtra(CoordinateE, 0.0);
        Log.d("In SettingActivity", "latitude: "+latitude+", longitude: "+longitude);
        if (theScore != 0){
//            in this place i need to get the coordinates (N,E) and not jus entering 32 and 34
            addMyItemList(theScore,latitude,longitude);
        }

    }

    private void setActions() {
        button_start_game.setOnClickListener(view -> startGame());
        button_score_screen.setOnClickListener(view -> moveToScoreScreen());
        Switch_speed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked)
                    fast_speed = 1;
                else
                    fast_speed = 0;
            }
        });

        Switch_controls.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked)
                    Sensors = 1;
                else
                    Sensors = 0;
            }
        });
    }

    private void moveToScoreScreen() {
        Intent intent = new Intent(this, ScoreActivity.class);
        String fromSP = MySPv1.getString(this,"savedValues","");
        intent.putExtra(ScoreActivity.KEY_STATUS,fromSP);
        startActivity(intent);
        finish();
    }

    private void startGame() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.SPEED,fast_speed);
        intent.putExtra(MainActivity.CONTROLS,Sensors);
        startActivity(intent);
        finish();
    }


    private void findViews() {
        main_IMG_background = findViewById(R.id.main_IMG_background);
        Switch_speed = (Switch) findViewById(R.id.setting_SWC_speed);
        Switch_controls = (Switch) findViewById(R.id.setting_SWC_controls);
        button_score_screen = findViewById(R.id.setting_BTN_Score_Screen);
        button_start_game = findViewById(R.id.setting_BTN_Start_Game);
    }

    private void addMyItemList(int score,double coordinateN,double coordinateE){
//        not needed any more (just for the first time running the program)
//        ///////////////////////////////////////////////////////////////////////////////////////////
//        // starting the items (i still need to sava all of them on the application).
//        ArrayList<Item> items= new ArrayList<>();
//        items.add(new Item(1000,32.1594,34.9732));
//        items.add(new Item(2000,32.2209,34.9924));
//        items.add(new Item(3000,32.1515,34.9680));
//        items.add(new Item(1222,32.1327,34.9255));
//        items.add(new Item(3214,32.1584,34.9742));
//        ItemList itemList = new ItemList(items);
//
//        // implementing json (i think this is the way of saving the status of the scores)
//        String ItemJson = new Gson().toJson(itemList);
//
//        // inserting the json file to "savedValues"
//        MySPv1.putString(this,"savedValues", ItemJson);
//        ///////////////////////////////////////////////////////////////////////////////////////////

//        uploading the values on the "savedValues" and make in a string
        String fromSP = MySPv1.getString(this,"savedValues","");
//        making the string of values in to an ItemList
        ItemList fromJsonItemList = new Gson().fromJson(fromSP,ItemList.class);
//        adding the new score to the ItemList we imported
        fromJsonItemList.getItems().add(new Item(score,coordinateN,coordinateE));
//        converting the ItemList back to a string
        String ItemJson = new Gson().toJson(fromJsonItemList);
//      updating the "savedValues" Json file to the new ItemList
        MySPv1.putString(this,"savedValues", ItemJson);


    }


}