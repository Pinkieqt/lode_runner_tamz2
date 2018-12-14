package com.loderunner.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public abstract class BaseBlock {
    private int topPosX;
    private int topPosY;
    private int size;

    public BaseBlock(int topPosX, int topPosY, int size, Canvas canvas) {
        this.topPosX = topPosX;
        this.topPosY = topPosY;
        this.size = size;
        Paint paint = new Paint();

        canvas.drawRect(topPosX, topPosY, topPosX + size, topPosY + size, paint);
    }


    public int getTopPosX() {
        return topPosX;
    }

    public int getTopPosY() {
        return topPosY;
    }

    public int getSize() {
        return size;
    }


}
