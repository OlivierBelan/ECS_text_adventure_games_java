package libs.customCommand.systemes;

import java.util.ArrayList;

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

public class S_Link extends CustomCommand {
    public S_Link(String name) {
        super(name);
    }

    @Override
    public boolean init(Manager manager) {
        return super.init(manager);
    }

    private boolean linkItem(Manager manager, String nameA, String nameB) {
        ArrayList<C_Item> c_Items = manager.getComponentManager().getComponentsByClass(C_Item.class);
        ArrayList<Entity> e_Players = manager.getEntitiesByComponents(C_Character.class);
        ArrayList<Entity> e_Rooms = manager.getEntitiesByComponents(C_Room.class);

        for (C_Item c_Item : c_Items) {
            if (c_Item.getDescription().equals(nameA)) {
                for (Entity e_Player : e_Players) {
                    if (manager.getComponentManager().getComponentByEntityAndType(e_Player, C_Character.class).getName()
                            .equals(nameB)) {
                        C_ItemList itemList = manager.getComponentManager().getComponentByEntityAndType(e_Player,
                                C_ItemList.class);
                        itemList.addItem(c_Item);
                        manager.getComponentManager().removeComponent(c_Item);
                        manager.linkEntityToComponent(e_Player, itemList);
                        return true;
                    }
                }
                for (Entity e_Room : e_Rooms) {
                    if (manager.getComponentManager().getComponentByEntityAndType(e_Room, C_Room.class).getName()
                            .equals(nameB)) {
                        C_ItemList itemList = manager.getComponentManager().getComponentByEntityAndType(e_Room,
                                C_ItemList.class);
                        itemList.addItem(c_Item);
                        manager.getComponentManager().removeComponent(c_Item);
                        manager.linkEntityToComponent(e_Room, itemList);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    boolean linkPlayerAndRoom(Manager manager, String nameA, String nameB) {
        ArrayList<Entity> e_Players = manager.getEntitiesByComponents(C_Character.class);
        ArrayList<Entity> e_Rooms = manager.getEntitiesByComponents(C_Room.class);
        for (Entity e_Player : e_Players) {
            if (manager.getComponentManager().getComponentByEntityAndType(e_Player, C_Character.class).getName()
                    .equals(nameA)) {
                for (Entity e_Room : e_Rooms) {
                    if (manager.getComponentManager().getComponentByEntityAndType(e_Room, C_Room.class).getName()
                            .equals(nameB)) {
                        Entity oldRoom = manager.getEntityByEntity(e_Player, C_Room.class);
                        if (oldRoom != null)
                            manager.unLinkEntityToEntity(oldRoom, e_Player);
                        manager.linkEntityToEntity(e_Player, e_Room);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void linkCommand(Entity e_Player, Manager manager, C_Action action) {
        try {
            linkItem(manager, action.getOptions().get(0), action.getOptions().get(1));
            linkItem(manager, action.getOptions().get(1), action.getOptions().get(0));
            linkPlayerAndRoom(manager, action.getOptions().get(0), action.getOptions().get(1));
            linkPlayerAndRoom(manager, action.getOptions().get(1), action.getOptions().get(0));
            action.setAction("look");
            manager.linkEntityToComponent(e_Player, action);
            System.out.println(action.getOptions());
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("PROBLEM Link function CUSTOM");
        }
    }

    @Override
    public boolean excute(Manager manager) {
        ArrayList<Entity> entities = manager.getEntitiesByComponents(C_Player.class, C_Action.class,
                C_CustomCommand.class);
        for (Entity entity : entities) {
            C_Action action = manager.getComponentManager().getComponentByEntityAndType(entity, C_Action.class);
            if (this.getName().equals(action.getAction())) {
                linkCommand(entity, manager, action);
            }
        }
        return super.excute(manager);
    }
}
