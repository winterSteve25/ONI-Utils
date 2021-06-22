package wintersteve25.oniutils.common.blocks.modules.resources.slime;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.blocks.libs.IGermCapProvider;
import wintersteve25.oniutils.common.blocks.libs.ONIBaseTE;
import wintersteve25.oniutils.common.capability.germ.api.GermStack;
import wintersteve25.oniutils.common.capability.germ.GermsCapability;
import wintersteve25.oniutils.common.capability.germ.api.EnumGermTypes;
import wintersteve25.oniutils.common.capability.germ.api.IGerms;
import wintersteve25.oniutils.common.init.ONIBlocks;
import wintersteve25.oniutils.common.init.ONIConfig;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SlimeTile extends ONIBaseTE implements ITickableTileEntity, IGermCapProvider {

    private GermStack germHandler = new GermStack();
    private LazyOptional<IGerms> lazyOptional =  LazyOptional.of(() -> germHandler);
    private int germDupTime = ONIConfig.GERM_DUP_SPEED.get();

    public SlimeTile() {
        super(ONIBlocks.SlimeTE.get());

        germHandler.addGerm(EnumGermTypes.SLIMELUNG, 15000);
    }

    @Override
    public void tick() {
        if (!level.isClientSide()) {
            germDupTime--;
            if (germDupTime < 0) {
                int currentGermAmount = germHandler.getGermAmount();
                germHandler.addGerm(EnumGermTypes.SLIMELUNG, 1);
                germDupTime = ONIConfig.GERM_DUP_SPEED.get();
                ONIUtils.LOGGER.info("germ amount" + currentGermAmount);
            }
        }
    }

    @Override
    public void load(BlockState p_230337_1_, CompoundNBT p_230337_2_) {
        germHandler.read(p_230337_2_.getCompound("germs"));

        super.load(p_230337_1_, p_230337_2_);
    }

    @Override
    public CompoundNBT save(CompoundNBT p_189515_1_) {
        p_189515_1_.put("germs", germHandler.write());

        return super.save(p_189515_1_);
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
