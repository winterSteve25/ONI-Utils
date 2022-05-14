package wintersteve25.oniutils.common.data.capabilities.world_gas.api;

import mekanism.api.chemical.gas.Gas;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.ChunkPos;
import net.minecraftforge.common.util.INBTSerializable;
import wintersteve25.oniutils.common.data.capabilities.world_gas.api.chemistry.GasStackWrapper;

import java.util.Map;

public interface IWorldGas extends INBTSerializable<CompoundTag> {
    void update();

    Map<GasStackWrapper, BlockPos> getGasMap();

    void setGasMap(Map<GasStackWrapper, BlockPos> map);

    Map<GasStackWrapper, Integer> getGasAtChunk(BlockPos pos);

    Map<GasStackWrapper, Integer> getGasAtChunk(ChunkPos pos);

    int getYFromGas(Gas gas, ChunkPos pos);

    boolean addGasToChunk(GasStackWrapper gas, BlockPos pos);

    boolean addGasToChunk(GasStackWrapper gas, ChunkPos pos);
}
