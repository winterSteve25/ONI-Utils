package wintersteve25.oniutils.common.data.saved_data.circuits;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.ListTag;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.data.capabilities.plasma.api.EnumPlasmaTileType;
import wintersteve25.oniutils.common.utils.helpers.MiscHelper;

import java.util.ArrayList;
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

    public static Circuit createCircuit(Level world, int powerTransferLimit) {
        if (world.isClientSide()) return null;
        WorldCircuits cap = WorldCircuits.get((ServerLevel) world);
        return new Circuit(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), cap.getNextID(), powerTransferLimit);
    }

    public static Circuit mergeCircuits(Level world, Circuit c1, Circuit c2) {
        return c1.mergeCircuits(world, c2);
    }

    public boolean addCable(BlockPos pos) {
        return addToList(cables, pos);
    }

    public boolean addConsumer(BlockPos pos) {
        return addToList(consumers, pos);
    }

    public boolean addProducer(BlockPos pos) {
        return addToList(producers, pos);
    }

    public boolean addDynamic(BlockPos pos) {
        return addToList(dynamics, pos);
    }

    private boolean addToList(List<BlockPos> list, BlockPos pos) {
        if (isApartOfCircuit(pos)) {
            return false;
        }
        list.add(pos);
        return true;
    }

    public boolean addToCircuit(EnumPlasmaTileType type, BlockPos pos) {
        return switch (type) {
            case DYNAMIC -> addDynamic(pos);
            case PRODUCER -> addProducer(pos);
            case CONSUMER -> addConsumer(pos);
        };
    }

    public Circuit mergeCircuits(Level world, Circuit other) {
        if (world.isClientSide()) {
            ONIUtils.LOGGER.error("Failed to merge circuit with ID: {} and {} as tried to perform this operation on the client", id, other.id);
            return null;
        }

        if (other == null) {
            ONIUtils.LOGGER.error("Failed to merge circuit with ID: {} as second circuit doesn't exist", id);
            return null;
        }

        if (other.getId() == this.getId()) {
            return this;
        }

        WorldCircuits data = WorldCircuits.get((ServerLevel) world);

        for (var b : other.getCables()) {
            addCable(b);
        }
        for (var b : other.getConsumers()) {
            addConsumer(b);
        }
        for (var b : other.getProducers()) {
            addProducer(b);
        }
        for (var b : other.getDynamics()) {
            addDynamic(b);
        }

        data.removeCircuitByID(other.id);

        return this;
    }

    public void removeCable(ServerLevel level, BlockPos pos) {
        cables.remove(pos);
        if (cables.isEmpty()) {
            WorldCircuits data = WorldCircuits.get(level);
            data.removeCircuitByID(getId());
        }
    }

    public boolean isApartOfCircuit(BlockPos pos) {
        return getCables().contains(pos) || getConsumers().contains(pos) || getProducers().contains(pos) || getDynamics().contains(pos);
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

    public CompoundTag write() {
        CompoundTag nbt = new CompoundTag();

        ListTag cablePoses = new ListTag();
        for (BlockPos pos : cables) {
            cablePoses.add(MiscHelper.writeBlockPos(pos));
        }
        nbt.put("cablePoses", cablePoses);

        ListTag consumersPoses = new ListTag();
        for (BlockPos pos : consumers) {
            consumersPoses.add(MiscHelper.writeBlockPos(pos));
        }
        nbt.put("consumerPoses", consumersPoses);

        ListTag producerPoses = new ListTag();
        for (BlockPos pos : producers) {
            producerPoses.add(MiscHelper.writeBlockPos(pos));
        }
        nbt.put("producerPoses", producerPoses);

        ListTag dynamicPoses = new ListTag();
        for (BlockPos pos : dynamics) {
            dynamicPoses.add(MiscHelper.writeBlockPos(pos));
        }
        nbt.put("dynamicPoses", dynamicPoses);

        nbt.putInt("circuitID", id);
        nbt.putInt("powerTransferLimit", powerTransferLimit);

        return nbt;
    }

    public void read(CompoundTag nbt) {
        try {
            ListTag cablePoses = (ListTag) nbt.get("cablePoses");
            if (cablePoses == null) throw new IllegalArgumentException();
            cables = new ArrayList<>();
            for (Tag pos : cablePoses) {
                cables.add(MiscHelper.readBlockPos((CompoundTag) pos));
            }
            ListTag consumerPoses = (ListTag) nbt.get("consumerPoses");
            if (consumerPoses == null) throw new IllegalArgumentException();
            consumers = new ArrayList<>();
            for (Tag pos : consumerPoses) {
                consumers.add(MiscHelper.readBlockPos((CompoundTag) pos));
            }
            ListTag producerPoses = (ListTag) nbt.get("producerPoses");
            if (producerPoses == null) throw new IllegalArgumentException();
            producers = new ArrayList<>();
            for (Tag pos : producerPoses) {
                producers.add(MiscHelper.readBlockPos((CompoundTag) pos));
            }
            ListTag dynamicPoses = (ListTag) nbt.get("dynamicPoses");
            if (dynamicPoses == null) throw new IllegalArgumentException();
            dynamics = new ArrayList<>();
            for (Tag pos : dynamicPoses) {
                dynamics.add(MiscHelper.readBlockPos((CompoundTag) pos));
            }
            id = nbt.getInt("circuitID");
            powerTransferLimit = nbt.getInt("powerTransferLimit");
        } catch (Throwable e){
            ONIUtils.LOGGER.error("Error reading world circuit NBT: {}", nbt);
            e.printStackTrace();
        }
    }

    public static Circuit readFromNBT(CompoundTag nbt) {
        try {
            ListTag cablePoses = (ListTag) nbt.get("cablePoses");
            if (cablePoses == null) throw new IllegalArgumentException();
            List<BlockPos> cables = new ArrayList<>();
            for (Tag pos : cablePoses) {
                cables.add(MiscHelper.readBlockPos((CompoundTag) pos));
            }
            ListTag consumerPoses = (ListTag) nbt.get("consumerPoses");
            if (consumerPoses == null) throw new IllegalArgumentException();
            List<BlockPos> consumers = new ArrayList<>();
            for (Tag pos : consumerPoses) {
                consumers.add(MiscHelper.readBlockPos((CompoundTag) pos));
            }
            ListTag producerPoses = (ListTag) nbt.get("producerPoses");
            if (producerPoses == null) throw new IllegalArgumentException();
            List<BlockPos> producers = new ArrayList<>();
            for (Tag pos : producerPoses) {
                producers.add(MiscHelper.readBlockPos((CompoundTag) pos));
            }
            ListTag dynamicPoses = (ListTag) nbt.get("dynamicPoses");
            if (dynamicPoses == null) throw new IllegalArgumentException();
            List<BlockPos> dynamics = new ArrayList<>();
            for (Tag pos : dynamicPoses) {
                dynamics.add(MiscHelper.readBlockPos((CompoundTag) pos));
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
