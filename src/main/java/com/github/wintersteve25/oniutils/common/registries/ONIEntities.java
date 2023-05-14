package com.github.wintersteve25.oniutils.common.registries;

import mekanism.common.registration.impl.EntityTypeDeferredRegister;
import net.minecraftforge.eventbus.api.IEventBus;
import com.github.wintersteve25.oniutils.ONIUtils;

public class ONIEntities {
    public static final EntityTypeDeferredRegister ENTITY = new EntityTypeDeferredRegister(ONIUtils.MODID);

    public static class BlockBounded {
    }

    public static void register(IEventBus bus) {
        ENTITY.register(bus);
    }
}
