package wintersteve25.oniutils.common.contents.base;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import wintersteve25.oniutils.api.ONIIRegistryObject;
import wintersteve25.oniutils.api.functional.IPlacementCondition;
import wintersteve25.oniutils.api.functional.IToolTipCondition;
import wintersteve25.oniutils.common.utils.ONIConstants;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

public interface ONIIItem extends ONIIRegistryObject<Item> {
    Supplier<TextFormatting> getColorName();

    void setColorName(Supplier<TextFormatting> colorName);

    Supplier<List<ITextComponent>> getTooltips();

    void setTooltips(Supplier<List<ITextComponent>> tooltips);

    Supplier<IToolTipCondition> getTooltipCondition();

    void setTooltipCondition(Supplier<IToolTipCondition> condition);

    default IPlacementCondition getPlacementCondition() {
        return null;
    }

    default void setPlacementCondition(IPlacementCondition placementCondition) {
    }

    void setDoModelGen(boolean doModelGen);

    void setDoLangGen(boolean doLangGen);

    default void tooltip(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (getTooltips() != null && getTooltips().get() != null && !getTooltips().get().isEmpty()) {
            IToolTipCondition condition = getTooltipCondition().get();
            if (condition == null) {
                tooltip.addAll(getTooltips().get());
            } else {
                if (condition == IToolTipCondition.DEFAULT.get()) {
                    if (condition.canShow(stack, worldIn, tooltip, flagIn)) {
                        tooltip.addAll(getTooltips().get());
                    } else {
                        tooltip.add(new TranslationTextComponent(ONIConstants.LangKeys.HOLD_SHIFT));
                    }
                } else {
                    if (condition.canShow(stack, worldIn, tooltip, flagIn)) {
                        tooltip.addAll(getTooltips().get());
                    } else {
                        tooltip.add(condition.textWhenNotShown());
                    }
                }
            }
        }
    }
}
