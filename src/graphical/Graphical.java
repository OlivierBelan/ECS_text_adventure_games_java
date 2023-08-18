package graphical;

import java.util.HashMap;

import graphical.Models.Model;
import graphical.Views.AView;
import graphical.Views.CustomView;
import graphical.Views.LoaderView;
import graphical.Views.PlayView;
import javafx.application.Application;
import javafx.stage.Stage;

public class Graphical extends Application {

    private final Model model = new Model();
    private static HashMap<String, AView> views = new HashMap<>();
    private static Stage stage;

    public Graphical() {
    }

    
    /** 
     * @return Stage
     */
    public static Stage getPrimaryStage() {
        return stage;
    }
    
    /** 
     * @return HashMap<String, AView>
     */
    public static HashMap<String, AView> getViews() {
        return views;
    }
    
    /** 
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        views.put("custom", new CustomView(model));
        views.put("play", new PlayView(model));
        views.put("loader", new LoaderView(model));
        model.registerObserver(views.get("custom")::update);
        model.registerObserver(views.get("play")::update);
        model.registerObserver(views.get("loader")::update);
        views.get("loader").initView();
        stage.setTitle("Zuul Graphic");
        stage.setScene(views.get("loader").getScene());
        stage.show();
    }

    public void run() {
        launch();
    }
}
