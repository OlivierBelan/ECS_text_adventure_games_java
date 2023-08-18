package engine.Manager;

import java.util.ArrayList;

import engine.Components.IComponent;
import engine.Systems.ASysteme;

public abstract class AFactory {
    protected ArrayList<ASysteme> systems = new ArrayList<>();
    protected ArrayList<IComponent> components = new ArrayList<>();

    public AFactory() {
        this.init();
        this.initComponents();
        this.initSystemes();
    }

    public ArrayList<IComponent> getComponents() {
        return components;
    }

    public ArrayList<ASysteme> getSystems() {
        return systems;
    }

    public abstract void init();

    public abstract void initComponents();

    public abstract void initSystemes();
}
