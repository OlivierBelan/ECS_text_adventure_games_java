package libs.command.systemes;

import java.util.ArrayList;

import communication.Message;
import engine.Entities.Entity;
import engine.Manager.Manager;
import libs.action.components.C_Action;
import libs.character.components.C_Player;
import libs.command.Command;
import libs.command.components.C_Commmand;

public class S_UnknowCommand extends Command {
    public S_UnknowCommand(String name) {
        super(name);
    }

    @Override
    public boolean init(Manager manager) {
        return super.init(manager);
    }

    public boolean unknowCommand(Entity entity, Manager manager) {
        manager.send.code = Message.Code.SUCCESS;
        manager.send.id = entity.getId();
        manager.send.message = "I don't know what you mean...";
        return true;
    }
    @Override
    public boolean excute(Manager manager) {
        ArrayList<Entity> entities = manager.getEntitiesByComponents(C_Player.class, C_Action.class, C_Commmand.class);
        for (Entity entity : entities) {
            C_Action action = manager.getComponentManager().getComponentByEntityAndType(entity, C_Action.class);
            if (this.getName().contains(action.getAction())) {
                unknowCommand(entity, manager);
            }
        }
        return super.init(manager);
    }
}
