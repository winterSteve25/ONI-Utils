package wintersteve25.oniutils.common.data.capabilities.world_gas.api.chemistry;

import mekanism.api.chemical.gas.Gas;
import mekanism.api.chemical.gas.GasStack;
import mekanism.common.base.IChemicalConstant;
import mekanism.common.registration.impl.GasRegistryObject;
import net.minecraft.resources.ResourceLocation;
import wintersteve25.oniutils.common.registries.ONIGases;
import wintersteve25.oniutils.common.utils.helpers.MiscHelper;

import javax.annotation.Nullable;

// Generated using elements.csv and Java IO
public enum Element implements IChemicalConstant, ONIIElement {
    H("Hydrogen", 255, 14.0F, 20, 1, 1.007F, true),
    He("Helium", 25500, -272.0F, 4, 2, 4.002F, true),
    Li("Lithium", 4015886, 454.0F, 1615, 3, 6.941F, false),
    B("Boron", 154176226, 2573.0F, 4200, 5, 10.811F, false),
    C("Carbon", 596063, 3948.0F, 4300, 6, 12.011F, true),
    N("Nitrogen", 66123214, 63.0F, 77, 7, 14.007F, true),
    O("Oxygen", 229220156, 51.0F, 90, 8, 15.999F, true),
    F("Fluorine", 20418655, 54.0F, 85, 9, 18.998F, true),
    Na("Sodium", 211198131, 371.0F, 1156, 11, 22.99F, false),
    Al("Aluminum", 24711069, 933.0F, 2792, 13, 26.982F, false),
    Si("Silicon", 173178121, 1683.0F, 3538, 14, 28.086F, false),
    P("Phosphorus", 23498132, 317.0F, 553, 15, 30.974F, true),
    S("Sulfur", 1451586, 389.0F, 718, 16, 32.065F, true),
    Cl("Chlorine", 7710228, 172.0F, 239, 17, 35.453F, true),
    K("Potassium", 19815295, 337.0F, 1032, 19, 39.098F, false),
    Ca("Calcium", 219210199, 1112.0F, 1757, 20, 40.078F, false),
    Ti("Titanium", 99255115, 1933.0F, 3560, 22, 47.867F, false),
    V("Vanadium", 195186242, 2175.0F, 3680, 23, 50.942F, false),
    Cr("Chromium", 236237218, 2130.0F, 2944, 24, 51.996F, false),
    Mn("Manganese", 225186242, 1519.0F, 2334, 25, 54.938F, false),
    Fe("Iron", 128128128, 1808.0F, 3134, 26, 55.845F, false),
    Co("Cobalt", 17114198, 1768.0F, 3200, 27, 58.933F, false),
    Ni("Nickel", 198157162, 1726.0F, 3186, 28, 58.693F, false),
    Cu("Copper", 25515430, 1358.0F, 2835, 29, 63.546F, false),
    Zn("Zinc", 189196141, 693.0F, 1180, 30, 65.38F, false),
    Ga("Gallium", 1222049, 303.0F, 2477, 31, 69.723F, false),
    Ge("Germanium", 104172255, 1211.0F, 3106, 32, 72.64F, false),
    As("Arsenic", 6214576, 1090.0F, 887, 33, 74.922F, false),
    Se("Selenium", 11662145, 494.0F, 958, 34, 78.96F, true),
    Br("Bromine", 771600, 266.0F, 332, 35, 79.904F, true),
    Kr("Krypton", 22915150, 116.0F, 120, 36, 83.798F, true),
    Y("Yttrium", 20617924, 1799.0F, 3609, 39, 88.906F, false),
    Zr("Zirconium", 1278022, 2125.0F, 4682, 40, 91.224F, false),
    Tc("Technetium", 7217063, 2473.0F, 5150, 43, 98.0F, false),
    Ru("Ruthenium", 25524086, 2523.0F, 4423, 44, 101.07F, false),
    Rh("Rhodium", 255080, 2239.0F, 3968, 45, 102.906F, false),
    Pd("Palladium", 255169, 1825.0F, 3236, 46, 106.42F, false),
    Ag("Silver", 226217206, 1234.0F, 2435, 47, 107.868F, false),
    Sn("Tin", 132161206, 505.0F, 2875, 50, 118.71F, false),
    I("Iodine", 621763, 387.0F, 457, 53, 126.904F, true),
    Xe("Xenon", 19651204, 161.0F, 165, 54, 131.293F, true),
    Cs("Cesium", 2551480, 302.0F, 944, 55, 132.905F, false),
    Ba("Barium", 219179, 1002.0F, 2170, 56, 137.327F, false),
    Ce("Cerium", 255254211, 1071.0F, 3716, 58, 140.116F, false),
    Nd("Neodymium", 382811, 1289.0F, 3347, 60, 144.242F, false),
    Pm("Promethium", 105175123, 1204.0F, 3273, 61, 145.0F, false),
    Sm("Samarium", 736973, 1345.0F, 2067, 62, 150.36F, false),
    Ta("Tantalum", 108142110, 3269.0F, 5731, 73, 180.948F, false),
    W("Tungsten", 120128140, 3680.0F, 5828, 74, 183.84F, false),
    Re("Rhenium", 19922689, 3453.0F, 5869, 75, 186.207F, false),
    Os("Osmium", 102129173, 3300.0F, 5285, 76, 190.23F, false),
    Ir("Iridium", 215242238, 2716.0F, 4701, 77, 192.217F, false),
    Pt("Platinum", 114202229, 2045.0F, 4098, 78, 195.084F, false),
    Au("Gold", 2552550, 1338.0F, 3129, 79, 196.967F, false),
    Hg("Mercury", 160159157, 234.0F, 630, 80, 200.59F, false),
    Pb("Lead", 186135193, 601.0F, 2022, 82, 207.2F, false),
    At("Astatine", 120128213, 575.0F, 610, 85, 210.0F, true),
    Rn("Radon", 7666179, 202.0F, 211, 86, 222.0F, false),
    Ra("Radium", 255181221, 973.0F, 2010, 88, 226.0F, false),
    Ac("Actinium", 14182145, 1323.0F, 3471, 89, 227.0F, false),
    U("Uranium", 9317819, 1405.0F, 4404, 92, 238.029F, false),
    Np("Neptunium", 3220158, 913.0F, 4273, 93, 237.0F, false),
    Pu("Plutonium", 211211209, 913.0F, 3501, 94, 244.0F, false),
    Am("Americium", 23712475, 1267.0F, 2880, 95, 243.0F, false),
    Cf("Californium", 17518216, 1925.0F, 1173, 98, 251.0F, false),
    No("Nobelium", 944452, 1100.0F, 1533, 102, 259.0F, false),
    Lr("Lawrencium", 2164592, 1100.0F, 4753, 103, 262.0F, false),
    Cn("Copernicium", 16040240, 1100.0F, 4564, 112, 285.0F, false),
    Og("Oganesson", 250150250, 1100.0F, 2535, 118, 294.0F, true);

