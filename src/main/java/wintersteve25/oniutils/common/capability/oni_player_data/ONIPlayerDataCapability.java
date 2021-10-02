package wintersteve25.oniutils.common.capability.oni_player_data;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import wintersteve25.oniutils.common.capability.oni_player_data.api.ONIPlayerDataImpl;
import wintersteve25.oniutils.common.capability.oni_player_data.api.ONIIPlayerData;

import javax.annotation.Nullable;

public class ONIPlayerDataCapability {
    @CapabilityInject(ONIIPlayerData.class)
    public static Capability<ONIIPlayerData> ONI_PLAYER_CAP;

    public static void register() {
        CapabilityManager.INSTANCE.register(ONIIPlayerData.class, new Storage(), ONIPlayerDataImpl::new);
    }


    private static class Storage implements Capability.IStorage<ONIIPlayerData> {
        @Nullable
        @Override
        public INBT writeNBT(Capability<ONIIPlayerData> capability, ONIIPlayerData instance, Direction side) {
            CompoundNBT nbt = new CompoundNBT();

            nbt.putInt("randomTrait", instance.getTraits().get(0));
            nbt.putInt("goodTrait", instance.getTraits().get(1));
            nbt.putInt("badTrait", instance.getTraits().get(2));
            nbt.putInt("morale", instance.getMorale());
            nbt.putInt("buildMoraleBonus", instance.getBuildBonus());

            nbt.putBoolean("gotTraitBonus", instance.getGottenTrait());

            return nbt;
        }

        @Override
        public void readNBT(Capability<ONIIPlayerData> capability, ONIIPlayerData instance, Direction side, INBT nbt) {

            CompoundNBT compoundNBT = (CompoundNBT) nbt;

            int randomTrait = (compoundNBT.getInt("randomTrait"));
            int goodTrait = (compoundNBT.getInt("goodTrait"));
            int badTrait = (compoundNBT.getInt("badTrait"));

            boolean gotTraitBonus = (compoundNBT.getBoolean("gotTraitBonus"));

            instance.setTrait(randomTrait, goodTrait, badTrait);
            instance.setGottenTrait(gotTraitBonus);
            instance.setMorale(compoundNBT.getInt("morale"));
            instance.setBuildBonus(compoundNBT.getInt("buildMoraleBonus"));
        }
    }
}
