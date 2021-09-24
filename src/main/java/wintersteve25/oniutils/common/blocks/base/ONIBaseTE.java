package wintersteve25.oniutils.common.blocks.base;

import mekanism.common.tile.base.TileEntityUpdateable;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import wintersteve25.oniutils.common.blocks.base.interfaces.ONIIForceStoppable;
import wintersteve25.oniutils.common.blocks.base.interfaces.ONIIHasProgress;
import wintersteve25.oniutils.common.blocks.base.interfaces.ONIIHasRedstoneOutput;
import wintersteve25.oniutils.common.blocks.base.interfaces.ONIIWorkable;

public abstract class ONIBaseTE extends TileEntityUpdateable implements ITickableTileEntity {

    public ONIBaseTE(TileEntityType<?> te) {
        super(te);
    }

    @Override
    public void tick() {
        if (this instanceof ONIIForceStoppable) {
            ONIIForceStoppable forceStoppable = (ONIIForceStoppable) this;
            if (forceStoppable.isInverted()) {
                forceStoppable.setForceStopped(!this.getWorldNN().isBlockPowered(pos));
            } else {
                forceStoppable.setForceStopped(this.getWorldNN().isBlockPowered(pos));
            }

            if (forceStoppable.getForceStopped()) {
                forceStoppable.setWorking(false);
                if (this instanceof ONIIHasProgress) {
                    ONIIHasProgress hasProgress = (ONIIHasProgress) this;
                    hasProgress.setProgress(0);
                }
            }
        }

    }

//    @Override
//    @Nullable
//    public SUpdateTileEntityPacket getUpdatePacket() {
//        return new SUpdateTileEntityPacket(this.getPos(), 3, this.getUpdateTag());
//    }
//
//    @Override
//    public CompoundNBT getUpdateTag() {
//        return this.write(new CompoundNBT());
//    }
//
//    @Override
//    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
//        super.onDataPacket(net, pkt);
//        if (world == null) return;
//        handleUpdateTag(world.getBlockState(pos),pkt.getNbtCompound());
//    }

    public void updateBlock(){
        if (world == null) return;
        BlockState state = world.getBlockState(pos);
        markDirty();
        world.notifyBlockUpdate(pos, state, state, 2);
    }

    @Override
    public void read(BlockState state, CompoundNBT tag) {
        if (this instanceof ONIIWorkable) {
            ONIIWorkable workable = (ONIIWorkable) this;
            workable.setWorking(tag.getBoolean("isWorking"));
            if (this instanceof ONIIHasProgress) {
                ONIIHasProgress hasProgress = (ONIIHasProgress) this;
                hasProgress.setProgress(tag.getInt("progress"));
            }
        }
        if (this instanceof ONIIHasRedstoneOutput) {
            ONIIHasRedstoneOutput redstoneOutput = (ONIIHasRedstoneOutput) this;
            redstoneOutput.setLowThreshold(tag.getInt("low_threshold"));
            redstoneOutput.setHighThreshold(tag.getInt("high_threshold"));
        }
        super.read(state, tag);
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        if (this instanceof ONIIWorkable) {
            ONIIWorkable workable = (ONIIWorkable) this;
            tag.putBoolean("isWorking", workable.getWorking());
            if (this instanceof ONIIHasProgress) {
                ONIIHasProgress hasProgress = (ONIIHasProgress) this;
                tag.putInt("progress", hasProgress.getProgress());
            }
        }
        if (this instanceof ONIIHasRedstoneOutput) {
            ONIIHasRedstoneOutput redstoneOutput = (ONIIHasRedstoneOutput) this;
            tag.putInt("low_threshold", redstoneOutput.lowThreshold());
            tag.putInt("high_threshold", redstoneOutput.highThreshold());
        }
        return super.write(tag);
    }
}
