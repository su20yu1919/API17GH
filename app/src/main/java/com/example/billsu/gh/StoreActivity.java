package com.example.billsu.gh;

/**
 * Created by billsu on 4/17/15.
 */
import android.app.Activity;


/**
 * Created by billsu on 4/17/15.
 */
import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

public class StoreActivity extends Activity {

    MediaPlayer storeSong;
    double money;
    double battery;
    double fear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_activity);
       


        storeSong = MediaPlayer.create(StoreActivity.this, R.raw.store_sound);
        storeSong.start();

    }


    public void fearUpgrade(View v) {
        if (MainActivity.money>= 10 && MainActivity.fearLevel>0){
                    MainActivity.fearLevel-=300;
                       MainActivity.money-=10;
        }



    }

    public void batteryUpgrade(View v) {
        if (money>= 2) {
            if (MainActivity.money >= 2 && MainActivity.battery < 600) {
                MainActivity.battery += 600;
                MainActivity.money -= 2;
            }

        }}

    public void startGame(View V) {
        storeSong.stop();


        this.finish(); //closes store, starts up game again
    }

}

