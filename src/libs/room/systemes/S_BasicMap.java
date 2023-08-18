package libs.room.systemes;

import engine.Entities.Entity;
import engine.Manager.Manager;
import engine.Systems.ASysteme;
import libs.item.components.C_ItemList;
import libs.room.components.C_Room;

public class S_BasicMap extends ASysteme {
    public S_BasicMap() {
    }

    public void setRoom(C_Room room, String name, String description) {
        room.setName(name);
        room.setDescription(description);
    }

    @Override
    public boolean init(Manager manager) {
        try {
            Entity e_outside = manager.getEntityManager().createEntity();
            Entity e_theatre = manager.getEntityManager().createEntity();
            Entity e_pub = manager.getEntityManager().createEntity();
            Entity e_lab = manager.getEntityManager().createEntity();
            Entity e_office = manager.getEntityManager().createEntity();
            C_Room outside = manager.getComponentManager().createComponent(C_Room.class);
            C_Room theatre = manager.getComponentManager().createComponent(C_Room.class);
            C_Room pub = manager.getComponentManager().createComponent(C_Room.class);
            C_Room lab = manager.getComponentManager().createComponent(C_Room.class);
            C_Room office = manager.getComponentManager().createComponent(C_Room.class);

            // Entity e_item = manager.getEntityManager().createEntity();
            C_ItemList notebook = manager.getComponentManager().createComponent(C_ItemList.class);
            notebook.addItem("notebook", 2);

            setRoom(outside, "outside", "outside the main entrance of the university");
            outside.setExits(null, e_lab.getId(), e_pub.getId(), e_theatre.getId());

            setRoom(theatre, "theatre", "in a lecture theatre");
            theatre.setExits(null, null, e_outside.getId(), null);

            setRoom(pub, "pub", "in the campus pub");
            pub.setExits(null, null, null, e_outside.getId());

            setRoom(lab, "lab", "in a computing lab");
            lab.setExits(e_outside.getId(), null, null, e_office.getId());

            setRoom(office, "office", "in the computing admin office");
            office.setExits(null, null, e_lab.getId(), null);

            // manager.linkEntityToComponent(e_item, notebook, new C_Type(ItemType.ITEM));
            // manager.linkEntityToComponent(e_outside, notebook);
            manager.linkEntityToComponent(e_outside, outside, notebook);
            manager.linkEntityToComponent(e_theatre, theatre, new C_ItemList());
            manager.linkEntityToComponent(e_pub, pub, new C_ItemList());
            manager.linkEntityToComponent(e_lab, lab, new C_ItemList());
            manager.linkEntityToComponent(e_office, office, new C_ItemList());

        } catch (Exception e) {
            System.out.println("Error from BasicMap.java:");
            System.out.println(e);
        }

        return false;
    }

    @Override
    public boolean excute(Manager manager) {
        return false;
    }

}
