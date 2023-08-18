package libs.character.components;

import java.util.function.Supplier;

import engine.Components.IComponent;

public class C_Player extends IComponent implements Supplier<IComponent> {

    public C_Player() {
    }

    @Override
    public C_Player get() {
        return new C_Player();
    }
}
