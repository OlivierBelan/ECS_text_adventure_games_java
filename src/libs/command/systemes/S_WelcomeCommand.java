package libs.command.systemes;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

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

public class S_WelcomeCommand extends Command {

    public S_WelcomeCommand(String name) {
        super(name);
    }

    @Override
    public boolean init(Manager manager) {
        return super.init(manager);
    }

    public String getExits(C_Room room, String message) {
        if (room != null) {
            message += "You are " + room.getDescription() + "\n";
            message += "Exits: ";
            for (Map.Entry<String, UUID> exits : room.getExits().entrySet()) {
                if (exits.getValue() != null) {
                    message += exits.getKey() + " ";
                }
            }
            message += "\n";
        }
        return message;
    }

    public String getItems(C_ItemList items, String message) {
        if (items != null) {
            message += "Items: ";
            for (int i = 0; i != items.getItems().size(); i++) {
                message += items.getItems().get(i).getDescription();
                message += "(" + items.getItems().get(i).getWeight() + ")";
            }
        }
        message += "\n";
        return message;
    }

    private String getPlayers(Entity entity, Entity e_room, Manager manager, String message){
        message += "Players: ";
        for (UUID entityId : e_room.entities) {
            C_Character character = manager.getComponentManager().getComponentByEntityAndType(manager.getEntityManager().getEntityById(entityId), C_Character.class);
            if (character != null && entityId != entity.getId()) {
                message += character.getName() + " ";
            }
        }
        return message;
    }

    public boolean welcomeCommand(Entity entity, Manager manager) {
        String message = "";
        Entity e_room = manager.getEntityByEntity(entity, C_Room.class);
        C_Room room = manager.getComponentManager().getComponentByEntityAndType(e_room, C_Room.class);
        C_ItemList itemsRoom = manager.getComponentManager().getComponentByEntityAndType(e_room, C_ItemList.class);
        message += "Welcome to the World of Zuul!\n";
        message += "World of Zuul is a new, incredibly boring adventure game.\n";
        message += "Type 'help' if you need help.\n\n";
        message = getExits(room, message);
        message = getItems(itemsRoom, message);
        message = getPlayers(entity, e_room, manager, message);
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
            if (this.getName().equals(action.getAction())) {
                welcomeCommand(entity, manager);
            }
        }
        return super.init(manager);
    }
}
