package libs.customCommand.systemes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import communication.Message;
// import engine.Components.IComponent;
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

public class S_Remove extends CustomCommand {
    public S_Remove(String name) {
        super(name);
    }

    @Override
    public boolean init(Manager manager) {
        return false;
    }

    private Entity getRoom(Manager manager) {
        Random r = new Random();
        ArrayList<Entity> entities = manager.getEntitiesByComponents(C_Room.class);
        int low = 0;
        int max = entities.size();
        return entities.get(r.nextInt(max - low) + low);
    }

    private void movePlayer(Entity e_Player, Manager manager) {
        Entity e_room = manager.getEntityByEntity(e_Player, C_Room.class);
        if (e_room == null) {
            manager.linkEntityToEntity(e_Player, getRoom(manager));
        }
    }

    private Entity isRoomExist(Manager manager, String option) {
        ArrayList<Entity> e_Rooms = manager.getEntitiesByComponents(C_Room.class);
        for (Entity e_Room : e_Rooms) {
            C_Room c_room = manager.getComponentManager().getComponentByEntityAndType(e_Room, C_Room.class);
            if (c_room.getName().equals(option)) {
                return e_Room;
            }
        }
        return null;
    }

    private Entity isPlayerExist(Manager manager, String option) {
        ArrayList<Entity> e_Characters = manager.getEntitiesByComponents(C_Character.class);
        for (Entity e_Character : e_Characters) {
            C_Character c_Character = manager.getComponentManager().getComponentByEntityAndType(e_Character,
                    C_Character.class);
            if (c_Character.getName().equals(option)) {
                return e_Character;
            }
        }
        return null;
    }

    private boolean removeItem(Manager manager, String option) {
        ArrayList<C_Item> itemsWithOutOnwner = manager.getComponentManager().getComponentsByClass(C_Item.class);
        ArrayList<C_ItemList> itemLists = manager.getComponentManager().getComponentsByClass(C_ItemList.class);

        // if (itemLists == null && itemsWithOutOnwner == null) {
        // return false;
        // }
        for (C_Item item : itemsWithOutOnwner) {
            if (item.getDescription().equals(option)) {
                manager.getComponentManager().removeComponent(item);
            }
        }
        for (C_ItemList itemList : itemLists) {
            if (itemList.removeItemByName(option) != null) {
                manager.getComponentManager().updataComponent(itemList);
            }
        }
        return true;
    }

    private void unLinkItemsList(C_ItemList c_Items, Manager manager) {
        ArrayList<C_Item> items = c_Items.getItems();
        for (C_Item item : items) {
            manager.getComponentManager().getComponents().add(item);
        }
        manager.getComponentManager().removeComponent(c_Items);
    }

    private boolean removePlayer(Entity e_Player, Manager manager, String option) {
        Entity e_PlayerToDelete = isPlayerExist(manager, option);
        if (e_PlayerToDelete == null || e_PlayerToDelete.getId() == e_Player.getId()) {
            return false;
        }
        Entity e_Room = manager.getEntityByEntity(e_PlayerToDelete, C_Room.class);
        if (e_Room != null) {
            e_Room.entities.remove(e_PlayerToDelete.getId());
        }
        try {
            C_ItemList c_Items = manager.getComponentManager().getComponentByEntityAndType(e_PlayerToDelete,
                    C_ItemList.class);
            manager.unLinkEntityToComponent(e_PlayerToDelete, c_Items);
            unLinkItemsList(c_Items, manager);
        } catch (Exception e) {
            System.out.println(e);
        }
        manager.getComponentManager().removeComponent(
                manager.getComponentManager().getComponentByEntityAndType(e_PlayerToDelete, C_Character.class));
        manager.getEntityManager().getEntities().remove(e_PlayerToDelete);
        return true;
    }

    private boolean removeRoom(Manager manager, String option) {
        Entity e_RoomToDelete = isRoomExist(manager, option);
        if (e_RoomToDelete == null || manager.getEntitiesByComponents(C_Room.class).size() <= 1) {
            return false;
        }
        ArrayList<Entity> e_Players = manager.getEntitiesByComponents(e_RoomToDelete.entities, C_Character.class);
        for (Entity e_Player : e_Players) {
            e_Player.entities.remove(e_RoomToDelete.getId());
            manager.unLinkEntityToEntity(e_RoomToDelete, e_Player);
        }
        ArrayList<Entity> e_Rooms = manager.getEntitiesByComponents(C_Room.class);
        for (Entity e_Room : e_Rooms) {
            C_Room c_Room = manager.getComponentManager().getComponentByEntityAndType(e_Room, C_Room.class);
            for (Map.Entry<String, UUID> exit : c_Room.getExits().entrySet()) {
                if (exit.getValue() == e_RoomToDelete.getId()) {
                    HashMap<String, UUID> newExit = c_Room.getExits();
                    newExit.put(exit.getKey(), null);
                    c_Room.setExits(newExit);
                    manager.linkEntityToComponent(e_Room, c_Room);
                }
            }
        }
        C_ItemList c_Items = manager.getComponentManager().getComponentByEntityAndType(e_RoomToDelete,
                C_ItemList.class);
        manager.unLinkEntityToComponent(e_RoomToDelete, c_Items);
        unLinkItemsList(c_Items, manager);
        manager.getComponentManager().removeComponent(
                manager.getComponentManager().getComponentByEntityAndType(e_RoomToDelete, C_Room.class));
        manager.getEntityManager().getEntities().remove(e_RoomToDelete);
        return true;
    }

    private boolean removeElement(Entity e_Player, Manager manager) {
        C_Action action = manager.getComponentManager().getComponentByEntityAndType(e_Player, C_Action.class);
        boolean remove_Player = removePlayer(e_Player, manager, action.getOptions().get(0));
        boolean remove_Room = removeRoom(manager, action.getOptions().get(0));
        boolean remove_Item = removeItem(manager, action.getOptions().get(0));
        if (!remove_Player && !remove_Room && !remove_Item) {
            _message.append(action.getOptions().get(0) + " doesn't exist");
        }
        return remove_Player || remove_Room || remove_Item;
    }

    private void removeCommand(Entity e_Player, Manager manager) {
        _message = new StringBuilder("");
        if (!isOption(manager)) {
            _message.append("remove what ?");
            setSend(manager, e_Player, Message.Code.SUCCESS);
            return;
        }
        removeElement(e_Player, manager);
        C_Action action = manager.getComponentManager().createComponent(C_Action.class);
        action.setAction("look");
        manager.linkEntityToComponent(e_Player, action);
        movePlayer(e_Player, manager);
        setSend(manager, e_Player, Message.Code.SUCCESS);
    }

    @Override
    public boolean excute(Manager manager) {
        ArrayList<Entity> entities = manager.getEntitiesByComponents(C_Player.class, C_Action.class,
                C_CustomCommand.class);
        for (Entity entity : entities) {
            C_Action action = manager.getComponentManager().getComponentByEntityAndType(entity, C_Action.class);
            if (this.getName().equals(action.getAction())) {
                removeCommand(entity, manager);
            }

        }
        return false;
    }
}
