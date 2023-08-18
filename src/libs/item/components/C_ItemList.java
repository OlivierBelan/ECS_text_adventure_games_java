package libs.item.components;

import java.util.ArrayList;
import java.util.function.Supplier;

import engine.Components.IComponent;

public class C_ItemList extends IComponent implements Supplier<IComponent> {
    private ArrayList<C_Item> items = new ArrayList<>();

    public C_ItemList() {

    }

    public C_Item getItemByName(String name) {
        for (int i = 0; i != items.size(); i++) {
            if (items.get(i).getDescription().equals(name)) {
                return items.get(i);
            }
        }
        return null;
    }

    public C_Item removeItemByName(String name) {
        for (int i = 0; i != items.size(); i++) {
            if (items.get(i).getDescription().equals(name)) {
                return items.remove(i);
            }
        }
        return null;
    }

    public boolean addItem(C_Item item) {
        return items.add(item);
    }
    public boolean addItem(String description, int weigth) {
        C_Item item = new C_Item(description, weigth);
        return items.add(item);
    }

    public Integer getWeigth() {
        int weigth = 0;
        for (int i = 0; i != items.size(); i++) {
            weigth += items.get(i).getWeight();
        }
        return weigth;
    }

    public ArrayList<C_Item> getItems() {
        return items;
    }

    @Override
    public IComponent get() {
        return new C_ItemList();
    }
}
