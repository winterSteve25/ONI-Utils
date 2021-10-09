package wintersteve25.oniutils.common.chemistry;

import mekanism.common.base.IChemicalConstant;

public enum Elements implements IChemicalConstant {
    HYDROGEN("hydrogen", -1, 0, 20.28F, 70.85F),
    OXYGEN("oxygen", -9641217, 0, 90.19F, 1141.0F),
    CHLORINE("chlorine", -3151872, 0, 207.15F, 1422.92F),
    SODIUM("sodium", -1442060, 0, 370.944F, 927.0F),
    LITHIUM("lithium", -1334272, 0, 453.65F, 512.0F),

    private final String name;
    private final int color;
    private final int temperature;
    private final int meltingPoint;
    private final int atomicNumber;
    private final float atomicMass;
    private final float density;
    private final int luminosity;

    Elements(String name, int color, int temperature, int meltingPoint, int atomicNumber, float atomicMass) {
        this(name, color, temperature, meltingPoint, atomicNumber, atomicMass, 500, 0);
    }

    Elements(String name, int color, int temperature, int meltingPoint, int atomicNumber, float atomicMass, float density, int luminosity) {
        this.name = name;
        this.color = color;
        this.temperature = temperature;
        this.meltingPoint = meltingPoint;
        this.atomicNumber = atomicNumber;
        this.atomicMass = atomicMass;
        this.density = density;
        this.luminosity = luminosity;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getColor() {
        return color;
    }

    @Override
    public float getTemperature() {
        return temperature;
    }

    public int getMeltingPoint() {
        return meltingPoint;
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
