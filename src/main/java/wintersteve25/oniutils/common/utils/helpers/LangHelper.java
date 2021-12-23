package wintersteve25.oniutils.common.utils.helpers;

import net.minecraft.util.text.TranslationTextComponent;

public class LangHelper {
    public static TranslationTextComponent guiTitle(String name) {
        return new TranslationTextComponent("oniutils.gui.titles." + MiscHelper.langToReg(name));
    }

    public static TranslationTextComponent itemTooltip(String name) {
        return new TranslationTextComponent("oniutils.tooltips.items." + MiscHelper.langToReg(name));
    }

    public static TranslationTextComponent modificationToolTip(String name) {
        return itemTooltip("modification." + name);
    }

    public static TranslationTextComponent curiosSlot(String name) {
        return new TranslationTextComponent("curios.identifier." + name);
    }
}
