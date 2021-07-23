package wintersteve25.oniutils.common.capability.morale;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import wintersteve25.oniutils.common.capability.morale.api.IMorale;
import wintersteve25.oniutils.common.capability.morale.api.MoraleStack;

import javax.annotation.Nullable;

public class MoraleCapability {

    @CapabilityInject(IMorale.class)
    public static Capability<IMorale> MORALE_CAPABILITY;

    public static void register() {
        CapabilityManager.INSTANCE.register(IMorale.class, new MoraleStorage(), MoraleStack::new);
    }

    public static class MoraleStorage implements Capability.IStorage<IMorale> {
        @Nullable
        @Override
        public INBT writeNBT(Capability<IMorale> capability, IMorale instance, Direction side) {
            CompoundNBT nbt = new CompoundNBT();

            nbt.putInt("morale", instance.getMorale());

            return nbt;
        }

        @Override
        public void readNBT(Capability<IMorale> capability, IMorale instance, Direction side, INBT nbt) {
            int morale = ((CompoundNBT) nbt).getInt("morale");

            instance.setMorale(morale);
        }
    }
}
