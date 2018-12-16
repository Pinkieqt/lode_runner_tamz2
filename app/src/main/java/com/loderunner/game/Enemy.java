package com.loderunner.game;

import android.graphics.Bitmap;

public class Enemy extends BaseChar {
    private int defaultX;
    private boolean movingLeft = false;
    private boolean movingRight = false;

    public Enemy(int posX, int posY, Bitmap[] x) {
        super(posX, posY, x);
        this.posY = this.posY + 5;
        defaultX = posX;
    }

    public void moveEnemy(String direction){
        this.charSpeed = 3;
        if(direction == "right"){
            if(!movingLeft) {
                this.posX += this.charSpeed;
                this.currentImg = this.img[1];
                if (this.posX >= defaultX + 200) {
                    this.movingLeft = true;
                }
            }
            if(movingLeft) {
                this.posX -= this.charSpeed;
                this.currentImg = this.img[0];
                if (this.posX <= defaultX - 200) {
                    this.movingLeft = false;
                }
            }
        }
        if(direction == "left"){
            if(movingRight) {
                this.posX += this.charSpeed;
                this.currentImg = this.img[1];
                if (this.posX >= defaultX + 200) {
                    this.movingRight = false;
                }
            }
            if(!movingRight) {
                this.posX -= this.charSpeed;
                this.currentImg = this.img[0];
                if (this.posX <= defaultX - 200) {
                    this.movingRight = true;
                }
            }
        }

    }
}
