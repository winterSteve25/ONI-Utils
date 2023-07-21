package com.github.wintersteve25.oniutils.common.contents.base.items;

import com.github.wintersteve25.oniutils.common.contents.base.ONIItemCategory;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;
import com.github.wintersteve25.oniutils.common.contents.base.interfaces.functional.IPlacementCondition;
import com.github.wintersteve25.oniutils.common.contents.base.interfaces.functional.IToolTipCondition;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

public class ONIBaseItemBlock extends BlockItem implements ONIIItem {

    // item builder properties
    private Supplier<ChatFormatting> colorName;
    private Supplier<List<Component>> tooltips;
    private Supplier<IToolTipCondition> tooltipCondition = IToolTipCondition.DEFAULT;
    private IPlacementCondition placementCondition;
    private ONIItemCategory itemCategory = ONIItemCategory.GENERAL;

    public ONIBaseItemBlock(Block blockIn, Properties builder) {
        super(blockIn, builder);
    }

    @Override
    public Component getName(ItemStack stack) {
        if (getColorName() != null && getColorName().get() != null) {
            return super.getName(stack).plainCopy().withStyle(getColorName().get());
        }
        return super.getName(stack);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip(stack, worldIn, tooltip, flagIn);
    }

    @Override
    protected boolean canPlace(BlockPlaceContext context, BlockState state) {
        if (placementCondition != null) {
            return super.canPlace(context, state) && placementCondition.test(context, state);
        }
        return super.canPlace(context, state);
    }

    @Override
    public Supplier<ChatFormatting> getColorName() {
        if (colorName != null) {
            return colorName;
        }

        return ()->itemCategory.getColor();
    }

    @Override
    public void setColorName(Supplier<ChatFormatting> colorName) {
        this.colorName = colorName;
    }

    @Override
    public Supplier<List<Component>> getTooltips() {
        return tooltips;
    }

    @Override
    public void setTooltips(Supplier<List<Component>> tooltips) {
        this.tooltips = tooltips;
    }

    @Override
    public Supplier<IToolTipCondition> getTooltipCondition() {
        return tooltipCondition;
    }

    @Override
    public void setTooltipCondition(Supplier<IToolTipCondition> condition) {
        this.tooltipCondition = condition;
    }

    @Override
    public IPlacementCondition getPlacementCondition() {
        return placementCondition;
    }

    @Override
    public void setPlacementCondition(IPlacementCondition placementCondition) {
        this.placementCondition = placementCondition;
    }

    public ONIItemCategory getONIItemCategory() {
        return itemCategory;
    }

    @Override
    public void setONIItemCategory(ONIItemCategory itemCategory) {
        this.itemCategory = itemCategory;
    }
}