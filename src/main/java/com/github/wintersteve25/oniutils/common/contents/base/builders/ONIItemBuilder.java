package com.github.wintersteve25.oniutils.common.contents.base.builders;

import com.github.wintersteve25.oniutils.common.contents.base.ONIItemCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import com.github.wintersteve25.oniutils.common.contents.base.blocks.ONIBaseBlock;
import com.github.wintersteve25.oniutils.common.contents.base.interfaces.functional.IPlacementCondition;
import com.github.wintersteve25.oniutils.common.contents.base.interfaces.functional.IToolTipCondition;
import com.github.wintersteve25.oniutils.common.contents.base.items.ONIBaseItem;
import com.github.wintersteve25.oniutils.common.contents.base.items.ONIIItem;
import com.github.wintersteve25.oniutils.common.utils.helpers.LangHelper;

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
    private ONIItemCategory category = ONIItemCategory.GENERAL;
    private boolean takeDurabilityDamage = false;

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

    public ONIItemBuilder<T> setCategory(ONIItemCategory category) {
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

    public ONIItemBuilder<T> takeDurabilityDamage() {
        takeDurabilityDamage = true;
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
            if (i instanceof ONIBaseItem i1) {
                i1.setTakeDurabilityDamage(takeDurabilityDamage);
            }
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