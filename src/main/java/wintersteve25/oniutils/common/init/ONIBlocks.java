package wintersteve25.oniutils.common.init;

import net.minecraft.util.Tuple;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.registries.RegistryObject;
import wintersteve25.oniutils.common.contents.base.*;
import wintersteve25.oniutils.common.contents.base.bounding.ONIBoundingBlock;
import wintersteve25.oniutils.common.contents.base.bounding.ONIBoundingTE;
import wintersteve25.oniutils.common.contents.base.builders.ONIAbstractContainer;
import wintersteve25.oniutils.common.contents.base.builders.ONIContainerBuilder;
import wintersteve25.oniutils.common.contents.base.enums.EnumCableTypes;
import wintersteve25.oniutils.common.contents.modules.blocks.power.cables.PowerCableBlock;
import wintersteve25.oniutils.common.contents.modules.blocks.power.coal.CoalGenTE;
import wintersteve25.oniutils.common.utils.helpers.MiscHelper;
import wintersteve25.oniutils.common.utils.helpers.ModelFileHelper;
import wintersteve25.oniutils.common.utils.helpers.RegistryHelper;
import wintersteve25.oniutils.common.utils.helpers.ResoureceLocationHelper;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class ONIBlocks {

    public static final class NonFunctionals {
        public static final ONIBaseBlock IGNEOUS_ROCK = new ONIBaseBlock(1, 2, 6, "Igneous Rock");
        public static final ONIBaseDirectional SEDIMENTARY_ROCK = new ONIBaseDirectional(0, 4, 10, "Sedimentary Rock") {
            @Nullable
            @Override
            public ModelFile getModelFile() {
                return ModelFileHelper.createModelFile(ResoureceLocationHelper.ResourceLocationBuilder
                        .getBuilder()
                        .block()
                        .rocks()
                        .addPath("sedimentary_rock")
                        .build());
            }
        };
        public static final ONIBaseBlock MAFIC_ROCK = new ONIBaseBlock(2, 5, 18, "Mafic Rock");
        public static final ONIBaseBlock REGOLITH = new ONIBaseBlock(1, 5, 18, "Regolith");
        public static final ONIBaseBlock NEUTRONIUM = new ONIBaseBlock(3, 7, 30, "Neutronium");
        public static final ONIBaseBlock FOSSIL = new ONIBaseBlock(0, 2, 3, "Fossil");
        public static final ONIBaseBlock BLEACH_STONE = new ONIBaseBlock(1, 1.5F, 2, "Bleach Stone");
        public static final ONIBaseBlock RUST = new ONIBaseBlock(1, 1.5F, 2, "Rust");
        public static final ONIBaseBlock POLLUTED_ICE = new ONIBaseBlock("Polluted Ice", BlockBehaviour.Properties.of(Material.ICE).sound(SoundType.GLASS).strength(0.7F, 1).friction(0.98F));
        public static final ONIBaseBlock ALGAE = new ONIBaseBlock("Algae", BlockBehaviour.Properties.of(Material.PLANT).sound(SoundType.CROP).strength(0.2F, 1)).setDoModelGen(true).setDoStateGen(true);
        public static final ONIBaseBlock PHOSPHORITE = new ONIBaseBlock(1, 1, 2, "Phosphorite");
        public static final ONIBaseBlock FERTILIZER = new ONIBaseBlock("Fertilizer", BlockBehaviour.Properties.of(Material.PLANT).sound(SoundType.CROP).strength(0.2F, 1));
        public static final ONIBaseBlock GOLD_AMALGAM = new ONIBaseBlock(2, 3, 5, "Gold Amalgam");
        public static final ONIBaseBlock WOLFRAMITE = new ONIBaseBlock(2, 6, 20, "Wolframite");
        public static final ONIBaseBlock ABYSSALITE = new ONIBaseBlock(2, 5, 15, "Abyssalite");
        public static final ONIBaseBlock GRANITE = new ONIBaseBlock(2, 4, 12, "Granite");
    }

    public static final class TileEntityBounded {
        //TE Blocks
    }

    public static final class Machines {
        //Machines
        public static final class Power {
            //Power
            public static final Tuple<ONIBaseLoggableMachine<CoalGenTE>, ONIBaseItemBlock> COAL_GEN_BUILDER = CoalGenTE.createBlock().build();
            public static final ONIBaseMachine<CoalGenTE> COAL_GEN_BLOCK = COAL_GEN_BUILDER.getA();
            public static final RegistryObject<BlockEntityType<CoalGenTE>> COAL_GEN_TE = RegistryHelper.registerTE(MiscHelper.langToReg("Coal Generator"), () -> BlockEntityType.Builder.of(CoalGenTE::new, COAL_GEN_BLOCK).build(null));
            public static final ONIContainerBuilder COAL_GEN_CONTAINER_BUILDER = CoalGenTE.createContainer();
            public static final RegistryObject<MenuType<ONIAbstractContainer>> COAL_GEN_CONTAINER = COAL_GEN_CONTAINER_BUILDER.getContainerTypeRegistryObject();
            public static final PowerCableBlock WIRE_BLOCK = new PowerCableBlock(EnumCableTypes.WIRE);
            public static final PowerCableBlock CONDUCTIVE_WIRE_BLOCK = new PowerCableBlock(EnumCableTypes.CONDUCTIVE);
            public static final PowerCableBlock HEAVI_WATT_WIRE_BLOCK = new PowerCableBlock(EnumCableTypes.HEAVIWATTS, BlockBehaviour.Properties.of(Material.METAL).noOcclusion());
        }

        public static final class Oxygen {
            //Oxygen
        }
    }

    //misc
    public static final class Misc {
        public static final ONIBoundingBlock BOUNDING_BLOCK = new ONIBoundingBlock();
        public static final RegistryObject<BlockEntityType<ONIBoundingTE>> BOUNDING_TE = RegistryHelper.registerTE(MiscHelper.langToReg("Bounding Block"), () -> BlockEntityType.Builder.of(ONIBoundingTE::new, BOUNDING_BLOCK.get()).build(null));
    }

    public static Map<ONIIRegistryObject<Block>, BlockItem> blockList = new HashMap<>();

    public static void register() {
        NonFunctionals.IGNEOUS_ROCK.init(blockList, null);
        NonFunctionals.MAFIC_ROCK.init(blockList, null);
        NonFunctionals.REGOLITH.init(blockList, null);
        NonFunctionals.NEUTRONIUM.init(blockList, null);
        NonFunctionals.FOSSIL.init(blockList, null);
        NonFunctionals.BLEACH_STONE.init(blockList, null);
        NonFunctionals.RUST.init(blockList, null);
        NonFunctionals.POLLUTED_ICE.init(blockList, null);
        NonFunctionals.ALGAE.init(blockList, null);
        NonFunctionals.PHOSPHORITE.init(blockList, null);
        NonFunctionals.FERTILIZER.init(blockList, null);
        NonFunctionals.GOLD_AMALGAM.init(blockList, null);
        NonFunctionals.WOLFRAMITE.init(blockList, null);
        NonFunctionals.ABYSSALITE.init(blockList, null);
        NonFunctionals.GRANITE.init(blockList, null);
        NonFunctionals.SEDIMENTARY_ROCK.init(blockList, null);

        //Blocks TE

        //Machines
        Machines.Power.COAL_GEN_BLOCK.init(blockList, Machines.Power.COAL_GEN_BUILDER.getB());
        Machines.Power.WIRE_BLOCK.init(blockList, null);
        Machines.Power.CONDUCTIVE_WIRE_BLOCK.init(blockList, null);
        Machines.Power.HEAVI_WATT_WIRE_BLOCK.init(blockList, null);

        Misc.BOUNDING_BLOCK.init(blockList, null);
    }
}