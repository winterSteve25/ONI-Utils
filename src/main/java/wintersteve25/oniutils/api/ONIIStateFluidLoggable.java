package wintersteve25.oniutils.api;

import mekanism.common.block.states.CorrectingIntegerProperty;
import mekanism.common.block.states.IStateFluidLoggable;
import net.minecraft.fluid.Fluid;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraftforge.registries.ForgeRegistries;
import wintersteve25.oniutils.common.utils.AddReturnableList;

import javax.annotation.Nonnull;
import java.util.List;

public interface ONIIStateFluidLoggable extends IStateFluidLoggable {
    List<Fluid> valids = new AddReturnableList<Fluid>().addAllAndReturn(ForgeRegistries.FLUIDS);

    @Nonnull
    @Override
    default Fluid[] getSupportedFluids() {
        return valids.toArray(new Fluid[valids.size()]);
    }

    @Nonnull
    @Override
    default IntegerProperty getFluidLoggedProperty() {
        return CorrectingIntegerProperty.create(BlockStateProperties.WATERLOGGED.getName(), 0, getSupportedFluids().length);
    }
}
