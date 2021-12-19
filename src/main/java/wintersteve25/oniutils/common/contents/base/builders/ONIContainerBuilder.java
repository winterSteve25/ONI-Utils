package wintersteve25.oniutils.common.contents.base.builders;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.network.IContainerFactory;
import wintersteve25.oniutils.common.utils.SlotArrangement;
import wintersteve25.oniutils.common.utils.helpers.MiscHelper;
import wintersteve25.oniutils.common.utils.helpers.RegistryHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ONIContainerBuilder {

    private boolean shouldAddPlayerSlots = true;
    private boolean shouldTrackPower = false;
    private boolean shouldTrackPowerCapacity = false;
    private boolean shouldTrackWorking = false;
    private boolean shouldTrackProgress = false;
    private boolean shouldTrackTotalProgress = false;
    private boolean shouldAddInternalInventory = false;
    private List<SlotArrangement> internalSlotArrangement = new ArrayList<>();
    private Tuple<Integer, Integer> playerSlotStart = new Tuple<>(8, 88);

    private final String regName;
    private final RegistryObject<ContainerType<ONIAbstractContainer>> registryObject;

    public ONIContainerBuilder(String regName) {
        this.regName = regName;
        registryObject = buildContainerType();
    }

    public ONIContainerBuilder disablePlayerSlots() {
        this.shouldAddPlayerSlots = false;
        return this;
    }

    public ONIContainerBuilder trackPower() {
        this.shouldTrackPower = true;
        return this;
    }

    public ONIContainerBuilder trackPowerCapacity() {
        this.shouldTrackPowerCapacity = true;
        return this;
    }

    public ONIContainerBuilder trackWorking() {
        this.shouldTrackWorking = true;
        return this;
    }

    public ONIContainerBuilder trackProgress() {
        this.shouldTrackProgress = true;
        return this;
    }

    public ONIContainerBuilder trackTotalProgress() {
        this.shouldTrackTotalProgress = true;
        return this;
    }

    public ONIContainerBuilder addInternalInventory() {
        this.shouldAddInternalInventory = true;
        return this;
    }

    public ONIContainerBuilder setInternalSlotArrangement(SlotArrangement... internalSlotArrangement) {
        this.internalSlotArrangement = Arrays.asList(internalSlotArrangement);
        return this;
    }

    public ONIContainerBuilder setPlayerSlotStart(Tuple<Integer, Integer> playerSlotStart) {
        this.playerSlotStart = playerSlotStart;
        return this;
    }

    private RegistryObject<ContainerType<ONIAbstractContainer>> buildContainerType() {
        return RegistryHelper.registerContainer(MiscHelper.langToReg(regName), () -> IForgeContainerType.create(buildFactory()));
    }

    public IContainerFactory<ONIAbstractContainer> buildFactory() {
        return (windowId, inv, data) -> build(windowId, inv.player.world, data.readBlockPos(), inv, inv.player);
    }

    public ONIAbstractContainer build(int windowID, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity player) {
        return new ONIAbstractContainer(
                windowID,
                world,
                pos,
                playerInventory,
                player,
                registryObject.get(),
                shouldAddPlayerSlots,
                shouldTrackPower,
                shouldTrackPowerCapacity,
                shouldTrackWorking,
                shouldTrackProgress,
                shouldTrackTotalProgress,
                shouldAddInternalInventory,
                internalSlotArrangement,
                playerSlotStart
        );
    }

    public RegistryObject<ContainerType<ONIAbstractContainer>> getContainerTypeRegistryObject() {
        return registryObject;
    }
}
