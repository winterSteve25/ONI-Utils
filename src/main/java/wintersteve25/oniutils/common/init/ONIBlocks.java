package wintersteve25.oniutils.common.init;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import wintersteve25.oniutils.common.blocks.libs.ONIBaseDirectional;
import wintersteve25.oniutils.common.blocks.libs.ONIBaseRock;
import wintersteve25.oniutils.common.blocks.modules.furniture.LightBulbBlock;
import wintersteve25.oniutils.common.blocks.modules.power.coalgen.CoalGenBlock;
import wintersteve25.oniutils.common.blocks.modules.power.coalgen.CoalGenContainer;
import wintersteve25.oniutils.common.blocks.modules.power.coalgen.CoalGenTE;
import wintersteve25.oniutils.common.blocks.modules.power.manual.ManualGenBlock;
import wintersteve25.oniutils.common.blocks.modules.power.manual.ManualGenTE;
import wintersteve25.oniutils.common.blocks.modules.resources.oxylite.OxyliteBlock;
import wintersteve25.oniutils.common.blocks.modules.resources.oxylite.OxyliteTile;
import wintersteve25.oniutils.common.blocks.modules.resources.slime.SlimeBlock;
import wintersteve25.oniutils.common.blocks.modules.resources.slime.SlimeTile;
import wintersteve25.oniutils.common.utils.helper.MiscHelper;
import wintersteve25.oniutils.common.utils.helper.RegistryHelper;

import java.util.ArrayList;
import java.util.List;

public class ONIBlocks {
    public static final RegistryObject<ONIBaseRock> IgneousRock = RegistryHelper.register("igneous_rock", () -> new ONIBaseRock(1, 2, 6));

    //blocks
    public static final ONIBaseDirectional SedimentaryRock = new ONIBaseDirectional(0, 4, 10, "Sedimentary Rock");
    public static final ONIBaseRock MaficRock = new ONIBaseRock(1, 4, 10, "Mafic Rock");
    public static final ONIBaseRock Regolith = new ONIBaseRock(1, 4, 10, "Regolith");
    public static final ONIBaseRock Neutronium = new ONIBaseRock(1, 4, 10, "Neutronium");
    public static final ONIBaseRock Fossil = new ONIBaseRock(0, 4, 10, "Fossil");
    public static final ONIBaseRock BleachStone = new ONIBaseRock(1, 4, 10, "Bleach Stone");
    public static final ONIBaseRock Rust = new ONIBaseRock(1, 4, 10, "Rust");
    public static final ONIBaseRock PollutedIce = new ONIBaseRock(0, 4, 10, "Polluted Ice", SoundType.GLASS, Material.ICE_SOLID);
    public static final ONIBaseRock Algae = new ONIBaseRock(0, 4, 10, "Algae", SoundType.CROP);
    public static final ONIBaseRock Phosphorite = new ONIBaseRock(1, 4, 10, "Phosphorite");
    public static final ONIBaseRock Fertilizer = new ONIBaseRock(0, 4, 10, "Fertilizer", SoundType.NYLIUM);
    public static final ONIBaseRock GoldAmalgam = new ONIBaseRock(2, 4, 10, "Gold Amalgam");
    public static final ONIBaseRock Wolframite = new ONIBaseRock(2, 7, 25, "Wolframite");
    public static final ONIBaseRock Abyssalite = new ONIBaseRock(2, 8, 20, "Abyssalite");
    public static final ONIBaseRock Granite = new ONIBaseRock(2, 6, 25, "Granite");

    //TE Blocks
    public static final OxyliteBlock Oxylite = new OxyliteBlock();
    public static final RegistryObject<TileEntityType<OxyliteTile>> OxyliteTE = RegistryHelper.registerTE(MiscHelper.langToReg(Oxylite.getRegName()), () -> TileEntityType.Builder.of(OxyliteTile::new, Oxylite).build(null));
    public static final SlimeBlock Slime = new SlimeBlock();
    public static final RegistryObject<TileEntityType<SlimeTile>> SlimeTE = RegistryHelper.registerTE(MiscHelper.langToReg(Slime.getRegName()), () -> TileEntityType.Builder.of(SlimeTile::new, Slime).build(null));

    //Machines
    public static final CoalGenBlock COAL_GEN_BLOCK = new CoalGenBlock();
    public static final RegistryObject<TileEntityType<CoalGenTE>> COAL_GEN_TE = RegistryHelper.registerTE(MiscHelper.langToReg(COAL_GEN_BLOCK.getRegName()), () -> TileEntityType.Builder.of(CoalGenTE::new, COAL_GEN_BLOCK).build(null));
    public static final RegistryObject<ContainerType<CoalGenContainer>> COAL_GEN_CONTAINER = RegistryHelper.registerContainer(MiscHelper.langToReg(COAL_GEN_BLOCK.getRegName()), () -> IForgeContainerType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        World world = inv.player.getCommandSenderWorld();
        return new CoalGenContainer(windowId, world, pos, inv, inv.player);
    }));

    public static final ManualGenBlock MANUAL_GEN_BLOCK = new ManualGenBlock();
    public static final RegistryObject<TileEntityType<ManualGenTE>> MANUAL_GEN_TE = RegistryHelper.registerTE(MiscHelper.langToReg(MANUAL_GEN_BLOCK.getRegName()), () -> TileEntityType.Builder.of(ManualGenTE::new, MANUAL_GEN_BLOCK).build(null));

    //furniture
    public static final LightBulbBlock BULB_BLOCK = new LightBulbBlock();

    public static List<ONIBaseRock> rocksList = new ArrayList<>();
    public static List<ONIBaseRock> rocksListNoDataGen = new ArrayList<>();
    public static List<ONIBaseDirectional> direList = new ArrayList<>();
    public static List<ONIBaseDirectional> direListNoDataGen = new ArrayList<>();

    static {
        SedimentaryRock.initRockNoDataGen(SedimentaryRock);
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

        //Blocks TE
        Oxylite.initRockNoDataGen(Oxylite);
        RegistryHelper.register("slime", () -> Slime, new SlimeBlock.SlimeBlockItem());

        //Machines
        COAL_GEN_BLOCK.initRockNoDataGen(COAL_GEN_BLOCK);
        MANUAL_GEN_BLOCK.initRockNoDataGen(MANUAL_GEN_BLOCK);

        //Furniture
        BULB_BLOCK.initRockNoDataGen(BULB_BLOCK);
    }

    public static void register(){}
}
