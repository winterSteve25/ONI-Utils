package wintersteve25.oniutils.common.data.capabilities.world_gas.api.chemistry;

public interface ONIIElement extends ONIIChemical {
    int getAtomicNumber();

    float getAtomicMass();

    /**
     * @return the melting point
     */
    float getTemperature();

    int getBoilingPoint();
}
