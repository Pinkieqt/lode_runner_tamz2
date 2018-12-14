package com.loderunner.game;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class GameView extends View {

    //Helping
    int pocitadlo;

    //Canvas
    private int canvasWidth;
    private int canvasHeight;

    //buttons
    private Bitmap leftBtn;
    private Bitmap rightBtn;
    private Bitmap topBtn;
    private Bitmap bottomBtn;

    //Character
    private Bitmap characterImg[] = new Bitmap[3];
    private Character character = new Character(400, 400, characterImg);

    //Map
    private Polygon pol;
    private ArrayList<WallBlock> wallBlocks = new ArrayList<>();
    private boolean isCharacterDrawed = false;

    //Background images
    private Bitmap bgImage;
    private Bitmap bgBlock;

    //Texts
    private Paint coinsText = new Paint();
    private Paint levelText = new Paint();

    //Touch check - moving bool
    private boolean isTouched = false;
    private String direction;


    //Level
    private int level[] = {
            0,1,0,0,0,0,0,0,0,0,
            0,0,0,1,1,0,0,1,1,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,1,1,1,1,1,1,0,
            0,0,0,0,0,1,0,1,0,0,
            0,1,0,0,0,0,0,0,0,0,
            0,1,1,1,1,1,0,0,1,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,2,0,0,0,0,0,
            0,1,1,1,1,1,1,1,1,0
    };



    public GameView(Context context) {
        super(context);

        bgImage = BitmapFactory.decodeResource(getResources(), R.drawable.bgimg);
        bgBlock = BitmapFactory.decodeResource(getResources(), R.drawable.blok);
        topBtn = BitmapFactory.decodeResource(getResources(), R.drawable.top_arrow);
        bottomBtn = BitmapFactory.decodeResource(getResources(), R.drawable.bottom_arrow);
        leftBtn = BitmapFactory.decodeResource(getResources(), R.drawable.left_arrow);
        rightBtn = BitmapFactory.decodeResource(getResources(), R.drawable.right_arrow);
        characterImg[0] = BitmapFactory.decodeResource(getResources(), R.drawable.char_left);
        characterImg[1] = BitmapFactory.decodeResource(getResources(), R.drawable.char_right);
        characterImg[2] = BitmapFactory.decodeResource(getResources(), R.drawable.char_climb);

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

    @SuppressLint("DrawAllocation")
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
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                switch(level[i*10 + j]){
                    case 0:
                        pol.posX += pol.cellSize;
                        break;
                    case 1:
                        //Hitboxy od zdí
                        wallBlocks.add(new WallBlock(pol.posX, pol.posY, pol.cellSize, canvas));
                        canvas.drawBitmap(Bitmap.createScaledBitmap(bgBlock, (int)pol.cellSize, (int)pol.cellSize, true), pol.posX, pol.posY, null);
                        pol.posX += pol.cellSize;
                        break;
                    case 2:
                        if(!isCharacterDrawed){
                            character = new Character(pol.posX, pol.posY, characterImg);
                            isCharacterDrawed = true;
                        }
                        character.drawCharacter(canvas, pol.cellSize - pol.cellSize / 10);
                        pol.posX += pol.cellSize;
                        break;
                }
            }
            pol.posX = 0;
            pol.posY += pol.cellSize;
        }

        //Draw infotaintment
        canvas.drawText("Coins: 0", 20, 60, coinsText);
        canvas.drawText("Level: 1", canvas.getWidth() / 2, 60, levelText);

        //Gravity check for hitbox of wallblocks
        pocitadlo += 1;
        if(pocitadlo == 15) {
            for (WallBlock tmp : wallBlocks) {
                if (isThereDownWall(tmp.getTopPosX(), tmp.getTopPosY(), tmp.getSize())) {
                    Log.i("hmm", "dotyka se");
                    break;
                }
                ;
                //if (character.posY + character.size )


            }
            pocitadlo = 0;
        }
        character.moveCharacter(isTouched, direction);

        //Draw map


    }



    public boolean isThereDownWall(int x, int y, int size){
        if(character.getPosX() >= x && character.getPosX() <= x + size && character.getPosY() + character.getSize() >= y && character.getPosY() + character.getSize() <= y + 10
                ||
           character.getPosX() + character.getSize() >= x && character.getPosX() + character.getSize() <= x + size && character.getPosY() + character.getSize() >= y && character.getPosY() + character.getSize() <= y + 10
                ){
            return true;
        }
        else {
            Log.i("levy spodni", "nedotyka se");
            return false;
        }
    }




    //On touch
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
                x = (int) event.getX();
                y = (int) event.getY();
                break;
            case MotionEvent.ACTION_UP:
                isTouched = false;
                break;
        }

        return true;
    }
}
