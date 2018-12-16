package com.loderunner.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

public class Character extends BaseChar{
    public Character(int posX, int posY, Bitmap[] x) {
        super(posX, posY, x);
    }

    public void characterGravityFall(){
        this.posY += charSpeed + 5;
    }

    public void moveCharacter(boolean isTouched, String direction, boolean canMoveDown, boolean canMoveLeft, boolean canMoveRight, boolean canMoveHore){
        if(isTouched) {
            if (canMoveLeft){
                if (direction == "left") {
                    //github prostě nefunguje?
                    this.currentImg = img[0];
                    this.posX -= charSpeed;
                }
            }
            if(canMoveDown) {
                if (direction == "bottom") {
                    this.posY += charSpeed;
                }
            }
            if(canMoveRight) {
                if (direction == "right") {
                    this.currentImg = img[1];
                    this.posX += charSpeed;
                }
            }
            if(canMoveHore) {
                if (direction == "top") {
                    this.currentImg = img[2];
                    this.posY -= charSpeed;
                }
            }
        }
    }
}
