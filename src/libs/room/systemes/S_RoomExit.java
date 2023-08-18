package libs.room.systemes;

import java.util.ArrayList;
import java.util.UUID;

import engine.Entities.Entity;
import engine.Manager.Manager;
import engine.Systems.ASysteme;
import libs.room.components.C_Room;
import libs.room.components.C_RoomFromFile;

public class S_RoomExit extends ASysteme {
    public S_RoomExit() {
    }

    @Override
    public boolean excute(Manager manager) {
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

    void createExit(Entity e_Room, Manager manager) {
        C_RoomFromFile roomFromFile = manager.getComponentManager().getComponentByEntityAndType(e_Room, C_RoomFromFile.class);
        String line[] = roomFromFile.getRoomFile();
        C_Room room = manager.getComponentManager().getComponentByEntityAndType(e_Room, C_Room.class);
        UUID north = getEntityRoomByName(manager, line[2]);
        UUID east = getEntityRoomByName(manager, line[3]);
        UUID south = getEntityRoomByName(manager, line[4]);
        UUID west = getEntityRoomByName(manager, line[5]);
        manager.unLinkEntityToComponent(e_Room, roomFromFile);
        room.setExits(north, south, west, east);
        manager.linkEntityToComponent(e_Room, room);
    }

    @Override
    public boolean init(Manager manager) {
        ArrayList<Entity> e_Rooms = manager.getEntitiesByComponents(C_RoomFromFile.class, C_Room.class);
        for (Entity e_Room : e_Rooms) {
            createExit(e_Room, manager);
        }
        return false;
    }
}
