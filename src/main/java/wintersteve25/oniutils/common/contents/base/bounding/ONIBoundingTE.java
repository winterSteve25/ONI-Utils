package wintersteve25.oniutils.common.contents.base.bounding;

import mekanism.api.NBTConstants;
import mekanism.common.util.NBTUtils;
import mekanism.common.util.WorldUtils;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import wintersteve25.oniutils.common.contents.base.ONIBaseTE;
import wintersteve25.oniutils.common.registries.ONIBlocks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Modified from https://github.com/mekanism/Mekanism/blob/1.16.x/src/main/java/mekanism/common/tile/TileEntityBoundingBlock.java
 * Compatible with MIT License https://github.com/mekanism/Mekanism/blob/1.16.x/LICENSE
 */
public class ONIBoundingTE extends ONIBaseTE {

    private BlockPos mainPos;
    public boolean receivedCoords;

    public ONIBoundingTE(BlockPos pos, BlockState state) {
        this(ONIBlocks.Misc.BOUNDING_TE.get(), pos, state);
    }

    public ONIBoundingTE(BlockEntityType<ONIBoundingTE> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.mainPos = BlockPos.ZERO;
    }

    public void setMainLocation(BlockPos pos) {
        this.receivedCoords = pos != null;
        if (this.isServer()) {
            this.mainPos = pos;
            this.sendNBTUpdatePacket();
        }
    }

    public BlockPos getMainPos() {
        if (this.mainPos == null) {
            this.mainPos = BlockPos.ZERO;
        }

        return this.mainPos;
    }

    @Nullable
    public BlockEntity getMainTile() {
        return this.receivedCoords ? WorldUtils.getTileEntity(this.level, this.getMainPos()) : null;
    }

    @Nullable
    public ONIBaseTE getMainONITile() {
        return this.receivedCoords ? WorldUtils.getTileEntity(ONIBaseTE.class, this.level, this.getMainPos()) : null;
    }

    @Override
    public void load(@Nonnull CompoundTag nbtTags) {
        super.load(nbtTags);
        NBTUtils.setBlockPosIfPresent(nbtTags, "main", (pos) -> this.mainPos = pos);
        this.receivedCoords = nbtTags.getBoolean("receivedCoords");
    }

    @Nonnull
    @Override
    public void saveAdditional(@Nonnull CompoundTag nbtTags) {
        super.saveAdditional(nbtTags);
        nbtTags.put("main", NbtUtils.writeBlockPos(this.getMainPos()));
        nbtTags.putBoolean("receivedCoords", this.receivedCoords);
    }

    @Override
    public void handleUpdateTag(@Nonnull CompoundTag tag) {
        super.handleUpdateTag(tag);
        NBTUtils.setBlockPosIfPresent(tag, "main", pos -> mainPos = pos);
        receivedCoords = tag.getBoolean(NBTConstants.RECEIVED_COORDS);
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
