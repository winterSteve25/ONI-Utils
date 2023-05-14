package com.github.wintersteve25.oniutils.common.utils;

public class SlotArrangement {
    private final int pixelX;
    private final int pixelY;
    private final int indexOfItemHandler;

    public SlotArrangement(int pixelX, int pixelY) {
        this(pixelX, pixelY, 0);
    }

    public SlotArrangement(int pixelX, int pixelY, int indexOfItemHandler) {
        this.pixelX = pixelX;
        this.pixelY = pixelY;
        this.indexOfItemHandler = indexOfItemHandler;
    }

    public int getPixelX() {
        return pixelX;
    }

    public int getPixelY() {
        return pixelY;
    }

    public int getIndexOfItemHandler() {
        return indexOfItemHandler;
    }
}
