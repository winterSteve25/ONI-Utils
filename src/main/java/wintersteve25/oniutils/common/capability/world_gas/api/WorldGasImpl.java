package wintersteve25.oniutils.common.capability.world_gas.api;

import mekanism.api.chemical.gas.Gas;
import mekanism.api.chemical.gas.GasStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

import java.util.HashMap;
import java.util.Map;

public class WorldGasImpl implements IWorldGas {
    private final Map<GasStack, BlockPos> gasMap = new HashMap<>();

    @Override
    public void update() {
    }

    @Override
    public Map<GasStack, BlockPos> getTable() {
        return gasMap;
    }

    @Override
    public Map<GasStack, Integer> getGasAtChunk(BlockPos pos) {
        Map<GasStack, Integer> output = new HashMap<>();
        for (GasStack gas : gasMap.keySet()) {
            BlockPos currentGasPos = gasMap.get(gas);
            if (currentGasPos.equals(pos)) {
                output.put(gas, currentGasPos.getY());
            }
        }

        return output;
    }

    @Override
    public Map<GasStack, Integer> getGasAtChunk(ChunkPos pos) {
        return null;
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
