package wintersteve25.oniutils.common.contents.modules.power.manual;

import mekanism.common.tile.interfaces.IBoundingBlock;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.PointOfView;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.Effects;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import wintersteve25.oniutils.client.keybinds.ONIKeybinds;
import wintersteve25.oniutils.common.contents.base.ONIBaseTE;
import wintersteve25.oniutils.api.ONIIHasProgress;
import wintersteve25.oniutils.common.capability.plasma.PlasmaCapability;
import wintersteve25.oniutils.common.capability.plasma.api.EnumWattsTypes;
import wintersteve25.oniutils.common.capability.plasma.api.IPlasma;
import wintersteve25.oniutils.common.capability.plasma.api.PlasmaStack;
import wintersteve25.oniutils.common.init.ONIBlocks;
import wintersteve25.oniutils.common.init.ONIConfig;
import wintersteve25.oniutils.common.utils.helpers.MiscHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ManualGenTE extends ONIBaseTE implements ITickableTileEntity, IAnimatable, IBoundingBlock, ONIIHasProgress {

    private final AnimationFactory manager = new AnimationFactory(this);
    private PlasmaStack plasmaHandler = new PlasmaStack(2000, EnumWattsTypes.LOW);
    private LazyOptional<IPlasma> plasmaLazyOptional = LazyOptional.of(() -> plasmaHandler);

    public boolean hasPlayer = false;

    private BlockState state;
    private BlockPos pos;
    private ServerPlayerEntity player;

    private int progress = 0;
    private int totalProgress = ONIConfig.COAL_GEN_PROCESS_TIME.get();
    private boolean isForceStopped = false;
    private boolean isInverted = false;
    private boolean isWorking = false;

    public ManualGenTE() {
        super(ONIBlocks.Machines.Power.MANUAL_GEN_TE.get());
    }

    @Override
    public void tick() {
        if (!world.isRemote()) {
            if (ONIKeybinds.offManualGen.isPressed()) {
                hasPlayer = false;
            }

            if (hasPlayer) {
                Direction facing = state.get(BlockStateProperties.FACING);
                switch (facing) {
                    case SOUTH:
                        player.teleport((ServerWorld) world,  pos.getX()-0.3, pos.getY(), pos.getZ()-0.3, -90, 4);
                    case WEST:
                        player.teleport((ServerWorld) world,  pos.getX()+0.2, pos.getY(), pos.getZ()-0.3, -90, 4);
                    case EAST:
                        player.teleport((ServerWorld) world,  pos.getX(), pos.getY(), pos.getZ()+0.3, -90, 4);
                    default:
                        player.teleport((ServerWorld) world,  pos.getX()+0.3, pos.getY(), pos.getZ()+0.3, 90, 4);
                }
                setWorking(true);
                progress--;
                if (progress < 0) {
                    if (player.isPotionActive(Effects.SPEED)) {
                        if (plasmaHandler.canGenerate(ONIConfig.MANUAL_GEN_PLASMA_OUTPUT_SPEED.get())) {
                            plasmaHandler.addPower(ONIConfig.MANUAL_GEN_PLASMA_OUTPUT_SPEED.get());

                            markDirty();
                        }
                    } else {
                        if (plasmaHandler.canGenerate(ONIConfig.MANUAL_GEN_PLASMA_OUTPUT.get())) {
                            plasmaHandler.addPower(ONIConfig.MANUAL_GEN_PLASMA_OUTPUT.get());

                            markDirty();
                        }
                    }

                    progress = ONIConfig.MANUAL_GEN_PROCESS_TIME.get();
                }

                markDirty();
            }
        }
    }

    public void getOnMill(PlayerEntity playerEntity, BlockPos pos, BlockState state) {
        if (!isWorking) {
            hasPlayer = true;

            if (pos != null && state != null && playerEntity != null) {

                this.player = (ServerPlayerEntity) playerEntity;
                this.state = state;
                this.pos = pos;

                playerEntity.sendStatusMessage(new TranslationTextComponent("oniutils.message.manualGen"), true);
                Minecraft.getInstance().gameSettings.setPointOfView(PointOfView.THIRD_PERSON_BACK);
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

        switch (facing) {
            case NORTH:
                MiscHelper.makeBoundingBlock(this.getWorld(), this.getPos().east(), this.getPos());
                MiscHelper.makeBoundingBlock(this.getWorld(), this.getPos().up(), this.getPos());
                MiscHelper.makeBoundingBlock(this.getWorld(), this.getPos().up().east(), this.getPos());
                break;
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
        }
    }

    @Override
    public void onBreak(BlockState blockState) {
        if (this.world != null) {
            Direction facing = getBlockState().get(BlockStateProperties.FACING);

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
                    this.world.removeBlock(this.getPos(), false);
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
    public boolean getForceStopped() {
        return isForceStopped;
    }

    @Override
    public void setForceStopped(boolean forceStopped) {
        this.isForceStopped = forceStopped;
    }

    @Override
    public boolean isInverted() {
        return isInverted;
    }

    @Override
    public void toggleInverted() {
        isInverted = !isInverted;
    }

    @Override
    public boolean getWorking() {
        return isWorking;
    }

    @Override
    public void setWorking(boolean isWorking) {
        this.isWorking = isWorking;
    }
}
