package engine.Manager;

import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import communication.IServer;
import communication.Message;
import engine.BasicLibs.components.C_Type;
import engine.BasicLibs.systemes.CreateEntityType;
import engine.BasicLibs.systemes.Server;
import engine.Components.ComponentManager;
import engine.Components.IComponent;
import engine.Entities.Entity;
import engine.Entities.EntityManager;
import engine.Systems.SystemeManager;
import libs.action.ActionFactory;
import libs.character.CharacterFactory;
import libs.command.CommandFactory;
import libs.customCommand.CustomCommandFactory;
import libs.item.ItemFactory;
import libs.reader.ReadFile;
import libs.room.RoomFactory;
import libs.room.components.C_RoomFromFile;

public class Manager implements IServer {
    private EntityManager entityManager = new EntityManager();
    private ComponentManager componentManager = new ComponentManager();
    private SystemeManager systemeManager = new SystemeManager();
    private ReadFile reader = new ReadFile();
    public Message receive = new Message();
    public Message send = new Message();
    public boolean isfinish = false;

    public Manager() {
        // init();
    }

    public void init() {
        initComponent();
        initSysteme();
        systemeManager.init(this);
        // System.out.println(entityManager.entities.size());
        // System.out.println(componentManager.components.size());
    }

    private void initSysteme() {
        systemeManager.addSysteme(new Server());
        systemeManager.addSysteme(new RoomFactory().getSystems());
        systemeManager.addSysteme(new ItemFactory().getSystems());
        systemeManager.addSysteme(new CreateEntityType());
        systemeManager.addSysteme(new CharacterFactory().getSystems());
        systemeManager.addSysteme(new CustomCommandFactory().getSystems());
        systemeManager.addSysteme(new CommandFactory().getSystems());
    }

    private void initComponent() {
        componentManager.addComponentToFactory(new C_Type());
        componentManager.addComponentToFactory(new RoomFactory().getComponents());
        componentManager.addComponentToFactory(new ItemFactory().getComponents());
        componentManager.addComponentToFactory(new CharacterFactory().getComponents());
        componentManager.addComponentToFactory(new ActionFactory().getComponents());
        componentManager.addComponentToFactory(new CustomCommandFactory().getComponents());
        componentManager.addComponentToFactory(new CommandFactory().getComponents());
    }

    public ComponentManager getComponentManager() {
        return componentManager;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public SystemeManager getSystemeManager() {
        return systemeManager;
    }

    public Entity linkEntityToComponent(Entity entity, IComponent... component) {
        for (int i = 0; i != component.length; i++) {
            IComponent tmp = entityHasComponentType(entity, component[i]);
            if (tmp != null) {
                this.entityManager.removeComponentToEntity(entity, tmp);
                this.componentManager.removeEntityToComponent(tmp, entity);
            }
            this.entityManager.addComponentToEntity(entity, component[i]);
            this.componentManager.addEntityToComponent(component[i], entity);
        }
        return this.entityManager.getEntityById(entity.getId());
    }

    public void unLinkEntityToComponent(Entity entity, IComponent... component) {
        for (int i = 0; i != component.length; i++) {
            this.entityManager.removeComponentToEntity(entity, component[i]);
            this.componentManager.removeEntityToComponent(component[i], entity);
        }
    }

    public void linkEntityToEntity(Entity entityA, Entity entityB) {
        this.entityManager.addEntityToEntity(entityA, entityB);
    }

    public void unLinkEntityToEntity(Entity entityA, Entity entityB) {
        this.entityManager.removeEntityToEntity(entityA, entityB);
    }

    public IComponent entityHasComponentType(Entity entity, IComponent component) {
        for (UUID id : entity.components) {
            if (componentManager.getComponentById(id).getClass() == component.getClass())
                return componentManager.getComponentById(id);
        }
        return null;
    }

    public IComponent entityHasComponentType(Entity entity, Class<?> clazz) {
        for (UUID id : entity.components) {
            if (componentManager.getComponentById(id).getClass() == clazz)
                return componentManager.getComponentById(id);
        }
        return null;
    }

    public ArrayList<Entity> getEntitiesByComponents(Class<?>... clazz) {
        ArrayList<Entity> res = new ArrayList<>();
        boolean hasComponents = true;
        for (int i = 0; i != entityManager.getEntities().size(); i++) {
            for (int j = 0; j != clazz.length; j++) {
                if (entityHasComponentType(entityManager.getEntities().get(i), clazz[j]) == null) {
                    hasComponents = false;
                }
            }
            if (hasComponents == true) {
                res.add(entityManager.getEntities().get(i));
            }
            hasComponents = true;
        }
        return res;
    }

    public ArrayList<Entity> getEntitiesByComponents(Set<UUID> entitiesId, Class<?>... clazz) {
        ArrayList<Entity> res = new ArrayList<>();
        boolean hasComponents = true;
        for (UUID entityId : entitiesId) {
            Entity entity = this.entityManager.getEntityById(entityId);
            for (int i = 0; i != clazz.length; i++) {
                if (entityHasComponentType(entity, clazz[i]) == null) {
                    hasComponents = false;
                }
            }
            if (hasComponents == true) {
                res.add(entity);
            }
        }
        return res;
    }

    public Entity getEntityByEntity(Entity entity, Class<?> clazz) {
        for (UUID entityId : entity.entities) {
            Entity res = entityManager.getEntityById(entityId);
            if (entityHasComponentType(res, clazz) != null) {
                return res;
            }
        }
        return null;
    }

    @Override
    public boolean receive(Message message) {
        this.receive = message;
        // System.out.println("RECEIVE FROM MANAGER");
        this.systemeManager.excute(this);
        return isfinish;
    }

    @Override
    public Message send() {
        // System.out.println("SEND FROM MANAGER");
        return send;
    }

    public boolean loadMap(String filePath) {
        ArrayList<String> file = reader.readFile(filePath);
        for (String line : file) {
            linkEntityToComponent(entityManager.createEntity(),
                    new C_RoomFromFile(line.replaceAll(",\\s+", ",").split(",")));
        }
        return false;

    }

    // @Override
    // public void start(Stage primaryStage) throws Exception {
    //     guiStage = primaryStage;
    //     guiStage.setTitle("Zuul Graphic");
    //     this.systemeManager.excute(this);
    //     // CustomView customView = new CustomView(controller);
    //     // controller.registerObserver(customView::update);
    //     // primaryStage.setScene(customView.getScene());
    //     guiStage.show();
    // }

}
