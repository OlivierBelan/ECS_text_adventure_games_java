package graphical.Controllers;

import engine.Manager.Manager;
import graphical.Models.*;
import libs.parser.Action;

public class AController {
    protected Action action = new Action();
    protected Model controller;
    protected Manager manager;

    public AController() {

    }

    
    /** 
     * @param manager
     */
    public void setManager(Manager manager) {
        this.manager = manager;
    }

    
    /** 
     * @param controller
     */
    public void setController(Model controller) {
        this.controller = controller;
    }

}
