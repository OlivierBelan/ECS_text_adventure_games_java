package libs.customCommand.components;

import java.util.function.Supplier;

import engine.Components.IComponent;

public class C_CustomCommand extends IComponent implements Supplier<IComponent> {

    public C_CustomCommand() {
    }

    @Override
    public C_CustomCommand get() {
        return new C_CustomCommand();
    }
}
