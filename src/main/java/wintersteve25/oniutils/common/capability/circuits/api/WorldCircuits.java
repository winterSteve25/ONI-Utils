package wintersteve25.oniutils.common.capability.circuits.api;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.math.BlockPos;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.utils.helpers.MiscHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WorldCircuits {
    private Map<Integer, Circuit> circuits = new HashMap<>();
    private int ID = 0;

    public WorldCircuits() {
    }

    public void addCircuits(Circuit... circuits) {
        for (Circuit circuit : circuits) {
            this.circuits.putIfAbsent(circuit.getId(), circuit);
        }
    }

    public void replaceAndAddCircuits(Circuit... circuits) {
        for (Circuit circuit : circuits) {
            if (!MiscHelper.getKeysByValue(this.circuits, circuit).isEmpty()) {
                this.circuits.remove(circuit.getId());
            }
            this.circuits.put(circuit.getId(), circuit);
        }
    }

    public List<Circuit> getCircuits() {
        return new ArrayList<>(circuits.values());
    }

    public Circuit getCircuitByID(int ID) {
        try {
            return circuits.get(ID);
        } catch (Throwable e) {
            ONIUtils.LOGGER.error("Circuit of ID: {} is not found!", ID);
        }
        return null;
    }

    public void removeCircuitByID(int ID) {
        try {
            circuits.remove(ID);
        } catch (Throwable e) {
            ONIUtils.LOGGER.error("Failed to remove circuit with ID: {}, as it doesn't exist", ID);
        }
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

    public CompoundNBT write() {
        CompoundNBT nbt = new CompoundNBT();
        ListNBT cablePoses = new ListNBT();
        for (Circuit circ : circuits.values()) {
            cablePoses.add(circ.write());
        }
        nbt.put("oni_circuits", cablePoses);
        return nbt;
    }

    public void read(CompoundNBT nbt) {
        try {
            ListNBT cablePoses = (ListNBT) nbt.get("oni_circuits");
            if (cablePoses == null) throw new IllegalArgumentException();
            circuits = new HashMap<>();
            for (INBT pos : cablePoses) {
                Circuit circuit = Circuit.readFromNBT((CompoundNBT) pos);
                if (circuit != null) {
                    circuits.putIfAbsent(circuit.getId(), circuit);
                }
            }
        } catch (Throwable e) {
            ONIUtils.LOGGER.error("Error reading world circuit NBT: {}", nbt);
            e.printStackTrace();
        }
    }
}
