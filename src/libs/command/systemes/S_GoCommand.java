package libs.command.systemes;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

import communication.Message;
import engine.Entities.Entity;
import engine.Manager.Manager;
import libs.action.components.C_Action;
import libs.character.components.C_Player;
import libs.command.Command;
import libs.command.components.C_Commmand;
import libs.room.components.C_Room;

public class S_GoCommand extends Command {

    public S_GoCommand(String name) {
        super(name);
    }

    @Override
    public boolean init(Manager manager) {
        return super.init(manager);
    }

    private void setSend(Manager manager, Entity entity, StringBuilder message) {
        manager.send.code = Message.Code.SUCCESS;
        manager.send.id = entity.getId();
        manager.send.message = message.toString();
    }

    private boolean isOption(Manager manager, StringBuilder message) {
        if (manager.receive.action.getOptions() == null || manager.receive.action.getOptions().isEmpty()) {
            message.append("Go where?");
            return true;
        }
        return false;
    }

    private boolean invalidOption(Manager manager, StringBuilder message) {
        message.append("There is no door!");
        return true;
    }

    private boolean swapEntity(Manager manager, Entity player, Entity e_From, Entity e_Dest) {
        player.entities.remove(e_From.getId());
        e_From.entities.remove(player.getId());
        player.entities.add(e_Dest.getId());
        e_Dest.entities.add(player.getId());
        return false;
    }

    // private boolean changeRoom(Manager manager,Entity e_Player, Entity e_room){
    //     Entity destination;
    //     String option = manager.receive.action.getOptions().get(0);
    //     C_Room room = manager.getComponentManager().getComponentByEntityAndType(e_room, C_Room.class);
    //     for (Map.Entry<String, UUID> exits : room.getExits().entrySet()) {
    //         if (exits.getKey().equals(option) && exits.getValue() != null) {
    //             destination = manager.getEntityManager().getEntityById(exits.getValue());
    //             swapEntity(manager, e_Player, e_room, destination);
    //             manager.receive.action.setAction("look");
    //             manager.getSystemeManager().excute(manager);
    //             return true;
    //         }
    //     }  
    //     return false;    
    // }
    
    private boolean goCommand(Entity entity, Manager manager) {
        StringBuilder message = new StringBuilder("");
        Entity e_room = manager.getEntityByEntity(entity, C_Room.class);
        C_Room room = manager.getComponentManager().getComponentByEntityAndType(e_room, C_Room.class);
        if (isOption(manager, message)) {
            setSend(manager, entity, message);
            return true;
        }
        Entity destination;
        String option = manager.receive.action.getOptions().get(0);
        for (Map.Entry<String, UUID> exits : room.getExits().entrySet()) {
            if (exits.getKey().equals(option) && exits.getValue() != null) {
                destination = manager.getEntityManager().getEntityById(exits.getValue());
                swapEntity(manager, entity, e_room, destination);
                manager.receive.action.setAction("look");
                manager.getSystemeManager().excute(manager);
                return true;
            }
        }      
        invalidOption(manager, message);
        setSend(manager, entity, message);
        return true;
    }

    @Override
    public boolean excute(Manager manager) {
        ArrayList<Entity> entities = manager.getEntitiesByComponents(C_Player.class, C_Action.class, C_Commmand.class);
        for (Entity entity : entities) {
            C_Action action = manager.getComponentManager().getComponentByEntityAndType(entity, C_Action.class);
            if (this.getName().contains(action.getAction())) {
                goCommand(entity, manager);
            }
        }
        return super.init(manager);
    }
}
