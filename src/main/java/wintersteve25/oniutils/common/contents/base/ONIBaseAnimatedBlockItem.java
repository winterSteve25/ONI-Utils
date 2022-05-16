package wintersteve25.oniutils.common.contents.base;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.BlockItem;
import net.minecraftforge.client.IItemRenderProperties;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class ONIBaseAnimatedBlockItem extends ONIBaseItemBlock implements IAnimatable {

    private final AnimationFactory factory = new AnimationFactory(this);
    private final Supplier<BlockEntityWithoutLevelRenderer> animatedModel;

    public ONIBaseAnimatedBlockItem(ONIBaseBlock blockIn, Supplier<BlockEntityWithoutLevelRenderer> animatedModel, Properties builder) {
        super(blockIn, builder);
        this.animatedModel = animatedModel;
    }

    @Override
    public void initializeClient(Consumer<IItemRenderProperties> consumer) {
        super.initializeClient(consumer);
        consumer.accept(new IItemRenderProperties() {
            private final BlockEntityWithoutLevelRenderer renderer = animatedModel.get();

            @Override
            public BlockEntityWithoutLevelRenderer getItemStackRenderer() {
                return renderer;
            }
        });
    }

    public <P extends BlockItem & IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        String controllerName = "controller";
        animationData.addAnimationController(new AnimationController(this, controllerName, 1, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
}
