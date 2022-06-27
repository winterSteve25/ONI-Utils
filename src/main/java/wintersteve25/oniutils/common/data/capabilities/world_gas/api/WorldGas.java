package wintersteve25.oniutils.common.data.capabilities.world_gas.api;

import mekanism.api.chemical.gas.Gas;
import mekanism.api.chemical.gas.GasStack;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.level.ChunkPos;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.data.capabilities.world_gas.api.chemistry.Element;
import wintersteve25.oniutils.common.data.capabilities.world_gas.api.chemistry.GasStackWrapper;
import wintersteve25.oniutils.common.utils.helpers.MiscHelper;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorldGas implements IWorldGas {
    @Nonnull
    private Map<GasStackWrapper, BlockPos> gasMap = new HashMap<>();
    private HashMap<GasStackWrapper, BlockPos> gasMapUpdated = new HashMap<>();
    private int updateCount = 0;

    @Override
    public void update() {
        if (gasMap.isEmpty()) return;
        updateCount++;
        gasMapUpdated = new HashMap<>(gasMap);
        for (Map.Entry<GasStackWrapper, BlockPos> entry : gasMap.entrySet()) {
            if (entry == null) continue;
            GasStackWrapper wrapper = entry.getKey();
            Element chemicals = Element.getFromRegName(wrapper.getTypeRegistryName());
            if (chemicals == null) return;
            BlockPos blockPos = entry.getValue();
            if (wrapper.isEmpty()) return;
            spreadVertical(wrapper, blockPos, chemicals);
            spreadHorizontal(wrapper, blockPos);
        }
        gasMap = gasMapUpdated;
    }

    private void spreadHorizontal(GasStackWrapper gas, BlockPos pos) {
    }

    private void spreadVertical(GasStackWrapper gas, BlockPos pos, Element chemicals) {
        // Move 1/8 of the gas from current Y to the y++ and another 1/8 to y--
        List<GasStackWrapper> upKeys = MiscHelper.getKeysByValue(gasMapUpdated, pos.above());
        List<GasStackWrapper> downKeys = MiscHelper.getKeysByValue(gasMapUpdated, pos.below());

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

        long amountToSpread = gas.getAmount() / 8;
        float currentGasDensity = chemicals.getAtomicMass() / gas.getAmount();

        moveGasOperation(newPosGasUp, amountToSpread, currentGasDensity, true, false, pos);
        moveGasOperation(newPosGasDown, amountToSpread, currentGasDensity, false, false, pos);

        for (GasStackWrapper stack : gasMapUpdated.keySet()) {
            ONIUtils.LOGGER.debug("Verticle Update " + updateCount + ": " + stack.getStack() + " " + gasMapUpdated.get(stack));
        }
    }

    private void moveGasOperation(GasStackWrapper newPosGas, long amountToSpread, float currentGasDensity, boolean moveUpOrLeft, boolean horizontal, BlockPos currentPosition) {
        // If the new position already have an existing gas, and it is not the same gas, get the density of both gas then the heavier one goes down, the other goes up
        // If the new position have the same gas, merge the two amounts and
        List<GasStackWrapper> copiedMapCurrentGasKeys = MiscHelper.getKeysByValue(gasMapUpdated, currentPosition);
        if (copiedMapCurrentGasKeys.isEmpty()) {
            return;
        }
        GasStackWrapper currentGas = copiedMapCurrentGasKeys.get(0);
        if (currentGas.isEmpty()) return;

        if (newPosGas != null && !newPosGas.isEmpty()) {
            if (newPosGas.getType() != currentGas.getType()) {
                Element chemicals = Element.getFromRegName(newPosGas.getTypeRegistryName());
                if (chemicals == null) {
                    moveGasWithNoObstacleOperation(newPosGas, currentGas, amountToSpread, moveUpOrLeft, horizontal);
                    return;
                }
                if (!horizontal) {
                    float newGasDensity = chemicals.getAtomicMass() / newPosGas.getAmount();
                    if (currentGasDensity > newGasDensity) {
                        if (moveUpOrLeft) return;
                        currentGas.shrink(amountToSpread);
                        modifyGasAmount(currentPosition.below(), new GasStackWrapper(new GasStack(currentGas.getType(), amountToSpread)), true);
                    } else {
                        if (!moveUpOrLeft) return;
                        currentGas.shrink(amountToSpread);
                        modifyGasAmount(currentPosition.above(), new GasStackWrapper(new GasStack(currentGas.getType(), amountToSpread)), true);
                    }
                } else {
                    // stuff
                }
            } else {
                if (horizontal) {
                    if (moveUpOrLeft) {
                        currentGas.shrink(amountToSpread);
                        modifyGasAmount(currentPosition.above(), new GasStackWrapper(new GasStack(currentGas.getType(), amountToSpread)), true);
                    } else {
                        currentGas.shrink(amountToSpread);
                        modifyGasAmount(currentPosition.below(), new GasStackWrapper(new GasStack(currentGas.getType(), amountToSpread)), true);
                    }
                }
            }
        } else {
            moveGasWithNoObstacleOperation(newPosGas, currentGas, amountToSpread, moveUpOrLeft, horizontal);
        }
    }

    private void moveGasWithNoObstacleOperation(GasStackWrapper newPosGas, GasStackWrapper currentGas, long amountToSpread, boolean moveUp, boolean horizontal) {
        if (newPosGas == null || newPosGas.isEmpty()) {
            currentGas.shrink(amountToSpread);
            if (moveUp) {
                updateGas(gasMapUpdated.get(currentGas).above(), new GasStackWrapper(new GasStack(currentGas.getType(), amountToSpread)));
            } else {
                updateGas(gasMapUpdated.get(currentGas).below(), new GasStackWrapper(new GasStack(currentGas.getType(), amountToSpread)));
            }
        }
    }

    private void updateGas(BlockPos posToUpdate, GasStackWrapper newGasStackWrapper) {
        if (gasMapUpdated.containsValue(posToUpdate)) {
            gasMapUpdated.remove(MiscHelper.getKeysByValue(gasMapUpdated, posToUpdate).get(0));
        }
        gasMapUpdated.put(newGasStackWrapper, posToUpdate);
    }

    private void updateGasRaw(BlockPos posToUpdate, GasStackWrapper newGasStackWrapper) {
        if (gasMap.containsValue(posToUpdate)) {
            gasMap.remove(MiscHelper.getKeysByValue(gasMap, posToUpdate).get(0));
        }
        gasMap.put(newGasStackWrapper, posToUpdate);
    }

    private void modifyGasAmount(BlockPos posToUpdate, GasStackWrapper updateTo, boolean grow) {
        if (gasMapUpdated.containsValue(posToUpdate)) {
            GasStackWrapper wrapper = MiscHelper.getKeysByValue(gasMapUpdated, posToUpdate).get(0);
            if (grow) {
                wrapper.grow(updateTo.getAmount());
            } else {
                wrapper.shrink(updateTo.getAmount());
            }
        } else {
            updateGas(posToUpdate, updateTo);
        }
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
        updateGasRaw(pos, gas);
        return true;
    }

    @Override
    public boolean addGasToChunk(GasStackWrapper gas, ChunkPos pos) {
        return addGasToChunk(gas, pos.getWorldPosition());
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();

        ListTag gas = new ListTag();
        ListTag pos = new ListTag();

        for (GasStackWrapper stack : getGasMap().keySet()) {
            gas.add(stack.write(new CompoundTag()));
            BlockPos position = getGasMap().get(stack);
            pos.add(NbtUtils.writeBlockPos(position));
        }

        nbt.put("gasList", gas);
        nbt.put("posList", pos);

        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        ListTag gasListINBT = nbt.getList("gasList", 0);
        ListTag posListINBT = nbt.getList("posList", 0);

        Map<GasStackWrapper, BlockPos> map = new HashMap<>();

        for (int i = 0; i < gasListINBT.size(); i++) {
            map.put(new GasStackWrapper(GasStack.readFromNBT((CompoundTag) gasListINBT.get(i))), NbtUtils.readBlockPos((CompoundTag) posListINBT.get(i)));
        }

        setGasMap(map);
    }
}
