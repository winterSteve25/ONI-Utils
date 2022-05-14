package wintersteve25.oniutils.common.contents.base;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;
import wintersteve25.oniutils.api.functional.IPlacementCondition;
import wintersteve25.oniutils.api.functional.IToolTipCondition;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

public class ONIBaseItemBlock extends BlockItem implements ONIIItem {

    private final String regName;

    // item builder properties
    private boolean doModelGen = true;
    private boolean doLangGen = true;
    private Supplier<ChatFormatting> colorName;
    private Supplier<List<Component>> tooltips;
    private Supplier<IToolTipCondition> tooltipCondition = IToolTipCondition.DEFAULT;
    private IPlacementCondition placementCondition;
    private ItemCategory itemCategory = ItemCategory.GENERAL;

    public ONIBaseItemBlock(ONIBaseBlock blockIn, Properties builder) {
        super(blockIn, builder);
        this.regName = blockIn.getRegName();
    }

    @Override
    public boolean doModelGen() {
        return doModelGen;
    }

    @Override
    public boolean doStateGen() {
        return false;
    }

    @Override
    public boolean doLangGen() {
        return doLangGen;
    }

    @Override
    public boolean doLootTableGen() {
        return false;
    }

    @Override
    public Item get() {
        return this;
    }

    @Override
    public String getRegName() {
        return regName;
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

    @Override
    public void setDoModelGen(boolean doModelGen) {
        this.doModelGen = doModelGen;
    }

    @Override
    public void setDoLangGen(boolean doLangGen) {
        this.doLangGen = doLangGen;
    }

    public ItemCategory getONIItemCategory() {
        return itemCategory;
    }

    @Override
    public void setONIItemCategory(ItemCategory itemCategory) {
        this.itemCategory = itemCategory;
    }
}