package com.example.billsu.gh;
/**
 * Created by AlysonI on 4/12/2015.
 * this fella floats around randomly
 */
import java.util.Random;
public class FloatingGhost extends Ghost {


    public FloatingGhost(double startX, double startY) {
        super(startX, startY);

    }

    public void move() {
        int c = 25; //this is subject to change, just how many units the ghot will move
        Random rand = new Random(4);
        int n = rand.nextInt(); // chooses a random direction to move in
        if (n == 0) {
            this.tX = (this.x + c);
        }
        if (n == 1) {
            this.tX = (this.x - c);
        }
        if (n == 2) {
            this.tY = (this.y + c);
        }
        if (n == 3) {
            this.tY = (this.y - c);
        }
        //this I stole from person class so if it doesn't work blame Luke
        double deltaX = this.tX - this.x;
        double deltaY = this.tY - this.y;
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        double xSpeed = 0;
        double ySpeed = 0;
        if (distance > 0) {
            xSpeed = this.speed * (deltaX / distance);
            ySpeed = this.speed * (deltaY / distance);
        }
        // actually move now
        this.x = this.x + xSpeed; //* time;
        this.y = this.y + ySpeed; //* time;
        this.updateHitbox(); //so the hitBox follows the ghost
        this.updateImage();
    }
}

