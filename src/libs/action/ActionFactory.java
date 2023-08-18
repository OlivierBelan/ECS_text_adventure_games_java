package libs.action;

import engine.Manager.AFactory;
import libs.action.components.C_Action;

public class ActionFactory extends AFactory {
    @Override
    public void init() {
        
    }
    @Override
    public void initComponents() {
        this.components.add(new C_Action());
        
    }
    @Override
    public void initSystemes() {
    }
}
