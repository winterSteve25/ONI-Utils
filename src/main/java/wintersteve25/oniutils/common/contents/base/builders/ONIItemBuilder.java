package wintersteve25.oniutils.common.contents.base.builders;

import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraftforge.common.util.Lazy;
import wintersteve25.oniutils.api.functional.IPlacementCondition;
import wintersteve25.oniutils.api.functional.IToolTipCondition;
import wintersteve25.oniutils.common.contents.base.ONIBaseItemBlock;
import wintersteve25.oniutils.common.contents.base.ONIIItem;
import wintersteve25.oniutils.common.utils.helpers.LangHelper;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class ONIItemBuilder<T extends ONIIItem> {
    private final String regName;
    private final Supplier<T> item;

    private Supplier<IToolTipCondition> toolTipCondition = IToolTipCondition.DEFAULT;
    private Supplier<List<Component>> tooltips;
    private Supplier<ChatFormatting> color;
    private IPlacementCondition placementCondition;
    private ONIIItem.ItemCategory category = ONIIItem.ItemCategory.GENERAL;

    public ONIItemBuilder(String regName, Supplier<T> item) {
        this.regName = regName;
        this.item = item;
    }

    public ONIItemBuilder<T> shiftToolTip() {
        toolTipCondition = IToolTipCondition.DEFAULT;
        return this;
    }

    public ONIItemBuilder<T> tooltipCondition(Supplier<IToolTipCondition> condition) {
        toolTipCondition = condition;
        return this;
    }

    public ONIItemBuilder<T> tooltip(Component... tooltips) {
        this.tooltips = () -> Arrays.asList(tooltips);
        return this;
    }

    public ONIItemBuilder<T> defaultTooltip() {
        tooltips = () -> List.of(LangHelper.itemTooltip(regName));
        return this;
    }

    public ONIItemBuilder<T> coloredName(Supplier<ChatFormatting> color) {
        this.color = color;
        return this;
    }

    public ONIItemBuilder<T> placementCondition(IPlacementCondition placementCondition) {
        this.placementCondition = placementCondition;
        return this;
    }

    public ONIItemBuilder<T> setCategory(ONIIItem.ItemCategory category) {
        this.category = category;
        return this;
    }

    public Lazy<T> build() {
        return Lazy.of(() -> {
            T i = item.get();
            i.setTooltipCondition(toolTipCondition);
            i.setTooltips(tooltips);
            i.setColorName(color);
            i.setPlacementCondition(placementCondition);
            i.setONIItemCategory(category);
            return i;
        });
    }

    public String getRegName() {
        return regName;
    }
}