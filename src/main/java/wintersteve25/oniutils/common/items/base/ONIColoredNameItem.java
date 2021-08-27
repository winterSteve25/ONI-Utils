package wintersteve25.oniutils.common.items.base;

import net.minecraft.util.text.TextFormatting;
import wintersteve25.oniutils.common.items.base.interfaces.ONIIColoredName;

public class ONIColoredNameItem extends ONIBaseItem implements ONIIColoredName {
    private final TextFormatting color;

    public ONIColoredNameItem(Properties properties, String regName, boolean doRegularDataGen, TextFormatting color) {
        super(properties, regName, doRegularDataGen);
        this.color = color;
    }

    @Override
    public TextFormatting color() {
        return color;
    }
}
