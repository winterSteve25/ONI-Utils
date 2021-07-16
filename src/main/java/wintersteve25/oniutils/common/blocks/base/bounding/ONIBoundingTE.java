package wintersteve25.oniutils.common.blocks.base.bounding;

import mekanism.common.tile.base.TileEntityUpdateable;
import mekanism.common.util.NBTUtils;
import mekanism.common.util.WorldUtils;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import wintersteve25.oniutils.common.blocks.base.ONIBaseTE;
import wintersteve25.oniutils.common.init.ONIBlocks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

/**
 * Modified from https://github.com/mekanism/Mekanism/blob/1.16.x/src/main/java/mekanism/common/tile/TileEntityBoundingBlock.java
 */
public class ONIBoundingTE extends TileEntityUpdateable {

    private BlockPos mainPos;
    public boolean receivedCoords;
    private int currentRedstoneLevel;

    public ONIBoundingTE() {
        this(ONIBlocks.BOUNDING_TE.get());
    }

    public ONIBoundingTE(TileEntityType<ONIBoundingTE> type) {
        super(type);
        this.mainPos = BlockPos.ZERO;
    }

    public void setMainLocation(BlockPos pos) {
        this.receivedCoords = pos != null;
        if (!this.isRemote()) {
            this.mainPos = pos;
            this.sendUpdatePacket();
        }

    }

    public BlockPos getMainPos() {
        if (this.mainPos == null) {
            this.mainPos = BlockPos.ZERO;
        }

        return this.mainPos;
    }

    @Nullable
    public TileEntity getMainTile() {
        return this.receivedCoords ? WorldUtils.getTileEntity(this.world, this.getMainPos()) : null;
    }

    public void onNeighborChange(BlockState state) {
        TileEntity tile = this.getMainTile();
        if (tile instanceof ONIBaseTE) {
            int power = this.world.getRedstonePowerFromNeighbors(this.getPos());
            if (this.currentRedstoneLevel != power) {
                this.currentRedstoneLevel = power;
                this.sendUpdatePacket();
            }
        }

    }

    @Override
    public void read(@Nonnull BlockState state, @Nonnull CompoundNBT nbtTags) {
        super.read(state, nbtTags);
        NBTUtils.setBlockPosIfPresent(nbtTags, "main", (pos) -> {
            this.mainPos = pos;
        });
        this.currentRedstoneLevel = nbtTags.getInt("redstone");
        this.receivedCoords = nbtTags.getBoolean("receivedCoords");
    }

    @Nonnull
    @Override
    public CompoundNBT write(@Nonnull CompoundNBT nbtTags) {
        super.write(nbtTags);
        nbtTags.put("main", NBTUtil.writeBlockPos(this.getMainPos()));
        nbtTags.putInt("redstone", this.currentRedstoneLevel);
        nbtTags.putBoolean("receivedCoords", this.receivedCoords);
        return nbtTags;
    }

    @Nonnull
    @Override
    public CompoundNBT getReducedUpdateTag() {
        CompoundNBT updateTag = super.getReducedUpdateTag();
        updateTag.put("main", NBTUtil.writeBlockPos(this.getMainPos()));
        updateTag.putInt("redstone", this.currentRedstoneLevel);
        updateTag.putBoolean("receivedCoords", this.receivedCoords);
        return updateTag;
    }

    @Override
    public void handleUpdateTag(BlockState state, @Nonnull CompoundNBT tag) {
        super.handleUpdateTag(state, tag);
        NBTUtils.setBlockPosIfPresent(tag, "main", (pos) -> {
            this.mainPos = pos;
        });
        this.currentRedstoneLevel = tag.getInt("redstone");
        this.receivedCoords = tag.getBoolean("receivedCoords");
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (getMainTile() != null) {
            return getMainTile().getCapability(cap, side);
        }

        return LazyOptional.empty();
    }
}
