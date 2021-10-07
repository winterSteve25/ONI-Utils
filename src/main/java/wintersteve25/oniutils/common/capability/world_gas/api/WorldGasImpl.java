package wintersteve25.oniutils.common.capability.world_gas.api;

import mekanism.api.chemical.gas.Gas;
import mekanism.api.chemical.gas.GasStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

import java.util.Map;

public class WorldGasImpl implements IWorldGas {
    

    @Override
    public Map<GasStack, BlockPos> getTable() {
        return null;
    }

    @Override
    public GasStack getGasAtChunk(BlockPos pos) {
        return null;
    }

    @Override
    public GasStack getGasAtChunk(ChunkPos pos) {
        return null;
    }

    @Override
    public void update() {

    }

    @Override
    public BlockPos getBPosFromGas(Gas gas, ChunkPos pos) {
        return null;
    }

    @Override
    public boolean addGasToChunk(BlockPos pos) {
        return false;
    }

    @Override
    public boolean addGasToChunk(ChunkPos pos) {
        return false;
    }
}
