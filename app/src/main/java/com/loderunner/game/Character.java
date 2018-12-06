package com.loderunner.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Character {
    private int charSpeed = 10;
    private int posX;
    private int posY;
    private Paint character;

    public Character(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
        this.character = new Paint();
        this.character.setColor(Color.BLACK);
        this.character.setAntiAlias(false);
    }

    public void drawCharacter(Canvas canvas){
        canvas.drawCircle(this.posX, this.posY, 25, this.character);
    }
}
