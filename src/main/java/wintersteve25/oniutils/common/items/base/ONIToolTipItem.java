package wintersteve25.oniutils.common.items.base;

import net.minecraft.util.text.ITextComponent;
import wintersteve25.oniutils.common.items.base.interfaces.ONIIHasToolTip;

import java.util.Arrays;
import java.util.List;

public class ONIToolTipItem extends ONIBaseItem implements ONIIHasToolTip {
    private final List<ITextComponent> tooltips;

    public ONIToolTipItem(Properties properties, String regName, boolean doRegularDataGen, ITextComponent... tooltips) {
        super(properties, regName, doRegularDataGen);
        this.tooltips = Arrays.asList(tooltips);
    }

    @Override
    public List<ITextComponent> tooltip() {
        return tooltips;
    }
}
