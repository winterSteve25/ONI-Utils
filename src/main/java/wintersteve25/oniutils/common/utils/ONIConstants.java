package wintersteve25.oniutils.common.utils;

import mekanism.common.util.WorldUtils;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.client.renderers.geckolibs.base.GeckolibModelBase;
import wintersteve25.oniutils.common.contents.modules.blocks.power.coal.CoalGenTE;
import wintersteve25.oniutils.common.contents.modules.blocks.power.manual.ManualGenTE;
import wintersteve25.oniutils.common.contents.base.ONIBaseAnimatedBlockItem;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

public final class ONIConstants {
    public static final class Geo {
        public static final GeckolibModelBase<CoalGenTE> COAL_GEN_TE = new GeckolibModelBase<>("machines/power/coal_gen.geo.json", "machines/power/coal_gen.png", "machines/power/coal_gen.animation.json");
        public static final GeckolibModelBase<ONIBaseAnimatedBlockItem> COAL_GEN_IB = new GeckolibModelBase<>(COAL_GEN_TE);
        public static final Supplier<Callable<ItemStackTileEntityRenderer>> COAL_GEN_ISTER = () -> () -> new GeoItemRendererDefault<>(COAL_GEN_IB);

        private static class GeoItemRendererDefault<T extends Item & IAnimatable> extends GeoItemRenderer<T> {
            public GeoItemRendererDefault(AnimatedGeoModel<T> modelProvider) {
                super(modelProvider);
            }
        }
    }
    public static final class PacketType {
        public static final byte REDSTONE_INPUT = 0;
        public static final byte REDSTONE_OUTPUT_LOW = 1;
        public static final byte REDSTONE_OUTPUT_HIGH = 2;

        public static final byte MODIFICATION_GUI = 0;
        public static final byte MODIFICATION_DATA = 1;
    }
    public static final class TextColor {
        public static final TextFormatting FURNITURE_CAT_COLOR = TextFormatting.YELLOW;
        public static final TextFormatting POWER_CAT_COLOR = TextFormatting.RED;
        public static final TextFormatting OXYGEN_CAT_COLOR = TextFormatting.AQUA;
        public static final TextFormatting VENTILATION_CAT_COLOR = TextFormatting.GREEN;
        public static final TextFormatting PLUMING_CAT_COLOR = TextFormatting.BLUE;
        public static final TextFormatting TE_BOUNDING_CAT_COLOR = TextFormatting.LIGHT_PURPLE;

        public static final TextFormatting GADGETS = TextFormatting.DARK_BLUE;
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

        public static final ResourceLocation CURIOS_GOGGLES = new ResourceLocation(ONIUtils.MODID, "gui/misc/curios/goggles");
    }
    public static final class LangKeys {
        public static final TranslationTextComponent MOD_TOOLTIP = new TranslationTextComponent("oniutils.tooltips.items.modification");
        public static final TranslationTextComponent HOLD_SHIFT = new TranslationTextComponent("oniutils.tooltips.items.holdShiftInfo");
        public static final String COAL_GEN = "coal_generator";
        public static final String VELOCITY = "velocity";
        public static final String ENERGY = "energy";
        public static final String GAS = "gas";
        public static final String FLUID = "fluid";
        public static final String TEMPERATURE = "temperature";
        public static final String COMPLEXITY = "complexity";
    }
    public static final class PlacementConditions {
        public static boolean fourByFourCondition(BlockItemUseContext context, BlockState state) {
            World world = context.getWorld();
            BlockPos ogPos = context.getPos();

            switch (state.get(BlockStateProperties.FACING)) {
                case NORTH:
                    if (WorldUtils.isValidReplaceableBlock(world, ogPos.east())) {
                        if (WorldUtils.isValidReplaceableBlock(world, ogPos.up())) {
                            if (WorldUtils.isValidReplaceableBlock(world, ogPos.east().up())) {
                                return true;
                            }
                        }
                    }
                    break;
                case SOUTH:
                    if (WorldUtils.isValidReplaceableBlock(world, ogPos.west())) {
                        if (WorldUtils.isValidReplaceableBlock(world, ogPos.up())) {
                            if (WorldUtils.isValidReplaceableBlock(world, ogPos.west().up())) {
                                return true;
                            }
                        }
                    }
                    break;
                case WEST:
                    if (WorldUtils.isValidReplaceableBlock(world, ogPos.north())) {
                        if (WorldUtils.isValidReplaceableBlock(world, ogPos.up())) {
                            if (WorldUtils.isValidReplaceableBlock(world, ogPos.north().up())) {
                                return true;
                            }
                        }
                    }
                    break;
                case EAST:
                    if (WorldUtils.isValidReplaceableBlock(world, ogPos.south())) {
                        if (WorldUtils.isValidReplaceableBlock(world, ogPos.up())) {
                            if (WorldUtils.isValidReplaceableBlock(world, ogPos.south().up())) {
                                return true;
                            }
                        }
                    }
                    break;
            }

            return false;
        }
    }
}
