package graphical.Views;

import graphical.Models.*;
import javafx.scene.Scene;
import javafx.scene.control.Label;

public abstract class AView {
    protected Scene scene;
    protected Model model;
    protected Label displayArea = new Label("Welcome");

    public AView(Model model) {
        this.model = model;
    }

    
    /** 
     * @return Scene
     */
    public Scene getScene() {
        return scene;
    }
    public abstract void initView();
    public abstract void update(Model update);
}
