package wintersteve25.oniutils.common.blocks.modules.power.coal;

import mekanism.common.util.WorldUtils;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wintersteve25.oniutils.client.renderers.geckolibs.machines.power.CoalGenItemBlockRenderer;
import wintersteve25.oniutils.common.blocks.base.ONIBaseBlockItem;
import wintersteve25.oniutils.common.init.ONIBlocks;
import wintersteve25.oniutils.common.utils.helper.MiscHelper;

public class CoalGenItemBlock extends ONIBaseBlockItem {
    public CoalGenItemBlock() {
        super(ONIBlocks.COAL_GEN_BLOCK, MiscHelper.DEFAULT_ITEM_PROPERTY.setISTER(() -> CoalGenItemBlockRenderer::new));
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
}
