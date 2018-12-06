package com.loderunner.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.View;

public class GameView extends View {

    //Canvas
    private int canvasWidth;
    private int canvasHeight;

    //Character
    private Character character = new Character(40, 40);

    //Map
    private Polygon pol;

    //Background
    private Bitmap bgImage;

    //Texts
    private Paint coinsText = new Paint();
    private Paint levelText = new Paint();



    public GameView(Context context) {
        super(context);

        //score
        coinsText.setColor(Color.BLACK);
        coinsText.setTextSize(32);
        coinsText.setTypeface(Typeface.DEFAULT_BOLD);
        coinsText.setAntiAlias(true);

        //level
        levelText.setColor(Color.BLACK);
        levelText.setTextSize(32);
        levelText.setTypeface(Typeface.DEFAULT_BOLD);
        levelText.setTextAlign(Paint.Align.CENTER);
        levelText.setAntiAlias(true);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();

        pol = new Polygon(canvasWidth, canvasHeight);
        pol.drawMap(canvas);

        canvas.drawText("Coins: 0", 20, 60, coinsText);
        canvas.drawText("Level: 1", canvas.getWidth() / 2, 60, levelText);
        character.drawCharacter(canvas);
    }
}
