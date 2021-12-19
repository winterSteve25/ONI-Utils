package wintersteve25.oniutils.common.contents.base.enums;

import net.minecraft.util.text.TextFormatting;

public enum EnumComponents {
    CIRCUIT("Circuit");

    private final String name;
    private final TextFormatting color;

    EnumComponents(String name) {
        this(name, TextFormatting.WHITE);
    }

    EnumComponents(String name, TextFormatting colorName) {
        this.name = name;
        this.color = colorName;
    }

    public String getName() {
        return name;
    }

    public TextFormatting getColor() {
        return color;
    }
}
