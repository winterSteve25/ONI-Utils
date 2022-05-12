package wintersteve25.oniutils.common.contents.modules.blocks.power.manual;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import wintersteve25.oniutils.api.ONIIHasProgress;
import wintersteve25.oniutils.api.ONIIWorkable;
import wintersteve25.oniutils.common.capability.plasma.PlasmaCapability;
import wintersteve25.oniutils.common.capability.plasma.api.EnumPlasmaTileType;
import wintersteve25.oniutils.common.capability.plasma.api.IPlasma;
import wintersteve25.oniutils.common.capability.plasma.api.PlasmaStack;
import wintersteve25.oniutils.common.contents.base.ONIBaseMachine;
import wintersteve25.oniutils.common.contents.base.ONIBaseTE;
import wintersteve25.oniutils.common.contents.base.bounding.ONIIBoundingBlock;
import wintersteve25.oniutils.common.contents.base.builders.ONIBlockBuilder;
import wintersteve25.oniutils.common.init.ONIConfig;
import wintersteve25.oniutils.common.utils.helpers.MiscHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ManualGenTE extends ONIBaseTE implements ITickableTileEntity, IAnimatable, ONIIBoundingBlock, ONIIHasProgress, ONIIWorkable {

    private final AnimationFactory manager = new AnimationFactory(this);
    private final PlasmaStack plasmaHandler = new PlasmaStack(2000, EnumPlasmaTileType.PRODUCER);
    private final LazyOptional<IPlasma> plasmaLazyOptional = LazyOptional.of(() -> plasmaHandler);

    public ManualGenEntity mountableEntity;

    private int progress = 0;
    private int totalProgress = ONIConfig.MANUAL_GEN_PROCESS_TIME.get();
    private boolean isWorking = false;

    public ManualGenTE() {
        super(null);
    }

    @Override
    public void tick() {
        if (isServer()) {
            if (getWorking()) {
                progress--;
                if (progress < 0) {
                    if (plasmaHandler.canGenerate(ONIConfig.MANUAL_GEN_PLASMA_OUTPUT.get())) {
                        plasmaHandler.addPower(ONIConfig.MANUAL_GEN_PLASMA_OUTPUT.get());
                    }

                    progress = ONIConfig.MANUAL_GEN_PROCESS_TIME.get();
                }
                if (mountableEntity.getPassengers().isEmpty()) {
                    setWorking(false);
                }
                updateBlock();
            }
        }
    }

    @Override
    public void read(BlockState state, CompoundNBT tag) {
        plasmaHandler.read(tag.getCompound("plasma"));

        super.read(state, tag);
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        tag.put("plasma", plasmaHandler.write());
        mountableEntity = ManualGenEntity.create(world, this.pos.add(0.5, 0, 0.5));
        getWorld().addEntity(mountableEntity);

        return super.write(tag);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == PlasmaCapability.POWER_CAPABILITY) {
            return plasmaLazyOptional.cast();
        }
        return super.getCapability(cap, side);
    }

    private <E extends TileEntity & IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        event.getController().transitionLengthTicks = 0;
        if (getWorking()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.manual_gen.new", true));
            return PlayState.CONTINUE;
        } else {
            event.getController().setAnimation(new AnimationBuilder());
            return PlayState.STOP;
        }
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return manager;
    }

    @Override
    public void onPlace() {
        Direction facing = getBlockState().get(BlockStateProperties.FACING);
        mountableEntity = ManualGenEntity.create(world, this.pos.add(0.5, 0, 0.5));
        switch (facing) {
            case SOUTH:
                MiscHelper.makeBoundingBlock(this.getWorld(), this.getPos().west(), this.getPos());
                MiscHelper.makeBoundingBlock(this.getWorld(), this.getPos().up(), this.getPos());
                MiscHelper.makeBoundingBlock(this.getWorld(), this.getPos().up().west(), this.getPos());
                break;
            case EAST:
                MiscHelper.makeBoundingBlock(this.getWorld(), this.getPos().south(), this.getPos());
                MiscHelper.makeBoundingBlock(this.getWorld(), this.getPos().up(), this.getPos());
                MiscHelper.makeBoundingBlock(this.getWorld(), this.getPos().up().south(), this.getPos());
                break;
            case WEST:
                MiscHelper.makeBoundingBlock(this.getWorld(), this.getPos().north(), this.getPos());
                MiscHelper.makeBoundingBlock(this.getWorld(), this.getPos().up(), this.getPos());
                MiscHelper.makeBoundingBlock(this.getWorld(), this.getPos().up().north(), this.getPos());
                break;
            default:
                MiscHelper.makeBoundingBlock(this.getWorld(), this.getPos().east(), this.getPos());
                MiscHelper.makeBoundingBlock(this.getWorld(), this.getPos().up(), this.getPos());
                MiscHelper.makeBoundingBlock(this.getWorld(), this.getPos().up().east(), this.getPos());
                break;
        }

        getWorld().addEntity(mountableEntity);
    }

    @Override
    public void onBreak(BlockState blockState) {
        if (this.world != null) {
            Direction facing = getBlockState().get(BlockStateProperties.FACING);

            if (mountableEntity != null) {
                mountableEntity.removePassengers();
                mountableEntity.remove();
            }

            switch (facing) {
                case NORTH:
                    this.world.removeBlock(this.getPos().east(), false);
                    this.world.removeBlock(this.getPos().up(), false);
                    this.world.removeBlock(this.getPos().up().east(), false);
                    break;
                case SOUTH:
                    this.world.removeBlock(this.getPos().west(), false);
                    this.world.removeBlock(this.getPos().up(), false);
                    this.world.removeBlock(this.getPos().up().west(), false);
                    break;
                case EAST:
                    this.world.removeBlock(this.getPos().south(), false);
                    this.world.removeBlock(this.getPos().up(), false);
                    this.world.removeBlock(this.getPos().up().south(), false);
                    break;
                case WEST:
                    this.world.removeBlock(this.getPos().north(), false);
                    this.world.removeBlock(this.getPos().up(), false);
                    this.world.removeBlock(this.getPos().up().north(), false);
                    break;
            }
        }
    }

    @Override
    public int getProgress() {
        return progress;
    }

    @Override
    public void setProgress(int progress) {
        this.progress = progress;
    }

    @Override
    public int getTotalProgress() {
        return totalProgress;
    }

    @Override
    public void setTotalProgress(int progress) {
        this.totalProgress = progress;
    }

    @Override
    public boolean getWorking() {
        return isWorking;
    }

    @Override
    public void setWorking(boolean isWorking) {
        this.isWorking = isWorking;
    }

    private static final VoxelShape NORTH = VoxelShapes.or(Block.makeCuboidShape(0, 0, 0, 32, 1, 16),
            Block.makeCuboidShape(2, 31, 1, 30, 32, 14),
            Block.makeCuboidShape(0, 3, 1, 2, 32, 14),
            Block.makeCuboidShape(30, 3, 1, 32, 32, 14),
            Block.makeCuboidShape(2, 3, 0, 30, 31, 1)).simplify();

    public static ONIBlockBuilder<ONIBaseMachine> createBlock() {
//        return new ONIBlockBuilder<>(() -> new ONIBaseMachine("Manual Generator", AbstractBlock.Properties.create(Material.IRON).harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.4F, 5).setRequiresTool().notSolid()), ONIConstants.Geo.MANUAL_GEN_ISTER, true)
//                .placementCondition(ONIConstants.PlacementConditions::fourByFourCondition)
//                .renderType((state) -> BlockRenderType.ENTITYBLOCK_ANIMATED)
//                .autoRotateShape()
//                .shape((state, world, pos, ctx) -> NORTH)
//                .tileEntity((state, world) -> ONIBlocks.Machines.Power.MANUAL_GEN_TE.get(), ManualGenTE.class)
//                .noModelGen()
//                .setCategory(ONIIItem.ItemCategory.POWER)
//                .tooltip(LangHelper.itemTooltip(ONIConstants.LangKeys.MANUAL_GEN))
//                .shiftToolTip();
        return null;
    }

    @Override
    public void onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTraceResult) {
        player.startRiding(mountableEntity);
        setWorking(true);
    }
}
