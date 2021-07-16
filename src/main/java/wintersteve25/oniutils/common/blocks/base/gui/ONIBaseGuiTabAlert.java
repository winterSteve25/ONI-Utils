package wintersteve25.oniutils.common.blocks.base.gui;

import net.minecraft.util.text.TranslationTextComponent;

public class ONIBaseGuiTabAlert extends ONIBaseGuiTab {
    protected TranslationTextComponent WARNING_DURABILITY;
    protected TranslationTextComponent WARNING_TEMPERATURE;
    protected TranslationTextComponent WARNING_GAS_PRESSURE;
    protected TranslationTextComponent WARNING_WRONG_GAS;
    protected TranslationTextComponent WARNING_ALL_CLEAR;

    public void init() {
        WARNING_DURABILITY = new TranslationTextComponent(ONIBaseGuiTab.WARNING_DURABILITY);
        WARNING_TEMPERATURE = new TranslationTextComponent(ONIBaseGuiTab.WARNING_TEMPERATURE);
        WARNING_GAS_PRESSURE = new TranslationTextComponent(ONIBaseGuiTab.WARNING_GAS_PRESSURE);
        WARNING_WRONG_GAS = new TranslationTextComponent(ONIBaseGuiTab.WARNING_WRONG_GAS);
        WARNING_ALL_CLEAR = new TranslationTextComponent(ONIBaseGuiTab.WARNING_ALL_CLEAR);
    }
}
