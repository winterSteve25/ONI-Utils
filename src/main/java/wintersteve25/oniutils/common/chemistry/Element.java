package wintersteve25.oniutils.common.chemistry;

import mekanism.common.base.IChemicalConstant;
import wintersteve25.oniutils.common.utils.MiscHelper;

public enum Element implements IChemicalConstant {
    H("Hydrogen", -1, -259, -252, 1, 1),
    Li("Lithium", -1334272, 180, 1342, 3, 6),
    O("Oxygen", -9641217, -218, -183, 8, 16),
    Na("Sodium", -1442060, 97, 882, 11, 22),
    Cl("Chlorine", -3151872, -101, 34, 17, 35);

    private final String name;
    private final int color;
    private final int meltingPoint;
    private final int boilingPoint;
    private final int atomicNumber;
    private final float atomicMass;
    private final float density;
    private final int luminosity;

    Element(String name, int color, int meltingPoint, int boilingPoint, int atomicNumber, float atomicMass) {
        this(name, color, meltingPoint, boilingPoint, atomicNumber, atomicMass, 500, 0);
    }

    Element(String name, int color, int meltingPoint, int boilingPoint, int atomicNumber, float atomicMass, float density, int luminosity) {
        this.name = name;
        this.color = color;
        this.meltingPoint = meltingPoint;
        this.boilingPoint = boilingPoint;
        this.atomicNumber = atomicNumber;
        this.atomicMass = atomicMass;
        this.density = density;
        this.luminosity = luminosity;
    }

    @Override
    public String getName() {
        return MiscHelper.langToReg(name);
    }

    public String getLang() {
        return name;
    }

    public String getSymbol() {
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

    public int getBoilingPoint() {
        return boilingPoint;
    }

    public int getAtomicNumber() {
        return atomicNumber;
    }

    public float getAtomicMass() {
        return atomicMass;
    }

    @Override
    public float getDensity() {
        return density;
    }

    @Override
    public int getLuminosity() {
        return luminosity;
    }
}
