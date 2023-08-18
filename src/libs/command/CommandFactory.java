package libs.command;

import java.util.ArrayList;

import engine.Manager.AFactory;
import libs.command.components.C_Commmand;
import libs.command.systemes.*;

public class CommandFactory extends AFactory{
    static public ArrayList<String> commandWord = new ArrayList<>();
    static {
        commandWord.add("welcome");
        commandWord.add("go");
        commandWord.add("help");
        commandWord.add("drop");
        commandWord.add("give");
        commandWord.add("look");
        commandWord.add("quit");
        commandWord.add("take");
        commandWord.add("unknow");
        commandWord.add("test");
        commandWord.add("custom");
    }
    public CommandFactory() {
    }
    public void init() {
    }
    @Override
    public void initComponents() {
        this.components.add(new C_Commmand());
    }
    @Override
    public void initSystemes() {
        this.systems.add(new S_WelcomeCommand("welcome"));
        this.systems.add(new S_GoCommand("go"));
        this.systems.add(new S_HelpCommand("help"));
        this.systems.add(new S_DropCommand("drop"));
        this.systems.add(new S_GiveCommand("give"));
        this.systems.add(new S_LookCommand("look"));
        this.systems.add(new S_QuitCommand("quit"));
        this.systems.add(new S_TakeCommand("take"));
        this.systems.add(new S_UnknowCommand("unknow"));        
        this.systems.add(new S_TestCommand("test"));  
        this.systems.add(new S_Custom("custom"));
    }
}
