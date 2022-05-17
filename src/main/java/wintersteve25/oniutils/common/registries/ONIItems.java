package wintersteve25.oniutils.common.registries;

import mekanism.common.registration.impl.ItemRegistryObject;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.contents.base.items.ONIBaseItem;
import wintersteve25.oniutils.common.contents.base.items.ONIBaseItemArmor;
import wintersteve25.oniutils.common.contents.base.items.ONIIItem;
import wintersteve25.oniutils.common.contents.base.builders.ONIItemBuilder;
import wintersteve25.oniutils.common.contents.base.enums.EnumModifications;
import wintersteve25.oniutils.common.contents.modules.items.gadgets.ONIToolItems;
import wintersteve25.oniutils.common.contents.modules.items.modifications.ONIModificationItem;
import wintersteve25.oniutils.common.registration.item.ONIItemDeferredRegister;

public class ONIItems {

    public static final ONIItemDeferredRegister ITEMS = new ONIItemDeferredRegister(ONIUtils.MODID);

    public static class Gadgets {
        public static final ItemRegistryObject<ONIBaseItemArmor> GAS_GOGGLES = registerBuilder(ONIToolItems.GAS_GOGGLES);
        public static final ItemRegistryObject<ONIBaseItem> WIRE_CUTTER = registerBuilder(ONIToolItems.WIRE_CUTTER);

        private static void register() {
        }
    }

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
        Gadgets.register();
        registerModifications();
    }

    private static void registerModifications() {
        for (EnumModifications modifications : EnumModifications.values()) {
            for (int i = 1; i < modifications.getTiers()+1; i++) {
                registerModification("modification_" + modifications.getName() + "_tier_" + i, modifications.getColor(), modifications.getBonusEachTier() * i, modifications, modifications.getTooltips());
            }
        }
    }

    private static void registerModification(String regName, ChatFormatting color, int maxBonus, EnumModifications modType, Component... tooltips) {
        ITEMS.register(regName, () -> new ONIModificationItem(ONIUtils.defaultProperties().stacksTo(1).setNoRepair(), color, maxBonus, modType, tooltips));
    }

    private static <T extends Item & ONIIItem> ItemRegistryObject<T> registerBuilder(ONIItemBuilder<T> builder) {
        return ITEMS.register(builder.getRegName(), () -> builder.build().apply(null), builder.isDoModelGen(), builder.isDoLangGen());
    }
}