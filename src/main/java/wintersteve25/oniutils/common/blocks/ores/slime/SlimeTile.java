package wintersteve25.oniutils.common.blocks.ores.slime;

import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.capability.germ.api.GermStack;
import wintersteve25.oniutils.common.capability.germ.GermsCapability;
import wintersteve25.oniutils.common.capability.germ.api.EnumGermTypes;
import wintersteve25.oniutils.common.capability.germ.api.IGerms;
import wintersteve25.oniutils.common.init.ONIBlocks;
import wintersteve25.oniutils.common.init.ONIConfig;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SlimeTile extends TileEntity implements ITickableTileEntity {

    private GermStack germHandler = new GermStack();
    private LazyOptional<IGerms> lazyOptional =  LazyOptional.of(() -> germHandler);
    private int germDupTime = ONIConfig.GERM_DUP_SPEED.get();

    public SlimeTile() {
        super(ONIBlocks.SlimeTE.get());
        germHandler.addGerm(EnumGermTypes.SLIMELUNG, 15000);
    }

    public static void reload() {}

    @Override
    public void tick() {
        germDupTime--;
        if (germDupTime < 0) {
            int currentGermAmount = germHandler.getGermAmount();
            germHandler.addGerm(EnumGermTypes.SLIMELUNG, 1);
            germDupTime = ONIConfig.GERM_DUP_SPEED.get();
            ONIUtils.LOGGER.info("germ amount" + currentGermAmount);
        }
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
