package libs.action.components;

import java.util.ArrayList;
import java.util.function.Supplier;

import engine.Components.IComponent;

public class C_Action extends IComponent implements Supplier<IComponent> {
    private String action = "unknow";
    private ArrayList<String> options = new ArrayList<>();

    public C_Action() {
    }

    public String getAction() {
        return action;
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }
    @Override
    public C_Action get() {
        return new C_Action();
    }
}
