package com.loderunner.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Polygon {
    public int posX = 0;
    public int posY;
    private int width;
    private int height;
    private int cellCount = 10;
    private String[][] polygon = new String[cellCount][cellCount];

    public int cellSize;
    private float paintX;
    private float paintY;

    public Polygon(int cvwidth, int cvheight) {
        //Velikost screenu
        this.width = cvwidth;
        this.height = cvwidth;

        this.posY = cvheight / 25;

        this.cellSize = this.width / this.cellCount;
    }

    public void drawMap(Canvas canvas){
        int x = 0;
        int y = 0;
        for(int i = 0; i < 4; i++)
        {
            for(int j = 0; j < 3; j++)
            {
                //if(i == 3)
                 //   polygon[i][j] = new WallBlock(x,y, x+50, y+50, false);

                //polygon[i][j] = new WallBlock(x , y, x+50, y+50, true);
                //polygon[i][j].drawBlock(canvas);
                //y += 50;

            }
            x = 0;
            y += 50;
        }
    }


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
