package wintersteve25.oniutils.common.utils.helper;

import net.minecraft.item.Item;
import wintersteve25.oniutils.ONIUtils;

import java.util.Random;

public class MiscHelper {
    public static final int INT_MAX = 2147483647;
    public static final double ONEPIXEL = 1D/16;
    public static final Item.Properties DEFAULT_ITEM_PROPERTY = new Item.Properties().tab(ONIUtils.creativeTab);

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
}
