package libs.command.systemes;

import java.util.ArrayList;

import communication.Message;
import engine.Entities.Entity;
import engine.Manager.Manager;
import libs.action.components.C_Action;
import libs.character.components.C_Player;
import libs.command.Command;
import libs.command.CommandFactory;
import libs.command.components.C_Commmand;

public class S_HelpCommand extends Command {

    public S_HelpCommand(String name) {
        super(name);
    }

    @Override
    public boolean init(Manager manager) {
        return super.init(manager);
    }

    public String getCommand(String message) {
        for (int i = 0; i != CommandFactory.commandWord.size(); i++) {
            message += CommandFactory.commandWord.get(i) + " ";
        }
        return message;
    }
    public boolean helpCommand(Entity entity, Manager manager) {
        String message = "You are lost. You are alone. You wander around at the university.\n\n";
        message += "Your command words are:\n   ";
        message = getCommand(message);
        manager.send.code = Message.Code.SUCCESS;
        manager.send.id = entity.getId();
        manager.send.message = message;
        return true;
    }

    @Override
    public boolean excute(Manager manager) {
        ArrayList<Entity> entities = manager.getEntitiesByComponents(C_Player.class, C_Action.class, C_Commmand.class);
        for (Entity entity : entities) {
            C_Action action = manager.getComponentManager().getComponentByEntityAndType(entity, C_Action.class);
            if (this.getName().contains(action.getAction())) {
                helpCommand(entity, manager);
            }
        }
        return super.init(manager);
    }
}
