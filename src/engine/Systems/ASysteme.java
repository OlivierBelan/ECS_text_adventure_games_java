package engine.Systems;

import engine.BasicLibs.components.C_Type;
import engine.Entities.Entity;
import engine.Manager.Manager;

public abstract class ASysteme {
    public abstract boolean init(Manager manager);

    public abstract boolean excute(Manager manager);

    protected boolean checkEntity(Entity entity, Manager manager, String type) {
        C_Type c_Type = manager.getComponentManager().getComponentByEntityAndType(entity, C_Type.class);
        if (c_Type != null && c_Type.type == type) {
            return true;
        }
        return false;
    }
}
