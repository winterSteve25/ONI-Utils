package wintersteve25.oniutils.common.items.base;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import wintersteve25.oniutils.common.items.base.interfaces.ONIIColoredName;
import wintersteve25.oniutils.common.items.base.interfaces.ONIIHasToolTip;
import wintersteve25.oniutils.common.items.base.interfaces.ONIIItem;

import javax.annotation.Nullable;
import java.util.List;

public class ONIBaseItem extends Item implements ONIIItem {
    private final String regName;
    private final boolean doRegularDataGen;

    public ONIBaseItem(Properties properties, String regName, boolean doRegularDataGen) {
        super(properties);
        this.regName = regName;
        this.doRegularDataGen = doRegularDataGen;
    }

    @Override
    public String getRegName() {
        return regName;
    }

    @Override
    public boolean doRegularDataGen() {
        return doRegularDataGen;
    }

    @Override
    public Item get() {
        return this;
    }

    @Override
    public ITextComponent getDisplayName(ItemStack stack) {
        if (this instanceof ONIIColoredName) {
            ONIIColoredName coloredName = (ONIIColoredName) this;
            return super.getDisplayName(stack).copyRaw().mergeStyle(coloredName.color());
        }
        return super.getDisplayName(stack);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (this instanceof ONIIHasToolTip) {
            if (Screen.hasShiftDown()) {
                ONIIHasToolTip toolTipBlock = (ONIIHasToolTip) this;
                tooltip.addAll(toolTipBlock.tooltip());
            } else {
                tooltip.add(new TranslationTextComponent("oniutils.tooltips.items.holdShiftInfo"));
            }
        }
    }
}
