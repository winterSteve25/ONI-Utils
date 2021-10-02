package wintersteve25.oniutils.common.capability.oni_player_data;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import wintersteve25.oniutils.common.capability.oni_player_data.api.ONIPlayerDataImpl;
import wintersteve25.oniutils.common.capability.oni_player_data.api.ONIIPlayerData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ONIPlayerDataCapProv implements ICapabilitySerializable<CompoundNBT> {
    private final ONIPlayerDataImpl trait = new ONIPlayerDataImpl();
    private final LazyOptional<ONIIPlayerData> lazyOptional = LazyOptional.of(() -> trait);

    public void invalidate() {
        lazyOptional.invalidate();
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == ONIPlayerDataCapability.ONI_PLAYER_CAP) {
            return lazyOptional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundNBT serializeNBT() {
        if (ONIPlayerDataCapability.ONI_PLAYER_CAP == null) {
            return new CompoundNBT();
        } else {
            return (CompoundNBT) ONIPlayerDataCapability.ONI_PLAYER_CAP.writeNBT(trait, null);
        }
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        if (ONIPlayerDataCapability.ONI_PLAYER_CAP != null) {
            ONIPlayerDataCapability.ONI_PLAYER_CAP.readNBT(trait, null, nbt);
        }
    }
}
