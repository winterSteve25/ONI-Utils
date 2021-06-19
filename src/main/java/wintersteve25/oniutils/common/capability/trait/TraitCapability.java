package wintersteve25.oniutils.common.capability.trait;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import wintersteve25.oniutils.common.capability.trait.api.DefaultTrait;
import wintersteve25.oniutils.common.capability.trait.api.ITrait;

import javax.annotation.Nullable;

public class TraitCapability {
    @CapabilityInject(ITrait.class)
    public static Capability<ITrait> TRAIT_CAPABILITY;

    public static void register() {
        CapabilityManager.INSTANCE.register(ITrait.class, new TraitStorage(), DefaultTrait::new);
    }


    public static class TraitStorage implements Capability.IStorage<ITrait> {
        @Nullable
        @Override
        public INBT writeNBT(Capability<ITrait> capability, ITrait instance, Direction side) {
            CompoundNBT nbt = new CompoundNBT();

            nbt.putInt("randomTrait", instance.getTraits().get(0));
            nbt.putInt("goodTrait", instance.getTraits().get(1));
            nbt.putInt("badTrait", instance.getTraits().get(2));

            nbt.putBoolean("gotTraitBonus", instance.getGottenTrait());

            return nbt;
        }

        @Override
        public void readNBT(Capability<ITrait> capability, ITrait instance, Direction side, INBT nbt) {

            int randomTrait = ((CompoundNBT) nbt).getInt("randomTrait");
            int goodTrait = ((CompoundNBT) nbt).getInt("goodTrait");
            int badTrait = ((CompoundNBT) nbt).getInt("badTrait");

            boolean gotTraitBonus = ((CompoundNBT) nbt).getBoolean("gotTraitBonus");

            instance.setTrait(randomTrait, goodTrait, badTrait);
            instance.setGottenTrait(gotTraitBonus);
        }
    }
}
