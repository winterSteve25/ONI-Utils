package wintersteve25.oniutils.common.capability.world_gas.api;

import mekanism.api.chemical.gas.Gas;
import mekanism.api.chemical.gas.GasStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import wintersteve25.oniutils.common.capability.world_gas.api.chemistry.Element;
import wintersteve25.oniutils.common.capability.world_gas.api.chemistry.GasStackWrapper;
import wintersteve25.oniutils.common.utils.helpers.MiscHelper;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorldGasImpl implements IWorldGas {
    @Nonnull
    private Map<GasStackWrapper, BlockPos> gasMap = new HashMap<>();

    @Override
    public void tick() {
        for (GasStackWrapper gasStack : gasMap.keySet()) {
            if (gasStack == null || gasStack.isEmpty()) return;
            BlockPos blockPos = gasMap.get(gasStack);
            spreadVertical(gasStack, blockPos);
        }
    }

    private void spreadHorizontal() {
    }

    private void spreadVertical(GasStackWrapper gas, BlockPos pos) {
        // Move 1/8 of the gas from current Y to the y++ and another 1/8 to y--
        // If the new position already have an existing gas, and it is not the same gas, get the density of both gas then the heavier one goes down, the other goes up
        // If the new position have the same gas, merge the two amounts and
        //TODO: Test if map auto updates with GasStackWrapper (.copy needed?)

        Element chemicals = Element.getFromRegName(gas.getTypeRegistryName());
        if (chemicals == null) return;

        List<GasStackWrapper> upKeys = MiscHelper.getKeysByValue(gasMap, pos.up());
        List<GasStackWrapper> downKeys = MiscHelper.getKeysByValue(gasMap, pos.down());

        GasStackWrapper newPosGasUp;
        GasStackWrapper newPosGasDown;

        if (upKeys.isEmpty()) {
            newPosGasUp = GasStackWrapper.EMPTY;
        } else {
            newPosGasUp = upKeys.get(0);
        }

        if (downKeys.isEmpty()) {
            newPosGasDown = GasStackWrapper.EMPTY;
        } else {
            newPosGasDown = downKeys.get(0);
        }

        long amountToSpread = gas.getAmount()/8;
        float currentGasDensity = chemicals.getAtomicMass()/gas.getAmount();

        moveGasOperation(newPosGasUp, gas, amountToSpread, currentGasDensity, true);
        moveGasOperation(newPosGasDown, gas, amountToSpread, currentGasDensity, false);

        for (GasStackWrapper stack : gasMap.keySet()) {
            System.out.println(stack + " " + gasMap.get(stack));
        }
    }

    private void moveGasOperation(GasStackWrapper newPosGas, GasStackWrapper currentGas, long amountToSpread, float currentGasDensity, boolean moveUp) {
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
                gasMap.put(new GasStackWrapper(new GasStack(currentGas.getType(), amountToSpread)), gasMap.get(currentGas).down());
            } else {
                if (!moveUp) return;
                currentGas.shrink(amountToSpread);
                gasMap.put(new GasStackWrapper(new GasStack(currentGas.getType(), amountToSpread)), gasMap.get(currentGas).up());
            }
        } else {
            moveGasWithNoObstacleOperation(newPosGas, currentGas, amountToSpread, moveUp);
        }
    }

    private void moveGasWithNoObstacleOperation(GasStackWrapper newPosGas, GasStackWrapper currentGas, long amountToSpread, boolean moveUp) {
        if (newPosGas == null || newPosGas.isEmpty()) {
            currentGas.shrink(amountToSpread);
            if (moveUp) {
                gasMap.put(new GasStackWrapper(new GasStack(currentGas.getType(), amountToSpread)), gasMap.get(currentGas).up());
            } else {
                gasMap.put(new GasStackWrapper(new GasStack(currentGas.getType(), amountToSpread)), gasMap.get(currentGas).down());
            }
        }
    }

    private void updateGas(BlockPos posToUpdate, GasStackWrapper newGasStackWrapper) {
        if (gasMap.containsValue(posToUpdate)) {
            gasMap.remove(MiscHelper.getKeysByValue(gasMap, posToUpdate).get(0));
        }
        gasMap.put(newGasStackWrapper, posToUpdate);
    }

    @Nonnull
    @Override
    public Map<GasStackWrapper, BlockPos> getGasMap() {
        return gasMap;
    }

    @Override
    public void setGasMap(@Nonnull Map<GasStackWrapper, BlockPos> map) {
        this.gasMap = map;
    }

    @Override
    public Map<GasStackWrapper, Integer> getGasAtChunk(BlockPos pos) {
        return getGasAtChunk(new ChunkPos(pos));
    }

    @Override
    public Map<GasStackWrapper, Integer> getGasAtChunk(ChunkPos pos) {
        Map<GasStackWrapper, Integer> output = new HashMap<>();
        for (GasStackWrapper gas : gasMap.keySet()) {
            BlockPos currentGasPos = gasMap.get(gas);
            if (new ChunkPos(currentGasPos).equals(pos)) {
                output.put(gas, currentGasPos.getY());
            }
        }

        return output;
    }

    @Override
    public int getYFromGas(Gas gas, ChunkPos pos) {
        for (GasStackWrapper gs : gasMap.keySet()) {
            BlockPos p = gasMap.get(gs);
            ChunkPos chunkPos = new ChunkPos(p);

            if (chunkPos.equals(pos)) {
                return p.getY();
            }
        }
        return -1;
    }

    @Override
    public boolean addGasToChunk(GasStackWrapper gas, BlockPos pos) {
        if (gas.isEmpty() || pos == null) return false;
        gasMap.put(gas, pos);
        return true;
    }

    @Override
    public boolean addGasToChunk(GasStackWrapper gas, ChunkPos pos) {
        return addGasToChunk(gas, pos.asBlockPos());
    }
}
