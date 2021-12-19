package wintersteve25.oniutils.common.init;

import javafx.util.Pair;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import wintersteve25.oniutils.api.ONIIRegistryObject;
import wintersteve25.oniutils.common.contents.base.*;
import wintersteve25.oniutils.common.contents.base.bounding.ONIBoundingBlock;
import wintersteve25.oniutils.common.contents.base.bounding.ONIBoundingTE;
import wintersteve25.oniutils.common.contents.base.builders.ONIAbstractContainer;
import wintersteve25.oniutils.common.contents.base.builders.ONIContainerBuilder;
import wintersteve25.oniutils.common.contents.modules.oxygen.algae.AlgaeDiffuserBlock;
import wintersteve25.oniutils.common.contents.modules.oxygen.algae.AlgaeDiffuserTE;
import wintersteve25.oniutils.common.contents.modules.power.cables.EnumCableTypes;
import wintersteve25.oniutils.common.contents.modules.power.cables.WireBlock;
import wintersteve25.oniutils.common.contents.modules.power.coal.CoalGenTE;
import wintersteve25.oniutils.common.contents.modules.power.manual.ManualGenBlock;
import wintersteve25.oniutils.common.contents.modules.power.manual.ManualGenItemBlock;
import wintersteve25.oniutils.common.contents.modules.power.manual.ManualGenTE;
import wintersteve25.oniutils.common.contents.modules.resources.oxylite.OxyliteBlock;
import wintersteve25.oniutils.common.contents.modules.resources.oxylite.OxyliteTE;
import wintersteve25.oniutils.common.contents.modules.resources.slime.SlimeBlock;
import wintersteve25.oniutils.common.contents.modules.resources.slime.SlimeTE;
import wintersteve25.oniutils.common.contents.base.ONIBaseBlockItem;
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
        public static final ONIBaseBlock POLLUTED_ICE = new ONIBaseBlock("Polluted Ice", AbstractBlock.Properties.create(Material.ICE).harvestLevel(0).harvestTool(ToolType.PICKAXE).sound(SoundType.GLASS).hardnessAndResistance(0.7F, 1).slipperiness(0.98F));
        public static final ONIBaseBlock ALGAE = new ONIBaseBlock("Algae", AbstractBlock.Properties.create(Material.PLANTS).sound(SoundType.CROP).hardnessAndResistance(0.2F, 1).harvestLevel(0)).setDoModelGen(true).setDoStateGen(true);
        public static final ONIBaseBlock PHOSPHORITE = new ONIBaseBlock(1, 1, 2, "Phosphorite");
        public static final ONIBaseBlock FERTILIZER = new ONIBaseBlock("Fertilizer", AbstractBlock.Properties.create(Material.PLANTS).sound(SoundType.CROP).hardnessAndResistance(0.2F, 1).harvestLevel(0));
        public static final ONIBaseBlock GOLD_AMALGAM = new ONIBaseBlock(2, 3, 5, "Gold Amalgam");
        public static final ONIBaseBlock WOLFRAMITE = new ONIBaseBlock(2, 6, 20, "Wolframite");
        public static final ONIBaseBlock ABYSSALITE = new ONIBaseBlock(2, 5, 15, "Abyssalite");
        public static final ONIBaseBlock GRANITE = new ONIBaseBlock(2, 4, 12, "Granite");
    }

    public static final class TileEntityBounded {
        //TE Blocks
        public static final OxyliteBlock OXYLITE_BLOCK = new OxyliteBlock();
        public static final RegistryObject<TileEntityType<OxyliteTE>> OXYLITE_TE = RegistryHelper.registerTE(MiscHelper.langToReg(OXYLITE_BLOCK.getRegName()), () -> TileEntityType.Builder.create(OxyliteTE::new, OXYLITE_BLOCK).build(null));
        public static final SlimeBlock SLIME_BLOCK = new SlimeBlock();
        public static final RegistryObject<TileEntityType<SlimeTE>> SLIME_TE = RegistryHelper.registerTE(MiscHelper.langToReg(SLIME_BLOCK.getRegName()), () -> TileEntityType.Builder.create(SlimeTE::new, SLIME_BLOCK).build(null));
    }

    public static final class Machines {
        //Machines
        public static final class Power {
            //Power
            public static final Pair<ONIBaseMachine, ONIBaseBlockItem> COAL_GEN_BUILDER = CoalGenTE.createBlock().build();
            public static final ONIBaseMachine COAL_GEN_BLOCK = COAL_GEN_BUILDER.getKey();
            public static final RegistryObject<TileEntityType<CoalGenTE>> COAL_GEN_TE = RegistryHelper.registerTE(MiscHelper.langToReg(COAL_GEN_BLOCK.getRegName()), () -> TileEntityType.Builder.create(CoalGenTE::new, COAL_GEN_BLOCK).build(null));
            public static final ONIContainerBuilder COAL_GEN_CONTAINER_BUILDER = CoalGenTE.createContainer();
            public static final RegistryObject<ContainerType<ONIAbstractContainer>> COAL_GEN_CONTAINER = COAL_GEN_CONTAINER_BUILDER.getContainerTypeRegistryObject();
            public static final ManualGenBlock MANUAL_GEN_BLOCK = new ManualGenBlock();
            public static final RegistryObject<TileEntityType<ManualGenTE>> MANUAL_GEN_TE = RegistryHelper.registerTE(MiscHelper.langToReg(MANUAL_GEN_BLOCK.getRegName()), () -> TileEntityType.Builder.create(ManualGenTE::new, MANUAL_GEN_BLOCK).build(null));
            public static final WireBlock WIRE_BLOCK = new WireBlock(EnumCableTypes.WIRE);
            public static final WireBlock CONDUCTIVE_WIRE_BLOCK = new WireBlock(EnumCableTypes.CONDUCTIVE);
            public static final WireBlock HEAVI_WATT_WIRE_BLOCK = new WireBlock(EnumCableTypes.HEAVIWATTS, AbstractBlock.Properties.create(Material.IRON).notSolid());
        }

        public static final class Oxygen {
            //Oxygen
            public static final AlgaeDiffuserBlock ALGAE_DIFFUSER_BLOCK = new AlgaeDiffuserBlock();
            public static final RegistryObject<TileEntityType<AlgaeDiffuserTE>> ALGAE_DIFFUSER_TE = RegistryHelper.registerTE(MiscHelper.langToReg(ALGAE_DIFFUSER_BLOCK.getRegName()), () -> TileEntityType.Builder.create(AlgaeDiffuserTE::new, ALGAE_DIFFUSER_BLOCK).build(null));
        }
    }

    //misc
    public static final class Misc {
        public static final ONIBoundingBlock BOUNDING_BLOCK = new ONIBoundingBlock();
        public static final RegistryObject<TileEntityType<ONIBoundingTE>> BOUNDING_TE = RegistryHelper.registerTE(MiscHelper.langToReg(BOUNDING_BLOCK.getRegName()), () -> TileEntityType.Builder.create(ONIBoundingTE::new, BOUNDING_BLOCK).build(null));
    }

    public static Map<ONIIRegistryObject<Block>, Item> blockList = new HashMap<>();

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
        TileEntityBounded.OXYLITE_BLOCK.init(blockList, null);
        TileEntityBounded.SLIME_BLOCK.init(blockList, new SlimeBlock.SlimeBlockItem());

        //Machines
        Machines.Power.COAL_GEN_BLOCK.init(blockList, Machines.Power.COAL_GEN_BUILDER.getValue());
        Machines.Power.MANUAL_GEN_BLOCK.init(blockList, new ManualGenItemBlock());
//        Machines.Power.WIRE_BLOCK.initBlock(WIRE_BLOCK, null);
        Machines.Power.CONDUCTIVE_WIRE_BLOCK.init(blockList, null);
//        Machines.Power.HEAVI_WATT_WIRE_BLOCK.initBlock(HEAVI_WATT_WIRE_BLOCK, null);
        Machines.Oxygen.ALGAE_DIFFUSER_BLOCK.init(blockList, null);

//        BULB_BLOCK.initRockNoDataGen(BULB_BLOCK);

        Misc.BOUNDING_BLOCK.init(blockList, null);
    }
}
