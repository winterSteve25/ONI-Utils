package wintersteve25.oniutils.common.items.base.enums;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public enum EnumModifications {
    SPEED("Velocity", 3, 25);

    private final String name;
    private final int tiers;
    private final int bonusEachTier;
    private final TextFormatting color;
    private final ITextComponent[] tooltips;

    EnumModifications(String name, int tiers, int bonusEachTier) {
        this(name, tiers, bonusEachTier, TextFormatting.WHITE, new TranslationTextComponent[]{});
    }

    EnumModifications(String name, int tiers, int bonusEachTier, TextFormatting colorName, ITextComponent... tooltips) {
        this.name = name;
        this.tiers = tiers;
        this.bonusEachTier = bonusEachTier;
        this.color = colorName;
        this.tooltips = tooltips;
    }

    public String getName() {
        return name;
    }

    public int getTiers() {
        return tiers;
    }

    public int getBonusEachTier() {
        return bonusEachTier;
    }

    public TextFormatting getColor() {
        return color;
    }

    public ITextComponent[] getTooltips() {
        return tooltips;
    }
}
