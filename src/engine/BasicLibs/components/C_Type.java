package engine.BasicLibs.components;

import java.util.function.Supplier;

import engine.Components.IComponent;

public class C_Type extends IComponent implements Supplier<IComponent> {
    public C_Type() {
    }
    public C_Type(String type) {
    }
    public String type = null;
    @Override
    public C_Type get() {
        return new C_Type();
    }
}