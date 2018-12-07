package com.loderunner.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class WallBlock {
    private float left;
    private float top;
    private Bitmap block;

    public WallBlock(float left, float top) {
        this.left = left;
        this.top = top;
        //this.block = BitmapFactory.decodeResource()
    }

    public void drawBlock(Canvas canvas){
        canvas.drawBitmap(this.block, this.left, this.top, null);
    }
}
