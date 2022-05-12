package wintersteve25.oniutils.common.capability.world_gas;

import mekanism.api.chemical.gas.GasStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import wintersteve25.oniutils.common.capability.world_gas.api.IWorldGas;
import wintersteve25.oniutils.common.capability.world_gas.api.WorldGasImpl;
import wintersteve25.oniutils.common.capability.world_gas.api.chemistry.GasStackWrapper;
import wintersteve25.oniutils.common.utils.helpers.MiscHelper;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class WorldGasCapability {
    @CapabilityInject(IWorldGas.class)
    public static Capability<IWorldGas> WORLD_GAS_CAP;

    public static void register() {
        CapabilityManager.INSTANCE.register(IWorldGas.class, new Storage(), WorldGasImpl::new);
    }

    private static class Storage implements Capability.IStorage<IWorldGas> {
        @Nullable
        @Override
        public INBT writeNBT(Capability<IWorldGas> capability, IWorldGas instance, Direction side) {
            CompoundNBT nbt = new CompoundNBT();

            ListNBT gas = new ListNBT();
            ListNBT pos = new ListNBT();

            for (GasStackWrapper stack : instance.getGasMap().keySet()) {
                gas.add(stack.write(new CompoundNBT()));
                BlockPos position = instance.getGasMap().get(stack);
                pos.add(MiscHelper.writeBlockPos(position));
            }

            nbt.put("gasList", gas);
            nbt.put("posList", pos);

            return nbt;
        }

        @Override
        public void readNBT(Capability<IWorldGas> capability, IWorldGas instance, Direction side, INBT nbt) {
            CompoundNBT compoundNBT = (CompoundNBT) nbt;

            ListNBT gasListINBT = compoundNBT.getList("gasList", 0);
            ListNBT posListINBT = compoundNBT.getList("posList", 0);

            Map<GasStackWrapper, BlockPos> map = new HashMap<>();

            for (int i = 0; i < gasListINBT.size(); i++) {
                map.put(new GasStackWrapper(GasStack.readFromNBT((CompoundNBT) gasListINBT.get(i))), MiscHelper.readBlockPos((CompoundNBT) posListINBT.get(i)));
            }

            instance.setGasMap(map);
        }
    }
}
