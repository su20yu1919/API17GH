package com.example.billsu.gh;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import android.os.Handler;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.billsu.gh.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends Activity {
    ImageView Radial;
    ImageView Scared_Person;
    ImageView Halo;
    Person guy;
    ArrayList<Rect> obstacles;
    ArrayList<Ghost> ghosts = new ArrayList<Ghost>();
    ArrayList<ImageView> moneys = new ArrayList<ImageView>();
    RelativeLayout layout;
    private double stressLevel = 0;
    private double battery = 120;
    private double money;
    private boolean timerRunning = false;
    private double fearLevel = 0;
    public static Boolean ENABLE_RESTART = false;
    int GhostId;
    double level;
    Timer myTimer;
    Timer myTimer2;
    MediaPlayer backgroundMusic;
    ImageView Batt;
    ImageView Meter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Scared_Person = (ImageView) findViewById(R.id.Scared_Person);
        Scared_Person.setImageResource(
                R.drawable.thresh);
        Scared_Person.setX((float) 361.72906);
        Scared_Person.setY((float) 403.70868);
        level = -20623;

        Batt = (ImageView) findViewById(R.id.Batt);
        Meter = (ImageView) findViewById(R.id.Meter);

        //   guy = new Person((double) Scared_Person.getX(), (double) Scared_Person.getY(), (double) Scared_Person.getWidth(), (double) Scared_Person.getHeight());
        guy = new Person((double) Scared_Person.getX(), (double) Scared_Person.getY(), 50, 50);
        // Log.i("image starts at", " " + Scared_Person.getX()+" "+ Scared_Person.getY());


        Radial = (ImageView) findViewById(R.id.lanternradial);
        Radial.setX(Scared_Person.getX() + 50);
        Radial.setY(Scared_Person.getY() + 70);
        layout = (RelativeLayout) findViewById(R.id.layout);

        // Log.i("image starts at", " " + Scared_Person.getX()+" "+ Scared_Person.getY());

        backgroundMusic = MediaPlayer.create(MainActivity.this, R.raw.mansion_music);
        backgroundMusic.start();

//        ImageView ghost = new ImageView(this);
//        ghost.setImageResource(R.drawable.ghost1);
//        ghost.setVisibility(View.VISIBLE);
//
//        layout.addView(ghost)
        Halo = (ImageView) findViewById(R.id.lanternkill);

        final ImageButton Lantern = (ImageButton) findViewById(R.id.Lantern);
        Lantern.setImageResource(R.drawable.lantern_off);

        Lantern.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Halo = (ImageView) findViewById(R.id.lanternkill);
                Halo.setX(Scared_Person.getX() - 90);
                Halo.setY(Scared_Person.getY() - 370);
                if (Halo.getVisibility() == View.VISIBLE) {
                    Halo.setVisibility(View.INVISIBLE);
                    Lantern.setImageResource(R.drawable.lantern_off);
                } else {
                    Halo.setVisibility(View.VISIBLE);
                    Lantern.setImageResource(R.drawable.lantern_on);
                }
            }
        });


        for (int i = 0; i < 2; i++) {
            ImageView ghost = new ImageView(this);
            ghost.setImageResource(R.drawable.ghost_tiny);
            ghost.setVisibility(View.VISIBLE);

            layout.addView(ghost);


            Ghost casper = new FloatingGhost((double) 100 + (50 * i), (double) 200 + (100 * i), ghost, i);
            ghosts.add(casper);
        }

        for (int i = 2; i < 4; i++) {
            ImageView ghost = new ImageView(this);
            ghost.setImageResource(R.drawable.ghost_tiny);
            ghost.setVisibility(View.VISIBLE);

            layout.addView(ghost);


            Ghost casper = new StalkingGhost(this.guy, (double) 210 + (50 * i), (double) 400 + (100 * i), ghost, i);
            ghosts.add(casper);
        }
        //Difficulty Timer
        myTimer = new Timer();

        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                TimerMethod();
            }

        }, 30000, (int) (Math.pow(1.0005, -1 * level) + 1000));

        //Movement Timer
        myTimer2 = new Timer();
        myTimer2.schedule(new TimerTask() {
            @Override
            public void run() {
                TimerMethod2();
            }

        }, 3000, 10);


    }

    private void TimerMethod() {
        this.runOnUiThread(Timer_Tick);

    }

    private Runnable Timer_Tick = new Runnable() {
        @Override
        public void run() {
            Random rand = new Random();

            for (int i = 0; i < 2; i++) {
                ImageView ghost = new ImageView(MainActivity.this);
                ghost.setImageResource(R.drawable.ghost_tiny);
                ghost.setVisibility(View.VISIBLE);

                layout.addView(ghost);


                Ghost casper = new FloatingGhost((double) rand.nextInt(500), (double) rand.nextInt(500), ghost, GhostId);
                GhostId += 1;
                ghosts.add(casper);
            }

            for (int i = 0; i < 2; i++) {
                ImageView ghost = new ImageView(MainActivity.this);
                ghost.setImageResource(R.drawable.ghost_tiny);
                ghost.setVisibility(View.VISIBLE);

                layout.addView(ghost);


                Ghost casper = new StalkingGhost(MainActivity.this.guy, (double) rand.nextInt(500), (double) rand.nextInt(500), ghost, GhostId);
                GhostId += 1;
                ghosts.add(casper);
            }

            //CHANGE THIS TO MODIFY SPAWN RATE, NOT OTHER STUFF
            level += 1000;

        }
    };

    private void TimerMethod2() {
        this.runOnUiThread(Timer_Tick2);

    }

    private Runnable Timer_Tick2 = new Runnable() {
        @Override
        public void run() {
            for (Ghost g : ghosts) {
                g.move();
                for (Ghost c : ghosts) {
                    if (g.getiD() != c.getiD()) {
                        g.getHitbox().intersect(c.getHitbox());
                    }

                }
            }

        }
    };






 /*   public void onStart(){
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                Log.d("run","yo we are in that run thing");
            }
        });
    } */


    public boolean onTouchEvent(MotionEvent event) {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (fearLevel > 100) {
            Intent myIntent = new Intent(this, EndGame.class);
            startActivity(myIntent);
            Meter.setImageResource(R.drawable.m_9_game_over);
        } else if (fearLevel > 90) {
            Meter.setImageResource(R.drawable.m_8);
        } else if (fearLevel > 80) {
            Meter.setImageResource(R.drawable.m_7);
        } else if (fearLevel > 70) {
            Meter.setImageResource(R.drawable.m_6);
        } else if (fearLevel > 60) {
            Meter.setImageResource(R.drawable.m_5);
        } else if (fearLevel > 50) {
            Meter.setImageResource(R.drawable.m_4);
        } else if (fearLevel > 40) {
            Meter.setImageResource(R.drawable.m_3);
        } else if (fearLevel > 30) {
            Meter.setImageResource(R.drawable.m_2);
        } else if (fearLevel > 20) {
            Meter.setImageResource(R.drawable.m_1);
        } else {
            Meter.setImageResource(R.drawable.m_0);
        }


        if (event.getAction() == MotionEvent.ACTION_UP == false) {
            guy.setTarget((double) event.getX(), (double) event.getY());
            guy.move();
            for (Ghost g : ghosts) {
                g.move();

            }
        }


        Scared_Person.setX((float) guy.getX());
        Scared_Person.setY((float) guy.getY() - 147);
        Radial.setX(Scared_Person.getX() - 220);
        Radial.setY(Scared_Person.getY() - 200);
        Halo.setX(Scared_Person.getX() - 95);
        Halo.setY(Scared_Person.getY() - 75);

        new CountDownTimer(1000, 100) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                battery = battery - 2;
                timerRunning = false;
                for (Iterator<Ghost> it = ghosts.iterator(); it.hasNext(); ) {
                    Ghost g = it.next();
                    double distance = Math.hypot(g.getX(), guy.getX());
                    if (distance > 500 && distance < 1000) {
                        fearLevel += 1;
                    } else if (distance > 200) {
                        fearLevel += 2;
                    } else if (distance > 100) {
                        fearLevel += 5;
                    }


                }
            }

        }.start();
        if (Halo.getVisibility() == View.VISIBLE && timerRunning == false) {
            timerRunning = true;
            /*    long startTime = System.currentTimeMillis();
                long timeDiff = startTime;
                while(timeDiff - startTime < 1000){
                    timeDiff = System.currentTimeMillis();
                    battery = battery - 1;
                } */
        }

        if (battery > 100) {
            Batt.setImageResource(R.drawable.b_6);
        } else if (battery > 80) {
            Batt.setImageResource(R.drawable.b_5);
        } else if (battery > 60) {
            Batt.setImageResource(R.drawable.b_4);
        } else if (battery > 40) {
            Batt.setImageResource(R.drawable.b_3);
        } else if (battery > 20) {
            Batt.setImageResource(R.drawable.b_2);
        } else if (battery > 0) {
            Batt.setImageResource(R.drawable.b_1);
        } else {
            Halo.setVisibility(View.INVISIBLE);
            Halo.setImageResource(R.drawable.lantern_off);
            Batt.setImageResource(R.drawable.b_0_0);


        }


        Log.i("battery is", "battery: " + battery);
        Log.i("Fear level is", "fear Level: " + fearLevel);
        for (Iterator<Ghost> it = ghosts.iterator(); it.hasNext(); ) {
            Ghost g = it.next();

            if (guy.getHitbox().intersect(g.getHitbox())
                    && Halo.getVisibility() == View.VISIBLE) {
                g.die();

                ImageView Money = new ImageView(this);
                Money.setImageResource(R.drawable.ectoplasm_small_trans);
                Money.setX((float) g.getX());
                Money.setY((float) g.getY());
                it.remove();
                moneys.add(Money);


                layout.addView(Money);


            }

            for (int i = 0; i < moneys.size(); i++) {
                ImageView m = moneys.get(i);
                if (guy.getHitbox().contains((int) m.getX(), (int) m.getY())) {
                    m.setVisibility(View.INVISIBLE);
                    money++;
                    moneys.remove(m);
                }
            }


            g.move();
        }

        TextView scoreBoard = (TextView) findViewById(R.id.textView);
        scoreBoard.setText("You Have: " + money + " Ectoplasms");
        scoreBoard.setTextColor(-65536);


        Scared_Person.setX((float) guy.getX());
        Scared_Person.setY((float) guy.getY() - 147);


        return true;

    }

    public void storeButtonClicked(View v) {
        Log.i("store button:", "intent step");
        Intent intent = new Intent(this, StoreActivity.class);
        Log.i("store button: ", "start store");
        startActivity(intent);
    }

    public void onPause() {
                super.onPause();
                backgroundMusic.pause();
            }
    public void onResume() {
        super.onResume();
        backgroundMusic.start();
    }
}