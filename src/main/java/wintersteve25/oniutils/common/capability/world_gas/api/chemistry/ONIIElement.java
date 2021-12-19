package wintersteve25.oniutils.common.capability.world_gas.api.chemistry;

public interface ONIIElement extends ONIIChemical {
    int getAtomicNumber();

    float getAtomicMass();

    /**
     * @return the melting point
     */
    float getTemperature();

    int getBoilingPoint();
}
