package wintersteve25.oniutils.common.contents.base;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import wintersteve25.oniutils.api.functional.IToolTipCondition;
import wintersteve25.oniutils.common.utils.ONIConstants;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

public class ONIBaseItemArmor extends ArmorItem implements ONIIItem {

    private final String regName;

    // item builder stuff
    private boolean doModelGen = true;
    private boolean doLangGen = true;
    private Supplier<TextFormatting> colorName;
    private Supplier<List<ITextComponent>> tooltips;
    private Supplier<IToolTipCondition> tooltipCondition = IToolTipCondition.DEFAULT;
    private ItemCategory itemCategory = ItemCategory.GENERAL;

    public ONIBaseItemArmor(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builderIn, String regName) {
        super(materialIn, slot, builderIn);
        this.regName = regName;
    }

    @Override
    public String getRegName() {
        return regName;
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
    public ITextComponent getDisplayName(ItemStack stack) {
        if (getColorName() != null && getColorName().get() != null) {
            return super.getDisplayName(stack).copyRaw().mergeStyle(getColorName().get());
        }
        return super.getDisplayName(stack);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public Supplier<TextFormatting> getColorName() {
        if (colorName != null) {
            return colorName;
        }

        switch (itemCategory) {
            case FURNITURE:
                return () -> ONIConstants.TextColor.FURNITURE_CAT_COLOR;
            case POWER:
                return () -> ONIConstants.TextColor.POWER_CAT_COLOR;
            case OXYGEN:
                return () -> ONIConstants.TextColor.OXYGEN_CAT_COLOR;
            case VENTILATION:
                return () -> ONIConstants.TextColor.VENTILATION_CAT_COLOR;
            case TE_BOUNDED:
                return () -> ONIConstants.TextColor.TE_BOUNDING_CAT_COLOR;
            case GADGETS:
                return () -> ONIConstants.TextColor.TOOLS;
            default:
                return null;
        }
    }

    @Override
    public void setColorName(Supplier<TextFormatting> colorName) {
        this.colorName = colorName;
    }

    @Override
    public Supplier<List<ITextComponent>> getTooltips() {
        return tooltips;
    }

    @Override
    public void setTooltips(Supplier<List<ITextComponent>> tooltips) {
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
    public void setDoModelGen(boolean doModelGen) {
        this.doModelGen = doModelGen;
    }

    @Override
    public void setDoLangGen(boolean doLangGen) {
        this.doLangGen = doLangGen;
    }

    @Override
    public ItemCategory getItemCategory() {
        return itemCategory;
    }

    @Override
    public void setItemCategory(ItemCategory itemCategory) {
        this.itemCategory = itemCategory;
    }
}
