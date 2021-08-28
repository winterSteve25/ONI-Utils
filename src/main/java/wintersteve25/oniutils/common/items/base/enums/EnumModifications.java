package wintersteve25.oniutils.common.items.base.enums;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public enum EnumModifications {

    SPEED("Velocity", 3, 25, TextFormatting.WHITE, new TranslationTextComponent("oniutils.tooltips.items.modification"), new TranslationTextComponent("oniutils.tooltips.items.modification.velocity")),
    ENERGY_EFFICIENCY("Plasma Conservation", 3, 10, TextFormatting.GREEN, new TranslationTextComponent("oniutils.tooltips.items.modification"), new TranslationTextComponent("oniutils.tooltips.items.modification.energy")),
    GAS_EFFICIENCY("Gas Efficiency", 3, 25, TextFormatting.GRAY, new TranslationTextComponent("oniutils.tooltips.items.modification"), new TranslationTextComponent("oniutils.tooltips.items.modification.gas")),
    FLUID_EFFICIENCY("Fluid Efficiency", 3, 25, TextFormatting.AQUA, new TranslationTextComponent("oniutils.tooltips.items.modification"), new TranslationTextComponent("oniutils.tooltips.items.modification.fluid")),
    TEMPERATURE("Temperature Regulation", 5, 20, TextFormatting.DARK_RED, new TranslationTextComponent("oniutils.tooltips.items.modification"), new TranslationTextComponent("oniutils.tooltips.items.modification.temperature")),
    COMPLEXITY("Entanglement", 2, 25, TextFormatting.YELLOW, new TranslationTextComponent("oniutils.tooltips.items.modification"), new TranslationTextComponent("oniutils.tooltips.items.modification.complexity"));

    private final String name;
    private final int tiers;
    private final int bonusEachTier;
    private final TextFormatting color;
    private final ITextComponent[] tooltips;

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
