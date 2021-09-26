package wintersteve25.oniutils.common.utils;

import net.minecraft.util.Tuple;

public class TextureElement extends Triplet<Integer, Integer, Tuple<Integer, Integer>>{
    public TextureElement(int U, int V, int width, int height) {
        super(U, V, new Tuple<>(width, height));
    }

    public TextureElement(int U, int V, Tuple<Integer, Integer> widthHeight) {
        super(U, V, widthHeight);
    }

    public static TextureElement createSlot(int U, int V) {
        return new TextureElement(U, V, 18, 18);
    }

    public static TextureElement createDefault(int U, int V) {
        return new TextureElement(U, V, 16, 16);
    }

    public int getWidth() {
        return getC().getA();
    }

    public int getHeight() {
        return getC().getB();
    }

    public int getU() {
        return getA();
    }

    public int getV() {
        return getB();
    }
}
