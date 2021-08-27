package wintersteve25.oniutils.common.blocks.modules.oxygen.algae;

import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import wintersteve25.oniutils.common.blocks.base.ONIBaseInvTE;
import wintersteve25.oniutils.common.capability.plasma.PlasmaCapability;
import wintersteve25.oniutils.common.capability.plasma.api.EnumWattsTypes;
import wintersteve25.oniutils.common.capability.plasma.api.IPlasma;
import wintersteve25.oniutils.common.capability.plasma.api.PlasmaStack;
import wintersteve25.oniutils.common.init.ONIBlocks;
import wintersteve25.oniutils.common.init.ONIConfig;
import wintersteve25.oniutils.common.utils.MiscHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class AlgaeDiffuserTE extends ONIBaseInvTE implements ITickableTileEntity {

    private PlasmaStack plasmaHandler = new PlasmaStack(500, EnumWattsTypes.LOW);
    private LazyOptional<IPlasma> powerLazyOptional = LazyOptional.of(() -> plasmaHandler);
    private static final List<Item> valids = new ArrayList<>();

    public AlgaeDiffuserTE() {
        super(ONIBlocks.ALGAE_DIFFUSER_TE.get());
        valids.add(new BlockItem(ONIBlocks.Algae, MiscHelper.DEFAULT_ITEM_PROPERTY));
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

    @Override
    public List<Item> validItems() {
        return valids;
    }

    @Override
    protected int totalProgress() {
        return ONIConfig.ALGAE_DIFFUSER_PROCESS_TIME.get();
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
