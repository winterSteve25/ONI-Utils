package com.github.wintersteve25.oniutils.common.utils;

import com.github.wintersteve25.oniutils.ONIUtils;
import com.github.wintersteve25.oniutils.common.data.saved_data.requests.ServerDupeRequests;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.util.INBTSerializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;

public class SerializableMap<K, V> implements INBTSerializable<CompoundTag>, Map<K, V> {
    
    private final Map<K, V> internalMap;
    private final Function<K, Tag> keySerializer;
    private final Function<V, Tag> valueSerializer;
    private final Function<Tag, K> keyDeserializer;
    private final Function<Tag, V> valueDeserializer;
    private final int keyDataType;
    private final int valueDataType;

    /**
     * @param keySerializer the function that will be used to serialize the key into nbt
     * @param valueSerializer the function that will be used to serialize the value into nbt
     * @param keyDeserializer the function that will be used to deserialize the key back into an object
     * @param valueDeserializer the function that will be used to deserialize the value back into an object
     * @param keyDataType the integer type of the key nbt, use constants from {@link Tag}
     * @param valueDataType the integer type of the key nbt, use constants from {@link Tag}
     * See example in {@link ServerDupeRequests}
     */
    public SerializableMap(Function<K, Tag> keySerializer, Function<V, Tag> valueSerializer, Function<Tag, K> keyDeserializer, Function<Tag, V> valueDeserializer, int keyDataType, int valueDataType) {
        this.keySerializer = keySerializer;
        this.valueSerializer = valueSerializer;
        this.keyDeserializer = keyDeserializer;
        this.valueDeserializer = valueDeserializer;
        this.keyDataType = keyDataType;
        this.valueDataType = valueDataType;
        internalMap = new HashMap<>();
    }
    
    @Override
    public CompoundTag serializeNBT() {
        ListTag keys = new ListTag();
        ListTag values = new ListTag();
        var compound = new CompoundTag();
        
        for (Entry<K, V> entry : internalMap.entrySet()) {
            keys.add(keySerializer.apply(entry.getKey()));
            values.add(valueSerializer.apply(entry.getValue()));
        }
        
        compound.put("keys", keys);
        compound.put("values", values);
        
        return compound;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        ListTag keys = nbt.getList("keys", keyDataType);
        ListTag values = nbt.getList("values", valueDataType);
        
        if (keys.size() != values.size()) {
            ONIUtils.LOGGER.error("Failed to deserialize a map from NBT because the number of keys does not match the number of values");
            return;
        }
        
        for (int i = 0; i < keys.size(); i++) {
            internalMap.put(keyDeserializer.apply(keys.get(i)), valueDeserializer.apply(values.get(i)));
        }
    }

    @Override
    public int size() {
        return internalMap.size();
    }

    @Override
    public boolean isEmpty() {
        return internalMap.isEmpty();
    }

    @Override
    public boolean containsKey(Object o) {
        return internalMap.containsKey(o);
    }

    @Override
    public boolean containsValue(Object o) {
        return internalMap.containsValue(o);
    }

    @Override
    public V get(Object o) {
        return internalMap.get(o);
    }

    @Nullable
    @Override
    public V put(K k, V v) {
        return internalMap.put(k, v);
    }

    @Override
    public V remove(Object o) {
        return internalMap.remove(o);
    }

    @Override
    public void putAll(@NotNull Map<? extends K, ? extends V> map) {
        internalMap.putAll(map);
    }

    @Override
    public void clear() {
        internalMap.clear();
    }

    @NotNull
    @Override
    public Set<K> keySet() {
        return internalMap.keySet();
    }

    @NotNull
    @Override
    public Collection<V> values() {
        return internalMap.values();
    }

    @NotNull
    @Override
    public Set<Entry<K, V>> entrySet() {
        return internalMap.entrySet();
    }
}
