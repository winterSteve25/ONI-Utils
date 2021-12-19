package wintersteve25.oniutils.api.functional;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

@FunctionalInterface
public interface IToolTipCondition {
    Supplier<IToolTipCondition> DEFAULT = ()-> (stack, world, tooltip, flag) -> Screen.hasShiftDown();

    boolean canShow(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn);

    default ITextComponent textWhenNotShown() {
        return new StringTextComponent("");
    }
}
