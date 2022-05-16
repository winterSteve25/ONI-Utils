package wintersteve25.oniutils.common.data.capabilities;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class ONICapabilityProvider<CAP extends INBTSerializable<CompoundTag>> implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    private final Lazy<CAP> data;
    private final LazyOptional<CAP> lazyOptional;
    private final Capability<CAP> capabilityToken;

    public ONICapabilityProvider(Supplier<CAP> dataFactory, Capability<CAP> capabilityToken) {
        this.capabilityToken = capabilityToken;
        data = Lazy.of(dataFactory);
        lazyOptional = LazyOptional.of(data::get);
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == capabilityToken) {
            return lazyOptional.cast();
        }
        return LazyOptional.empty();
    }

    public void invalidate() {
        lazyOptional.invalidate();
    }

    @Override
    public CompoundTag serializeNBT() {
        return data.get().serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        data.get().deserializeNBT(nbt);
    }
}
