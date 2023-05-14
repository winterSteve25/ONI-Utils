package com.github.wintersteve25.oniutils.common.contents.base.builders;

import net.minecraft.util.Tuple;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.Item;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraftforge.common.util.Lazy;
import com.github.wintersteve25.oniutils.ONIUtils;
import com.github.wintersteve25.oniutils.common.contents.base.blocks.ONIBaseBlock;
import com.github.wintersteve25.oniutils.common.contents.base.blocks.ONIBaseDirectional;
import com.github.wintersteve25.oniutils.common.contents.base.blocks.ONIBaseMachine;
import com.github.wintersteve25.oniutils.common.contents.base.interfaces.ONIIHasGui;
import com.github.wintersteve25.oniutils.common.contents.base.interfaces.functional.*;
import com.github.wintersteve25.oniutils.common.contents.base.items.ONIBaseAnimatedBlockItem;
import com.github.wintersteve25.oniutils.common.contents.base.items.ONIBaseItemBlock;
import com.github.wintersteve25.oniutils.common.contents.base.items.ONIIItem;

import java.util.function.Function;
import java.util.function.Supplier;

public class ONIBlockBuilder<T extends ONIBaseBlock> {

    private final String regName;
    private final Supplier<T> block;
    private final ONIItemBuilder<ONIBaseItemBlock> blockItem;

    private IVoxelShapeProvider hitBox;
    private boolean allowVertical;
    private boolean allowRotateShape;
    private IRenderTypeProvider renderType;
    private ONIIHasGui container;
    private boolean doStateGen = false;
    private boolean doModelGen = true;
    private boolean doLangGen = true;
    private boolean doLootableGen = true;

    public ONIBlockBuilder(String regName, Supplier<T> block) {
        this(regName, block, null, false);
    }

    public ONIBlockBuilder(String regName, Supplier<T> block, Supplier<BlockEntityWithoutLevelRenderer> ister, boolean isAnimated) {
        this(regName, block, ONIUtils.defaultProperties(), ister, isAnimated);
    }

    public ONIBlockBuilder(String regName, Supplier<T> block, Item.Properties properties, Supplier<BlockEntityWithoutLevelRenderer> ister, boolean isAnimated) {
        this.block = block;
        this.regName = regName;
        if (isAnimated) {
            this.blockItem = new ONIItemBuilder<>(this.regName, (b) -> new ONIBaseAnimatedBlockItem(b, ister, properties));
        } else {
            this.blockItem = new ONIItemBuilder<>(this.regName, (b) -> new ONIBaseItemBlock(b, properties));
        }
    }

    public ONIBlockBuilder<T> placementCondition(IPlacementCondition condition) {
        this.blockItem.placementCondition(condition);
        return this;
    }

    public ONIBlockBuilder<T> shiftToolTip() {
        this.blockItem.shiftToolTip();
        return this;
    }

    public ONIBlockBuilder<T> tooltipCondition(Supplier<IToolTipCondition> condition) {
        this.blockItem.tooltipCondition(condition);
        return this;
    }

    public ONIBlockBuilder<T> tooltip(Component... tooltips) {
        this.blockItem.tooltip(tooltips);
        return this;
    }

    public ONIBlockBuilder<T> coloredName(Supplier<ChatFormatting> color) {
        this.blockItem.coloredName(color);
        return this;
    }

    public ONIBlockBuilder<T> setCategory(ONIIItem.ItemCategory category) {
        this.blockItem.setCategory(category);
        return this;
    }

    public ONIBlockBuilder<T> shape(IVoxelShapeProvider voxelShape) {
        this.hitBox = voxelShape;
        return this;
    }

    public ONIBlockBuilder<T> allowVerticalPlacement() {
        this.allowVertical = true;
        return this;
    }

    public ONIBlockBuilder<T> autoRotateShape() {
        this.allowRotateShape = true;
        return this;
    }

    public ONIBlockBuilder<T> renderType(IRenderTypeProvider renderType) {
        this.renderType = renderType;
        return this;
    }

    public ONIBlockBuilder<T> container(ONIIHasGui gui) {
        container = gui;
        return this;
    }

    public ONIBlockBuilder<T> doStateGen() {
        doStateGen = true;
        return this;
    }

    public ONIBlockBuilder<T> noModelGen() {
        doModelGen = false;
        blockItem.noModelGen();
        return this;
    }

    public ONIBlockBuilder<T> noLangGen() {
        doLangGen = false;
        blockItem.noLangGen();
        return this;
    }

    public ONIBlockBuilder<T> noLootableGen() {
        doLootableGen = false;
        return this;
    }

    public Tuple<Lazy<T>, Function<ONIBaseBlock, ONIBaseItemBlock>> build() {
        return new Tuple<>(Lazy.of(() -> {
            T b = block.get();
            b.setHitBox(hitBox);
            ((ONIBaseDirectional) b).setAllowVertical(allowVertical);
            ((ONIBaseDirectional) b).setAutoRotateShape(allowRotateShape);
            b.setRenderType(renderType);
            ((ONIBaseMachine<?>) b).setGui(container);
            return b;
        }), this.blockItem.build());
    }

    public String getRegName() {
        return regName;
    }

    public boolean isDoStateGen() {
        return doStateGen;
    }

    public boolean isDoModelGen() {
        return doModelGen;
    }

    public boolean isDoLangGen() {
        return doLangGen;
    }

    public boolean isDoLootableGen() {
        return doLootableGen;
    }
}