package libs.customCommand.systemes;

import java.util.ArrayList;
import java.util.UUID;

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

public class S_Add extends CustomCommand {
    public S_Add(String name) {
        super(name);
    }

    @Override
    public boolean init(Manager manager) {
        return false;
    }

    UUID getEntityRoomByName(Manager manager, String name) {
        ArrayList<Entity> e_Rooms = manager.getEntitiesByComponents(C_Room.class);
        for (Entity e_Room : e_Rooms) {
            C_Room room = manager.getComponentManager().getComponentByEntityAndType(e_Room, C_Room.class);
            if (room.getName().equals(name)) {
                return e_Room.getId();
            }
        }
        return null;
    }

    private void addRoom(Manager manager, C_Action action) {
        try {
            Entity e_Room = manager.getEntityManager().createEntity();
            C_Room c_Room = new C_Room(action.getOptions().get(1), action.getOptions().get(2));
            c_Room.setExits(getEntityRoomByName(manager, action.getOptions().get(3)),
                    getEntityRoomByName(manager, action.getOptions().get(5)),
                    getEntityRoomByName(manager, action.getOptions().get(6)),
                    getEntityRoomByName(manager, action.getOptions().get(4)));
            manager.linkEntityToComponent(e_Room, c_Room);
            manager.linkEntityToComponent(e_Room, new C_ItemList());
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Problem Add ROOM CUSTOM");
        }
    }

    private void addItem(Manager manager, C_Action action) {
        try {
            C_Item c_Item = new C_Item(action.getOptions().get(1), Integer.parseInt(action.getOptions().get(2)));
            manager.getComponentManager().getComponents().add(c_Item);
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Problem Add ITEM CUSTOM");
        }
    }

    private void addPlayer(Manager manager, C_Action action) {
        try {
            Entity e_Player = manager.getEntityManager().createEntity();
            C_Character c_Character = new C_Character(action.getOptions().get(1));
            c_Character.setMaxWeight(Integer.parseInt(action.getOptions().get(2)));
            manager.linkEntityToComponent(e_Player, c_Character);
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Problem Add ITEM CUSTOM");
        }
    }

    void addItemCommand(Entity e_Player, Manager manager, C_Action action) {
        try {
            _message = new StringBuilder("");
            System.out.println("ADD ITEM");
            if (action.getOptions().get(0).equals("Room")) {
                addRoom(manager, action);
            } else if (action.getOptions().get(0).equals("Item")) {
                addItem(manager, action);
            } else if (action.getOptions().get(0).equals("Player")) {
                addPlayer(manager, action);
            }
            action.setAction("look");
            manager.linkEntityToComponent(e_Player, action);
            System.out.println(action.getOptions());

        } catch (Exception e) {
            System.out.println(e);
            System.out.println("PROBLEM IN add SYSTEM");
        }
    }

    @Override
    public boolean excute(Manager manager) {
        ArrayList<Entity> entities = manager.getEntitiesByComponents(C_Player.class, C_Action.class,
                C_CustomCommand.class);
        for (Entity entity : entities) {
            C_Action action = manager.getComponentManager().getComponentByEntityAndType(entity, C_Action.class);
            if (this.getName().equals(action.getAction())) {
                addItemCommand(entity, manager, action);
            }

        }
        return false;
    }

}
