package libs.item;

import engine.Manager.AFactory;
import libs.item.components.*;

public class ItemFactory extends AFactory {
    public ItemFactory() {
    }

    @Override
    public void init() {
    }

    @Override
    public void initComponents() {
        this.components.add(new C_Item());
        this.components.add(new C_ItemList());
    }

    @Override
    public void initSystemes() {

    }

}
