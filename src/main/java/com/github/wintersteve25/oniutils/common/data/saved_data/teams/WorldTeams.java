package com.github.wintersteve25.oniutils.common.data.saved_data.teams;

import com.github.wintersteve25.oniutils.common.data.saved_data.circuits.WorldCircuits;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WorldTeams extends SavedData {
    
    private final Map<UUID, Team> teams;
    
    public WorldTeams() {
        teams = new HashMap<>();
    }

    public WorldTeams(CompoundTag tag) {
        teams = new HashMap<>();
    }
    
    @Override
    public CompoundTag save(CompoundTag pCompoundTag) {
        return null;
    }

    public static WorldTeams get(ServerLevel level) {
        if (level == null) {
            throw new RuntimeException("Attempted to get the data from client. This is should not happen");
        }

        // getting the overworld because teams data should not be stored per-dimension
        ServerLevel world = level.getServer().getLevel(Level.OVERWORLD);

        if (world == null) {
            throw new RuntimeException("Overworld does not exist. This should not happens");
        }

        DimensionDataStorage storage = world.getDataStorage();
        return storage.computeIfAbsent(WorldTeams::new, WorldTeams::new, "oni_teams");
    }
}
