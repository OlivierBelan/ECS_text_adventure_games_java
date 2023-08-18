package libs.command.systemes;

import java.util.ArrayList;

import communication.Message;
import engine.Entities.Entity;
import engine.Manager.Manager;
import libs.action.components.C_Action;
import libs.character.components.C_Character;
import libs.character.components.C_Player;
import libs.command.Command;
import libs.command.components.C_Commmand;
import libs.item.components.C_ItemList;
import libs.room.components.C_Room;

public class S_GiveCommand extends Command {

    public S_GiveCommand(String name) {
        super(name);
    }

    @Override
    public boolean init(Manager manager) {
        return super.init(manager);
    }

    private boolean isOption(Manager manager, StringBuilder message) {
        if (manager.receive.action.getOptions() == null || manager.receive.action.getOptions().isEmpty()) {
            message.append("Give what?");
            return true;
        }

        return false;
    }

    private boolean isInvalidOption(Manager manager, Entity e_player, StringBuilder message) {
        C_ItemList playerItems = manager.getComponentManager().getComponentByEntityAndType(e_player, C_ItemList.class);
        for (int i = 0; i != playerItems.getItems().size(); i++) {
            if (playerItems.getItems().get(i).getDescription().equals(manager.receive.action.getOptions().get(0))) {
                if (manager.receive.action.getOptions().size() < 2) {
                    message.append("Give it to who?");
                    return true;
                }
                return false;
            }
        }
        message.append("You don't have the " + manager.receive.action.getOptions().get(0));
        return true;
    }

    private Entity isCharacter(Entity e_player, Entity e_room, Manager manager) {
        ArrayList<Entity> characters = manager.getEntitiesByComponents(e_room.entities, C_Character.class);
        for (Entity charac : characters) {
            C_Character character = manager.getComponentManager().getComponentByEntityAndType(charac, C_Character.class);
            if (charac.getId() != e_player.getId()
                    && character.getName().equals(manager.receive.action.getOptions().get(1))) {
                return charac;
            }
        }
        return null;
    }

    public boolean giveCommand(Entity e_player, Manager manager) {
        _message = new StringBuilder("");
        Entity e_room = manager.getEntityByEntity(e_player, C_Room.class);
        if (isOption(manager, _message)) {
            setSend(manager, e_player, Message.Code.SUCCESS);
            return true;
        } else if (isInvalidOption(manager, e_player, _message)) {
            setSend(manager, e_player, Message.Code.SUCCESS);
            return true;
        }
        Entity character = isCharacter(e_player, e_room, manager);
        if (character != null && moveItem(manager, e_player, character)) {
            setSend(manager, e_player, Message.Code.SUCCESS);
            return true;
        }
        _message.append("There is no " + manager.receive.action.getOptions().get(1) + " in this room");
        setSend(manager, e_player, Message.Code.SUCCESS);
        return true;
    }

    @Override
    public boolean excute(Manager manager) {
        ArrayList<Entity> entities = manager.getEntitiesByComponents(C_Player.class, C_Action.class, C_Commmand.class);
        for (Entity entity : entities) {
            C_Action action = manager.getComponentManager().getComponentByEntityAndType(entity, C_Action.class);
            if (this.getName().contains(action.getAction())) {
                giveCommand(entity, manager);
            }
        }
        return super.init(manager);
    }
}
