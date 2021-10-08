package wintersteve25.oniutils.common.blocks.base.interfaces;

import mekanism.api.chemical.gas.GasStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

/**
 * Should be implemented in a {@link net.minecraft.tileentity.TileEntity}
 */
public interface ONIIMachine {
    List<MachineType> machineType();

    default boolean hasPower() {
        return isPowerConsumer() || isPowerProducer();
    }

    default boolean isPowerProducer() {
        return machineType().contains(ONIIMachine.MachineType.POWER_PRODUCER);
    }

    default boolean isPowerConsumer() {
        return machineType().contains(ONIIMachine.MachineType.POWER_CONSUMER);
    }

    default boolean isGasProducer() {
        return machineType().contains(ONIIMachine.MachineType.GAS_PRODUCER);
    }

    default boolean isGasConsumer() {
        return machineType().contains(ONIIMachine.MachineType.GAS_CONSUMER);
    }

    default boolean isLiquidConsumer() {
        return machineType().contains(ONIIMachine.MachineType.LIQUID_CONSUMER);
    }

    default boolean isLiquidProducer() {
        return machineType().contains(ONIIMachine.MachineType.LIQUID_PRODUCER);
    }

    default int producingPower() {
        return 0;
    }

    default int consumingPower() {
        return 0;
    }

    default GasStack producingGas() {
        return GasStack.EMPTY;
    }

    default GasStack consumingGas() {
        return GasStack.EMPTY;
    }

    default FluidStack producingLiquid() {
        return FluidStack.EMPTY;
    }

    default FluidStack consumingLiquid() {
        return FluidStack.EMPTY;
    }

    enum MachineType {
        POWER_PRODUCER,
        POWER_CONSUMER,
        GAS_PRODUCER,
        GAS_CONSUMER,
        LIQUID_PRODUCER,
        LIQUID_CONSUMER
    }
}
