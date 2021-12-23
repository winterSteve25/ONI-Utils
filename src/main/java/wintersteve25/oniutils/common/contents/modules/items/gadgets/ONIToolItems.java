package wintersteve25.oniutils.common.contents.modules.items.gadgets;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.contents.base.ONIBaseItemArmor;
import wintersteve25.oniutils.common.contents.base.ONIIItem;
import wintersteve25.oniutils.common.contents.base.ONIItemArmorMaterials;
import wintersteve25.oniutils.common.contents.base.builders.ONIItemBuilder;
import wintersteve25.oniutils.common.utils.helpers.LangHelper;

public class ONIToolItems {
    public static ONIItemBuilder<ONIBaseItemArmor> GAS_GOGGLES = new ONIItemBuilder<>(() -> new ONIBaseItemArmor(ONIItemArmorMaterials.GAS_GOGGLES, EquipmentSlotType.HEAD, new Item.Properties().group(ONIUtils.creativeTab), "Gas Visualization Goggles"))
            .setCategory(ONIIItem.ItemCategory.GADGETS)
            .tooltip(LangHelper.itemTooltip("gas_visual_goggles"));
}
