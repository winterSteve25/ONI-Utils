package wintersteve25.oniutils.common.worldgen.features.temperate;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import wintersteve25.oniutils.common.utils.helpers.MiscHelper;

public class TemperatePocketFeature extends Feature<TemperatePocketFeatureConfiguration> {
    public TemperatePocketFeature() {
        super(TemperatePocketFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<TemperatePocketFeatureConfiguration> pContext) {
        var config = pContext.config();

        var origin = pContext.origin().mutable();
        var originHeight = origin.getY();
        var width = config.getWidth();
        var height = config.getHeight();
        var length = config.getLength();
        var world = pContext.level();
        var predicate = isReplaceable(config.getIrreplaceableBlocks());
        var worldgenRandom = new WorldgenRandom(new LegacyRandomSource(world.getSeed()));
        var noise = NormalNoise.create(worldgenRandom, 2, 1);
        var random = pContext.random();

        if (random.nextBoolean()) {
            var temp = width;
            width = length;
            length = temp;
        }

        width += random.nextInt(-2, 3);
        length += random.nextInt(-2, 3);
        height += random.nextInt(-1, 3);

        if (width >= length) {
            if (random.nextBoolean()) {
                width /= 2;
            } else {
                length *= 2;
            }
        }

        if (height <= 3) {
            height = height*2 + 1;
        }

        var randX = random.nextInt(-2, 3);
        var randZ = random.nextInt(-2, 3);

        for (var i = 0; i < height; i++) {
            var corner1 = origin.offset(-width, originHeight, -length);
            var corner2 = origin.offset(width, originHeight, length);

            for (var pos : BlockPos.betweenClosed(corner1, corner2)) {
                var dist = Math.min(MiscHelper.distEuclidean(pos, corner1), MiscHelper.distEuclidean(pos, corner2));
                if (dist <= 3) {
                    if (noise.getValue(pos.getX() + randX, pos.getY(), pos.getZ() + randZ) < 0.1) {
                        safeSetBlock(world, pos.offset(randX, 0, randZ), Blocks.AIR.defaultBlockState(), predicate);
                    }
                } else {
                    if (noise.getValue(pos.getX() + randX, pos.getY(), pos.getZ() + randZ) < 0.6) {
                        safeSetBlock(world, pos.offset(randX, 0, randZ), Blocks.AIR.defaultBlockState(), predicate);
                    }
                }
            }

            if (random.nextBoolean()) {
                originHeight++;
            }

            if (i >= height/2) {
                width--;
                length--;
            } else if (i < height/2) {
                width++;
                length++;
            }


            randX = random.nextInt(-2, 3);
            randZ = random.nextInt(-2, 3);
        }

        return true;
    }
}
