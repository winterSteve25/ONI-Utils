package wintersteve25.oniutils.common.blocks.ores.slime;

import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import wintersteve25.oniutils.common.capability.germ.api.GermStack;
import wintersteve25.oniutils.common.capability.germ.GermsCapability;
import wintersteve25.oniutils.common.capability.germ.api.EnumGermTypes;
import wintersteve25.oniutils.common.capability.germ.api.IGerms;
import wintersteve25.oniutils.common.init.ONIBlocks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SlimeTile extends TileEntity implements ITickableTileEntity {

    private GermStack germHandler = new GermStack();
    private LazyOptional<IGerms> lazyOptional =  LazyOptional.of(() -> germHandler);

    public SlimeTile() {
        super(ONIBlocks.SlimeTE.get());
        germHandler.addGerm(EnumGermTypes.SLIMELUNG, 8000);
    }

    @Override
    public void tick() {
        int currentGermAmount = germHandler.getGermAmount();
        germHandler.addGerm(EnumGermTypes.SLIMELUNG, currentGermAmount+1);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == GermsCapability.GERM_CAPABILITY) {
            return lazyOptional.cast();
        }
        return super.getCapability(cap, side);
    }
}
