package engine.Entities;

import java.util.ArrayList;
import java.util.UUID;

import engine.Components.IComponent;

public class EntityManager {
    private ArrayList<Entity> entities = new ArrayList<>();

    public EntityManager() {
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }
    public Entity createEntity() {
        Entity newEntity = new Entity();
        entities.add(newEntity);
        return newEntity;
    }
    public Entity createEntity(IComponent ...component) {
        Entity newEntity = new Entity();
        entities.add(newEntity);
        return newEntity;
    }

    public Entity getEntityById(UUID id) {
        for (int i = 0; i != entities.size(); i++) {
            if (entities.get(i).getId() == id) {
                return entities.get(i);
            }
        }
        return null;
    }
    public boolean addComponentToEntity(Entity entity, IComponent component) {
        for (int i = 0; i != entities.size(); i++) {
            if (entities.get(i).getId() == entity.getId() && !entities.get(i).components.contains(component.getId())) {
                entities.get(i).components.add(component.getId());
                return true;
            }
        }
        return false;
    }

    public boolean removeComponentToEntity(Entity entity, IComponent component) {
        for (int i = 0; i != entities.size(); i++) {
            if (entities.get(i).getId() == entity.getId() && entities.get(i).components.contains(component.getId())) {
                entities.get(i).components.remove(component.getId());
                return true;
            }
        }
        return false;
    }

    public boolean addEntityToEntity(Entity entityA, Entity entityB) {
        if (hasEntity(entityA) && hasEntity(entityB)) {
            for (int i = 0; i != entities.size(); i++) {
                if (entities.get(i).getId() == entityA.getId()) {
                    entities.get(i).entities.add(entityB.getId());
                }
                if (entities.get(i).getId() == entityB.getId()) {
                    entities.get(i).entities.add(entityA.getId());
                }
            }
            return true;
        }
        return false;
    }

    public boolean removeEntityToEntity(Entity entityA, Entity entityB) {
        if (hasEntity(entityA) && hasEntity(entityB)) {
            for (int i = 0; i != entities.size(); i++) {
                if (entities.get(i).getId() == entityA.getId()) {
                    entities.get(i).entities.remove(entityB.getId());
                }
                if (entities.get(i).getId() == entityB.getId()) {
                    entities.get(i).entities.remove(entityA.getId());
                }
            }
            return true;
        }
        return false;
    }

    public boolean hasEntity(Entity entity) {
        for (int i = 0; i != entities.size(); i++) {
            if (entities.get(i).getId() == entity.getId())
                return true;
        }
        return false;
    }
}