    private final String name;
    private final int color;
    private final float meltingPoint;
    private final int boilingPoint;
    private final int atomicNumber;
    private final float atomicMass;
    private final int luminosity;
    private final boolean isNonMetal;

    Element(String name, int color, float meltingPoint, int boilingPoint, int atomicNumber, float atomicMass, boolean isNonMetal) {
        this(name, color, meltingPoint, boilingPoint, atomicNumber, atomicMass, 0, isNonMetal);
    }

    Element(String name, int color, float meltingPoint, int boilingPoint, int atomicNumber, float atomicMass, int luminosity, boolean isNonMetal) {
        this.name = name;
        this.color = color;
        this.meltingPoint = meltingPoint;
        this.boilingPoint = boilingPoint;
        this.atomicNumber = atomicNumber;
        this.atomicMass = atomicMass;
        this.luminosity = luminosity;
        this.isNonMetal = isNonMetal;
    }

    @Override
    public String getName() {
        return MiscHelper.langToReg(name);
    }

    @Override
    public String getLang() {
        return name;
    }

    @Override
    public String getFormula() {
        return name();
    }

    @Override
    public int getColor() {
        return color;
    }

    @Override
    public float getTemperature() {
        return meltingPoint;
    }

    @Override
    public int getBoilingPoint() {
        return boilingPoint;
    }

    @Override
    public int getAtomicNumber() {
        return atomicNumber;
    }

    @Override
    public float getAtomicMass() {
        return atomicMass;
    }

    @Override
    public boolean isNonMetal() {
        return isNonMetal;
    }

    @Override
    public float getDensity() {
        return 500;
    }

    @Override
    public int getLuminosity() {
        return luminosity;
    }

    @Nullable
    public static Element getFromRegName(ResourceLocation res) {
        for (Element e : Element.values()) {
            if (e.getName().equals(res.getPath())) {
                return e;
            }
        }

        return null;
    }

    @Nullable
    public static Gas getGasFromElement(Element element) {
        for (GasRegistryObject<Gas> g : ONIGases.ELEMENT_GASES) {
            Gas gas = g.get();
            if (!gas.isEmptyType()) {
                ResourceLocation resourceLocation = gas.getRegistryName();
                if (resourceLocation != null) {
                    if (resourceLocation.getPath().equals(element.getName())) return gas;
                }
            }
        }
        return null;
    }

    public static GasStack getGasStackFromElement(Element element, long amount) {
        return new GasStack(Element.getGasFromElement(element), amount);
    }
}
