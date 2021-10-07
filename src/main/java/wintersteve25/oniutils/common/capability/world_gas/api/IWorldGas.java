package wintersteve25.oniutils.common.capability.world_gas.api;

import mekanism.api.chemical.gas.Gas;
import mekanism.api.chemical.gas.GasStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

import java.util.Map;

public interface IWorldGas {
    Map<GasStack, BlockPos> getTable();

    GasStack getGasAtChunk(BlockPos pos);

    GasStack getGasAtChunk(ChunkPos pos);

    void update();

    BlockPos getBPosFromGas(Gas gas, ChunkPos pos);

    boolean addGasToChunk(BlockPos pos);

    boolean addGasToChunk(ChunkPos pos);
}
