package libs.character.components;

import java.util.function.Supplier;

import engine.Components.IComponent;

public class C_Character extends IComponent implements Supplier<IComponent> {
    protected String name;
    protected int maxWeight = 10;
    protected int weight = 0;

    public C_Character(String name) {
        this.name = name;
    }

    public C_Character() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxWeight() {
        return maxWeight;
    }

    public int getWeight() {
        return weight;
    }

    public boolean setWeight(int weight) {
        if (weight <= maxWeight) {
            this.weight = weight;
            return true;
        }
        return false;
    }

    public boolean setMaxWeight(int weight) {
        this.maxWeight = weight;
        return true;
    }
    @Override
    public C_Character get() {
        return new C_Character();
    }
}
