package wintersteve25.oniutils.common.capability.world_gas.api;

import mekanism.api.chemical.gas.Gas;
import mekanism.api.chemical.gas.GasStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import wintersteve25.oniutils.common.capability.world_gas.api.chemistry.Element;
import wintersteve25.oniutils.common.utils.helpers.MiscHelper;

import java.util.HashMap;
import java.util.Map;

public class WorldGasImpl implements IWorldGas {
    private final Map<GasStack, BlockPos> gasMap = new HashMap<>();

    @Override
    public void tick() {
        for (GasStack gasStack : gasMap.keySet()) {
            if (gasStack == null || gasStack.isEmpty()) return;
            BlockPos blockPos = gasMap.get(gasStack);
            spreadVertical(gasStack, blockPos);
        }
    }

    private void spreadHorizontal() {
    }

    private void spreadVertical(GasStack gas, BlockPos pos) {
        // Move 1/8 of the gas from current Y to the y++ and another 1/8 to y--
        // If the new position already have an existing gas, and it is not the same gas, get the density of both gas then the heavier one goes down, the other goes up
        // If the new position have the same gas, merge the two amounts and
        //TODO: Test if map auto updates with GasStack (.copy needed?)

        Element chemicals = Element.getFromRegName(gas.getTypeRegistryName());
        if (chemicals == null) return;

        GasStack newPosGasUp = MiscHelper.getKeysByValue(gasMap, pos.up()).get(0);
        GasStack newPosGasDown = MiscHelper.getKeysByValue(gasMap, pos.down()).get(0);

        long amountToSpread = gas.getAmount()/8;
        float currentGasDensity = chemicals.getAtomicMass()/gas.getAmount();

        moveGasOperation(newPosGasUp, gas, amountToSpread, currentGasDensity, true);
        moveGasOperation(newPosGasDown, gas, amountToSpread, currentGasDensity, false);
    }

    private void moveGasOperation(GasStack newPosGas, GasStack currentGas, long amountToSpread, float currentGasDensity, boolean moveUp) {
        if (newPosGas != null && !newPosGas.isEmpty() && newPosGas.getType() != currentGas.getType()) {
            Element chemicals = Element.getFromRegName(newPosGas.getTypeRegistryName());
            if (chemicals == null) {
                moveGasWithNoObstacleOperation(newPosGas, currentGas, amountToSpread, moveUp);
                return;
            }
            float newGasDensity = chemicals.getAtomicMass()/newPosGas.getAmount();
            boolean currentGasDenser = currentGasDensity > newGasDensity;
            if (currentGasDenser) {
                if (moveUp) return;
                currentGas.shrink(amountToSpread);
                gasMap.put(new GasStack(currentGas.getType(), amountToSpread), gasMap.get(currentGas).down());
            } else {
                if (!moveUp) return;
                currentGas.shrink(amountToSpread);
                gasMap.put(new GasStack(currentGas.getType(), amountToSpread), gasMap.get(currentGas).up());
            }
        }
    }

    private void moveGasWithNoObstacleOperation(GasStack newPosGas, GasStack currentGas, long amountToSpread, boolean moveUp) {
        if (newPosGas == null || newPosGas.isEmpty()) {
            currentGas.shrink(amountToSpread);
            if (moveUp) {
                gasMap.put(new GasStack(currentGas.getType(), amountToSpread), gasMap.get(currentGas).up());
            } else {
                gasMap.put(new GasStack(currentGas.getType(), amountToSpread), gasMap.get(currentGas).down());
            }
        }
    }

    private void updateGas(BlockPos posToUpdate, GasStack newGasStack) {
        if (gasMap.containsValue(posToUpdate)) {
            gasMap.remove(MiscHelper.getKeysByValue(gasMap, posToUpdate).get(0));
        }
        gasMap.put(newGasStack, posToUpdate);
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
