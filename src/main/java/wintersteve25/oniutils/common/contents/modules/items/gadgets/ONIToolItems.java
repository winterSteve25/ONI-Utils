package wintersteve25.oniutils.common.contents.modules.items.gadgets;

import net.minecraft.world.entity.EquipmentSlot;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.contents.base.items.ONIBaseItem;
import wintersteve25.oniutils.common.contents.base.items.ONIBaseItemArmor;
import wintersteve25.oniutils.common.contents.base.items.ONIIItem;
import wintersteve25.oniutils.common.contents.base.enums.ONIItemArmorMaterials;
import wintersteve25.oniutils.common.contents.base.builders.ONIItemBuilder;

public class ONIToolItems {
    public static ONIItemBuilder<ONIBaseItemArmor> GAS_GOGGLES = new ONIItemBuilder<>("gas_visual_goggles", (b) -> new ONIBaseItemArmor(ONIItemArmorMaterials.GAS_GOGGLES, EquipmentSlot.HEAD, ONIUtils.defaultProperties()))
            .setCategory(ONIIItem.ItemCategory.GADGETS)
            .defaultTooltip();
    public static ONIItemBuilder<ONIBaseItem> WIRE_CUTTER = new ONIItemBuilder<>("wire_cutter", (b) -> new ONIBaseItem(ONIUtils.defaultProperties().durability(800)))
            .setCategory(ONIIItem.ItemCategory.GADGETS)
            .defaultTooltip();
}
