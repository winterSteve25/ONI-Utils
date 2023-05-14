package com.github.wintersteve25.oniutils.common.contents.base.enums;

import net.minecraft.ChatFormatting;

public enum EnumComponents {
    CIRCUIT("Circuit");

    private final String name;
    private final ChatFormatting color;

    EnumComponents(String name) {
        this(name, ChatFormatting.WHITE);
    }

    EnumComponents(String name, ChatFormatting colorName) {
        this.name = name;
        this.color = colorName;
    }

    public String getName() {
        return name;
    }

    public ChatFormatting getColor() {
        return color;
    }
}
