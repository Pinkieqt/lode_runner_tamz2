package com.loderunner.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public abstract class BaseChar {
    public int charSpeed = 10;
    public int posX;
    public int posY;
    public int size;
    public Paint character;
    public Bitmap img[];
    public Bitmap currentImg;

    public BaseChar(int posX, int posY, Bitmap[] x) {
        this.posX = posX;
        this.posY = posY;
        this.character = new Paint();
        this.character.setColor(Color.BLACK);
        this.character.setAntiAlias(false);
        this.img = x;
        this.currentImg = this.img[1];
    }

    public void drawCharacter(Canvas canvas, int size){
        this.size = size;
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
