package com.github.wintersteve25.oniutils.common.data.saved_data.teams;

import com.github.wintersteve25.oniutils.common.data.saved_data.teams.research.Research;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Team implements INBTSerializable<CompoundTag> {
    
    private UUID uuid;
    private List<Research> researchesUnlocked;
    
    public Team() {
        uuid = UUID.randomUUID();
        researchesUnlocked = new ArrayList<>();
    }
    
    @Override
    public CompoundTag serializeNBT() {
        var tag = new CompoundTag();
        
        tag.putUUID("uuid", uuid);
        
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        uuid = nbt.getUUID("uuid");
    }
}
