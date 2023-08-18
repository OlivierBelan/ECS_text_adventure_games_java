package libs.command;

import java.util.ArrayList;
import java.util.Iterator;

import engine.Entities.Entity;
import engine.Manager.Manager;
import engine.Systems.ASysteme;
import libs.character.components.C_Character;
import libs.item.components.C_Item;
import libs.item.components.C_ItemList;
import communication.Message.Code;

public abstract class Command extends ASysteme {
    private ArrayList<String> _commandOptions;
    private String _name;
    protected StringBuilder _message = new StringBuilder("");

    public Command(String name) {
        this._name = name;
    }

    public String getName() {
        return _name;
    }

    public ArrayList<String> getOptions() {
        return this._commandOptions;
    }

    public boolean setOptions(ArrayList<String> options) {
        this._commandOptions = options;
        return true;
    };

    @Override
    public boolean init(Manager manager) {
        return false;
    }

    @Override
    public boolean excute(Manager manager) {
        return false;
    }

    protected boolean isOption(Manager manager) {
        if (manager.receive.action.getOptions() == null || manager.receive.action.getOptions().isEmpty()) {
            return false;
        }
        return true;
    }

    protected boolean moveItem(Manager manager, Entity e_Source, Entity e_Dest) {
        if (manager.entityHasComponentType(e_Dest, C_Character.class) != null) {
            return moveToCharacter(manager, e_Source, e_Dest);
        }
        return moveToEntity(manager, e_Source, e_Dest);
    }

    private boolean moveToCharacter(Manager manager, Entity e_Source, Entity e_Dest) {
        C_ItemList items_Dest = manager.getComponentManager().getComponentByEntityAndType(e_Dest, C_ItemList.class);
        C_ItemList items_Source = manager.getComponentManager().getComponentByEntityAndType(e_Source, C_ItemList.class);
        C_Character player = manager.getComponentManager().getComponentByEntityAndType(e_Dest, C_Character.class);
        boolean isAction = false;
        if (items_Dest == null) {
            items_Dest = manager.getComponentManager().createComponent(C_ItemList.class);
        }
        for (String option : manager.receive.action.getOptions()) {
            Iterator<C_Item> it_source = items_Source.getItems().iterator();
            while (it_source.hasNext()) {
                C_Item item = it_source.next();
                if (item.getDescription().equals(option)) {
                    isAction = true;
                    if (player.setWeight(player.getWeight() + item.getWeight())) {
                        items_Dest.addItem(item);
                        manager.linkEntityToComponent(e_Dest, items_Dest);
                        it_source.remove();
                        manager.linkEntityToComponent(e_Source, items_Source);
                        _message.append(option + " taken\n");
                    } else
                        _message.append(option + " is to heavy\n");
                }
            }
        }
        return isAction;
    }

    private boolean moveToEntity(Manager manager, Entity e_Source, Entity e_Dest) {
        C_ItemList items_Dest = manager.getComponentManager().getComponentByEntityAndType(e_Dest, C_ItemList.class);
        C_ItemList items_Source = manager.getComponentManager().getComponentByEntityAndType(e_Source, C_ItemList.class);
        boolean isAction = false;
        if (items_Dest == null) {
            items_Dest = manager.getComponentManager().createComponent(C_ItemList.class);
        }
        for (String option : manager.receive.action.getOptions()) {
            Iterator<C_Item> it_source = items_Source.getItems().iterator();
            while (it_source.hasNext()) {
                C_Item item = it_source.next();
                if (item.getDescription().equals(option)) {
                    isAction = true;
                    items_Dest.addItem(item);
                    manager.linkEntityToComponent(e_Dest, items_Dest);
                    it_source.remove();
                    manager.linkEntityToComponent(e_Source, items_Source);
                    _message.append(option + " ");
                }
            }
        }
        return isAction;
    }

    protected void setSend(Manager manager, Entity entity, Code code) {
        manager.send.code = code;
        manager.send.id = entity.getId();
        manager.send.message = _message.toString();
    }

}
