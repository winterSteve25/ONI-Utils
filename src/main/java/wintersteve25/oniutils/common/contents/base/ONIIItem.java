package wintersteve25.oniutils.common.contents.base;

import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;
import wintersteve25.oniutils.api.functional.IPlacementCondition;
import wintersteve25.oniutils.api.functional.IToolTipCondition;
import wintersteve25.oniutils.common.utils.ONIConstants;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

public interface ONIIItem {
    default Supplier<ChatFormatting> getColorName() {
        return null;
    }

    default void setColorName(Supplier<ChatFormatting> colorName) {

    }

    default Supplier<List<Component>> getTooltips() {
        return null;
    }

    default void setTooltips(Supplier<List<Component>> tooltips) {

    }

    default Supplier<IToolTipCondition> getTooltipCondition() {
        return IToolTipCondition.DEFAULT;
    }

    default void setTooltipCondition(Supplier<IToolTipCondition> condition) {

    }

    default IPlacementCondition getPlacementCondition() {
        return null;
    }

    default void setPlacementCondition(IPlacementCondition placementCondition) {
    }

    default ItemCategory getONIItemCategory() {
        return ItemCategory.GENERAL;
    }

    default void setONIItemCategory(ItemCategory itemCategory) {
    }

    default void tooltip(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        if (getTooltips() != null && getTooltips().get() != null && !getTooltips().get().isEmpty()) {
            IToolTipCondition condition = getTooltipCondition().get();
            if (condition == null) {
                tooltip.addAll(getTooltips().get());
            } else {
                if (condition == IToolTipCondition.DEFAULT.get()) {
                    if (condition.canShow(stack, worldIn, tooltip, flagIn)) {
                        tooltip.addAll(getTooltips().get());
                    } else {
                        tooltip.add(ONIConstants.LangKeys.HOLD_SHIFT);
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

    enum ItemCategory {
        GENERAL("", null),
        GADGETS("gadgets/", ONIConstants.TextColor.GADGETS),
        FURNITURE("furniture/", ONIConstants.TextColor.FURNITURE_CAT_COLOR),
        OXYGEN("oxygen/", ONIConstants.TextColor.OXYGEN_CAT_COLOR),
        POWER("power/", ONIConstants.TextColor.POWER_CAT_COLOR),
        TE_BOUNDED("te_bounded/", ONIConstants.TextColor.TE_BOUNDING_CAT_COLOR),
        VENTILATION("ventilation/", ONIConstants.TextColor.VENTILATION_CAT_COLOR);

        private final String pathName;
        private final ChatFormatting color;

        ItemCategory(String pathName, ChatFormatting color) {
            this.pathName = pathName;
            this.color = color;
        }

        public String getPathName() {
            return pathName;
        }

        public ChatFormatting getColor() {
            return color;
        }
    }
}
