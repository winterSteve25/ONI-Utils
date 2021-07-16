package wintersteve25.oniutils.common.init;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import wintersteve25.oniutils.common.blocks.base.bounding.ONIBoundingBlock;
import wintersteve25.oniutils.common.blocks.base.bounding.ONIBoundingTE;
import wintersteve25.oniutils.common.blocks.base.ONIBaseDirectional;
import wintersteve25.oniutils.common.blocks.base.ONIBaseBlock;
import wintersteve25.oniutils.common.blocks.modules.furniture.LightBulbBlock;
import wintersteve25.oniutils.common.blocks.modules.oxygen.algae.AlgaeDiffuserBlock;
import wintersteve25.oniutils.common.blocks.modules.oxygen.algae.AlgaeDiffuserTE;
import wintersteve25.oniutils.common.blocks.modules.power.coal.CoalGenBlock;
import wintersteve25.oniutils.common.blocks.modules.power.coal.CoalGenContainer;
import wintersteve25.oniutils.common.blocks.modules.power.coal.CoalGenTE;
import wintersteve25.oniutils.common.blocks.modules.power.manual.ManualGenBlock;
import wintersteve25.oniutils.common.blocks.modules.power.manual.ManualGenTE;
import wintersteve25.oniutils.common.blocks.modules.resources.oxylite.OxyliteBlock;
import wintersteve25.oniutils.common.blocks.modules.resources.oxylite.OxyliteTE;
import wintersteve25.oniutils.common.blocks.modules.resources.slime.SlimeBlock;
import wintersteve25.oniutils.common.blocks.modules.resources.slime.SlimeTE;
import wintersteve25.oniutils.common.utils.helper.MiscHelper;
import wintersteve25.oniutils.common.utils.helper.RegistryHelper;

import java.util.ArrayList;
import java.util.List;

public class ONIBlocks {
    public static final RegistryObject<ONIBaseBlock> IgneousRock = RegistryHelper.register("igneous_rock", () -> new ONIBaseBlock(1, 2, 6));

    //blocks
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
    public static final CoalGenBlock COAL_GEN_BLOCK = new CoalGenBlock();
    public static final RegistryObject<TileEntityType<CoalGenTE>> COAL_GEN_TE = RegistryHelper.registerTE(MiscHelper.langToReg(COAL_GEN_BLOCK.getRegName()), () -> TileEntityType.Builder.create(CoalGenTE::new, COAL_GEN_BLOCK).build(null));
    public static final RegistryObject<ContainerType<CoalGenContainer>> COAL_GEN_CONTAINER = RegistryHelper.registerContainer(MiscHelper.langToReg(COAL_GEN_BLOCK.getRegName()), () -> IForgeContainerType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        World world = inv.player.world;
        return new CoalGenContainer(windowId, world, pos, inv, inv.player);
    }));

    public static final ManualGenBlock MANUAL_GEN_BLOCK = new ManualGenBlock();
    public static final RegistryObject<TileEntityType<ManualGenTE>> MANUAL_GEN_TE = RegistryHelper.registerTE(MiscHelper.langToReg(MANUAL_GEN_BLOCK.getRegName()), () -> TileEntityType.Builder.create(ManualGenTE::new, MANUAL_GEN_BLOCK).build(null));

    public static final AlgaeDiffuserBlock ALGAE_DIFFUSER_BLOCK = new AlgaeDiffuserBlock();
    public static final RegistryObject<TileEntityType<AlgaeDiffuserTE>> ALGAE_DIFFUSER_TE = RegistryHelper.registerTE(MiscHelper.langToReg(ALGAE_DIFFUSER_BLOCK.getRegName()), () -> TileEntityType.Builder.create(AlgaeDiffuserTE::new, ALGAE_DIFFUSER_BLOCK).build(null));

    //furniture
    public static final LightBulbBlock BULB_BLOCK = new LightBulbBlock();

    //misc
    public static final ONIBoundingBlock BOUNDING_BLOCK = new ONIBoundingBlock();
    public static final RegistryObject<TileEntityType<ONIBoundingTE>> BOUNDING_TE = RegistryHelper.registerTE(MiscHelper.langToReg(BOUNDING_BLOCK.getRegName()), () -> TileEntityType.Builder.create(ONIBoundingTE::new, BOUNDING_BLOCK).build(null));

    public static List<ONIBaseBlock> blockList = new ArrayList<>();
    public static List<ONIBaseBlock> blockNoDataList = new ArrayList<>();
    public static List<ONIBaseDirectional> directionalList = new ArrayList<>();
    public static List<ONIBaseDirectional> directionalNoDataList = new ArrayList<>();

    public static void register(){
        MaficRock.initBlockNoData(MaficRock);
        Regolith.initBlockNoData(Regolith);
        Neutronium.initBlockNoData(Neutronium);
        Fossil.initBlockNoData(Fossil);
        BleachStone.initBlockNoData(BleachStone);
        Rust.initBlockNoData(Rust);
        PollutedIce.initBlockNoData(PollutedIce);
        Algae.initBlock(Algae);
        Phosphorite.initBlockNoData(Phosphorite);
        Fertilizer.initBlockNoData(Fertilizer);
        GoldAmalgam.initBlockNoData(GoldAmalgam);
        Wolframite.initBlockNoData(Wolframite);
        Abyssalite.initBlockNoData(Abyssalite);
        Granite.initBlockNoData(Granite);
        SedimentaryRock.initDirectionalNoData(SedimentaryRock);

        //Blocks TE
        Oxylite.initBlockNoData(Oxylite);
        RegistryHelper.register("slime", () -> Slime, new SlimeBlock.SlimeBlockItem());

        //Machines
        COAL_GEN_BLOCK.initDirectionalNoData(COAL_GEN_BLOCK);
        MANUAL_GEN_BLOCK.initDirectionalNoData(MANUAL_GEN_BLOCK);

        ALGAE_DIFFUSER_BLOCK.initDirectionalNoData(ALGAE_DIFFUSER_BLOCK);

        //Furniture
//        BULB_BLOCK.initRockNoDataGen(BULB_BLOCK);

        //Misc
        BOUNDING_BLOCK.initBlock(BOUNDING_BLOCK);
    }
}
