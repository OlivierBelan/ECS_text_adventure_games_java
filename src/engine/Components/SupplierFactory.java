package engine.Components;

import java.util.HashMap;
import java.util.function.*;

public class SupplierFactory {
    private static final HashMap<Class<?>, Supplier<? extends IComponent>> factorySupplier = new HashMap<>();
    static {
    }

    public SupplierFactory() {
    }

    public void registerSupplier(Class<?> type, Supplier<? extends IComponent> supplier) {
        factorySupplier.put(type, supplier);
    }

    public void updateSupplier(Class<?> type, Supplier<? extends IComponent> supplier) {
        if (getSupplier(type) != null)
            factorySupplier.put(type, supplier);
    }

    public IComponent getSupplier(Class<?> type) {
        Supplier<? extends IComponent> supplier = factorySupplier.get(type);
        return supplier != null ? supplier.get() : null;
    }

    public boolean deleteFactory(Class<?> type) {
        if (factorySupplier.remove(type) != null)
            return true;
        return false;
    }
}
