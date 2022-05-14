package wintersteve25.oniutils.common.contents.base.builders;

import net.minecraft.util.Tuple;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.block.entity.BlockEntityType;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.api.ONIIHasGui;
import wintersteve25.oniutils.api.functional.*;
import wintersteve25.oniutils.common.contents.base.*;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

public class ONIBlockBuilder<T extends ONIBaseBlock> {

    private final T block;
    private final ONIItemBuilder<ONIBaseItemBlock> blockItem;

    public ONIBlockBuilder(Supplier<T> block) {
        this(block, null, false);
    }

    public ONIBlockBuilder(Supplier<T> block, Supplier<BlockEntityWithoutLevelRenderer> ister, boolean isAnimated) {
        this(block, new Item.Properties().tab(ONIUtils.creativeTab), ister, isAnimated);
    }

    public ONIBlockBuilder(Supplier<T> block, Item.Properties properties, Supplier<BlockEntityWithoutLevelRenderer> ister, boolean isAnimated) {
        this.block = block.get();
        if (isAnimated) {
            this.blockItem = new ONIItemBuilder<>(() -> new ONIBaseAnimatedBlockItem(this.block, ister, properties));
        } else {
            this.blockItem = new ONIItemBuilder<>(() -> new ONIBaseItemBlock(this.block, properties));
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

    public ONIBlockBuilder<T> noModelGen() {
        this.block.setDoModelGen(false);
        this.blockItem.noModelGen();
        return this;
    }

    public ONIBlockBuilder<T> doStateGen() {
        this.block.setDoStateGen(true);
        return this;
    }

    public ONIBlockBuilder<T> noLangGen() {
        this.block.setDoLangGen(false);
        this.blockItem.noLangGen();
        return this;
    }

    public ONIBlockBuilder<T> noLootTableGen() {
        this.block.setDoLootTableGen(false);
        return this;
    }

    public ONIBlockBuilder<T> setCategory(ONIIItem.ItemCategory category) {
        this.blockItem.setCategory(category);
        return this;
    }

    public ONIBlockBuilder<T> shape(IVoxelShapeProvider voxelShape) {
        this.block.setHitBox(voxelShape);
        return this;
    }

    public ONIBlockBuilder<T> allowVerticalPlacement() {
        isDirectional();
        ((ONIBaseDirectional) this.block).setAllowVertical(true);
        return this;
    }

    public ONIBlockBuilder<T> autoRotateShape() {
        isDirectional();
        ((ONIBaseDirectional) this.block).setAutoRotateShape(true);
        return this;
    }

    public ONIBlockBuilder<T> renderType(IRenderTypeProvider renderType) {
        this.block.setRenderType(renderType);
        return this;
    }

    public ONIBlockBuilder<T> container(ONIIHasGui gui) {
        isMachine();
        ((ONIBaseMachine) this.block).setGui(gui);
        return this;
    }

    public Tuple<T, ONIBaseItemBlock> build() {
        return new Tuple<>(this.block, this.blockItem.build());
    }

    private void isMachine() {
        if (!(this.block instanceof ONIBaseMachine)) throw new IllegalStateException("Tried to create machine-only properties with a non-machine block");
    }

    private void isDirectional() {
        if (!(this.block instanceof ONIBaseDirectional)) throw new IllegalStateException("Tried to create directional-only properties with a non-directional block");
    }
}