package engine.Entities;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Entity {
    private UUID id = UUID.randomUUID();
    public Set<UUID> entities = new HashSet<>();
    public Set<UUID> components = new HashSet<>();
    public Entity() {

    }
    public UUID getId() {
        return id;
    }
}
