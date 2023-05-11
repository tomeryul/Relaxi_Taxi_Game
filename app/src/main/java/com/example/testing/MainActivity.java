package com.example.testing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;

import com.example.testing.Interfaces.StepCallback;
import com.example.testing.Utilities.StepDetector;
import com.example.testing.logic.GameManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {


    private ShapeableImageView[] main_IMG_hearts;
    private ShapeableImageView[][] main_IMG_traffic_lights;
    private ShapeableImageView[] main_IMG_car;
    private FloatingActionButton time_FAB_left;
    private FloatingActionButton time_FAB_right;
    private MaterialTextView ScoreMaterialTextView;
    private MediaPlayer Theme_Song,how_you_doin;

    private StepDetector stepDetector;
    public static final String SPEED = "SPEED";
    public static final String CONTROLS = "CONTROLS";
    private int new_traffic_lights_timer = 0;
    public int DELAY = 1000;
    private int isSensors = 0;
    long startTime = 0;
    int collided;
    double latitude,longitude;
    private Timer timer;
    private GameManager gameManager;
    Random rand = new Random();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myIntent();
        findViews();
        initMediaPlayer();
        setFirstVisibility();
        startTime();
        initView();


        gameManager = new GameManager(main_IMG_hearts.length,getApplicationContext());

    }

    private void initMediaPlayer() {
        Theme_Song = MediaPlayer.create(this,R.raw.gamesong);
        Theme_Song.setVolume(1.0f,1.0f);
        how_you_doin = MediaPlayer.create(this,R.raw.crushsound);
        how_you_doin.setVolume(1.0f,1.0f);
    }

    private void loopSong() {
        if(!Theme_Song.isPlaying()){
            Theme_Song.start();
        }
    }

    private void initStepDetector() {
        stepDetector = new StepDetector(this, new StepCallback() {
            @Override
            public void stepLeft() {
                GoLeft();
            }

            @Override
            public void stepRight() {
                GoRight();
            }

            @Override
            public void stepY() {
                // Pass
            }
        });
    }

    public void getConnection(){
        requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;

        }
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        Log.d("inMainActivity", "latitude: "+latitude+", longitude: "+longitude);
    }

    private void myIntent() {
        Intent previousIntent = getIntent();
        int isFast = previousIntent.getIntExtra(SPEED,0);
        if(isFast == 1)
            DELAY = 500;
        isSensors = previousIntent.getIntExtra(CONTROLS,0);

    }

    private void setFirstVisibility() {
        for(int i = 0; i < main_IMG_car.length; i++){
            if (i != main_IMG_car.length / 2){
                main_IMG_car[i].setVisibility(View.INVISIBLE);
            }
        }

        for (ShapeableImageView[] main_img_traffic_light : main_IMG_traffic_lights) {
            for (ShapeableImageView shapeableImageView : main_img_traffic_light) {
                shapeableImageView.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void initView() {
        if(isSensors == 0) {
            time_FAB_left.setOnClickListener(v -> GoLeft());
            time_FAB_right.setOnClickListener(v -> GoRight());
        }
        else {
            time_FAB_left.setVisibility(View.INVISIBLE);
            time_FAB_right.setVisibility(View.INVISIBLE);
            initStepDetector();
        }
    }

    private void GoRight() {
        if(main_IMG_car[main_IMG_traffic_lights[0].length-1].getVisibility() == View.INVISIBLE){
            for( int i = 0; i < main_IMG_traffic_lights[0].length-1 ; i++){
                if(main_IMG_car[i].getVisibility()==View.VISIBLE) {
                    main_IMG_car[i].setVisibility(View.INVISIBLE);
                    main_IMG_car[i+1].setVisibility(View.VISIBLE);
                    break;
                }
            }
        }
    }

    private void GoLeft() {
        if(main_IMG_car[0].getVisibility() == View.INVISIBLE){
            for( int i = 1; i < main_IMG_traffic_lights[0].length ; i++){
                if(main_IMG_car[i].getVisibility()==View.VISIBLE) {
                    main_IMG_car[i].setVisibility(View.INVISIBLE);
                    main_IMG_car[i-1].setVisibility(View.VISIBLE);
                    break;
                }
            }
        }
    }


    private void findViews() {
        ScoreMaterialTextView = findViewById(R.id.main_LBL_score);
        main_IMG_hearts = new ShapeableImageView[]{
                findViewById(R.id.main_IMG_heart1),
                findViewById(R.id.main_IMG_heart2),
                findViewById(R.id.main_IMG_heart3)};

        main_IMG_traffic_lights = new ShapeableImageView[][]{{
                findViewById(R.id.traffic_light1),
                findViewById(R.id.traffic_light2),
                findViewById(R.id.traffic_light3),
                findViewById(R.id.traffic_light4),
                findViewById(R.id.traffic_light5)},{
                findViewById(R.id.traffic_light6),
                findViewById(R.id.traffic_light7),
                findViewById(R.id.traffic_light8),
                findViewById(R.id.traffic_light9),
                findViewById(R.id.traffic_light10)},{
                findViewById(R.id.traffic_light11),
                findViewById(R.id.traffic_light12),
                findViewById(R.id.traffic_light13),
                findViewById(R.id.traffic_light14),
                findViewById(R.id.traffic_light15)},{
                findViewById(R.id.traffic_light16),
                findViewById(R.id.traffic_light17),
                findViewById(R.id.traffic_light18),
                findViewById(R.id.traffic_light19),
                findViewById(R.id.traffic_light20)},{
                findViewById(R.id.traffic_light21),
                findViewById(R.id.traffic_light22),
                findViewById(R.id.traffic_light23),
                findViewById(R.id.traffic_light24),
                findViewById(R.id.traffic_light25)},{
                findViewById(R.id.traffic_light26),
                findViewById(R.id.traffic_light27),
                findViewById(R.id.traffic_light28),
                findViewById(R.id.traffic_light29),
                findViewById(R.id.traffic_light30)}};

        main_IMG_car = new ShapeableImageView[]{
                findViewById(R.id.main_IMG_car1),
                findViewById(R.id.main_IMG_car2),
                findViewById(R.id.main_IMG_car3),
                findViewById(R.id.main_IMG_car4),
                findViewById(R.id.main_IMG_car5)};

        time_FAB_left = findViewById(R.id.time_FAB_left);
        time_FAB_right = findViewById(R.id.time_FAB_right);
    }


    private void updateTimeUI() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        for(int i = main_IMG_traffic_lights.length-1 ; i >= 0 ; i--){
            for(int j = main_IMG_traffic_lights[i].length-1 ; j >=0  ; j--){
                if(main_IMG_traffic_lights[i][j].getVisibility()==View.VISIBLE){
                    if(i < main_IMG_traffic_lights.length-1) {
                        main_IMG_traffic_lights[i][j].setVisibility(View.INVISIBLE);
                        main_IMG_traffic_lights[i + 1][j].setVisibility(View.VISIBLE);
                        if(main_IMG_traffic_lights[i][j].getDrawable().getConstantState() == getResources().getDrawable( R.drawable.ic_icon_coin).getConstantState()) {
// another way of writing the if statement (that don't have deprecated)
// if(main_IMG_traffic_lights[i][j].getDrawable().getConstantState() == Objects.requireNonNull(ContextCompat.getDrawable(this, R.drawable.ic_icon_coin)).getConstantState()) {
                            main_IMG_traffic_lights[i + 1][j].setImageResource(R.drawable.ic_icon_coin);
                            main_IMG_traffic_lights[i][j].setImageResource(R.drawable.ic_icon_traffic_light);
                        }
                    }
                    else {
                        main_IMG_traffic_lights[i][j].setVisibility(View.INVISIBLE);
                        main_IMG_traffic_lights[i][j].setImageResource(R.drawable.ic_icon_traffic_light);
                    }
                }
            }
        }
        if(new_traffic_lights_timer==1){
            loopSong();
            makeNewLight();
        }
        new_traffic_lights_timer = (new_traffic_lights_timer + 1)%2;
        collided = 0;
        collided = gameManager.checkCollision(main_IMG_traffic_lights,main_IMG_car,getResources());
        if(collided == 1)
            how_you_doin.start();
        updateLife();
    }

    private void updateLife() {
        if(gameManager.getWrong() != 0) {
            if (!gameManager.getIsEnded())
                main_IMG_hearts[gameManager.getLife() - gameManager.getWrong()].setVisibility(View.INVISIBLE);
            else {
                EndGame();
            }
        }
        ScoreMaterialTextView.setText("" + gameManager.getScore());
    }

    private void EndGame() {
        Intent intent = new Intent(this, SettingActivity.class);
        intent.putExtra(SettingActivity.NEWSCORE,gameManager.getScore());
        getConnection();
        intent.putExtra(SettingActivity.CoordinateN,latitude);
        intent.putExtra(SettingActivity.CoordinateE,longitude);
        Theme_Song.pause();
        startActivity(intent);
        finish();
    }

    private void makeNewLight() {
        int where = rand.nextInt(main_IMG_traffic_lights[0].length);
        int whatImage = rand.nextInt(6);
        main_IMG_traffic_lights[0][where].setVisibility(View.VISIBLE);
//        Log.d("check random", "the whatImage num is: "+whatImage);
        if(whatImage == 2){
            main_IMG_traffic_lights[0][where].setImageResource(R.drawable.ic_icon_coin);
        }
    }

    private void startTime() {
        if (timer == null) {
            startTime = System.currentTimeMillis();
            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(() -> updateTimeUI());
                }
            }, 0, DELAY);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        loopSong();
        startTime();
        if(isSensors == 1)
            stepDetector.start();
    }

    @Override
    protected void onPause() {
        super.onPause();

        Theme_Song.pause();
        timer.cancel(); // end the timer to stop refresh the UI
        timer = null;
    }

}