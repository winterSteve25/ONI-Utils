package wintersteve25.oniutils.common.blocks.base.interfaces;

import mekanism.common.block.states.CorrectingIntegerProperty;
import mekanism.common.block.states.IStateFluidLoggable;
import net.minecraft.fluid.Fluid;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.properties.BlockStateProperties;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public interface ONIIStateFluidLoggable extends IStateFluidLoggable {
    List<Fluid> valids = new ArrayList<>();

    @Nonnull
    @Override
    default Fluid[] getSupportedFluids() {
        return IStateFluidLoggable.super.getSupportedFluids();
    }

    @Nonnull
    @Override
    default IntegerProperty getFluidLoggedProperty() {
        return CorrectingIntegerProperty.create(BlockStateProperties.WATERLOGGED.getName(), 0, getSupportedFluids().length);
    }
}
