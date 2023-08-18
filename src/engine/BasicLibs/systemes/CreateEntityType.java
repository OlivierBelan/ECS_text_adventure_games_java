package engine.BasicLibs.systemes;

import engine.Manager.*;
import engine.BasicLibs.components.C_Type;
import engine.Entities.Entity;
import engine.Manager.Manager;
import engine.Systems.ASysteme;

public class CreateEntityType extends ASysteme {
    public CreateEntityType() {
    }

    @Override
    public boolean init(Manager manager) {
        try {
            Entity entity = manager.getEntityManager().createEntity();
            C_Type type = manager.getComponentManager().createComponent(C_Type.class);
            type.type = Type.UNKNOW;
            entity = manager.linkEntityToComponent(entity, type);

        } catch (Exception e) {
            System.out.println("FROM CreateEntityType, in Init function : ERROR DURING CREATION");
            System.out.println(e);
        }
        return false;
    }

    @Override
    public boolean excute(Manager manager) {
        return false;
    }
}
