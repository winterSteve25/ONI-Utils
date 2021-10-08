package wintersteve25.oniutils.common.capability.world_gas.api;

import mekanism.api.chemical.gas.Gas;
import mekanism.api.chemical.gas.GasStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

import java.util.Map;

public interface IWorldGas {
    void update();

    Map<GasStack, BlockPos> getTable();

    Map<GasStack, Integer> getGasAtChunk(BlockPos pos);

    Map<GasStack, Integer> getGasAtChunk(ChunkPos pos);

    BlockPos getBPosFromGas(Gas gas, ChunkPos pos);

    boolean addGasToChunk(BlockPos pos);

    boolean addGasToChunk(ChunkPos pos);
}
