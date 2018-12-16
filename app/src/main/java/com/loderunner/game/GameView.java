package com.loderunner.game;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.app.Activity;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class GameView extends View {

    //Helping
    private boolean isDead = false;
    private boolean oneTime = false;

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
    private int coinsCount = 0;
    private int pocetCoinu = 0;
    private String levelNum = "1";
    private boolean isFalling;
    private boolean canMoveDown;
    private boolean canMoveLeft;
    private boolean canMoveRight;
    private boolean canMoveHore;

    //Enemy
    private Bitmap enemyImg[] = new Bitmap[2];
    private Enemy enemy;
    private Enemy enemy2 = new Enemy(400, 400, enemyImg);
    private boolean isEnemyDrawed = false;
    private boolean isEnemy2Drawed = false;

    //Map
    private Polygon pol;
    private ArrayList<WallBlock> wallBlocks = new ArrayList<>();
    private ArrayList<LadderBlock> ladderBlocks = new ArrayList<>();
    private ArrayList<CoinBlock> coinBlocks = new ArrayList<>();
    private boolean isCharacterDrawed = false;

    //Background images
    private Bitmap bgImage;
    private Bitmap bgBlock;
    private Bitmap bgLadder;
    private Bitmap bgCoin;

    //Coin
    final MediaPlayer mPlayer;

    //Texts
    private Paint coinsText = new Paint();
    private Paint levelText = new Paint();

    //Touch check - moving bool
    private boolean isTouched = false;
    private String direction;

    private Context cont;
    private boolean areCoinsDrawed = false;

    //Level
    private int level[] = {
            0,0,0,0,0,0,0,0,0,0,
            1,1,0,1,1,0,0,1,1,1,
            0,0,5,0,4,0,0,0,0,0,
            0,0,3,1,1,1,1,1,1,1,
            0,0,3,0,0,1,0,1,0,0,
            0,0,3,0,0,5,0,0,0,0,
            0,1,1,1,1,1,3,0,1,1,
            0,0,0,0,0,0,3,0,0,0,
            1,0,0,0,2,0,3,4,0,1,
            1,1,1,1,1,1,1,1,1,1
    };



    public GameView(Context context) {
        super(context);
        cont = context;
        mPlayer = MediaPlayer.create(context, R.raw.coinsound);
        bgImage = BitmapFactory.decodeResource(getResources(), R.drawable.bgimg);
        bgBlock = BitmapFactory.decodeResource(getResources(), R.drawable.blok);
        bgLadder = BitmapFactory.decodeResource(getResources(), R.drawable.ladderwithbg);
        bgCoin = BitmapFactory.decodeResource(getResources(), R.drawable.coinwithbg);
        topBtn = BitmapFactory.decodeResource(getResources(), R.drawable.top_arrow);
        bottomBtn = BitmapFactory.decodeResource(getResources(), R.drawable.bottom_arrow);
        leftBtn = BitmapFactory.decodeResource(getResources(), R.drawable.left_arrow);
        rightBtn = BitmapFactory.decodeResource(getResources(), R.drawable.right_arrow);
        enemyImg[0] = BitmapFactory.decodeResource(getResources(), R.drawable.enemy_left);
        enemyImg[1] = BitmapFactory.decodeResource(getResources(), R.drawable.enemy_right);
        characterImg[0] = BitmapFactory.decodeResource(getResources(), R.drawable.char_left);
        characterImg[1] = BitmapFactory.decodeResource(getResources(), R.drawable.char_right);
        characterImg[2] = BitmapFactory.decodeResource(getResources(), R.drawable.char_climb);


        //score
        coinsText.setColor(Color.WHITE);
        coinsText.setTextSize(32);
        coinsText.setTypeface(Typeface.DEFAULT_BOLD);
        coinsText.setTextAlign(Paint.Align.LEFT);
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

        if(!oneTime) {
            for (int tmp : level) {
                if (tmp == 4) {
                    pocetCoinu++;

                }
            }
            oneTime = true;
        }

        //Draw map
        pol = new Polygon(canvasWidth, canvasHeight);
        int tmpPlayerX = 0;
        int tmpPlayerY = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                switch(level[i*10 + j]){
                    case 0:
                        pol.posX += pol.cellSize;
                        break;
                    case 1:
                        wallBlocks.add(new WallBlock(pol.posX, pol.posY, pol.cellSize, canvas));
                        canvas.drawBitmap(Bitmap.createScaledBitmap(bgBlock, (int)pol.cellSize, (int)pol.cellSize, true), pol.posX, pol.posY, null);
                        pol.posX += pol.cellSize;
                        break;
                    case 2:
                        if(!isCharacterDrawed){
                            character = new Character(pol.posX, pol.posY, characterImg);
                            isCharacterDrawed = true;
                            character.drawCharacter(canvas, pol.cellSize - pol.cellSize / 10);
                        }
                        pol.posX += pol.cellSize;
                        break;
                    case 3:
                        ladderBlocks.add(new LadderBlock(pol.posX, pol.posY, pol.cellSize, canvas));
                        canvas.drawBitmap(Bitmap.createScaledBitmap(bgLadder, (int)pol.cellSize, (int)pol.cellSize, true), pol.posX, pol.posY, null);
                        pol.posX += pol.cellSize;
                        break;
                    case 4:
                        if(pocetCoinu > 0) {
                            if(!areCoinsDrawed) {
                                coinBlocks.add(new CoinBlock(pol.posX, pol.posY, pol.cellSize, canvas));
                            }
                            canvas.drawBitmap(Bitmap.createScaledBitmap(bgCoin, (int) pol.cellSize, (int) pol.cellSize, true), pol.posX, pol.posY, null);
                            pol.posX += pol.cellSize;
                        }
                        break;
                    case 5:
                        if(!isEnemy2Drawed && isEnemyDrawed) {
                            enemy2 = new Enemy(pol.posX, pol.posY, enemyImg);
                            isEnemy2Drawed = true;
                            enemy2.drawCharacter(canvas, pol.cellSize - pol.cellSize / 10);
                        }
                        if(!isEnemyDrawed){
                            enemy = new Enemy(pol.posX, pol.posY, enemyImg);
                            isEnemyDrawed = true;
                            enemy.drawCharacter(canvas, pol.cellSize - pol.cellSize / 10);
                        }
                        pol.posX += pol.cellSize;
                        break;
                }
            }
            pol.posX = 0;
            pol.posY += pol.cellSize;
        }

        areCoinsDrawed = true;

        character.drawCharacter(canvas, pol.cellSize - pol.cellSize / 10);
        enemy.drawCharacter(canvas, pol.cellSize - pol.cellSize / 10);
        enemy2.drawCharacter(canvas, pol.cellSize - pol.cellSize / 10);

        //Draw infotaintment
        canvas.drawText("Coins: " + coinsCount, 20, 60, coinsText);
        canvas.drawText("Level: " + levelNum, canvas.getWidth() / 2, 60, levelText);


        isFalling = true;
        canMoveDown = true;
        canMoveLeft = true;
        canMoveRight = true;
        canMoveHore = false;

        for (WallBlock tmp : wallBlocks) {
            if (isThereDownWall(tmp.getTopPosX(), tmp.getTopPosY(), tmp.getSize())) {
                canMoveDown = false;
                isFalling = false;
                break;
            }
        }
        for (WallBlock tmp : wallBlocks) {
            if (isThereLeftWall(tmp.getTopPosX(), tmp.getTopPosY(), tmp.getSize())) {
                canMoveLeft = false;
                break;
            }
        }
        for (WallBlock tmp : wallBlocks) {
            if (isThereRightWall(tmp.getTopPosX(), tmp.getTopPosY(), tmp.getSize())) {
                canMoveRight = false;
                break;
            }
        }
        for (LadderBlock tmp : ladderBlocks) {
            if (isThereLadder(tmp.getTopPosX(), tmp.getTopPosY(), tmp.getSize())) {
                canMoveHore = true;
                isFalling = false;
            }
        }
        for (CoinBlock tmp : coinBlocks) {
            if (isThereCoin(tmp.getTopPosX(), tmp.getTopPosY(), tmp.getSize())) {
                mPlayer.start();
                if(!tmp.isEated){
                    coinsCount += 1;
                    tmp.isEated = true;
                }


                int tmpIndex = coinBlocks.indexOf(tmp);
                int helpPocitadlo = -1;
                for(int i = 0; i < level.length; i++){
                    if(level[i] == 4){
                        helpPocitadlo++;
                        if(helpPocitadlo == tmpIndex){
                            level[i] = 0;
                        }
                    }
                }
                coinBlocks.remove(tmp);


                
                break;
            }
        }

        //Enemy detection
        if(isThereEnemy(enemy.getPosX(), enemy.getPosY(), enemy.getSize())){
            if(!isDead) {
                String value = Integer.toString(coinsCount);
                Intent intent = new Intent(getContext(), GameOverActivity.class);
                intent.putExtra("score", value);
                getContext().startActivity(intent);
            }
            isDead = true;
        }
        if(isThereEnemy(enemy2.getPosX(), enemy2.getPosY(), enemy2.getSize())){
            if(!isDead) {
                String value = Integer.toString(coinsCount);
                Intent intent = new Intent(getContext(), GameOverActivity.class);
                intent.putExtra("score", value);
                getContext().startActivity(intent);
            }
            isDead = true;
        }



        //Gravity falling
        if(isFalling) character.characterGravityFall();

        //moving
        character.moveCharacter(isTouched, direction, canMoveDown, canMoveLeft, canMoveRight, canMoveHore);
        enemy.moveEnemy("left");
        enemy2.moveEnemy("right");

        //Draw map


    }



    //Detection methods

    public boolean isThereDownWall(int x, int y, int size){
        if(character.getPosX() >= x && character.getPosX() <= x + size && character.getPosY() + character.getSize() >= y && character.getPosY() + character.getSize() <= y + 10
                ||
           character.getPosX() + character.getSize() >= x && character.getPosX() + character.getSize() <= x + size && character.getPosY() + character.getSize() >= y && character.getPosY() + character.getSize() <= y + 10
                ){
            return true;
        }
        else {
            return false;
        }
    }

    public boolean isThereLeftWall(int x, int y, int size){
        if(character.getPosX() >= x + size - 10 && character.getPosX() <= x + size && character.getPosY() >= y && character.getPosY() <= y + size
                ||
           character.getPosX() >= x + size - 10 && character.getPosX() <= x + size && character.getPosY() + character.getSize() - 15 >= y && character.getPosY() - 15 + character.getSize() <= y + size
                ){
            return true;
        }
        else {
            return false;
        }
    }

    public boolean isThereRightWall(int x, int y, int size){
        if(character.getPosX() + character.getSize() >= x && character.getPosX() + character.getSize() <= x + 10 && character.getPosY() >= y && character.getPosY() <= y + size
                ||
           character.getPosX() + character.getSize() >= x && character.getPosX() + character.getSize() <= x + 10 && character.getPosY() + character.getSize() - 15 >= y && character.getPosY() - 15 + character.getSize() <= y + size
                ){
            return true;
        }
        else {
            return false;
        }
    }

    public boolean isThereLadder(int x, int y, int size){
        if(character.getPosX() + (character.getSize() / 3) >= x + (size / 3) && character.getPosX() + (character.getSize() / 3) <= x + (2 * (size / 3)) && character.getPosY() >= y - (size) && character.getPosY() + character.getSize() <= y + size + 10
                ||
           character.getPosX() + (2 * (character.getSize() / 3)) >= x + (size / 3) && character.getPosX() + (2 * (character.getSize() / 3)) <= x + (2 * (size / 3)) && character.getPosY() >= y - (size) && character.getPosY() + character.getSize() <= y + size + 10
                ){
            return true;
        }
        else {
            return false;
        }
    }

    public boolean isThereCoin(int x, int y, int size){
        if(character.getPosX() + (character.getSize() / 3) >= x + (size / 3) && character.getPosX() + (character.getSize() / 3) <= x + (2 * (size / 3)) && character.getPosY() >= y && character.getPosY() + character.getSize() <= y + size + 10
                ||
                character.getPosX() + (2 * (character.getSize() / 3)) >= x + (size / 3) && character.getPosX() + (2 * (character.getSize() / 3)) <= x + (2 * (size / 3)) && character.getPosY() >= y && character.getPosY() + character.getSize() <= y + size + 10
                ){
            return true;
        }
        else {
            return false;
        }
    }

    public boolean isThereEnemy(int x, int y, int size){
        if(character.getPosX() >= x + 30 && character.getPosX() <= x + size - 5 && character.getPosY() >= y + 15 && character.getPosY() + character.getSize() <= y + size + 15
                ||
                character.getPosX() + character.getSize() >= x + 30 && character.getPosX() + character.getSize() <= x + size - 5 && character.getPosY() + character.getSize() >= y + 15 && character.getPosY() + character.getSize() <= y + size + 15
                ){
            return true;
        }
        else {
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
                if (x > ((canvasWidth / 5) - 50) && x < ((canvasWidth / 5) - 50 + 225) && y > (canvasHeight - (canvasHeight / 10) - 50) && y < (canvasHeight - (canvasHeight / 10) - 50) + 225) {
                    isTouched = true;
                    direction = "left";
                }
                //bottom
                if(x > ((canvasWidth / 5) * 2) && x < ((canvasWidth / 5) * 2 + 225) &&  y > (canvasHeight - (canvasHeight / 10) - 50) && y < (canvasHeight - (canvasHeight / 10) - 50) + 225){
                    isTouched = true;
                    direction = "bottom";
                }
                //right
                if (x > ((canvasWidth / 5) * 3 + 50) && x < ((canvasWidth / 5) * 3 + 50 + 225) && y > (canvasHeight - (canvasHeight / 10) - 50) && y < (canvasHeight - (canvasHeight / 10) - 50) + 225) {
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
            case MotionEvent.ACTION_SCROLL:
                Log.i("hm", "hmm");
                break;
        }

        return true;
    }
}
