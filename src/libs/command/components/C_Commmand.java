package libs.command.components;

import java.util.function.Supplier;

import engine.Components.IComponent;

public class C_Commmand extends IComponent implements Supplier<IComponent>{
    
    public C_Commmand() {
    }
    @Override
    public C_Commmand get() {
        return new C_Commmand();
    }
}
