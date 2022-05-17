package wintersteve25.oniutils.common.utils.helpers;

import net.minecraft.network.chat.TranslatableComponent;

public class LangHelper {
    public static TranslatableComponent guiTitle(String name) {
        return new TranslatableComponent("oniutils.gui.titles." + MiscHelper.langToReg(name));
    }

    public static TranslatableComponent itemTooltip(String name) {
        return new TranslatableComponent("oniutils.tooltips.items." + MiscHelper.langToReg(name));
    }

    public static TranslatableComponent modificationToolTip(String name) {
        return itemTooltip("modification." + name);
    }

    public static TranslatableComponent germ(String name) {
        return new TranslatableComponent("germ.oniutils." + name);
    }

    public static TranslatableComponent curiosSlot(String name) {
        return new TranslatableComponent("curios.identifier." + name);
    }
}
