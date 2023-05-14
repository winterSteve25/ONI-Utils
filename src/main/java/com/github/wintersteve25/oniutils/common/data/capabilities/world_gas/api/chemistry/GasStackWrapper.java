package com.github.wintersteve25.oniutils.common.data.capabilities.world_gas.api.chemistry;

import mekanism.api.chemical.gas.Gas;
import mekanism.api.chemical.gas.GasStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

public record GasStackWrapper(GasStack stack) {
    public static final GasStackWrapper EMPTY = new GasStackWrapper(GasStack.EMPTY);

    public GasStack getStack() {
        return stack;
    }

    public long getAmount() {
        return stack.getAmount();
    }

    public void setAmount(long amount) {
        stack.setAmount(amount);
    }

    public Gas getType() {
        return stack.getType();
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public CompoundTag write(CompoundTag nbt) {
        return stack.write(nbt);
    }

    public GasStack copy() {
        return stack.copy();
    }

    public ResourceLocation getTypeRegistryName() {
        return stack.getTypeRegistryName();
    }

    public void grow(long amount) {
        stack.grow(amount);
    }

    public void shrink(long amount) {
        stack.shrink(amount);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof GasStackWrapper && ((GasStackWrapper) obj).getType() == getType() && ((GasStackWrapper) obj).getAmount() == getAmount();
    }
}
