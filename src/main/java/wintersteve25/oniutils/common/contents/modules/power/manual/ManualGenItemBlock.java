package wintersteve25.oniutils.common.contents.modules.power.manual;

import mekanism.common.util.WorldUtils;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import wintersteve25.oniutils.client.renderers.geckolibs.machines.power.ManualGenItemBlockRenderer;
import wintersteve25.oniutils.common.contents.base.ONIBaseAnimatedBlockItem;
import wintersteve25.oniutils.common.init.ONIBlocks;
import wintersteve25.oniutils.common.utils.helpers.MiscHelper;
import wintersteve25.oniutils.common.utils.ONIConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ManualGenItemBlock extends ONIBaseAnimatedBlockItem {
    public ManualGenItemBlock() {
        super(ONIBlocks.Machines.Power.MANUAL_GEN_BLOCK, MiscHelper.DEFAULT_ITEM_PROPERTY.setISTER(() -> ManualGenItemBlockRenderer::new));
    }

    @Override
    protected boolean canPlace(BlockItemUseContext context, BlockState state) {
        boolean canPlace = false;

        World world = context.getWorld();
        BlockPos ogPos = context.getPos();

        switch (state.get(BlockStateProperties.FACING)) {
            case NORTH:
                if (WorldUtils.isValidReplaceableBlock(world, ogPos.east())) {
                    if (WorldUtils.isValidReplaceableBlock(world, ogPos.up())) {
                        if (WorldUtils.isValidReplaceableBlock(world, ogPos.east().up())) {
                            canPlace = true;
                        }
                    }
                }
                break;
            case SOUTH:
                if (WorldUtils.isValidReplaceableBlock(world, ogPos.west())) {
                    if (WorldUtils.isValidReplaceableBlock(world, ogPos.up())) {
                        if (WorldUtils.isValidReplaceableBlock(world, ogPos.west().up())) {
                            canPlace = true;
                        }
                    }
                }
                break;
            case WEST:
                if (WorldUtils.isValidReplaceableBlock(world, ogPos.north())) {
                    if (WorldUtils.isValidReplaceableBlock(world, ogPos.up())) {
                        if (WorldUtils.isValidReplaceableBlock(world, ogPos.north().up())) {
                            canPlace = true;
                        }
                    }
                }
                break;
            case EAST:
                if (WorldUtils.isValidReplaceableBlock(world, ogPos.south())) {
                    if (WorldUtils.isValidReplaceableBlock(world, ogPos.up())) {
                        if (WorldUtils.isValidReplaceableBlock(world, ogPos.south().up())) {
                            canPlace = true;
                        }
                    }
                }
                break;
        }

        return super.canPlace(context, state) && canPlace;
    }

    @Override
    public Supplier<List<ITextComponent>> getTooltips() {
        List<ITextComponent> list = new ArrayList<>();
        list.add(new TranslationTextComponent("oniutils.tooltips.items.manual_gen"));
        return ()->list;
    }

    @Override
    public Supplier<TextFormatting> getColorName() {
        return ()->ONIConstants.TextColor.POWER_CAT_COLOR;
    }
}
