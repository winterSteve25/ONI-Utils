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
    public void tick() {

    }

    @Override
    public Map<GasStack, BlockPos> getGasMap() {
        return gasMap;
    }

    @Override
    public Map<GasStack, Integer> getGasAtChunk(BlockPos pos) {
        return getGasAtChunk(new ChunkPos(pos));
    }

    @Override
    public Map<GasStack, Integer> getGasAtChunk(ChunkPos pos) {
        Map<GasStack, Integer> output = new HashMap<>();
        for (GasStack gas : gasMap.keySet()) {
            BlockPos currentGasPos = gasMap.get(gas);
            if (new ChunkPos(currentGasPos).equals(pos)) {
                output.put(gas, currentGasPos.getY());
            }
        }

        return output;
    }

    @Override
    public int getYFromGas(Gas gas, ChunkPos pos) {
        for (GasStack gs : gasMap.keySet()) {
            BlockPos p = gasMap.get(gs);
            ChunkPos chunkPos = new ChunkPos(p);

            if (chunkPos.equals(pos)) {
                return p.getY();
            }
        }
        return -1;
    }

    @Override
    public boolean addGasToChunk(GasStack gas, BlockPos pos) {
        if (gas.isEmpty() || pos == null) return false;
        gasMap.put(gas, pos);
        return true;
    }

    @Override
    public boolean addGasToChunk(GasStack gas, ChunkPos pos) {
        return addGasToChunk(gas, pos.asBlockPos());
    }
}
