package wintersteve25.oniutils.common.capability.circuits.api;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.capability.circuits.CircuitCapability;
import wintersteve25.oniutils.common.capability.plasma.api.EnumPlasmaTileType;
import wintersteve25.oniutils.common.utils.helpers.MiscHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Each circuit is a power network that contains any number of cables, consumers, producers, or dynamic plasma users (both consume and produce)
 */
public class Circuit {
    private List<BlockPos> cables;
    private List<BlockPos> consumers;
    private List<BlockPos> producers;
    private List<BlockPos> dynamics;
    private int id;
    private int powerTransferLimit;

    protected Circuit(List<BlockPos> cables, List<BlockPos> consumers, List<BlockPos> producers, List<BlockPos> dynamics, int id, int powerTransferLimit) {
        this.cables = cables;
        this.consumers = consumers;
        this.producers = producers;
        this.dynamics = dynamics;
        this.id = id;
        this.powerTransferLimit = powerTransferLimit;
    }

    public static Circuit createCircuit(World world, int powerTransferLimit) {
        if (!world.getCapability(CircuitCapability.WORLD_CIRCUIT_CAPABILITY).isPresent()) return null;
        WorldCircuits cap = world.getCapability(CircuitCapability.WORLD_CIRCUIT_CAPABILITY).resolve().get();
        Circuit circuit = new Circuit(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), cap.getNextID(), powerTransferLimit);
        cap.addCircuits(circuit);
        return circuit;
    }

    public static Circuit mergeCircuits(World world, Circuit c1, Circuit c2) {
        return c1.mergeCircuits(world, c2);
    }

    public void addCables(BlockPos... pos) {
        cables.addAll(Arrays.asList(pos));
    }

    public void addConsumers(BlockPos... pos) {
        consumers.addAll(Arrays.asList(pos));
    }

    public void addProducers(BlockPos... pos) {
        producers.addAll(Arrays.asList(pos));
    }

    public void addDynamics(BlockPos... pos) {
        dynamics.addAll(Arrays.asList(pos));
    }

    public void addToCircuit(EnumPlasmaTileType type, BlockPos pos) {
        switch (type) {
            case DYNAMIC:
                addDynamics(pos);
                break;
            case PRODUCER:
                addProducers(pos);
                break;
            case CONSUMER:
                addConsumers(pos);
                break;
        }
    }

    public Circuit mergeCircuits(World world, Circuit other) {
        if (!world.getCapability(CircuitCapability.WORLD_CIRCUIT_CAPABILITY).isPresent()) {
            ONIUtils.LOGGER.error("Failed to merge circuit with ID: {} and {} as circuit capability in {} doesn't exist", id, other.id, world.getDimensionKey().getRegistryName());
            return null;
        }

        if (other == null) {
            ONIUtils.LOGGER.error("Failed to merge circuit with ID: {} as second circuit doesn't exist", id);
            return null;
        }

        WorldCircuits cap = world.getCapability(CircuitCapability.WORLD_CIRCUIT_CAPABILITY).resolve().get();

        cables.addAll(other.cables);
        consumers.addAll(other.consumers);
        producers.addAll(other.producers);
        dynamics.addAll(other.dynamics);

        cap.removeCircuitByID(other.id);

        return this;
    }

    public int getId() {
        return id;
    }

    public List<BlockPos> getCables() {
        return cables;
    }

    public List<BlockPos> getConsumers() {
        return consumers;
    }

    public List<BlockPos> getProducers() {
        return producers;
    }

    public List<BlockPos> getDynamics() {
        return dynamics;
    }

    public int getPowerTransferLimit() {
        return powerTransferLimit;
    }

    public CompoundNBT write() {
        CompoundNBT nbt = new CompoundNBT();

        ListNBT cablePoses = new ListNBT();
        for (BlockPos pos : cables) {
            cablePoses.add(MiscHelper.writeBlockPos(pos));
        }
        nbt.put("cablePoses", cablePoses);

        ListNBT consumersPoses = new ListNBT();
        for (BlockPos pos : consumers) {
            cablePoses.add(MiscHelper.writeBlockPos(pos));
        }
        nbt.put("consumerPoses", consumersPoses);

        ListNBT producerPoses = new ListNBT();
        for (BlockPos pos : producers) {
            cablePoses.add(MiscHelper.writeBlockPos(pos));
        }
        nbt.put("producerPoses", producerPoses);

        ListNBT dynamicPoses = new ListNBT();
        for (BlockPos pos : dynamics) {
            cablePoses.add(MiscHelper.writeBlockPos(pos));
        }
        nbt.put("dynamicPoses", dynamicPoses);

        nbt.putInt("circuitID", id);
        nbt.putInt("powerTransferLimit", powerTransferLimit);

        return nbt;
    }

    public void read(CompoundNBT nbt) {
        try {
            ListNBT cablePoses = (ListNBT) nbt.get("cablePoses");
            if (cablePoses == null) throw new IllegalArgumentException();
            cables = new ArrayList<>();
            for (INBT pos : cablePoses) {
                cables.add(MiscHelper.readBlockPos((CompoundNBT) pos));
            }
            ListNBT consumerPoses = (ListNBT) nbt.get("consumerPoses");
            if (consumerPoses == null) throw new IllegalArgumentException();
            consumers = new ArrayList<>();
            for (INBT pos : consumerPoses) {
                consumers.add(MiscHelper.readBlockPos((CompoundNBT) pos));
            }
            ListNBT producerPoses = (ListNBT) nbt.get("producerPoses");
            if (producerPoses == null) throw new IllegalArgumentException();
            producers = new ArrayList<>();
            for (INBT pos : producerPoses) {
                producers.add(MiscHelper.readBlockPos((CompoundNBT) pos));
            }
            ListNBT dynamicPoses = (ListNBT) nbt.get("dynamicPoses");
            if (dynamicPoses == null) throw new IllegalArgumentException();
            dynamics = new ArrayList<>();
            for (INBT pos : dynamicPoses) {
                dynamics.add(MiscHelper.readBlockPos((CompoundNBT) pos));
            }
            id = nbt.getInt("circuitID");
            powerTransferLimit = nbt.getInt("powerTransferLimit");
        } catch (Throwable e){
            ONIUtils.LOGGER.error("Error reading world circuit NBT: {}", nbt);
            e.printStackTrace();
        }
    }

    public static Circuit readFromNBT(CompoundNBT nbt) {
        try {
            ListNBT cablePoses = (ListNBT) nbt.get("cablePoses");
            if (cablePoses == null) throw new IllegalArgumentException();
            List<BlockPos> cables = new ArrayList<>();
            for (INBT pos : cablePoses) {
                cables.add(MiscHelper.readBlockPos((CompoundNBT) pos));
            }
            ListNBT consumerPoses = (ListNBT) nbt.get("consumerPoses");
            if (consumerPoses == null) throw new IllegalArgumentException();
            List<BlockPos> consumers = new ArrayList<>();
            for (INBT pos : consumerPoses) {
                consumers.add(MiscHelper.readBlockPos((CompoundNBT) pos));
            }
            ListNBT producerPoses = (ListNBT) nbt.get("producerPoses");
            if (producerPoses == null) throw new IllegalArgumentException();
            List<BlockPos> producers = new ArrayList<>();
            for (INBT pos : producerPoses) {
                producers.add(MiscHelper.readBlockPos((CompoundNBT) pos));
            }
            ListNBT dynamicPoses = (ListNBT) nbt.get("dynamicPoses");
            if (dynamicPoses == null) throw new IllegalArgumentException();
            List<BlockPos> dynamics = new ArrayList<>();
            for (INBT pos : dynamicPoses) {
                dynamics.add(MiscHelper.readBlockPos((CompoundNBT) pos));
            }
            int ID = nbt.getInt("circuitID");
            int powerTransferLimit = nbt.getInt("powerTransferLimit");
            return new Circuit(cables, consumers, producers, dynamics, ID, powerTransferLimit);
        } catch (Throwable e){
            ONIUtils.LOGGER.error("Error reading world circuit NBT: {}", nbt);
            e.printStackTrace();
        }

        return null;
    }
}
