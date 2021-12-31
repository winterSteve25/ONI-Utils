package wintersteve25.oniutils.common.utils;

import java.util.function.Supplier;

public class LazyObject<T> {
    private final Supplier<T> factory;
    private T obj;

    public LazyObject(Supplier<T> factory) {
        this.factory = factory;
    }

    public T get() {
        if (obj == null) {
            obj = factory.get();
            return obj;
        }

        return obj;
    }

    public Supplier<T> getFactory() {
        return factory;
    }
}
