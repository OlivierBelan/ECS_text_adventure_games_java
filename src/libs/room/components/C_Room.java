package libs.room.components;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

import engine.Components.IComponent;

public class C_Room extends IComponent implements Supplier<IComponent> {
    HashMap<String, UUID> exits = new HashMap<>();
    private String description;
    private String name;

    public C_Room(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public C_Room() {
    }

    public void setExits(HashMap<String, UUID> exits) {
        this.exits = exits;
    }

    public void setExits(UUID north, UUID south, UUID west, UUID east) {
        this.exits.put("north", north);
        this.exits.put("south", south);
        this.exits.put("west", west);
        this.exits.put("east", east);
    }

    public HashMap<String, UUID> getExits() {
        return this.exits;
    }

    public boolean setDescription(String description) {
        if (description != null && description != "null") {
            this.description = description;
            return true;
        }
        return false;
    }

    public boolean setName(String name) {
        if (name != null && name != "null") {
            this.name = name;
            return true;
        }
        return false;
    }

    public boolean setNameAndDescription(String name, String description) {
        return (setName(name) && setDescription(description));
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getExitsText() {
        String message = "";
        for (Map.Entry<String, UUID> exits : this.getExits().entrySet()) {
            if (exits.getValue() != null) {
                message += exits.getKey() + " ";
            }
        }
        return message;
    }

    @Override
    public C_Room get() {
        return new C_Room(name, description);
    }
}
