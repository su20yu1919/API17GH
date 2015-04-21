package com.example.billsu.gh;
import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import android.os.Handler;
import android.widget.RelativeLayout;

import com.example.billsu.gh.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;


public class MainActivity extends Activity {
    ImageView Radial;
    ImageView Scared_Person;
    ImageView Halo;
    Person guy;
    ArrayList<Rect> obstacles;
    ArrayList<Ghost> ghosts = new ArrayList<Ghost>();
    RelativeLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Scared_Person = (ImageView) findViewById(R.id.Scared_Person);
        Scared_Person.setImageResource(
                R.drawable.thresh);
        Scared_Person.setX((float) 361.72906);
        Scared_Person.setY((float) 403.70868);

        guy = new Person((double) Scared_Person.getX(), (double) Scared_Person.getY(), (double) Scared_Person.getWidth(), (double) Scared_Person.getHeight());

        // Log.i("image starts at", " " + Scared_Person.getX()+" "+ Scared_Person.getY());


        Radial = (ImageView) findViewById(R.id.lanternradial);
        Radial.setX(Scared_Person.getX() + 70);
        Radial.setY(Scared_Person.getY() + 150);
        layout = (RelativeLayout) findViewById(R.id.layout);
        // Log.i("image starts at", " " + Scared_Person.getX()+" "+ Scared_Person.getY());


        ImageView radial = (ImageView) findViewById(R.id.lanternradial);
        radial.setX(Scared_Person.getX());
        radial.setY(Scared_Person.getY());
        layout = (RelativeLayout) findViewById(R.id.layout);
//
//        ImageView ghost = new ImageView(this);
//        ghost.setImageResource(R.drawable.ghost1);
//        ghost.setVisibility(View.VISIBLE);
//
//        layout.addView(ghost)
        Halo = (ImageView) findViewById(R.id.lanternkill);
        Random rand = new Random(500);

        final ImageButton Lantern = (ImageButton) findViewById(R.id.Lantern);
        Lantern.setImageResource(R.drawable.lantern_off);

        Lantern.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Halo = (ImageView) findViewById(R.id.lanternkill);
                Halo.setX(Scared_Person.getX() - 210);
                Halo.setY(Scared_Person.getY() - 180);
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
    }


 /*   public void onStart(){
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                Log.d("run","yo we are in that run thing");
            }
        });
    } */



  public boolean onTouchEvent(MotionEvent event){
      try {
          Thread.sleep(50);
      } catch (InterruptedException e){
          e.printStackTrace();
      }

      if (event.getAction() == MotionEvent.ACTION_UP == false){
          guy.setTarget((double) event.getX(), (double) event.getY());
          guy.move();
          for ( Ghost g : ghosts ){
                g.move();
                for(Ghost c: ghosts){
                    if(g.getiD() != c.getiD()){
                        g.collides(c);
                    }

                }
          }

          Scared_Person.setX((float) guy.getX());
          Scared_Person.setY((float) guy.getY()-147);
          Radial.setX((float)Scared_Person.getX()-320);
          Radial.setY((float)Scared_Person.getY()-440);
          Halo.setX(Scared_Person.getX()-200);
          Halo.setY(Scared_Person.getY()-200);


          for ( Ghost g: this.ghosts ){
              g.move();
          }


          Scared_Person.setX((float) guy.getX());
          Scared_Person.setY((float) guy.getY()-147);
//          Log.i("image:", "now at "+ Scared_Person.getX() +", " + Scared_Person.getY());
//          int[] location=new int[2];
//          Scared_Person.getLocationOnScreen(location);
//          Log.i("image position now at", " " + location[0]+ " "+ location[1]);

      }

      return true;
  }


}
