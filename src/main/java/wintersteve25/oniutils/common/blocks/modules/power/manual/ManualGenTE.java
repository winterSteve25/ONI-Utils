package wintersteve25.oniutils.common.blocks.modules.power.manual;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.Effects;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.client.keybinds.ONIKeybinds;
import wintersteve25.oniutils.common.blocks.base.ONIBaseInvTE;
import wintersteve25.oniutils.common.blocks.base.ONIBaseTE;
import wintersteve25.oniutils.common.capability.plasma.PlasmaCapability;
import wintersteve25.oniutils.common.capability.plasma.api.EnumWattsTypes;
import wintersteve25.oniutils.common.capability.plasma.api.IPlasma;
import wintersteve25.oniutils.common.capability.plasma.api.PlasmaStack;
import wintersteve25.oniutils.common.init.ONIBlocks;
import wintersteve25.oniutils.common.init.ONIConfig;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ManualGenTE extends ONIBaseTE implements ITickableTileEntity, IAnimatable {

    private final AnimationFactory manager = new AnimationFactory(this);
    private PlasmaStack plasmaHandler = new PlasmaStack(2000, EnumWattsTypes.LOW);
    private LazyOptional<IPlasma> plasmaLazyOptional = LazyOptional.of(() -> plasmaHandler);

    public boolean hasPlayer = false;

    @Override
    protected int progress() {
        return ONIConfig.MANUAL_GEN_PROCESS_TIME.get();
    }

    private BlockState state;
    private BlockPos pos;
    private ServerPlayerEntity player;

    public ManualGenTE() {
        super(ONIBlocks.MANUAL_GEN_TE.get());
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
                        player.setPosition(pos.getX()-0.3, pos.getY(), pos.getZ());
                    case WEST:
                        player.setPosition(pos.getX()+0.2, pos.getY(), pos.getZ()-0.3);
                    case EAST:
                        player.setPosition(pos.getX(), pos.getY(), pos.getZ()+0.3);
                    default:
                        player.setPosition(pos.getX()+0.3, pos.getY(), pos.getZ());
                }

                ONIUtils.LOGGER.info(plasmaHandler.getPower());

                progress--;
                if (progress < 0) {
                    if (player.isPotionActive(Effects.SPEED)) {
                        if (plasmaHandler.canGenerate()) {
                            plasmaHandler.addPower(ONIConfig.MANUAL_GEN_PLASMA_OUTPUT_SPEED.get());

                            markDirty();
                        }
                    } else {
                        if (plasmaHandler.canGenerate()) {
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

    public void getOnMill(ServerPlayerEntity playerEntity, BlockPos pos, BlockState state) {
        if (!isWorking) {
            hasPlayer = true;

            if (pos != null && state != null && playerEntity != null) {

                this.player = playerEntity;
                this.state = state;
                this.pos = pos;

                playerEntity.sendStatusMessage(new TranslationTextComponent("oniutils.message.manualGen"), true);
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
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.manual_gen.new", true));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return manager;
    }
}
