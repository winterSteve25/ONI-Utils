package wintersteve25.oniutils.common.init;

import wintersteve25.oniutils.common.chunk.germ.world.GermData;

import java.util.ArrayList;
import java.util.List;

public class ONIMiscs {
    public static List<GermData> germTypeList = new ArrayList<>();

    public static final GermData SLIMELUNG = new GermData("Slime Lung", 0x008020, 0);
    public static final GermData FLORALSCENTS= new GermData("Floral Scents", 0xd585e6, 0);
    public static final GermData FOODPOISON= new GermData("Food Poisoning", 0x17e64b, 0);
    public static final GermData ZOMBIESPORES= new GermData("Zombie Spores", 0x3ab4e8, 0);

    static {
        if(ONIConfig.ENABLE_SLIMELUNG.get())
            SLIMELUNG.initGerm(SLIMELUNG);
        if(ONIConfig.ENABLE_FLORALSCENTS.get())
            FLORALSCENTS.initGerm(FLORALSCENTS);
        if(ONIConfig.ENABLE_FOODPOISON.get())
            FOODPOISON.initGerm(FOODPOISON);
        if(ONIConfig.ENABLE_ZOMBIESPORES.get())
            ZOMBIESPORES.initGerm(ZOMBIESPORES);
    }
}
