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

public class StoreActivity extends Activity {

    MediaPlayer storeSong;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_activity);

        storeSong = MediaPlayer.create(StoreActivity.this, R.raw.store_sound);

    }

    public void lanternUpgrade() {

    }
    public void fearUpgrade() {

    }

    public void batteryUpgrade() {

    }

    public void startGame() {
        this.finish(); //closes store, starts up game again
    }

}

