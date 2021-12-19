package wintersteve25.oniutils.common.contents.modules.oxygen.algae;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import wintersteve25.oniutils.common.contents.base.ONIBaseInvTE;
import wintersteve25.oniutils.common.capability.plasma.PlasmaCapability;
import wintersteve25.oniutils.common.capability.plasma.api.EnumWattsTypes;
import wintersteve25.oniutils.common.capability.plasma.api.IPlasma;
import wintersteve25.oniutils.common.capability.plasma.api.PlasmaStack;
import wintersteve25.oniutils.common.init.ONIBlocks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class AlgaeDiffuserTE extends ONIBaseInvTE implements ITickableTileEntity {

    private final PlasmaStack plasmaHandler = new PlasmaStack(500, EnumWattsTypes.LOW);
    private final LazyOptional<IPlasma> powerLazyOptional = LazyOptional.of(() -> plasmaHandler);

    public AlgaeDiffuserTE() {
        super(ONIBlocks.Machines.Oxygen.ALGAE_DIFFUSER_TE.get());
    }

    @Override
    public void tick() {

    }

    @Override
    public void read(BlockState state, CompoundNBT tag) {
        plasmaHandler.read(tag.getCompound("plasma"));

        super.read(state, tag);
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        tag.put("plasma", plasmaHandler.write());

        return super.write(tag);
    }

    @Override
    public int getInvSize() {
        return 1;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == PlasmaCapability.POWER_CAPABILITY) {
            return powerLazyOptional.cast();
        }

        return super.getCapability(cap, side);
    }
}
