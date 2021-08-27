package wintersteve25.oniutils.common.items.base;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import wintersteve25.oniutils.common.items.base.interfaces.ONIIColoredName;
import wintersteve25.oniutils.common.items.base.interfaces.ONIIHasToolTip;

import java.util.Arrays;
import java.util.List;

public class ONIToolTipColorNameItem extends ONIBaseItem implements ONIIHasToolTip, ONIIColoredName{

    private final TextFormatting color;
    private final List<ITextComponent> tooltips;

    public ONIToolTipColorNameItem(Properties properties, String regName, boolean doRegularDataGen, TextFormatting color, ITextComponent... tooltips) {
        super(properties, regName, doRegularDataGen);
        this.color = color;
        this.tooltips = Arrays.asList(tooltips);
    }

    @Override
    public TextFormatting color() {
        return color;
    }

    @Override
    public List<ITextComponent> tooltip() {
        return tooltips;
    }
}
