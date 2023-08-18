package libs.customCommand.systemes;

import java.util.ArrayList;

import communication.Message;
import engine.Entities.Entity;
import engine.Manager.Manager;
import libs.action.components.C_Action;
import libs.character.components.C_Player;
import libs.command.components.C_Commmand;
import libs.customCommand.CustomCommand;
import libs.customCommand.components.C_CustomCommand;

public class S_Play extends CustomCommand {
    public S_Play(String name) {
        super(name);
    }
    @Override
    public boolean init(Manager manager) {
        return super.init(manager);
    }

    private void playCommand(Entity e_Player, Manager manager) {
        _message = new StringBuilder("");
        C_CustomCommand c_CustomCommand = manager.getComponentManager().getComponentByEntityAndType(e_Player, C_CustomCommand.class);;
        C_Commmand c_Commmand = manager.getComponentManager().createComponent(C_Commmand.class);
        manager.unLinkEntityToComponent(e_Player, c_CustomCommand);
        manager.linkEntityToComponent(e_Player, c_Commmand);
        C_Action action = manager.getComponentManager().createComponent(C_Action.class);
        action.setAction("welcome");
        manager.linkEntityToComponent(e_Player, action);
        _message.append("Play Mode\n");
        setSend(manager, e_Player, Message.Code.SUCCESS);

    }

    @Override
    public boolean excute(Manager manager) {
        ArrayList<Entity> entities = manager.getEntitiesByComponents(C_Player.class, C_Action.class, C_CustomCommand.class);
        for (Entity entity : entities) {
            C_Action action = manager.getComponentManager().getComponentByEntityAndType(entity, C_Action.class);
            if (this.getName().equals(action.getAction())) {
                playCommand(entity, manager);
            }

        }
        return false;
    }

}
