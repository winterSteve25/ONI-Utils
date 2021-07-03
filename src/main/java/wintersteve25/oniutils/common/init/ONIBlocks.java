package wintersteve25.oniutils.common.init;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import wintersteve25.oniutils.common.blocks.base.ONIBaseDirectional;
import wintersteve25.oniutils.common.blocks.base.ONIBaseRock;
import wintersteve25.oniutils.common.blocks.modules.furniture.LightBulbBlock;
import wintersteve25.oniutils.common.blocks.modules.power.coalgen.CoalGenBlock;
import wintersteve25.oniutils.common.blocks.modules.power.coalgen.CoalGenContainer;
import wintersteve25.oniutils.common.blocks.modules.power.coalgen.CoalGenTE;
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
    public static final RegistryObject<ONIBaseRock> IgneousRock = RegistryHelper.register("igneous_rock", () -> new ONIBaseRock(1, 2, 6));

    //blocks
    public static final ONIBaseDirectional SedimentaryRock = new ONIBaseDirectional(0, 4, 10, "Sedimentary Rock");
    public static final ONIBaseRock MaficRock = new ONIBaseRock(2, 5, 18, "Mafic Rock");
    public static final ONIBaseRock Regolith = new ONIBaseRock(1, 5, 18, "Regolith");
    public static final ONIBaseRock Neutronium = new ONIBaseRock(3, 7, 30, "Neutronium");
    public static final ONIBaseRock Fossil = new ONIBaseRock(0, 2, 3, "Fossil");
    public static final ONIBaseRock BleachStone = new ONIBaseRock(1, 1.5F, 2, "Bleach Stone");
    public static final ONIBaseRock Rust = new ONIBaseRock(1, 1.5F, 2, "Rust");
    public static final ONIBaseRock PollutedIce = new ONIBaseRock("Polluted Ice", AbstractBlock.Properties.create(Material.ICE).harvestLevel(0).harvestTool(ToolType.PICKAXE).sound(SoundType.GLASS).hardnessAndResistance(0.7F, 1).slipperiness(0.98F));
    public static final ONIBaseRock Algae = new ONIBaseRock("Algae", AbstractBlock.Properties.create(Material.PLANTS).sound(SoundType.CROP).hardnessAndResistance(0.2F, 1).harvestLevel(0));
    public static final ONIBaseRock Phosphorite = new ONIBaseRock(1, 1, 2, "Phosphorite");
    public static final ONIBaseRock Fertilizer = new ONIBaseRock("Fertilizer", AbstractBlock.Properties.create(Material.PLANTS).sound(SoundType.CROP).hardnessAndResistance(0.2F, 1).harvestLevel(0));
    public static final ONIBaseRock GoldAmalgam = new ONIBaseRock(2, 3, 5, "Gold Amalgam");
    public static final ONIBaseRock Wolframite = new ONIBaseRock(2, 6, 20, "Wolframite");
    public static final ONIBaseRock Abyssalite = new ONIBaseRock(2, 5, 15, "Abyssalite");
    public static final ONIBaseRock Granite = new ONIBaseRock(2, 4, 12, "Granite");

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

    //furniture
    public static final LightBulbBlock BULB_BLOCK = new LightBulbBlock();

    public static List<ONIBaseRock> rocksList = new ArrayList<>();
    public static List<ONIBaseRock> rocksListNoDataGen = new ArrayList<>();
    public static List<ONIBaseDirectional> direList = new ArrayList<>();
    public static List<ONIBaseDirectional> direListNoDataGen = new ArrayList<>();

    public static void register(){
        MaficRock.initRockNoDataGen(MaficRock);
        Regolith.initRockNoDataGen(Regolith);
        Neutronium.initRockNoDataGen(Neutronium);
        Fossil.initRockNoDataGen(Fossil);
        BleachStone.initRockNoDataGen(BleachStone);
        Rust.initRockNoDataGen(Rust);
        PollutedIce.initRockNoDataGen(PollutedIce);
        Algae.initRock(Algae);
        Phosphorite.initRockNoDataGen(Phosphorite);
        Fertilizer.initRockNoDataGen(Fertilizer);
        GoldAmalgam.initRockNoDataGen(GoldAmalgam);
        Wolframite.initRockNoDataGen(Wolframite);
        Abyssalite.initRockNoDataGen(Abyssalite);
        Granite.initRockNoDataGen(Granite);
        SedimentaryRock.initRockNoDataGen(SedimentaryRock);

        //Blocks TE
        Oxylite.initRockNoDataGen(Oxylite);
        RegistryHelper.register("slime", () -> Slime, new SlimeBlock.SlimeBlockItem());

        //Machines
        COAL_GEN_BLOCK.initRockNoDataGen(COAL_GEN_BLOCK);
        MANUAL_GEN_BLOCK.initRockNoDataGen(MANUAL_GEN_BLOCK);

        //Furniture
        BULB_BLOCK.initRockNoDataGen(BULB_BLOCK);
    }
}
