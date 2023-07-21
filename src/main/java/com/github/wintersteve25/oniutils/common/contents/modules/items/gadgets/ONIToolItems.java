package com.github.wintersteve25.oniutils.common.contents.modules.items.gadgets;

import com.github.wintersteve25.oniutils.common.contents.base.ONIItemCategory;
import com.github.wintersteve25.oniutils.common.contents.modules.items.gadgets.blueprint.ONIBlueprint;
import net.minecraft.world.entity.EquipmentSlot;
import com.github.wintersteve25.oniutils.ONIUtils;
import com.github.wintersteve25.oniutils.common.contents.base.items.ONIBaseItem;
import com.github.wintersteve25.oniutils.common.contents.base.items.ONIBaseItemArmor;
import com.github.wintersteve25.oniutils.common.contents.base.enums.ONIItemArmorMaterials;
import com.github.wintersteve25.oniutils.common.contents.base.builders.ONIItemBuilder;

public class ONIToolItems {
    public static ONIItemBuilder<ONIBaseItemArmor> GAS_GOGGLES = new ONIItemBuilder<>("gas_visual_goggles", (b) -> new ONIBaseItemArmor(ONIItemArmorMaterials.GAS_GOGGLES, EquipmentSlot.HEAD, ONIUtils.defaultProperties()))
            .setCategory(ONIItemCategory.GADGETS)
            .defaultTooltip();
    public static ONIItemBuilder<ONIBaseItem> WIRE_CUTTER = new ONIItemBuilder<>("wire_cutter", (b) -> new ONIBaseItem(ONIUtils.defaultProperties().durability(800)))
            .setCategory(ONIItemCategory.GADGETS)
            .defaultTooltip()
            .takeDurabilityDamage();
    public static ONIItemBuilder<ONIBlueprint> BLUEPRINT = new ONIItemBuilder<>("blueprint", (b) -> new ONIBlueprint())
            .setCategory(ONIItemCategory.GADGETS)
            .defaultTooltip();
}
