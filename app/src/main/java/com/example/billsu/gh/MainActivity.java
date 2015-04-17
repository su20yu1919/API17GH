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




public class MainActivity extends Activity {

    ImageView Scared_Person;
    Person guy;
    ArrayList<Rect> obstacles;
    ArrayList<Ghost> ghost;
    RelativeLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Scared_Person = (ImageView) findViewById(R.id.Scared_Person);
        Scared_Person.setImageResource(
                R.drawable.thresh);
        Scared_Person.setX((float)361.72906);
        Scared_Person.setY((float)403.70868);

        guy = new Person((double) Scared_Person.getX(), (double) Scared_Person.getY(), (double) Scared_Person.getWidth(), (double) Scared_Person.getHeight());

        Log.i("image starts at", " " + Scared_Person.getX()+" "+ Scared_Person.getY());

        layout = (RelativeLayout) findViewById(R.id.layout);
        ImageView ghost = new ImageView(this);
        ghost.setImageResource(R.drawable.ufo);
        ghost.setVisibility(View.VISIBLE);

        layout.addView(ghost);
        ImageView radial = (ImageView)findViewById(R.id.lanternradial);
        radial.setX(Scared_Person.getX());
        radial.setY(Scared_Person.getY());
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

    public void onButtonClick(View v) {

    }

  public boolean onTouchEvent(MotionEvent event){
      try {
          Thread.sleep(50);
      } catch (InterruptedException e){
          e.printStackTrace();
      }

      if (event.getAction() == MotionEvent.ACTION_UP == false){
          guy.setTarget((double) event.getX(), (double) event.getY());
          guy.move();
          Log.i("Guy:", "Guy is now at " + guy.getX() + ",  " + guy.getY());

          Scared_Person.setX((float) guy.getX());
          Scared_Person.setY((float) guy.getY()-147);
          Log.i("image:", "now at "+ Scared_Person.getX() +", " + Scared_Person.getY());
          int[] location=new int[2];
          Scared_Person.getLocationOnScreen(location);
          Log.i("image position now at", " " + location[0]+ " "+ location[1]);

      }

      return true;
  }


}
