package com.loderunner.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Character {
    public int charSpeed = 10;
    public int posX;
    public int posY;
    private int size = 40;
    private Paint character;

    public Character(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
        this.character = new Paint();
        this.character.setColor(Color.BLACK);
        this.character.setAntiAlias(false);
    }

    public void moveCharacter(boolean isTouched, String direction){
        if(isTouched){
            if(direction == "left"){
                //github prostě nefunguje?
                this.posX -= charSpeed;
            }
            if(direction == "bottom"){
                this.posY += charSpeed;
            }
            if(direction == "right"){
                this.posX += charSpeed;
            }
            if(direction == "top"){
                this.posY -= charSpeed;
            }
        }
    }

    public void drawCharacter(Canvas canvas){
        canvas.drawCircle(this.posX, this.posY, this.size, this.character);
    }

    public int getHeight(){
        return this.size;
    }
}
