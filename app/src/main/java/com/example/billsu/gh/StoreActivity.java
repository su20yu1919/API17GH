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
        Bundle bundle = getIntent().getExtras();
        money = bundle.getDouble("money");
        fear = bundle.getDouble("fear");
        battery = bundle.getDouble("battery");


        storeSong = MediaPlayer.create(StoreActivity.this, R.raw.store_sound);
        storeSong.start();

    }


    public void fearUpgrade(View v) {
        if (money>= 10){
            fear=-300;
            money=-10;
        }



    }

    public void batteryUpgrade(View v) {
        if (money>= 2){
            battery+=600;
            money=-2;
        }

    }

    public void startGame(View V) {
        storeSong.stop();


        this.finish(); //closes store, starts up game again
    }

}

