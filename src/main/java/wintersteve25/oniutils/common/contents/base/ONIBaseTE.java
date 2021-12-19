package wintersteve25.oniutils.common.contents.base;

import mekanism.common.tile.base.TileEntityUpdateable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import wintersteve25.oniutils.api.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ONIBaseTE extends TileEntityUpdateable implements ITickableTileEntity {

    public ONIBaseTE(TileEntityType<?> te) {
        super(te);
    }

    @Override
    public void tick() {
        if (!isServer()) return;
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

    @Override
    @Nullable
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.getPos(), 3, this.getUpdateTag());
    }

    @Override
    public void handleUpdateTag(BlockState state, @Nonnull CompoundNBT tag) {
        this.read(state, tag);
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.write(super.getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        if (world == null) return;
        handleUpdateTag(world.getBlockState(pos), pkt.getNbtCompound());
        super.onDataPacket(net, pkt);
    }

    public void updateBlock(){
        if (world == null) return;
        BlockState state = world.getBlockState(pos);
        world.notifyBlockUpdate(pos, state, state, 2);
        markDirty();
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
            if (this instanceof ONIIForceStoppable) {
                ONIIForceStoppable forceStoppable = (ONIIForceStoppable) this;
                forceStoppable.setForceStopped(tag.getBoolean("isForceStopped"));
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
            if (this instanceof ONIIForceStoppable) {
                ONIIForceStoppable forceStoppable = (ONIIForceStoppable) this;
                tag.putBoolean("isForceStopped", forceStoppable.getForceStopped());
            }
        }
        if (this instanceof ONIIHasRedstoneOutput) {
            ONIIHasRedstoneOutput redstoneOutput = (ONIIHasRedstoneOutput) this;
            tag.putInt("low_threshold", redstoneOutput.lowThreshold());
            tag.putInt("high_threshold", redstoneOutput.highThreshold());
        }
        return super.write(tag);
    }

    public boolean isServer() {
        return world != null && !world.isRemote();
    }

    public ITextComponent machineName() {
        if (world != null) {
            Block block = world.getBlockState(pos).getBlock();
            if (block instanceof ONIBaseMachine) {
                ONIIHasGui gui = ((ONIBaseMachine) block).getGui();
                return gui != null ? gui.machineName() : new StringTextComponent("");
            }
        }

        return new StringTextComponent("");
    }

    public void onHarvested(World world, BlockPos pos, BlockState state, PlayerEntity player) {
    }

    public void onBroken(BlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean isMoving) {
    }

    public void onPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
    }

    public void getWeakPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
    }

    public void onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTraceResult) {
    }

    public boolean canConnectRedstone(BlockState state, IBlockReader world, BlockPos pos, @Nullable Direction side) {
        return world.getBlockState(pos).getBlock().canConnectRedstone(state, world, pos, side);
    }
}
