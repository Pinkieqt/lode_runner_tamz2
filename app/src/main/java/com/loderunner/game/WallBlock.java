package com.loderunner.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class WallBlock {
    private float left;
    private float top;
    private float right;
    private float bottom;
    private Paint block;

    public WallBlock(float left, float top, float right, float bottom, boolean x) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.block = new Paint();
        if(x) this.block.setColor(Color.BLUE);
        if(!x) this.block.setColor(Color.YELLOW);
        this.block.setStyle(Paint.Style.FILL);
        this.block.setAntiAlias(false);
    }

    public void drawBlock(Canvas canvas){
        canvas.drawRect(this.left, this.top, this.right, this.bottom, this.block);
    }
}
