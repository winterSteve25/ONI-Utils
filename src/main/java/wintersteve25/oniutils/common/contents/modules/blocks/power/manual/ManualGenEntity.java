package wintersteve25.oniutils.common.contents.modules.blocks.power.manual;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import wintersteve25.oniutils.common.init.ONIEntities;

public class ManualGenEntity extends Entity {
    public ManualGenEntity(EntityType<ManualGenEntity> entityEntityType, World world) {
        super(entityEntityType, world);
        noClip = true;
    }

    public static ManualGenEntity create(World world, BlockPos pos) {
//        ManualGenEntity instance = new ManualGenEntity(ONIEntities.BlockBounded.MANUAL_GEN_ENTITY.get(), world);
//        instance.setPosition(pos.getX(), pos.getY(), pos.getZ());
//        return instance;
        return null;
    }

    @Override
    public void removePassengers() {
        super.removePassengers();
        this.remove(false);
    }

    @Override
    protected void registerData() {}

    @Override
    protected void readAdditional(CompoundNBT tag) {}

    @Override
    protected void writeAdditional(CompoundNBT tag) {}

    @Override
    public IPacket<?> createSpawnPacket()
    {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
