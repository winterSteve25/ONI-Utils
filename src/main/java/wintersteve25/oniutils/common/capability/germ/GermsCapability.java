package wintersteve25.oniutils.common.capability.germ;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import wintersteve25.oniutils.common.capability.germ.api.EnumGermTypes;
import wintersteve25.oniutils.common.capability.germ.api.IGerms;

import javax.annotation.Nullable;

public class GermsCapability {

    @CapabilityInject(IGerms.class)
    public static Capability<IGerms> GERM_CAPABILITY = null;

    public static void register() {
        CapabilityManager.INSTANCE.register(IGerms.class, new GermStorage(), GermStack::new);
    }


    public static class GermStorage implements Capability.IStorage<IGerms> {
        @Nullable
        @Override
        public INBT writeNBT(Capability<IGerms> capability, IGerms instance, Direction side) {
            CompoundNBT nbt = new CompoundNBT();

            nbt.putString("germName", instance.getGermType().getName());
            nbt.putInt("germAmounts", instance.getGermAmount());

            return nbt;
        }

        @Override
        public void readNBT(Capability<IGerms> capability, IGerms instance, Direction side, INBT nbt) {
            String germName = ((CompoundNBT) nbt).getString("germName");
            EnumGermTypes germType = EnumGermTypes.getGermFromName(germName);

            int germAmounts = ((CompoundNBT) nbt).getInt("germAmounts");

            instance.setGerm(germType, germAmounts);
        }
    }
}
