package engine.Systems;

import java.util.ArrayList;

import engine.Manager.*;

public class SystemeManager {
    private ArrayList<ASysteme> systems = new ArrayList<>();

    public SystemeManager() {
    }

    public SystemeManager(ASysteme... systemes) {
        this.addSysteme(systemes);
    }

    public boolean excute(Manager manager) {
        for (int i = 0; i != systems.size(); i++) {
            systems.get(i).excute(manager);
        }
        return true;
    }

    public boolean init(Manager manager) {
        for (int i = 0; i != systems.size(); i++) {
            systems.get(i).init(manager);
        }
        return true;
    }

    // public boolean get(Manager manager) {
    //     for (int i = 0; i != systems.size(); i++) {
    //         systems.get(i).get(manager);
    //     }
    //     return true;
    // }

    // public boolean post(Manager manager) {
    //     for (int i = 0; i != systems.size(); i++) {
    //         systems.get(i).post(manager);
    //     }
    //     return true;
    // }

    public boolean addSysteme(ASysteme system) {
        return this.systems.add(system);
    }

    public boolean addSysteme(ASysteme... system) {
        for (int i = 0; i != system.length; i++) {
            this.systems.add(system[i]);
        }
        return true;
    }

    public boolean addSysteme(ArrayList<ASysteme> system) {
        return this.systems.addAll(system);
    }

    public boolean removeSystem(int index) {
        return this.systems.remove(this.systems.get(index));
    }

    public boolean removeSystem(ArrayList<ASysteme> system) {
        return this.systems.removeAll(system);
    }

    public boolean removeSystem(ASysteme ...system) {
        for (int i = 0; i != system.length; i++) {
            this.systems.remove(system[i]);
        }
        return true;
    }

}
