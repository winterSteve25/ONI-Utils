package wintersteve25.oniutils.common.utils;

import net.minecraft.resources.ResourceLocation;

public class TextureElement {
    private final int U;
    private final int V;
    private final int width;
    private final int height;
    private final ResourceLocation textureLocation;

    public TextureElement(int U, int V, int width, int height) {
        this.U = U;
        this.V = V;
        this.width = width;
        this.height = height;
        textureLocation = ONIConstants.Resources.WIDGETS;
    }

    public TextureElement(int U, int V, int width, int height, ResourceLocation textureLocation) {
        this.U = U;
        this.V = V;
        this.width = width;
        this.height = height;
        this.textureLocation = textureLocation;
    }

    public static TextureElement createSlot(int U, int V) {
        return new TextureElement(U, V, 18, 18);
    }

    public static TextureElement createDefault(int U, int V) {
        return new TextureElement(U, V, 16, 16);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getU() {
        return U;
    }

    public int getV() {
        return V;
    }

    public ResourceLocation getTextureLocation() {
        return textureLocation;
    }
}
