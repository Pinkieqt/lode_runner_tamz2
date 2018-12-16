package com.loderunner.game;

import android.graphics.Canvas;

public class CoinBlock extends BaseBlock {

    public boolean isEated = false;

    public CoinBlock(int topPosX, int topPosY, int size, Canvas canvas) {
        super(topPosX, topPosY, size, canvas);
    }



}
