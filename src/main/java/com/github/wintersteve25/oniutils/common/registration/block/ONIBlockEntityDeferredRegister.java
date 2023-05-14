package com.github.wintersteve25.oniutils.common.registration.block;

import mekanism.common.registration.impl.BlockRegistryObject;
import mekanism.common.registration.impl.TileEntityTypeDeferredRegister;
import mekanism.common.registration.impl.TileEntityTypeRegistryObject;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ONIBlockEntityDeferredRegister extends TileEntityTypeDeferredRegister {
    public ONIBlockEntityDeferredRegister(String modid) {
        super(modid);
    }

    public <BE extends BlockEntity> TileEntityTypeRegistryObject<BE> registerBE(BlockRegistryObject<?, ?> block, BlockEntityType.BlockEntitySupplier<? extends BE> factory) {
        return this.<BE>builder(block, factory).build();
    }
}
