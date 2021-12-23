package wintersteve25.oniutils.common.contents.base.enums;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import wintersteve25.oniutils.common.utils.ONIConstants;
import wintersteve25.oniutils.common.utils.helpers.LangHelper;

public enum EnumModifications {

    SPEED("Velocity", 3, 25, TextFormatting.WHITE, ONIConstants.LangKeys.MOD_TOOLTIP, LangHelper.modificationToolTip(ONIConstants.LangKeys.VELOCITY)),
    ENERGY_EFFICIENCY("Plasma Conservation", 3, 10, TextFormatting.GREEN, ONIConstants.LangKeys.MOD_TOOLTIP, LangHelper.modificationToolTip(ONIConstants.LangKeys.ENERGY)),
    GAS_EFFICIENCY("Gas Efficiency", 3, 25, TextFormatting.GRAY, ONIConstants.LangKeys.MOD_TOOLTIP, LangHelper.modificationToolTip(ONIConstants.LangKeys.GAS)),
    FLUID_EFFICIENCY("Fluid Efficiency", 3, 25, TextFormatting.AQUA, ONIConstants.LangKeys.MOD_TOOLTIP, LangHelper.modificationToolTip(ONIConstants.LangKeys.FLUID)),
    TEMPERATURE("Temperature Regulation", 5, 20, TextFormatting.DARK_RED, ONIConstants.LangKeys.MOD_TOOLTIP, LangHelper.modificationToolTip(ONIConstants.LangKeys.TEMPERATURE)),
    COMPLEXITY("Entanglement", 2, 25, TextFormatting.YELLOW, ONIConstants.LangKeys.MOD_TOOLTIP, LangHelper.modificationToolTip(ONIConstants.LangKeys.COMPLEXITY));

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
