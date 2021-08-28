package wintersteve25.oniutils.common.items.base;

import net.minecraft.block.Block;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import wintersteve25.oniutils.common.items.base.interfaces.ONIIColoredName;
import wintersteve25.oniutils.common.items.base.interfaces.ONIIHasToolTip;

import javax.annotation.Nullable;
import java.util.List;

public class ONIBaseBlockItem extends BlockItem implements IAnimatable {
    public AnimationFactory factory = new AnimationFactory(this);
    public String controllerName = "controller";

    public ONIBaseBlockItem(Block blockIn, Properties builder) {
        super(blockIn, builder);
    }

    public <P extends BlockItem & IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController(this, controllerName, 1, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
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
