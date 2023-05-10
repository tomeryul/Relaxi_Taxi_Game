package com.example.testing.logic;

import android.content.Context;
import android.content.res.Resources;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Toast;
import com.example.testing.logic.SignalGenerator;

import com.example.testing.R;
import com.google.android.material.imageview.ShapeableImageView;

public class GameManager {

    private final int life;
    private int wrong;
    private boolean isEnded;
    private int score;
    private int traffic_lights_Collision;


    public GameManager(int life,Context context) {
        this.life = life;
        this.wrong = 0;
        this.isEnded = false;
        this.score = 0;
        SignalGenerator.init(context);
    }

    public int getScore() {
        return score;
    }

    public boolean getIsEnded() {
        return isEnded;
    }
    public int getWrong() {
        return wrong;
    }

    public int getLife() {return life;}

    public int checkCollision(ShapeableImageView[][] main_img_traffic_lights, ShapeableImageView[]
            main_img_car, Resources resources) {
        this.traffic_lights_Collision = 0;
        if (!isEnded) {
            for(int i = 0 ; i < main_img_traffic_lights[0].length ; i++) {
                if (main_img_traffic_lights[main_img_traffic_lights.length-1][i].getVisibility() == View.VISIBLE &&
                        main_img_car[i].getVisibility() == View.VISIBLE) {
                    if(main_img_traffic_lights[main_img_traffic_lights.length - 1][i].getDrawable().getConstantState() ==
                            resources.getDrawable( R.drawable.ic_icon_coin).getConstantState())
                    {
                        this.score+=100;
                    }
                    else {
                        SignalGenerator.getInstance().toast("Collision occurred!!",Toast.LENGTH_SHORT);
                        SignalGenerator.getInstance().vibrate(500);
                        this.traffic_lights_Collision = 1;
                    }
                }
            }

            this.score+=10; // update the score

            if(this.traffic_lights_Collision==1){
                wrong += 1;
                if (wrong == 3) {
                    isEnded = true;
                }
            }

        }
        return this.traffic_lights_Collision;
    }


}
