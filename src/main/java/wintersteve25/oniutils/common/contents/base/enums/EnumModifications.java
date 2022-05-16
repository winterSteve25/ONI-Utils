package wintersteve25.oniutils.common.contents.base.enums;

import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import wintersteve25.oniutils.common.utils.ONIConstants;
import wintersteve25.oniutils.common.utils.helpers.LangHelper;

public enum EnumModifications {

    SPEED(ONIConstants.LangKeys.VELOCITY, 3, 25, ChatFormatting.WHITE, ONIConstants.LangKeys.MOD_TOOLTIP, LangHelper.modificationToolTip(ONIConstants.LangKeys.VELOCITY)),
    ENERGY_EFFICIENCY(ONIConstants.LangKeys.ENERGY, 3, 10, ChatFormatting.GREEN, ONIConstants.LangKeys.MOD_TOOLTIP, LangHelper.modificationToolTip(ONIConstants.LangKeys.ENERGY)),
    GAS_EFFICIENCY(ONIConstants.LangKeys.GAS, 3, 25, ChatFormatting.GRAY, ONIConstants.LangKeys.MOD_TOOLTIP, LangHelper.modificationToolTip(ONIConstants.LangKeys.GAS)),
    FLUID_EFFICIENCY(ONIConstants.LangKeys.FLUID, 3, 25, ChatFormatting.AQUA, ONIConstants.LangKeys.MOD_TOOLTIP, LangHelper.modificationToolTip(ONIConstants.LangKeys.FLUID)),
    TEMPERATURE(ONIConstants.LangKeys.TEMPERATURE, 5, 20, ChatFormatting.DARK_RED, ONIConstants.LangKeys.MOD_TOOLTIP, LangHelper.modificationToolTip(ONIConstants.LangKeys.TEMPERATURE)),
    COMPLEXITY(ONIConstants.LangKeys.COMPLEXITY, 2, 25, ChatFormatting.YELLOW, ONIConstants.LangKeys.MOD_TOOLTIP, LangHelper.modificationToolTip(ONIConstants.LangKeys.COMPLEXITY));

    private final String name;
    private final int tiers;
    private final int bonusEachTier;
    private final ChatFormatting color;
    private final Component[] tooltips;

    EnumModifications(String name, int tiers, int bonusEachTier, ChatFormatting colorName, Component... tooltips) {
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

    public ChatFormatting getColor() {
        return color;
    }

    public Component[] getTooltips() {
        return tooltips;
    }
}
