package libs.customCommand.systemes;

import java.util.ArrayList;
import java.util.UUID;

import communication.Message;
import engine.Entities.Entity;
import engine.Manager.Manager;
import libs.action.components.C_Action;
import libs.character.components.C_Character;
import libs.character.components.C_Player;
import libs.customCommand.CustomCommand;
import libs.customCommand.components.C_CustomCommand;
import libs.item.components.C_Item;
import libs.item.components.C_ItemList;
import libs.room.components.C_Room;

public class S_Look extends CustomCommand {
    public S_Look(String name) {
        super(name);
    }

    private String getItems(C_ItemList items) {
        String message = "";
        if (items != null) {
            for (int i = 0; i != items.getItems().size(); i++) {
                message += items.getItems().get(i).getDescription();
                message += "(" + items.getItems().get(i).getWeight() + ")";
                if (i != items.getItems().size() - 1) {
                    message += ", ";
                }
            }
        }
        return message;
    }

    private String getPlayers(Entity e_room, Manager manager) {
        String message = "";
        int i = 0;
        for (UUID entityId : e_room.entities) {
            C_Character character = manager.getComponentManager()
                    .getComponentByEntityAndType(manager.getEntityManager().getEntityById(entityId), C_Character.class);
            message += character.getName();
            if (i != e_room.entities.size() - 1) {
                message += ", ";
            }
            i++;
        }
        return message;
    }

    private void roomDescription(Manager manager) {
        _message.append("Room(s) : ");
        ArrayList<Entity> e_Rooms = manager.getEntitiesByComponents(C_Room.class);
        for (Entity e_Room : e_Rooms) {
            C_Room c_Room = manager.getComponentManager().getComponentByEntityAndType(e_Room, C_Room.class);
            C_ItemList items = manager.getComponentManager().getComponentByEntityAndType(e_Room, C_ItemList.class);
            _message.append("\nName : " + c_Room.getName() + " Exits : " + c_Room.getExitsText() + " Items : "
                    + getItems(items) + " Player : " + getPlayers(e_Room, manager));
        }
        _message.append("\n\n");
    }

    private void itemWithOutOwner(Manager manager) {
        _message.append("Item without owner : ");
        int i = 0;
        // ArrayList<C_ItemList> c_ItemLists = manager.getComponentManager().getComponentsByClass(C_ItemList.class);
        // for (C_ItemList c_ItemList : c_ItemLists) {
        //     if (c_ItemList.entities.size() == 0) {
        //         _message.append(getItems(c_ItemList));
        //     }
        // }
        ArrayList<C_Item> c_Items = manager.getComponentManager().getComponentsByClass(C_Item.class);
        // System.out.println("c_Items size = " + c_Items.size());
        for (C_Item c_Item : c_Items) {
            _message.append(c_Item.getDescription() + "(" + c_Item.getWeight()+ ")");
            if (i != c_Items.size() -1) {
                _message.append(", ");
            }
            i++;
        }
        _message.append("\n\n");
    }

    private void playerWithOutRoom(Manager manager) {
        _message.append("Player without room : ");
        ArrayList<Entity> e_CharacLists = manager.getEntitiesByComponents(C_Character.class);
        int i = 0;
        for (Entity e_Character : e_CharacLists) {
            Entity e_room = manager.getEntityByEntity(e_Character, C_Room.class);
            if (e_room == null) {
                // C_Room room =
                // manager.getComponentManager().getComponentByEntityAndType(e_room,
                // C_Room.class);
                C_Character character = manager.getComponentManager().getComponentByEntityAndType(e_Character,
                        C_Character.class);
                _message.append(character.getName());
                if (i != e_CharacLists.size() - 1) {
                    _message.append(", ");
                }
            }
            i++;
        }
        _message.append("\n");
    }

    private void lookCommand(Entity e_Player, Manager manager) {
        _message = new StringBuilder("");
        roomDescription(manager);
        itemWithOutOwner(manager);
        playerWithOutRoom(manager);
        setSend(manager, e_Player, Message.Code.SUCCESS);
    }

    @Override
    public boolean excute(Manager manager) {
        ArrayList<Entity> entities = manager.getEntitiesByComponents(C_Player.class, C_Action.class,
                C_CustomCommand.class);
        for (Entity entity : entities) {
            C_Action action = manager.getComponentManager().getComponentByEntityAndType(entity, C_Action.class);
            if (this.getName().equals(action.getAction())) {
                lookCommand(entity, manager);
            }
        }
        return false;
    }

}
