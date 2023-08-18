package engine.BasicLibs.systemes;


import communication.Message;
import engine.BasicLibs.components.C_Type;
import engine.Entities.Entity;
import engine.Manager.Manager;
import engine.Systems.ASysteme;
import libs.action.components.C_Action;
import libs.character.CharacterType;
import libs.command.CommandFactory;
import libs.customCommand.CustomCommandFactory;

public class Server extends ASysteme {
    public Server() {
    }

    @Override
    public boolean init(Manager manager) {
        return false;
    }

    @Override
    public boolean excute(Manager manager) {
        if (manager.receive != null) {
            if (manager.receive.event == Message.Event.GET) {
                get(manager);
            } else if (manager.receive.event == Message.Event.POST) {
                post(manager);
            } else if (manager.receive.event == Message.Event.CREATE) {
                createEntityType(1, CharacterType.CREATEPLAYER, manager);
            } else if (manager.receive.event == Message.Event.PLAY) {
                play(manager);
            }
        }
        return false;
    }

    public boolean get(Manager manager) {
        Message send = new Message();
        Entity entity = manager.getEntityManager().getEntityById(manager.receive.id);
        send.id = entity.getId();

        return true;
    }

    public boolean post(Manager manager) {
        // Entity entity = manager.entityManager.getEntityById(manager.receive.id);

        return true;
    }
    public boolean play(Manager manager) {
        Entity entity  = manager.getEntityManager().getEntityById(manager.receive.id);
        C_Action action = manager.getComponentManager().getComponentByEntityAndType(entity, C_Action.class);
        if (!CommandFactory.commandWord.contains(manager.receive.action.getAction()) && !CustomCommandFactory.commandWord.contains(manager.receive.action.getAction())) {
            action.setAction("unknow");
        } else {
            action.setAction(manager.receive.action.getAction());
        }
        action.setOptions(manager.receive.action.getOptions());
        manager.linkEntityToComponent(entity, action);
        return false;
    }
    public void createEntityType(int nb, String type, Manager manager) {
        for (int i = 0; i != nb; i++) {
            Entity entity = manager.getEntityManager().createEntity();
            C_Type c_type = manager.getComponentManager().createComponent(C_Type.class);
            c_type.type = type;
            entity = manager.linkEntityToComponent(entity, c_type);
        }
        // System.out.println(entityManager.entities.size());
    }

}
