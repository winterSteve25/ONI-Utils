package wintersteve25.oniutils.common.data.saved_data.circuits;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.ListTag;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.utils.helpers.MiscHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WorldCircuits extends SavedData {
    private Map<Integer, Circuit> circuits = new HashMap<>();
    private int ID = 0;

    public WorldCircuits() {
    }

    public WorldCircuits(CompoundTag tag) {
        try {
            ListTag cablePoses = (ListTag) tag.get("oni_circuits");
            if (cablePoses == null) {
                ONIUtils.LOGGER.error("Tried to read world circuit data but no data is present");
                return;
            }
            circuits = new HashMap<>();
            for (Tag pos : cablePoses) {
                Circuit circuit = Circuit.readFromNBT((CompoundTag) pos);
                if (circuit != null) {
                    circuits.putIfAbsent(circuit.getId(), circuit);
                }
            }
        } catch (ClassCastException e) {
            ONIUtils.LOGGER.error("Error reading world circuit NBT: {}", tag);
            e.printStackTrace();
        }
    }

    @Override
    public CompoundTag save(CompoundTag pCompoundTag) {
        ListTag cablePoses = new ListTag();

        for (Circuit circ : circuits.values()) {
            cablePoses.add(circ.write());
        }

        pCompoundTag.put("oni_circuits", cablePoses);
        pCompoundTag.putInt("oni_circuits_id", ID);

        return pCompoundTag;
    }

    public void addCircuits(Circuit... circuits) {
        for (Circuit circuit : circuits) {
            this.circuits.putIfAbsent(circuit.getId(), circuit);
        }
        setDirty();
    }

    public void replaceAndAddCircuits(Circuit... circuits) {
        for (Circuit circuit : circuits) {
            if (!MiscHelper.getKeysByValue(this.circuits, circuit).isEmpty()) {
                this.circuits.remove(circuit.getId());
            }
            this.circuits.put(circuit.getId(), circuit);
        }
        setDirty();
    }

    public List<Circuit> getCircuits() {
        return new ArrayList<>(circuits.values());
    }

    public Circuit getCircuitByID(int ID) {
        return circuits.get(ID);
    }

    public void removeCircuitByID(int ID) {
        circuits.remove(ID);
        setDirty();
    }

    public Circuit getCircuitWithCableAtPos(BlockPos pos) {
        return circuits.values().stream()
                .filter((circuit) -> circuit.getCables().contains(pos))
                .collect(Collectors.toList())
                .get(0);
    }

    public int getNextID() {
        return ID++;
    }

    public static WorldCircuits get(ServerLevel level) {
        DimensionDataStorage storage = level.getDataStorage();
        return storage.computeIfAbsent(WorldCircuits::new, WorldCircuits::new, "oni_world_circuits");
    }
}
