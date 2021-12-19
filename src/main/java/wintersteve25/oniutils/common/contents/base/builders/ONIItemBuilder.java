package wintersteve25.oniutils.common.contents.base.builders;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import wintersteve25.oniutils.api.functional.IPlacementCondition;
import wintersteve25.oniutils.api.functional.IToolTipCondition;
import wintersteve25.oniutils.common.contents.base.ONIBaseBlockItem;
import wintersteve25.oniutils.common.contents.base.ONIIItem;

import java.util.List;
import java.util.function.Supplier;

public class ONIItemBuilder<T extends ONIIItem> {
    public final T item;

    public ONIItemBuilder(Supplier<T> item) {
        this.item = item.get();
    }

    public ONIItemBuilder<T> noModelGen() {
        this.item.setDoModelGen(false);
        return this;
    }

    public ONIItemBuilder<T> noLangGen() {
        this.item.setDoLangGen(false);
        return this;
    }

    public ONIItemBuilder<T> shiftToolTip() {
        this.item.setTooltipCondition(IToolTipCondition.DEFAULT);
        return this;
    }

    public ONIItemBuilder<T> tooltipCondition(Supplier<IToolTipCondition> condition) {
        this.item.setTooltipCondition(condition);
        return this;
    }

    public ONIItemBuilder<T> tooltip(Supplier<List<ITextComponent>> tooltips) {
        this.item.setTooltips(tooltips);
        return this;
    }

    public ONIItemBuilder<T> coloredName(Supplier<TextFormatting> color) {
        this.item.setColorName(color);
        return this;
    }

    public ONIItemBuilder<T> placementCondition(IPlacementCondition placementCondition) {
        blockItem();
        this.item.setPlacementCondition(placementCondition);
        return this;
    }

    public T build() {
        return this.item;
    }

    private void blockItem() {
        if (!(this.item instanceof ONIBaseBlockItem)) throw new IllegalStateException("Tried to create blockitem-only properties with a normal item");
    }
}
