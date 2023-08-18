package libs.command.systemes;

import java.util.ArrayList;

import engine.Entities.Entity;
import engine.Manager.Manager;
import libs.action.components.C_Action;
import libs.character.components.C_Player;
import libs.command.Command;
import libs.command.components.C_Commmand;

public class S_QuitCommand extends Command {

    public S_QuitCommand(String name) {
        super(name);
    }

    @Override
    public boolean init(Manager manager) {
        return super.init(manager);
    }

    public boolean quitCommand(Entity entity, Manager manager) {
        manager.isfinish = true;
        manager.send.message = "Thank you for playing.  Good bye.";
        return true;
    }
    @Override
    public boolean excute(Manager manager) {
        ArrayList<Entity> entities = manager.getEntitiesByComponents(C_Player.class, C_Action.class, C_Commmand.class);
        for (Entity entity : entities) {
            C_Action action = manager.getComponentManager().getComponentByEntityAndType(entity, C_Action.class);
            if (this.getName().contains(action.getAction())) {
                quitCommand(entity, manager);
            }
        }
        return super.init(manager);
    }
}
