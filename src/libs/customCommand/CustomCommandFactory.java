package libs.customCommand;

import java.util.ArrayList;

import engine.Manager.AFactory;
import libs.customCommand.components.C_CustomCommand;
import libs.customCommand.systemes.S_Add;
import libs.customCommand.systemes.S_Help;
import libs.customCommand.systemes.S_Link;
import libs.customCommand.systemes.S_Look;
import libs.customCommand.systemes.S_Play;
import libs.customCommand.systemes.S_Remove;

public class CustomCommandFactory extends AFactory {
    static public ArrayList<String> commandWord = new ArrayList<>();
    static {
        commandWord.add("add");
        commandWord.add("remove");
        commandWord.add("play");
        commandWord.add("look");
        commandWord.add("help");
        commandWord.add("link");

    }

    @Override
    public void init() {

    }

    @Override
    public void initComponents() {
        this.components.add(new C_CustomCommand());
    }

    @Override
    public void initSystemes() {
        this.systems.add(new S_Add("add"));
        this.systems.add(new S_Link("link"));
        this.systems.add(new S_Remove("remove"));
        this.systems.add(new S_Play("play"));
        this.systems.add(new S_Help("help"));
        this.systems.add(new S_Look("look"));
    }
}
