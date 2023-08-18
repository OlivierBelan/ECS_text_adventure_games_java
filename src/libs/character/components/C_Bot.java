package libs.character.components;

import java.util.function.Supplier;

import engine.Components.IComponent;

public class C_Bot extends IComponent implements Supplier<IComponent> {
    public C_Bot() {
    }

    @Override
    public C_Bot get() {
        return new C_Bot();
    }
}
