package wintersteve25.oniutils.common.contents.modules.blocks.te_bounded.oxylite;

import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.server.ServerWorld;
import wintersteve25.oniutils.api.ONIIHasProgress;
import wintersteve25.oniutils.common.capability.world_gas.WorldGasCapability;
import wintersteve25.oniutils.common.capability.world_gas.api.chemistry.Element;
import wintersteve25.oniutils.common.capability.world_gas.api.chemistry.GasStackWrapper;
import wintersteve25.oniutils.common.contents.base.ONIBaseBlock;
import wintersteve25.oniutils.common.contents.base.ONIBaseTE;
import wintersteve25.oniutils.common.contents.base.builders.ONIBlockBuilder;
import wintersteve25.oniutils.common.init.ONIBlocks;
import wintersteve25.oniutils.common.init.ONIConfig;
import wintersteve25.oniutils.common.utils.helpers.MiscHelper;

public class OxyliteTE extends ONIBaseTE implements ITickableTileEntity, ONIIHasProgress {
    private int totalProgress = -1;
    private int progress = totalProgress;

    public OxyliteTE() {
        super(ONIBlocks.TileEntityBounded.OXYLITE_TE.get());
    }

    @Override
    public void tick() {
        if (isServer()) {
            if (progress == -1) {
                Chunk chunk = world.getChunkAt(getPos());
                chunk.getCapability(WorldGasCapability.WORLD_GAS_CAP).ifPresent((cap) -> {
                    cap.addGasToChunk(new GasStackWrapper(Element.getGasStackFromElement(Element.O, 4000)), getPos());
                    totalProgress = ONIConfig.OXYLITE_COOLDOWN.get();
                    progress = totalProgress;
                    float i = MiscHelper.randomInRange(.5f, 1.f);
                    ((ServerWorld) getWorld()).spawnParticle(ParticleTypes.BUBBLE_COLUMN_UP, pos.getX() + i, pos.getY() + MiscHelper.randomInRange(1.2f, 1.6f), pos.getZ() + i, 10, 0, 0, 0, 0.005d);
                    if (MiscHelper.chanceHandling(8)) {
                        getWorld().destroyBlock(getPos(), false);
                    }
                });
            } else {
                progress--;
            }
        }
    }

    public static ONIBlockBuilder<ONIBaseBlock> createBlock() {
        return new ONIBlockBuilder<>(()->new ONIBaseBlock(1, 4, 10, "Oxylite"))
                .tileEntity(((state, world1) -> ONIBlocks.TileEntityBounded.OXYLITE_TE.get()), OxyliteTE.class);
    }

    @Override
    public int getProgress() {
        return progress;
    }

    @Override
    public void setProgress(int progress) {
        this.progress = progress;
    }

    @Override
    public int getTotalProgress() {
        return totalProgress;
    }

    @Override
    public void setTotalProgress(int totalProgress) {
        this.totalProgress = totalProgress;
    }
}
