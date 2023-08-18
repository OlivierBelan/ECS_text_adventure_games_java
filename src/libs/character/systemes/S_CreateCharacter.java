package libs.character.systemes;

import java.util.ArrayList;
import java.util.Random;

import engine.BasicLibs.components.C_Type;
import engine.Entities.Entity;
import engine.Manager.Manager;
import engine.Systems.*;
import libs.action.components.C_Action;
import libs.character.CharacterType;
import libs.character.components.C_Character;
import libs.character.components.C_Bot;
import libs.character.components.C_Player;
import libs.customCommand.components.C_CustomCommand;
import libs.item.components.C_ItemList;
import libs.room.components.C_Room;

public class S_CreateCharacter extends ASysteme {
    public S_CreateCharacter() {

    }

    @Override
    public boolean init(Manager manager) {
        Entity entity = manager.getEntityManager().createEntity();
        createBot(entity, manager);
        C_Type type = manager.getComponentManager().createComponent(C_Type.class);
        type.type = CharacterType.BOT;
        manager.linkEntityToComponent(entity, type);

        return false;
    }

    private Entity getRoom(Manager manager) {
        Random r = new Random();
        ArrayList<Entity> entities = manager.getEntitiesByComponents(C_Room.class);
        int low = 0;
        int max = entities.size();
        return entities.get(r.nextInt(max - low) + low);
    }

    private void createPlayer(Entity entity, Manager manager) {
        C_Action action = manager.getComponentManager().createComponent(C_Action.class);
        action.setAction("look");
        manager.linkEntityToComponent(entity, action);
        manager.linkEntityToComponent(entity, new C_Character("You"));
        manager.linkEntityToComponent(entity, manager.getComponentManager().createComponent(C_Player.class));
        manager.linkEntityToComponent(entity, manager.getComponentManager().createComponent(C_ItemList.class));
        manager.linkEntityToComponent(entity, manager.getComponentManager().createComponent(C_CustomCommand.class));
        manager.linkEntityToEntity(entity, getRoom(manager));
    }

    private void createBot(Entity entity, Manager manager) {
        C_Character bot = manager.getComponentManager().createComponent(C_Character.class);
        bot.setName("Marc");
        manager.linkEntityToComponent(entity, bot);
        manager.linkEntityToComponent(entity, manager.getComponentManager().createComponent(C_Action.class));
        manager.linkEntityToComponent(entity, manager.getComponentManager().createComponent(C_Bot.class));
        manager.linkEntityToComponent(entity, manager.getComponentManager().createComponent(C_ItemList.class));
        manager.linkEntityToEntity(entity, getRoom(manager));
    }

    private void create(Entity entity, Manager manager) {
        try {
            C_Type type = manager.getComponentManager().getComponentByEntityAndType(entity, C_Type.class);
            if (type.type == CharacterType.CREATEPLAYER) {
                createPlayer(entity, manager);
                type.type = CharacterType.PLAYER;
            } else if (type.type == CharacterType.CREATEBOT) {
                createBot(entity, manager);
                type.type = CharacterType.BOT;
            }
            manager.linkEntityToComponent(entity, type);

            manager.send.id = entity.getId();
            manager.send.type = type.type;
        } catch (Exception e) {
            System.out.println("Error from create(), CreateCharacter.java:");
            System.out.println(e);
        }
    }

    @Override
    public boolean excute(Manager manager) {
        for (Entity entity : manager.getEntityManager().getEntities()) {
            if (checkEntity(entity, manager, CharacterType.CREATEPLAYER)
                    || checkEntity(entity, manager, CharacterType.CREATEBOT)) {
                create(entity, manager);
            }
        }
        return false;
    }
}
