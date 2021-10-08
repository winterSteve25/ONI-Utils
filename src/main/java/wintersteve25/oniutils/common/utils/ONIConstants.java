package wintersteve25.oniutils.common.utils;

import net.minecraft.client.renderer.texture.Texture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.client.renderers.geckolibs.base.GeckolibModelBase;
import wintersteve25.oniutils.common.blocks.modules.power.coal.CoalGenItemBlock;
import wintersteve25.oniutils.common.blocks.modules.power.coal.CoalGenTE;
import wintersteve25.oniutils.common.blocks.modules.power.manual.ManualGenItemBlock;
import wintersteve25.oniutils.common.blocks.modules.power.manual.ManualGenTE;

import javax.xml.soap.Text;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public final class ONIConstants {
    public static final class Geo {
        public static final GeckolibModelBase<CoalGenTE> COAL_GEN_TE = new GeckolibModelBase<>("machines/power/coal_gen.geo.json", "machines/power/coal_gen.png", "machines/power/coal_gen.animation.json");
        public static final GeckolibModelBase<CoalGenItemBlock> COAL_GEN_IB = new GeckolibModelBase<>("machines/power/coal_gen.geo.json", "machines/power/coal_gen.png", "machines/power/coal_gen.animation.json");
        public static final GeckolibModelBase<ManualGenTE> MANUAL_GEN_TE = new GeckolibModelBase<>("machines/power/manual_gen.geo.json", "machines/power/manual_gen.png", "machines/power/manual_gen.animation.json");
        public static final GeckolibModelBase<ManualGenItemBlock> MANUAL_GEN_IB = new GeckolibModelBase<>("machines/power/manual_gen.geo.json", "machines/power/manual_gen.png", "machines/power/manual_gen.animation.json");
    }
    public static final class PacketType {
        public static final byte REDSTONE_INPUT = 0;
        public static final byte REDSTONE_OUTPUT_LOW = 1;
        public static final byte REDSTONE_OUTPUT_HIGH = 2;

        public static final byte MODIFICATION_GUI = 0;
        public static final byte MODIFICATION_DATA = 1;
    }
    public static final class TextColor {
        public static final TextFormatting POWER_CAT_COLOR = TextFormatting.RED;
        public static final TextFormatting OXYGEN_CAT_COLOR = TextFormatting.AQUA;
        public static final TextFormatting VENTILATION_CAT_COLOR = TextFormatting.GREEN;
        public static final TextFormatting PLUMING_CAT_COLOR = TextFormatting.BLUE;
    }
    public static final class Resources {
        public static final ResourceLocation WIDGETS = new ResourceLocation(ONIUtils.MODID, "textures/gui/misc/widgets.png");
        public static final TextureElement BUTTON_BG = TextureElement.createDefault(0, 0);
        public static final TextureElement BUTTON_BG_HOVER = TextureElement.createDefault(16, 0);
        public static final TextureElement INFO_BUTTON = TextureElement.createDefault(34, 0);
        public static final TextureElement ALERT_BUTTON = TextureElement.createDefault(51, 0);
        public static final TextureElement REDSTONE_OUTPUT_BUTTON = TextureElement.createDefault(68, 0);
        public static final TextureElement MODIFICATION_BUTTON = TextureElement.createDefault(85, 0);
        public static final TextureElement REDSTONE_BUTTON_ON = TextureElement.createDefault(102, 0);
        public static final TextureElement REDSTONE_BUTTON_OFF = TextureElement.createDefault(119, 0);
        public static final TextureElement ITEM_SLOT = TextureElement.createSlot(0, 67);
        public static final TextureElement ITEM_SLOT_HOVER = TextureElement.createSlot(18, 67);
        public static final TextureElement POWER_BAR = new TextureElement(0, 17, 18, 50);
        public static final TextureElement FLAME = new TextureElement(20, 17, 14, 14);
        public static final TextureElement RIGHT_ARROW_BIG = new TextureElement(36, 19, 24, 10);
        public static final TextureElement BATTERY_BG = TextureElement.createDefault(0, 85);
        public static final TextureElement RIGHT_ARROW_BIG_BG = new TextureElement(17, 88, 24, 10);
        public static final TextureElement FLAME_BG = new TextureElement(43, 86, 14, 14);
        public static final TextureElement POWER_BAR_BG = new TextureElement(0, 101, 18, 50);

        public static final ResourceLocation BLANK_GUI = new ResourceLocation(ONIUtils.MODID, "textures/gui/machines/blank_gui.png");
    }
}
