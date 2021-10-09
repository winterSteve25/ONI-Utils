package wintersteve25.oniutils.common.init;

import mekanism.common.registration.impl.TileEntityTypeDeferredRegister;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import wintersteve25.oniutils.common.blocks.base.ONIBaseSixWaysBlock;
import wintersteve25.oniutils.common.blocks.base.bounding.ONIBoundingBlock;
import wintersteve25.oniutils.common.blocks.base.bounding.ONIBoundingTE;
import wintersteve25.oniutils.common.blocks.base.ONIBaseDirectional;
import wintersteve25.oniutils.common.blocks.base.ONIBaseBlock;
import wintersteve25.oniutils.common.blocks.modules.furniture.LightBulbBlock;
import wintersteve25.oniutils.common.blocks.modules.oxygen.algae.AlgaeDiffuserBlock;
import wintersteve25.oniutils.common.blocks.modules.oxygen.algae.AlgaeDiffuserTE;
import wintersteve25.oniutils.common.blocks.modules.power.cables.EnumCableTypes;
import wintersteve25.oniutils.common.blocks.modules.power.cables.WireBlock;
import wintersteve25.oniutils.common.blocks.modules.power.coal.CoalGenBlock;
import wintersteve25.oniutils.common.blocks.modules.power.coal.CoalGenContainer;
import wintersteve25.oniutils.common.blocks.modules.power.coal.CoalGenItemBlock;
import wintersteve25.oniutils.common.blocks.modules.power.coal.CoalGenTE;
import wintersteve25.oniutils.common.blocks.modules.power.manual.ManualGenBlock;
import wintersteve25.oniutils.common.blocks.modules.power.manual.ManualGenItemBlock;
import wintersteve25.oniutils.common.blocks.modules.power.manual.ManualGenTE;
import wintersteve25.oniutils.common.blocks.modules.resources.oxylite.OxyliteBlock;
import wintersteve25.oniutils.common.blocks.modules.resources.oxylite.OxyliteTE;
import wintersteve25.oniutils.common.blocks.modules.resources.slime.SlimeBlock;
import wintersteve25.oniutils.common.blocks.modules.resources.slime.SlimeTE;
import wintersteve25.oniutils.common.utils.MiscHelper;
import wintersteve25.oniutils.common.utils.RegistryHelper;

import java.util.HashMap;
import java.util.Map;

public class ONIBlocks {

    //blocks
    public static final ONIBaseBlock IgneousRock = new ONIBaseBlock(1, 2, 6, "Igneous Rock");
    public static final ONIBaseDirectional SedimentaryRock = new ONIBaseDirectional(0, 4, 10, "Sedimentary Rock", null, 0);
    public static final ONIBaseBlock MaficRock = new ONIBaseBlock(2, 5, 18, "Mafic Rock");
    public static final ONIBaseBlock Regolith = new ONIBaseBlock(1, 5, 18, "Regolith");
    public static final ONIBaseBlock Neutronium = new ONIBaseBlock(3, 7, 30, "Neutronium");
    public static final ONIBaseBlock Fossil = new ONIBaseBlock(0, 2, 3, "Fossil");
    public static final ONIBaseBlock BleachStone = new ONIBaseBlock(1, 1.5F, 2, "Bleach Stone");
    public static final ONIBaseBlock Rust = new ONIBaseBlock(1, 1.5F, 2, "Rust");
    public static final ONIBaseBlock PollutedIce = new ONIBaseBlock("Polluted Ice", AbstractBlock.Properties.create(Material.ICE).harvestLevel(0).harvestTool(ToolType.PICKAXE).sound(SoundType.GLASS).hardnessAndResistance(0.7F, 1).slipperiness(0.98F));
    public static final ONIBaseBlock Algae = new ONIBaseBlock("Algae", AbstractBlock.Properties.create(Material.PLANTS).sound(SoundType.CROP).hardnessAndResistance(0.2F, 1).harvestLevel(0));
    public static final ONIBaseBlock Phosphorite = new ONIBaseBlock(1, 1, 2, "Phosphorite");
    public static final ONIBaseBlock Fertilizer = new ONIBaseBlock("Fertilizer", AbstractBlock.Properties.create(Material.PLANTS).sound(SoundType.CROP).hardnessAndResistance(0.2F, 1).harvestLevel(0));
    public static final ONIBaseBlock GoldAmalgam = new ONIBaseBlock(2, 3, 5, "Gold Amalgam");
    public static final ONIBaseBlock Wolframite = new ONIBaseBlock(2, 6, 20, "Wolframite");
    public static final ONIBaseBlock Abyssalite = new ONIBaseBlock(2, 5, 15, "Abyssalite");
    public static final ONIBaseBlock Granite = new ONIBaseBlock(2, 4, 12, "Granite");

    //TE Blocks
    public static final OxyliteBlock Oxylite = new OxyliteBlock();
    public static final RegistryObject<TileEntityType<OxyliteTE>> OXYLITE_TE = RegistryHelper.registerTE(MiscHelper.langToReg(Oxylite.getRegName()), () -> TileEntityType.Builder.create(OxyliteTE::new, Oxylite).build(null));
    public static final SlimeBlock Slime = new SlimeBlock();
    public static final RegistryObject<TileEntityType<SlimeTE>> SLIME_TE = RegistryHelper.registerTE(MiscHelper.langToReg(Slime.getRegName()), () -> TileEntityType.Builder.create(SlimeTE::new, Slime).build(null));

