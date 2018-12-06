package com.loderunner.game;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Polygon {
    private int width;
    private int height;
    private int polygonHorizontal = 20;
    private int polygonVertical = 35;
    private WallBlock[][] polygon = new WallBlock[polygonHorizontal][polygonVertical];

    private double paintWidth;
    private double paintHeight;
    private double paintX;
    private double paintY;



    public Polygon(int cvwidth, int cvheight) {
        this.width = cvwidth;
        this.height = cvheight;

        this.paintWidth = cvwidth / this.polygonHorizontal;
        this.paintHeight = cvheight / this.polygonVertical;
    }

    public void drawMap(Canvas canvas){
        int x = 0;
        int y = 0;
        for(int i = 0; i < 4; i++)
        {
            for(int j = 0; j < 3; j++)
            {
                if(i == 3)
                    polygon[i][j] = new WallBlock(x,y, x+50, y+50, false);

                polygon[i][j] = new WallBlock(x , y, x+50, y+50, true);
                polygon[i][j].drawBlock(canvas);
                y += 50;

            }
            x = 0;
            y += 50;
        }
    }
}
