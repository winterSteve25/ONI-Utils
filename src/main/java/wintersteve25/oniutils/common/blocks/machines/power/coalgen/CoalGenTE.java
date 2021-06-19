package wintersteve25.oniutils.common.blocks.machines.power.coalgen;

import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import wintersteve25.oniutils.common.blocks.libs.ONIBaseTE;
import wintersteve25.oniutils.common.init.ONIBlocks;

public class CoalGenTE extends ONIBaseTE implements ITickableTileEntity, IAnimatable {
    private final AnimationFactory manager = new AnimationFactory(this);

    public CoalGenTE() {
        super(ONIBlocks.COAL_GEN_TE.get());
    }

    @Override
    public void tick() {

    }

    private <E extends TileEntity & IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        event.getController().transitionLengthTicks = 0;
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.motor.new", true));
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
