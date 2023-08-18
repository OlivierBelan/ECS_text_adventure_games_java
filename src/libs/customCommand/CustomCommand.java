package libs.customCommand;

import java.util.ArrayList;

import communication.Message.Code;
import engine.Entities.Entity;
import engine.Manager.Manager;
import engine.Systems.ASysteme;

public class CustomCommand extends ASysteme {
    private ArrayList<String> _commandOptions;
    private String _name;
    protected StringBuilder _message = new StringBuilder("");

    public CustomCommand(String name) {
        this._name = name;
    }

    public String getName() {
        return _name;
    }

    public ArrayList<String> getOptions() {
        return this._commandOptions;
    }

    public boolean setOptions(ArrayList<String> options) {
        this._commandOptions = options;
        return true;
    };

    @Override
    public boolean init(Manager manager) {
        return false;
    }

    @Override
    public boolean excute(Manager manager) {
        return false;
    }

    protected boolean isOption(Manager manager) {
        if (manager.receive.action.getOptions() == null || manager.receive.action.getOptions().isEmpty()) {
            return false;
        }
        return true;
    }

    protected void setSend(Manager manager, Entity entity, Code code) {
        manager.send.code = code;
        manager.send.id = entity.getId();
        manager.send.message = _message.toString();
    }

}
