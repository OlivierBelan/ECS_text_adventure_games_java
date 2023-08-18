package libs.command.systemes;

import java.util.ArrayList;

import communication.Message;
import engine.Entities.Entity;
import engine.Manager.Manager;
import libs.action.components.C_Action;
import libs.character.components.C_Player;
import libs.command.Command;
import libs.command.components.C_Commmand;
import libs.item.components.C_ItemList;
import libs.room.components.C_Room;

public class S_TakeCommand extends Command {

    public S_TakeCommand(String name) {
        super(name);
    }

    @Override
    public boolean init(Manager manager) {
        return super.init(manager);
    }

    private boolean isInvalidOption(Manager manager, Entity e_Entity) {
        boolean isInvalid = true;
        C_ItemList playerItems = manager.getComponentManager().getComponentByEntityAndType(e_Entity, C_ItemList.class);
        for (String option : manager.receive.action.getOptions()) {
            isInvalid = true;
            for (int i = 0; i != playerItems.getItems().size(); i++) {
                if (playerItems.getItems().get(i).getDescription().equals(option))
                    isInvalid = false;
            }
            if (isInvalid == true) {
                _message.append("No " + option + " in the room\n");
            }
        }
        return isInvalid;
    }

    public boolean takeCommand(Entity e_player, Manager manager) {
        _message = new StringBuilder("");
        Entity e_room = manager.getEntityByEntity(e_player, C_Room.class);
        if (!isOption(manager)) {
            _message.append("Take what?");
            setSend(manager, e_player, Message.Code.SUCCESS);
            return true;
        }
        isInvalidOption(manager, e_room);
        moveItem(manager, e_room, e_player);
        setSend(manager, e_player, Message.Code.SUCCESS);
        return true;
    }

    @Override
    public boolean excute(Manager manager) {
        ArrayList<Entity> entities = manager.getEntitiesByComponents(C_Player.class, C_Action.class, C_Commmand.class);
        for (Entity entity : entities) {
            C_Action action = manager.getComponentManager().getComponentByEntityAndType(entity, C_Action.class);
            if (this.getName().contains(action.getAction())) {
                takeCommand(entity, manager);
            }
        }
        return super.init(manager);
    }
}
