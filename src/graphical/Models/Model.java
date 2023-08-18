package graphical.Models;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import communication.Message;
import communication.Server;
import engine.Manager.Manager;
import libs.parser.Action;

public class Model {

    private final Server server = new Server();
    private final List<Consumer<Model>> observers = new ArrayList<>();
    private Manager manager;

    public Model() {
    }

    public void setMessage() {
    }

    
    /** 
     * @param action
     */
    public void compute(Action action) {
        this.server.setMessage(action);
        this.manager.receive(this.server.send());
        this.server.receive(this.manager.send());
        tellViews();
    }

    
    /** 
     * @return Message
     */
    public Message getMessage() {
        return this.server.getReceive();
    }

    
    /** 
     * @param observer
     */
    public void registerObserver(Consumer<Model> observer) {
        observers.add(observer);
    }

    private void tellViews() {
        for (Consumer<Model> o : observers) {
            o.accept(this);
        }
    }
    
    /** 
     * @param filePath
     */
    public void newGame(String filePath) {
        this.manager = new Manager();
        this.manager.loadMap(filePath);
        this.manager.init();
        this.manager.receive(server.initSend());
        this.server.initReceive(this.manager.send());

    }
    
    /** 
     * @return Manager
     */
    public Manager getManager() {
        return manager;
    }
}