    //Machines
        //Power
    public static final CoalGenBlock COAL_GEN_BLOCK = new CoalGenBlock();
    public static final RegistryObject<TileEntityType<CoalGenTE>> COAL_GEN_TE = RegistryHelper.registerTE(MiscHelper.langToReg(COAL_GEN_BLOCK.getRegName()), () -> TileEntityType.Builder.create(CoalGenTE::new, COAL_GEN_BLOCK).build(null));
    public static final RegistryObject<ContainerType<CoalGenContainer>> COAL_GEN_CONTAINER = RegistryHelper.registerContainer(MiscHelper.langToReg(COAL_GEN_BLOCK.getRegName()), () -> IForgeContainerType.create((windowId, inv, data) -> new CoalGenContainer(windowId, inv.player.world, data.readBlockPos(), inv, inv.player)));
    public static final ManualGenBlock MANUAL_GEN_BLOCK = new ManualGenBlock();
    public static final RegistryObject<TileEntityType<ManualGenTE>> MANUAL_GEN_TE = RegistryHelper.registerTE(MiscHelper.langToReg(MANUAL_GEN_BLOCK.getRegName()), () -> TileEntityType.Builder.create(ManualGenTE::new, MANUAL_GEN_BLOCK).build(null));

    public static final WireBlock WIRE_BLOCK = new WireBlock(EnumCableTypes.WIRE);
    public static final WireBlock CONDUCTIVE_WIRE_BLOCK = new WireBlock(EnumCableTypes.CONDUCTIVE);
    public static final WireBlock HEAVI_WATT_WIRE_BLOCK = new WireBlock(EnumCableTypes.HEAVIWATTS, AbstractBlock.Properties.create(Material.IRON).notSolid());

        //Oxygen
    public static final AlgaeDiffuserBlock ALGAE_DIFFUSER_BLOCK = new AlgaeDiffuserBlock();
    public static final RegistryObject<TileEntityType<AlgaeDiffuserTE>> ALGAE_DIFFUSER_TE = RegistryHelper.registerTE(MiscHelper.langToReg(ALGAE_DIFFUSER_BLOCK.getRegName()), () -> TileEntityType.Builder.create(AlgaeDiffuserTE::new, ALGAE_DIFFUSER_BLOCK).build(null));

    //furniture
    public static final LightBulbBlock BULB_BLOCK = new LightBulbBlock();

    //misc
    public static final ONIBoundingBlock BOUNDING_BLOCK = new ONIBoundingBlock();
    public static final RegistryObject<TileEntityType<ONIBoundingTE>> BOUNDING_TE = RegistryHelper.registerTE(MiscHelper.langToReg(BOUNDING_BLOCK.getRegName()), () -> TileEntityType.Builder.create(ONIBoundingTE::new, BOUNDING_BLOCK).build(null));

    public static Map<ONIBaseBlock, Item> blockList = new HashMap<>();
    public static Map<ONIBaseBlock, Item> blockNoDataList = new HashMap<>();
    public static Map<ONIBaseDirectional, Item> directionalList = new HashMap<>();
    public static Map<ONIBaseDirectional, Item> directionalNoDataList = new HashMap<>();
    public static Map<ONIBaseSixWaysBlock, Item> sixWaysList = new HashMap<>();

    public static void register(){
        IgneousRock.initBlockNoData(IgneousRock, null);
        MaficRock.initBlockNoData(MaficRock, null);
        Regolith.initBlockNoData(Regolith, null);
        Neutronium.initBlockNoData(Neutronium, null);
        Fossil.initBlockNoData(Fossil, null);
        BleachStone.initBlockNoData(BleachStone, null);
        Rust.initBlockNoData(Rust, null);
        PollutedIce.initBlockNoData(PollutedIce, null);
        Algae.initBlock(Algae, null);
        Phosphorite.initBlockNoData(Phosphorite, null);
        Fertilizer.initBlockNoData(Fertilizer, null);
        GoldAmalgam.initBlockNoData(GoldAmalgam, null);
        Wolframite.initBlockNoData(Wolframite, null);
        Abyssalite.initBlockNoData(Abyssalite, null);
        Granite.initBlockNoData(Granite, null);
        SedimentaryRock.initDirectionalNoData(SedimentaryRock, null);

        //Blocks TE
        Oxylite.initBlockNoData(Oxylite, null);
        Slime.initBlockNoData(Slime, new SlimeBlock.SlimeBlockItem());

        //Machines
            //Power
        COAL_GEN_BLOCK.initDirectionalNoData(COAL_GEN_BLOCK, new CoalGenItemBlock());
        MANUAL_GEN_BLOCK.initDirectionalNoData(MANUAL_GEN_BLOCK, new ManualGenItemBlock());
//        WIRE_BLOCK.initBlock(WIRE_BLOCK, null);
        CONDUCTIVE_WIRE_BLOCK.initBlock(CONDUCTIVE_WIRE_BLOCK, null);
//        HEAVI_WATT_WIRE_BLOCK.initBlock(HEAVI_WATT_WIRE_BLOCK, null);
            //Oxygen
        ALGAE_DIFFUSER_BLOCK.initDirectionalNoData(ALGAE_DIFFUSER_BLOCK, null);

        //Furniture
//        BULB_BLOCK.initRockNoDataGen(BULB_BLOCK);

        //Misc
        BOUNDING_BLOCK.initBlock(BOUNDING_BLOCK, null);
    }
}
