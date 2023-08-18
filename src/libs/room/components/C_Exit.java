package libs.room.components;

import java.util.HashMap;
import java.util.UUID;
import java.util.function.Supplier;

import engine.Components.IComponent;

public class C_Exit extends IComponent implements Supplier<IComponent> {
    private HashMap<String, UUID> exits = new HashMap<>();

    public C_Exit() {

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

    @Override
    public C_Exit get() {
        return new C_Exit();
    }
}
