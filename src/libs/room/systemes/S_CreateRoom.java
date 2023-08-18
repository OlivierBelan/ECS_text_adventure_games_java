package libs.room.systemes;

import java.util.ArrayList;

import engine.Entities.Entity;
import engine.Manager.Manager;
import engine.Systems.ASysteme;
import libs.item.components.C_ItemList;
import libs.room.components.C_Room;
import libs.room.components.C_RoomFromFile;

public class S_CreateRoom extends ASysteme {
    public S_CreateRoom() {
    }

    @Override
    public boolean excute(Manager manager) {
        return false;
    }

    private void addRoom(Entity e_Room, Manager manager, String line[]) {
        C_Room room = new C_Room();
        if (line.length >= 2 && room.setNameAndDescription(line[0], line[1])) {
            System.out.println("name = " + line[0]+ " description = " + line[1]);
            manager.linkEntityToComponent(e_Room, room);
        }
    }

    private void addItems(Entity e_Room, Manager manager, String line[]) {
        C_ItemList itemList = new C_ItemList();
        if (line.length >= 8) {
            for(int i = 7; i <= line.length; i += 2) {
                try {
                    itemList.addItem(line[i-1], Integer.parseInt(line[i]));
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
        manager.linkEntityToComponent(e_Room, itemList);
    }

    private void createRoom(Entity e_Room, Manager manager) {
        String line[] = manager.getComponentManager().getComponentByEntityAndType(e_Room, C_RoomFromFile.class)
                .getRoomFile();
        addRoom(e_Room, manager, line);
        addItems(e_Room, manager, line);
    }
    
    @Override
    public boolean init(Manager manager) {
        ArrayList<Entity> e_Rooms = manager.getEntitiesByComponents(C_RoomFromFile.class);
        for (Entity e_Room : e_Rooms) {
            createRoom(e_Room, manager);
        }
        return false;
    }
}
