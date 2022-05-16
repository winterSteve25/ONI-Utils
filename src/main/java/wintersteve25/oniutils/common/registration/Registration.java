package wintersteve25.oniutils.common.registration;

import mekanism.common.registration.impl.*;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.registration.block.ONIBlockDeferredRegister;
import wintersteve25.oniutils.common.registries.*;

public class Registration {
    public static final FluidDeferredRegister FLUID = new FluidDeferredRegister(ONIUtils.MODID);
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, ONIUtils.MODID);

    public static void init() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ONIBlocks.register(eventBus);
        ONIItems.register(eventBus);
        ONIGases.register(eventBus);
        ONISounds.register(eventBus);
        ONICapabilities.register(eventBus);

        EFFECTS.register(eventBus);
        FLUID.register(eventBus);

        ONIEffects.register();

        ONIUtils.LOGGER.info("ONIUtils Registration Completed");
    }
}