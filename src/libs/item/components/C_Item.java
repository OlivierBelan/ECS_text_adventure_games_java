package libs.item.components;

import java.util.function.Supplier;

import engine.Components.IComponent;

public class C_Item extends IComponent implements Supplier<IComponent> {
    private String description;
    private int weight;

    public C_Item() {
    }

    public C_Item(String description, int weight) {
        this.description = description;
        this.weight = weight;
    }

    public int getWeight() {
        return this.weight;
    }

    public boolean setWeight(int weight) {
        this.weight = weight;
        return true;
    }

    public String getDescription() {
        return this.description;
    }

    public boolean setDescription(String description) {
        this.description = description;
        return true;
    }

    public C_Item get() {
        return new C_Item();
    }
}
