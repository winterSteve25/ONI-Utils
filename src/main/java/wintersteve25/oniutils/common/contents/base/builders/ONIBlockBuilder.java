package wintersteve25.oniutils.common.contents.base.builders;

import net.minecraft.util.Tuple;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.Item;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraftforge.common.util.Lazy;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.api.ONIIHasGui;
import wintersteve25.oniutils.api.functional.*;
import wintersteve25.oniutils.common.contents.base.*;

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
            this.blockItem = new ONIItemBuilder<>(this.regName, () -> new ONIBaseAnimatedBlockItem(this.block.get(), ister, properties));
        } else {
            this.blockItem = new ONIItemBuilder<>(this.regName, () -> new ONIBaseItemBlock(this.block.get(), properties));
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

    public String getRegName() {
        return regName;
    }

    public Tuple<Lazy<T>, Lazy<ONIBaseItemBlock>> build() {
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
}