package libs.command.systemes;

import java.util.ArrayList;

import communication.Message;
import engine.Entities.Entity;
import engine.Manager.Manager;
import libs.action.components.C_Action;
import libs.character.components.C_Player;
import libs.command.Command;
import libs.command.components.C_Commmand;
import libs.customCommand.components.C_CustomCommand;

public class S_Custom extends Command {
    public S_Custom(String name) {
        super(name);
    }

    @Override
    public boolean init(Manager manager) {
        return super.init(manager);
    }

    private void customCommand(Entity e_Player, Manager manager){
        _message = new StringBuilder("");
        C_Commmand c_Commmand = manager.getComponentManager().getComponentByEntityAndType(e_Player, C_Commmand.class);
        C_CustomCommand c_CustomCommand = manager.getComponentManager().createComponent(C_CustomCommand.class);
        manager.unLinkEntityToComponent(e_Player, c_Commmand);
        manager.linkEntityToComponent(e_Player, c_CustomCommand);
        C_Action action = manager.getComponentManager().createComponent(C_Action.class);
        action.setAction("look");
        manager.linkEntityToComponent(e_Player, action);
        _message.append("Custom Mode\n");
        setSend(manager, e_Player, Message.Code.SUCCESS);
    }

    @Override
    public boolean excute(Manager manager) {
        ArrayList<Entity> entities = manager.getEntitiesByComponents(C_Player.class, C_Action.class, C_Commmand.class);
        for (Entity entity : entities) {
            C_Action action = manager.getComponentManager().getComponentByEntityAndType(entity, C_Action.class);
            if (this.getName().equals(action.getAction())) {
                customCommand(entity, manager);
            }

        }
        return super.init(manager);
    }
}
