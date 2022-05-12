package wintersteve25.oniutils.common.contents.modules.items.gadgets;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.contents.base.ONIBaseItem;
import wintersteve25.oniutils.common.contents.base.ONIBaseItemArmor;
import wintersteve25.oniutils.common.contents.base.ONIIItem;
import wintersteve25.oniutils.common.contents.base.enums.ONIItemArmorMaterials;
import wintersteve25.oniutils.common.contents.base.builders.ONIItemBuilder;
import wintersteve25.oniutils.common.utils.helpers.LangHelper;

public class ONIToolItems {
    public static ONIItemBuilder<ONIBaseItemArmor> GAS_GOGGLES = new ONIItemBuilder<>(() -> new ONIBaseItemArmor(ONIItemArmorMaterials.GAS_GOGGLES, EquipmentSlotType.HEAD, new Item.Properties().group(ONIUtils.creativeTab), "Gas Visual Goggles"))
            .setCategory(ONIIItem.ItemCategory.GADGETS)
            .tooltip(LangHelper.itemTooltip("gas_visual_goggles"));
    public static ONIItemBuilder<ONIBaseItem> WIRE_CUTTER = new ONIItemBuilder<>(() -> new ONIBaseItem(new Item.Properties().group(ONIUtils.creativeTab).maxDamage(800), "Wire Cutter"))
            .setCategory(ONIIItem.ItemCategory.GADGETS)
            .tooltip(LangHelper.itemTooltip("wire_cutter"));
}
