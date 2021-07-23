package wintersteve25.oniutils.common.utils.helper;

import mekanism.common.util.WorldUtils;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.blocks.base.bounding.ONIBoundingBlock;
import wintersteve25.oniutils.common.blocks.base.bounding.ONIBoundingTE;
import wintersteve25.oniutils.common.init.ONIBlocks;

import javax.annotation.Nullable;
import java.util.Random;

public class MiscHelper {

    public static final int INT_MAX = 2147483647;
    public static final double ONEPIXEL = 1D/16;
    public static final Item.Properties DEFAULT_ITEM_PROPERTY = new Item.Properties().group(ONIUtils.creativeTab);

    public static String langToReg(String lang) {
        String reg = lang.toLowerCase().replace(' ', '_').replace('-', '_');
        return reg;
    }

    public static double randomInRange(double min, double max) {
        return (java.lang.Math.random() * (max - min)) + min;
    }

    public static float randomInRange(float min, float max) {
        return (float) ((java.lang.Math.random() * (max - min)) + min);
    }

    public static int randomInRange(int min, int max) {
        Random rand = new Random();

        return rand.nextInt((max - min) + 1) + min;
    }

    /**
     * Method modified from https://github.com/mekanism/Mekanism/blob/1.16.x/src/main/java/mekanism/common/util/WorldUtils.java#L537
     */
    public static void makeBoundingBlock(@Nullable IWorld world, BlockPos boundingLocation, BlockPos orig) {
        if (world != null) {
            ONIBoundingBlock boundingBlock = (ONIBoundingBlock) ONIBlocks.BOUNDING_BLOCK;
            BlockState newState = boundingBlock.getDefaultState();
            world.setBlockState(boundingLocation, newState, 3);
            if (!world.isRemote()) {
                ONIBoundingTE tile = (ONIBoundingTE) WorldUtils.getTileEntity((Class) ONIBoundingTE.class, (IBlockReader) world, boundingLocation);
                if (tile != null) {
                    tile.setMainLocation(orig);
                } else {
                    ONIUtils.LOGGER.warn("Unable to find Bounding Block Tile at: {}", boundingLocation);
                }
            }
        }
    }
}
