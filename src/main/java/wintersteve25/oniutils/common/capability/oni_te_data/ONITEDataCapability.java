package wintersteve25.oniutils.common.capability.oni_te_data;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import wintersteve25.oniutils.common.capability.oni_te_data.api.ONITEDataImpl;
import wintersteve25.oniutils.common.capability.oni_te_data.api.ONIITEData;

import javax.annotation.Nullable;

public class ONITEDataCapability {
    @CapabilityInject(ONIITEData.class)
    public static Capability<ONIITEData> ONI_TE_CAP;

    public static void register() {
        CapabilityManager.INSTANCE.register(ONIITEData.class, new ONITEDataCapability.Storage(), ONITEDataImpl::new);
    }

    private static class Storage implements Capability.IStorage<ONIITEData> {
        @Nullable
        @Override
        public INBT writeNBT(Capability<ONIITEData> capability, ONIITEData instance, Direction side) {
            return instance.write();
        }

        @Override
        public void readNBT(Capability<ONIITEData> capability, ONIITEData instance, Direction side, INBT nbt) {
            instance.read((CompoundNBT) nbt);
        }
    }
}
