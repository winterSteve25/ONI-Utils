package wintersteve25.oniutils.common.contents.base.builders;

import javafx.util.Pair;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.api.ONIIHasGui;
import wintersteve25.oniutils.api.functional.*;
import wintersteve25.oniutils.common.contents.base.*;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

public class ONIBlockBuilder<T extends ONIBaseBlock> {
    @Nonnull
    private final T block;
    @Nonnull
    private final ONIItemBuilder<ONIBaseItemBlock> blockItem;

    public ONIBlockBuilder(Supplier<T> block) {
        this(block, new Item.Properties().group(ONIUtils.creativeTab), false);
    }

    public ONIBlockBuilder(Supplier<T> block, Item.Properties properties, boolean isAnimated) {
        this.block = block.get();
        if (isAnimated) {
            this.blockItem = new ONIItemBuilder<>(() -> new ONIBaseAnimatedBlockItem(this.block, properties));
        } else {
            this.blockItem = new ONIItemBuilder<>(() -> new ONIBaseItemBlock(this.block, properties));
        }
    }

    public ONIBlockBuilder(Supplier<T> block, Supplier<Callable<ItemStackTileEntityRenderer>> ister, boolean isAnimated) {
        this(block, new Item.Properties().group(ONIUtils.creativeTab).setISTER(ister), isAnimated);
    }

    public ONIBlockBuilder(Supplier<T> block, Item.Properties properties, Supplier<Callable<ItemStackTileEntityRenderer>> ister, boolean isAnimated) {
        this(block, properties.setISTER(ister), isAnimated);
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

    public ONIBlockBuilder<T> tooltip(ITextComponent... tooltips) {
        this.blockItem.tooltip(tooltips);
        return this;
    }

    public ONIBlockBuilder<T> coloredName(Supplier<TextFormatting> color) {
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

    public ONIBlockBuilder<T> tileEntity(ITETypeProvider teT, Class<? extends TileEntity> teClass) {
        this.block.setTileEntityType(teT);
        this.block.setTeClass(teClass);
        return this;
    }

    public ONIBlockBuilder<T> container(ONIIHasGui gui) {
        isMachine();
        ((ONIBaseMachine) this.block).setGui(gui);
        return this;
    }

    public Pair<T, ONIBaseItemBlock> build() {
        return new Pair<>(this.block, this.blockItem.build());
    }

    private void isMachine() {
        if (!(this.block instanceof ONIBaseMachine)) throw new IllegalStateException("Tried to create machine-only properties with a non-machine block");
    }

    private void isDirectional() {
        if (!(this.block instanceof ONIBaseDirectional)) throw new IllegalStateException("Tried to create directional-only properties with a non-directional block");
    }

    @Deprecated
    public String getBlockRegName() {
        return block.getRegName();
    }
}
