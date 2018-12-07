package com.loderunner.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View {

    //Canvas
    private int canvasWidth;
    private int canvasHeight;

    //buttons
    private Bitmap leftBtn;
    private Bitmap rightBtn;
    private Bitmap topBtn;
    private Bitmap bottomBtn;

    //Character
    private Character character = new Character(70, 80);

    //Map
    private Polygon pol;

    //Background images
    private Bitmap bgImage;
    private Bitmap bgBlock;

    //Texts
    private Paint coinsText = new Paint();
    private Paint levelText = new Paint();

    //Touch check - moving bool
    private boolean isTouched = false;
    private String direction;



    public GameView(Context context) {
        super(context);

        bgImage = BitmapFactory.decodeResource(getResources(), R.drawable.bgimg);
        bgBlock = BitmapFactory.decodeResource(getResources(), R.drawable.blok);
        topBtn = BitmapFactory.decodeResource(getResources(), R.drawable.top_arrow);
        bottomBtn = BitmapFactory.decodeResource(getResources(), R.drawable.bottom_arrow);
        leftBtn = BitmapFactory.decodeResource(getResources(), R.drawable.left_arrow);
        rightBtn = BitmapFactory.decodeResource(getResources(), R.drawable.right_arrow);

        //score
        coinsText.setColor(Color.WHITE);
        coinsText.setTextSize(32);
        coinsText.setTypeface(Typeface.DEFAULT_BOLD);
        coinsText.setAntiAlias(true);

        //level
        levelText.setColor(Color.WHITE);
        levelText.setTextSize(32);
        levelText.setTypeface(Typeface.DEFAULT_BOLD);
        levelText.setTextAlign(Paint.Align.CENTER);
        levelText.setAntiAlias(true);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();

        //Draw bg
        canvas.drawBitmap(bgImage, 0, 0, null);

        //Draw buttons
        canvas.drawBitmap(leftBtn, (canvasWidth / 5) - 50, canvasHeight - (canvasHeight / 10) - 50, null);
        canvas.drawBitmap(bottomBtn, (canvasWidth / 5) * 2, canvasHeight - (canvasHeight / 10) - 50, null);
        canvas.drawBitmap(rightBtn, (canvasWidth / 5) * 3 + 50, canvasHeight - (canvasHeight / 10) - 50, null);
        canvas.drawBitmap(topBtn, (canvasWidth / 5) * 2, canvasHeight - ((canvasHeight / 10) * 2) - 130, null);

        //Draw map
        pol = new Polygon(canvasWidth, canvasHeight);
        pol.drawMap(canvas);

        //Draw infotaintment
        canvas.drawText("Coins: 0", 20, 60, coinsText);
        canvas.drawText("Level: 1", canvas.getWidth() / 2, 60, levelText);

        //Draw character
        int minCharY = character.getHeight();
        int maxCharY = 730;
        character.drawCharacter(canvas);
        character.moveCharacter(isTouched, direction);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //left
                if(x > ((canvasWidth / 5) - 50) && x < ((canvasWidth / 5) - 50 + 225) &&  y > (canvasHeight - (canvasHeight / 10) - 50) && y < (canvasHeight - (canvasHeight / 10) - 50) + 225){
                    isTouched = true;
                    direction = "left";
                }
                //bottom
                if(x > ((canvasWidth / 5) * 2) && x < ((canvasWidth / 5) * 2 + 225) &&  y > (canvasHeight - (canvasHeight / 10) - 50) && y < (canvasHeight - (canvasHeight / 10) - 50) + 225){
                    isTouched = true;
                    direction = "bottom";
                }
                //right
                if(x > ((canvasWidth / 5) * 3 + 50) && x < ((canvasWidth / 5) * 3 + 50 + 225) &&  y > (canvasHeight - (canvasHeight / 10) - 50) && y < (canvasHeight - (canvasHeight / 10) - 50) + 225){
                    isTouched = true;
                    direction = "right";
                }
                //top
                if(x > ((canvasWidth / 5) * 2) && x < ((canvasWidth / 5) * 2 + 225) &&  y > ((canvasHeight - (canvasHeight / 10) * 2) - 130) && y < ((canvasHeight - (canvasHeight / 10) * 2) - 130) + 225){
                    isTouched = true;
                    direction = "top";
                }
                break;
            case MotionEvent.ACTION_MOVE:
                //Log.i("TAG", "moving: (" + x + ", " + y + ")");
                break;
            case MotionEvent.ACTION_UP:
                isTouched = false;
                break;
        }

        return true;
    }
}
