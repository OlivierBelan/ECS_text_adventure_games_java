package libs.room;

import engine.Manager.AFactory;
import libs.room.components.C_Room;
import libs.room.components.C_RoomFromFile;
// import libs.room.systemes.S_BasicMap;
import libs.room.systemes.S_CreateRoom;
import libs.room.systemes.S_RoomExit;

public class RoomFactory extends AFactory {
    public RoomFactory() {
    }

    public void init() {
    }

    @Override
    public void initComponents() {
        this.components.add(new C_Room());
        this.components.add(new C_RoomFromFile());
    }

    @Override
    public void initSystemes() {
        // this.systems.add(new S_BasicMap());
        this.systems.add(new S_CreateRoom());
        this.systems.add(new S_RoomExit());
    }

}
