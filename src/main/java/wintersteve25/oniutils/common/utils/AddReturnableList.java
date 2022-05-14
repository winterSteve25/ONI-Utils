package wintersteve25.oniutils.common.utils;

import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class AddReturnableList<T> extends ArrayList<T> {
    public List<T> addAndReturn(T element) {
        this.add(element);
        return this;
    }

    public List<T> addAllAndReturn(int index, Collection<T> list) {
        this.addAll(index, list);
        return this;
    }

    public List<T> addAllAndReturn(Collection<T> list) {
        this.addAll(list);
        return this;
    }

    public List<T> addAllFromIterator(Iterator<T> list) {
        while (list.hasNext()) {
            T t = list.next();
            this.add((T) t);
        }
        return this;
    }

    public <REGISTRY extends IForgeRegistryEntry<REGISTRY>> List<T> addFromRegistry(IForgeRegistry<REGISTRY> list) {
        for (REGISTRY t : list) {
            try {
                this.add((T) t);
            } catch (Exception ignore) {
            }
        }
        return this;
    }
}
