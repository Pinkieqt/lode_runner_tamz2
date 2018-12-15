package com.loderunner.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

public class Character {
    public int charSpeed = 10;
    private int posX;
    private int posY;
    private int size;
    private Paint character;
    private Bitmap img[];
    private Bitmap currentImg;

    public Character(int posX, int posY, Bitmap[] x) {
        this.posX = posX;
        this.posY = posY;
        this.character = new Paint();
        this.character.setColor(Color.BLACK);
        this.character.setAntiAlias(false);
        this.img = x;
        this.currentImg = this.img[2];
    }

    public void getCharacterHitBox(){

    }

    public void characterGravityFall(){
        this.posY += charSpeed + 5;
    }

    public void moveCharacter(boolean isTouched, String direction, boolean canMoveDown, boolean canMoveLeft, boolean canMoveRight){
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
            if(direction == "top"){
                this.currentImg = img[2];
                this.posY -= charSpeed;
            }
        }
    }

    public void drawCharacter(Canvas canvas, int size){
        this.size = size;
        int centerCirclePos = size / 2;
        canvas.drawBitmap(Bitmap.createScaledBitmap(this.currentImg, (int)size, (int)size, true), this.posX, this.posY, null);
    }

    public int getSize(){
        return this.size;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }
}
