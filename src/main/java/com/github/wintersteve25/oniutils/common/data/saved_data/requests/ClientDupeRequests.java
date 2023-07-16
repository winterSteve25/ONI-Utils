package com.github.wintersteve25.oniutils.common.data.saved_data.requests;

import net.minecraft.nbt.CompoundTag;

public class ClientDupeRequests implements IDupeRequests {

    public static final ClientDupeRequests INSTANCE = new ClientDupeRequests();
    
    private final DupleRequests internal;
    
    public ClientDupeRequests() {
        internal = new DupleRequests();
    }
    
    public void update(CompoundTag tag) {
        internal.deserializeNBT(tag);
    }
}
