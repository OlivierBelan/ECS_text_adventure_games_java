package libs.customCommand.systemes;

import java.util.ArrayList;

import communication.Message;
import engine.Entities.Entity;
import engine.Manager.Manager;
import libs.action.components.C_Action;
import libs.character.components.C_Player;
import libs.customCommand.CustomCommand;
import libs.customCommand.CustomCommandFactory;
import libs.customCommand.components.C_CustomCommand;

public class S_Help extends CustomCommand {
    public S_Help(String name) {
        super(name);
    }

    @Override
    public boolean init(Manager manager) {
        return super.init(manager);
    }

    public String getCommand(String message) {
        for (int i = 0; i != CustomCommandFactory.commandWord.size(); i++) {
            message += CustomCommandFactory.commandWord.get(i) + " ";
        }
        return message;
    }

    private void helpCommand(Entity e_Player, Manager manager){
        String message = "Custom Mode:\n\n";
        message += "Your command words are:\n   ";
        message = getCommand(message);
        manager.send.code = Message.Code.SUCCESS;
        manager.send.id = e_Player.getId();
        manager.send.message = message;

    }

    @Override
    public boolean excute(Manager manager) {
        ArrayList<Entity> entities = manager.getEntitiesByComponents(C_Player.class, C_Action.class, C_CustomCommand.class);
        for (Entity entity : entities) {
            C_Action action = manager.getComponentManager().getComponentByEntityAndType(entity, C_Action.class);
            if (this.getName().equals(action.getAction())) {
                helpCommand(entity, manager);
            }

        }
        return false;
    }
}
