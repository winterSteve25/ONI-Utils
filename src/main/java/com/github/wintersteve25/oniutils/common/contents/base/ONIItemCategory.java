package com.github.wintersteve25.oniutils.common.contents.base;

import com.github.wintersteve25.oniutils.common.utils.ONIConstants;
import net.minecraft.ChatFormatting;

public enum ONIItemCategory {
    GENERAL("", null),
    GADGETS("gadgets/", ONIConstants.TextColor.GADGETS),
    FURNITURE("furniture/", ONIConstants.TextColor.FURNITURE_CAT_COLOR),
    OXYGEN("oxygen/", ONIConstants.TextColor.OXYGEN_CAT_COLOR),
    POWER("power/", ONIConstants.TextColor.POWER_CAT_COLOR),
    TE_BOUNDED("te_bounded/", ONIConstants.TextColor.TE_BOUNDING_CAT_COLOR),
    VENTILATION("ventilation/", ONIConstants.TextColor.VENTILATION_CAT_COLOR);

    private final String pathName;
    private final ChatFormatting color;

    ONIItemCategory(String pathName, ChatFormatting color) {
        this.pathName = pathName;
        this.color = color;
    }

    public String getPathName() {
        return pathName;
    }

    public ChatFormatting getColor() {
        return color;
    }
}