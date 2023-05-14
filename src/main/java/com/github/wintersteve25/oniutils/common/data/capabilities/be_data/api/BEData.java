package com.github.wintersteve25.oniutils.common.data.capabilities.be_data.api;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.BlockPos;

public class BEData implements IBEData {

    private int temperature;
    private int durability;
    private BlockPos myPos;

    public BEData() {
        this(30, 0, BlockPos.ZERO);
    }

    public BEData(int temperature, int durability, BlockPos myPos) {
        this.temperature = temperature;
        this.durability = durability;
        this.myPos = myPos;
    }

    @Override
    public int getTemperature() {
        return temperature;
    }

    @Override
    public void setTemperature(int in) {
        temperature = in;
    }

    @Override
    public int getDurability() {
        return durability;
    }

    @Override
    public void setDurability(int in) {
        durability = in;
    }

    @Override
    public BlockPos getMyPos() {
        return myPos;
    }

    @Override
    public void setMyPos(BlockPos pos) {
        myPos = pos;
    }

    @Override
    public CompoundTag write() {
        CompoundTag nbt = new CompoundTag();
        nbt.putInt("Temperature", temperature);
        nbt.putInt("ONIDurability", durability);

        if (myPos == null) return nbt;

        nbt.putInt("MyPosX", myPos.getX());
        nbt.putInt("MyPosY", myPos.getY());
        nbt.putInt("MyPosZ", myPos.getZ());

        return nbt;
    }

    @Override
    public void read(CompoundTag nbt) {
        setTemperature(nbt.getInt("Temperature"));
        setDurability(nbt.getInt("ONIDurability"));
        setMyPos(new BlockPos(nbt.getInt("MyPosX"), nbt.getInt("MyPosY"), nbt.getInt("MyPosZ")));
    }
}
