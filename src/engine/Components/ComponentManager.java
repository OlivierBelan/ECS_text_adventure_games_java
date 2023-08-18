package engine.Components;

import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;
import java.util.function.Supplier;

import engine.Entities.Entity;

public class ComponentManager {
    private ArrayList<IComponent> components = new ArrayList<>();
    private SupplierFactory factory = new SupplierFactory();

    public ComponentManager() {
    }

    public ComponentManager(IComponent... components) {
        this.addComponentToFactory(components);
    }

    @SuppressWarnings("unchecked")
    public <T> boolean addComponentToFactory(IComponent... component) {
        for (int i = 0; i != component.length; i++) {
            this.factory.registerSupplier(component[i].getClass(), (Supplier<? extends IComponent>) component[i]);
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    public boolean addComponentToFactory(ArrayList<IComponent> component) {
        for (int i = 0; i != component.size(); i++) {
            this.factory.registerSupplier(component.get(i).getClass(),
                    (Supplier<? extends IComponent>) component.get(i));
        }
        return true;
    }

    public boolean removeComponent(int index) {
        return this.components.remove(this.components.get(index));
    }

    public boolean removeComponent(IComponent component) {
        if (hasComponent(component)) {
            for (int i = 0; i != components.size(); i++) {
                if (components.get(i).getId() == component.getId()) {
                    components.remove(i);
                    return true;
                }
            }
        }
        return true;
    }

    public ArrayList<IComponent> getComponents() {
        return components;
    }

    public boolean addEntityToComponent(IComponent component, Entity entity) {
        if (!hasComponent(component)) {
            this.components.add(component);
        }
        if (hasComponent(component)) {
            for (int i = 0; i != components.size(); i++) {
                if (components.get(i).getId() == component.getId()) {
                    components.get(i).entities.add(entity.getId());
                }
            }
            return true;
        }
        return false;
    }

    public boolean removeEntityToComponent(IComponent component, Entity entity) {
        if (hasComponent(component)) {
            for (int i = 0; i != components.size(); i++) {
                if (components.get(i).getId() == component.getId()) {
                    components.get(i).entities.remove(entity.getId());
                }
            }
            return true;
        }
        return false;
    }

    public <T> T getComponentByEntityAndType(Entity entity, Class<T> clazz) {
        for (int i = 0; i != components.size(); i++) {
            if (components.get(i).entities.contains(entity.getId()) && components.get(i).getClass() == clazz) {
                return clazz.cast(components.get(i));
            }
        }
        return null;
    }

    public <T> T getComponentByEntityIdAndType(Set<UUID> entitiesIds, Class<T> clazz) {
        for (UUID entityId : entitiesIds) {
            for (int i = 0; i != components.size(); i++) {
                if (components.get(i).entities.contains(entityId) && components.get(i).getClass() == clazz) {
                    return clazz.cast(components.get(i));
                }
            }
        }
        return null;
    }

    private <T> T convertInstanceOfObject(Object o, Class<T> clazz) {
        try {
            return clazz.cast(o);
        } catch (ClassCastException e) {
            System.out.println(e);
            return null;
        }
    }

    public <T> ArrayList<T> getComponentsByClass(Class<T> clazz) {
        ArrayList<T> res = new ArrayList<>();
        for (IComponent c : components) {
            if (c.getClass() == clazz && convertInstanceOfObject(c, clazz) != null) {
                res.add(convertInstanceOfObject(c, clazz));
            }
        }
        return res;
    }

    public IComponent getComponentById(UUID id) {
        for (int i = 0; i != components.size(); i++) {
            if (components.get(i).getId() == id) {
                return components.get(i);
            }
        }
        return null;
    }

    public <T> T createComponent(Class<T> clazz) {
        IComponent newComponnent = factory.getSupplier(clazz);
        if (newComponnent != null) {
            this.components.add(newComponnent);
            return clazz.cast(newComponnent);
        }
        System.out.println("Error : " + clazz.getName() + "doesnt exist in factory");
        return null;
    }

    public boolean updataComponent(IComponent update) {
        for (IComponent component : components) {
            if (component.getId() == update.getId()) {
                components.remove(component);
                components.add(update);
                return true;
            }
        }
        return false;
    }

    public boolean hasComponent(IComponent component) {
        for (int i = 0; i != components.size(); i++) {
            if (components.get(i).getId() == component.getId())
                return true;
        }
        return false;
    }
}
