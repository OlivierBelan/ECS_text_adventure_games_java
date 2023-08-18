package libs.room.components;

import java.util.function.Supplier;

import engine.Components.IComponent;

public class C_RoomFromFile extends IComponent implements Supplier<IComponent> {

    String roomFile[];

    public C_RoomFromFile() {

    }

    public C_RoomFromFile(String roomFile[]) {
        this.roomFile = roomFile;
    }

    public String [] getRoomFile() {
        return roomFile;
    }

    @Override
    public IComponent get() {
        return null;
    }
}
