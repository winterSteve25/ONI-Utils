package wintersteve25.oniutils.common.contents.base.interfaces.functional;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

@FunctionalInterface
public interface IToolTipCondition {
    Supplier<IToolTipCondition> DEFAULT = ()-> (stack, world, tooltip, flag) -> Screen.hasShiftDown();

    boolean canShow(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn);

    default Component textWhenNotShown() {
        return new TextComponent("");
    }
}
