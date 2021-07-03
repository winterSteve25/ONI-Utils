package wintersteve25.oniutils.common.capability.durability;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.util.Constants;
import wintersteve25.oniutils.common.capability.durability.api.DurabilityStack;
import wintersteve25.oniutils.common.capability.durability.api.IDurability;
import wintersteve25.oniutils.common.utils.helper.MiscHelper;

import javax.annotation.Nullable;

public class DurabilityCapability {

    @CapabilityInject(IDurability.class)
    public static Capability<IDurability> DURABILITY_CAPABILITY;

    public static void register() {
        CapabilityManager.INSTANCE.register(IDurability.class, new DurabilityStorage(), DurabilityStack::new);
    }

    public static class DurabilityStorage implements Capability.IStorage<IDurability> {
        @Nullable
        @Override
        public INBT writeNBT(Capability<IDurability> capability, IDurability instance, Direction side) {
            CompoundNBT nbt = new CompoundNBT();
            nbt.putInt("durability", instance.getDurability());
            return nbt;
        }

        @Override
        public void readNBT(Capability<IDurability> capability, IDurability instance, Direction side, INBT nbt) {
            instance.setDurability(((CompoundNBT) nbt).getInt("durability"));
        }
    }
}
