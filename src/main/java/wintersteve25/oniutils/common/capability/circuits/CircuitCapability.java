package wintersteve25.oniutils.common.capability.circuits;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import wintersteve25.oniutils.common.capability.circuits.api.WorldCircuits;

import javax.annotation.Nullable;

public class CircuitCapability {
    @CapabilityInject(WorldCircuits.class)
    public static Capability<WorldCircuits> WORLD_CIRCUIT_CAPABILITY;

    public static void register() {
        CapabilityManager.INSTANCE.register(WorldCircuits.class, new CircuitCapability.Storage(), WorldCircuits::new);
    }

    private static class Storage implements Capability.IStorage<WorldCircuits> {
        @Nullable
        @Override
        public INBT writeNBT(Capability<WorldCircuits> capability, WorldCircuits instance, Direction side) {
            return instance.write();
        }

        @Override
        public void readNBT(Capability<WorldCircuits> capability, WorldCircuits instance, Direction side, INBT nbt) {
            CompoundNBT compoundNBT = (CompoundNBT) nbt;
            instance.read(compoundNBT);
        }
    }
}
