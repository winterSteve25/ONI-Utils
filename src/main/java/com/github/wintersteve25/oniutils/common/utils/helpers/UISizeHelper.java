package com.github.wintersteve25.oniutils.common.utils.helpers;

import com.github.wintersteve25.tau.Tau;
import com.github.wintersteve25.tau.utils.Size;
import com.github.wintersteve25.tau.utils.Vector2i;

public class UISizeHelper {
    public static Size mixed(float percentageX, int height) {
        if (!(percentageX < 0.0F) && !(percentageX > 1.0F)) {
            return (size) -> new Vector2i(Math.round((float)size.x * percentageX), height);
        } else {
            Tau.LOGGER.error("Size percentage can not be less than 0 or greater than 1");
            return Size.ZERO;
        }
    }
    
    public static Size mixed(int width, float percentageY) {
        if (!(percentageY < 0.0f) && !(percentageY > 1.0f)) {
            return (size) -> new Vector2i(width, Math.round((float)size.y * percentageY));
        } else {
            Tau.LOGGER.error("Size percentage can not be less than 0 or greater than 1");
            return Size.ZERO;
        }
    }
    
    public static Size squareWithY(float percentageY) {
        if (!(percentageY < 0.0f) && !(percentageY > 1.0f)) {
            return (size) -> {
                int length = Math.round((float)size.y * percentageY);
                return new Vector2i(length, length);
            };
        } else {
            Tau.LOGGER.error("Size percentage can not be less than 0 or greater than 1");
            return Size.ZERO;
        }
    }
    
    public static Size squareWithX(float percentageX) {
        if (!(percentageX < 0.0f) && !(percentageX > 1.0f)) {
            return (size) -> {
                int length = Math.round((float)size.x * percentageX);
                return new Vector2i(length, length);
            };
        } else {
            Tau.LOGGER.error("Size percentage can not be less than 0 or greater than 1");
            return Size.ZERO;
        }
    }
}
