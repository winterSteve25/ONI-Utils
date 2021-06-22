package wintersteve25.oniutils.common.capability.plasma;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import wintersteve25.oniutils.common.capability.plasma.api.EnumWattsTypes;
import wintersteve25.oniutils.common.capability.plasma.api.IPlasma;
import wintersteve25.oniutils.common.capability.plasma.api.PlasmaStack;

import javax.annotation.Nullable;

public class PlasmaCapability {
    @CapabilityInject(IPlasma.class)
    public static Capability<IPlasma> POWER_CAPABILITY;

    public static void register() {
        CapabilityManager.INSTANCE.register(IPlasma.class, new PowerStorage(), PlasmaStack::new);
    }

    public static class PowerStorage implements Capability.IStorage<IPlasma> {
        @Nullable
        @Override
        public INBT writeNBT(Capability<IPlasma> capability, IPlasma instance, Direction side) {
            CompoundNBT nbt = new CompoundNBT();

            nbt.putInt("plasma", instance.getPower());
            nbt.putInt("plasma_capacity", instance.getCapacity());
            nbt.putString("wattType", instance.getWatts().getName());

            return nbt;
        }

        @Override
        public void readNBT(Capability<IPlasma> capability, IPlasma instance, Direction side, INBT nbt) {

            int plasma =  ((CompoundNBT) nbt).getInt("plasma");
            int capacity =  ((CompoundNBT) nbt).getInt("plasma_capacity");
            String watts = ((CompoundNBT) nbt).getString("wattType");

            instance.setPower(plasma);
            instance.setCapacity(capacity);
            instance.setWatts(EnumWattsTypes.getWattsFromName(watts));
        }
    }
}
