package wintersteve25.oniutils.common.init;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.RegistryObject;
import wintersteve25.oniutils.common.blocks.libs.ONIBaseDirectional;
import wintersteve25.oniutils.common.blocks.libs.ONIBaseRock;
import wintersteve25.oniutils.common.blocks.ores.OxyliteBlock;
import wintersteve25.oniutils.common.lib.helper.RegistryHelper;
import wintersteve25.oniutils.common.lib.helper.TextHelper;

import java.util.ArrayList;
import java.util.List;

public class ONIBlocks {
    public static final RegistryObject<ONIBaseRock> IgneousRock = RegistryHelper.register("igneous_rock", () -> new ONIBaseRock(1, 2, 6));

    public static final ONIBaseDirectional SedimentaryRock = new ONIBaseDirectional(1, 4, 10, "Sedimentary Rock");
    public static final ONIBaseRock MaficRock = new ONIBaseRock(1, 4, 10, "Mafic Rock");
    public static final ONIBaseRock Regolith = new ONIBaseRock(1, 4, 10, "Regolith");
    public static final ONIBaseRock Neutronium = new ONIBaseRock(1, 4, 10, "Neutronium");
    public static final ONIBaseRock Fossil = new ONIBaseRock(1, 4, 10, "Fossil");
    public static final ONIBaseRock BleachStone = new ONIBaseRock(1, 4, 10, "Bleach Stone");
    public static final ONIBaseRock Rust = new ONIBaseRock(1, 4, 10, "Rust");
    public static final ONIBaseRock PollutedIce = new ONIBaseRock(1, 4, 10, "Polluted Ice", SoundType.GLASS, Material.ICE_SOLID);
    public static final ONIBaseRock Algae = new ONIBaseRock(1, 4, 10, "Algae", SoundType.CROP);
    public static final ONIBaseRock Phosphorite = new ONIBaseRock(1, 4, 10, "Phosphorite");
    public static final ONIBaseRock Fertilizer = new ONIBaseRock(1, 4, 10, "Fertilizer", SoundType.NYLIUM);
    public static final ONIBaseRock GoldAmalgam = new ONIBaseRock(1, 4, 10, "Gold Amalgam");
    public static final ONIBaseRock Wolframite = new ONIBaseRock(1, 4, 10, "Wolframite");
    public static final ONIBaseRock Abyssalite = new ONIBaseRock(1, 4, 10, "Abyssalite");
    public static final OxyliteBlock Oxylite = new OxyliteBlock();

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
        RegistryHelper.register(TextHelper.langToReg(Oxylite.getRegName()), () -> new OxyliteBlock());
    }

    public static void register(){}
}
