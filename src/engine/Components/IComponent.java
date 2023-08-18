package engine.Components;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public abstract class IComponent {
    private UUID id = UUID.randomUUID();
    protected boolean init = false;
    public Set<UUID> entities = new HashSet<>();
    public UUID getId() {
        return id;
    }
    public boolean getInit() {
        return init;
    }
    public void setInit(boolean init) {
        this.init = init;
    }

}   
