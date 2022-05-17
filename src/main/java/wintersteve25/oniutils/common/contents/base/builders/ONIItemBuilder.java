package wintersteve25.oniutils.common.contents.base.builders;

import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import wintersteve25.oniutils.common.contents.base.blocks.ONIBaseBlock;
import wintersteve25.oniutils.common.contents.base.interfaces.functional.IPlacementCondition;
import wintersteve25.oniutils.common.contents.base.interfaces.functional.IToolTipCondition;
import wintersteve25.oniutils.common.contents.base.items.ONIIItem;
import wintersteve25.oniutils.common.utils.helpers.LangHelper;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class ONIItemBuilder<T extends ONIIItem> {
    private final String regName;
    private final Function<ONIBaseBlock, T> item;

    private Supplier<IToolTipCondition> toolTipCondition = IToolTipCondition.DEFAULT;
    private Supplier<List<Component>> tooltips;
    private Supplier<ChatFormatting> color;
    private IPlacementCondition placementCondition;
    private ONIIItem.ItemCategory category = ONIIItem.ItemCategory.GENERAL;

    private boolean doModelGen = true;
    private boolean doLangGen = true;

    public ONIItemBuilder(String regName, Function<ONIBaseBlock, T> item) {
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

    public ONIItemBuilder<T> noModelGen() {
        doModelGen = false;
        return this;
    }

    public ONIItemBuilder<T> noLangGen() {
        doLangGen = false;
        return this;
    }

    public Function<ONIBaseBlock, T> build() {
        return (b) -> {
            T i = item.apply(b);
            i.setTooltipCondition(toolTipCondition);
            i.setTooltips(tooltips);
            i.setColorName(color);
            i.setPlacementCondition(placementCondition);
            i.setONIItemCategory(category);
            return i;
        };
    }

    public String getRegName() {
        return regName;
    }

    public boolean isDoModelGen() {
        return doModelGen;
    }

    public boolean isDoLangGen() {
        return doLangGen;
    }
}