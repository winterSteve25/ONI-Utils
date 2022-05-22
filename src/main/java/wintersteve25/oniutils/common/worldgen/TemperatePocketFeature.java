package wintersteve25.oniutils.common.worldgen;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import wintersteve25.oniutils.common.utils.helpers.MiscHelper;

public class TemperatePocketFeature extends Feature<TemperatePocketFeatureConfiguration> {
    public TemperatePocketFeature() {
        super(TemperatePocketFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<TemperatePocketFeatureConfiguration> pContext) {
        var config = pContext.config();

        var rand = pContext.random();
        var origin = pContext.origin().mutable();
        var originHeight = origin.getY();
        var width = config.getWidth() / 2;
        var height = config.getHeight() / 2;
        var world = pContext.level();
        var predicate = isReplaceable(config.getIrreplaceableBlocks());

        for (var i = 0; i < height; i++) {
            if (MiscHelper.chanceHandling(rand, 0.8f)) {
                width += i*rand.nextInt(1, 3);
            }

            for (var pos : BlockPos.betweenClosed(origin.offset(-width, originHeight, -width), origin.offset(width, originHeight, width))) {
                safeSetBlock(world, pos, Blocks.AIR.defaultBlockState(), predicate);
            }

            originHeight++;
        }

        return true;
    }
}
