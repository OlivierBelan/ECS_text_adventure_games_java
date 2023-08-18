package graphical.Views;

import java.io.File;

import graphical.Models.*;
import graphical.Graphical;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;

public class LoaderView extends AView {

    public LoaderView(Model model) {
        super(model);

    }

    @Override
    public void initView() {
        FileChooser fileChooser = new FileChooser();
        Button button = new Button("Select File");
        button.setAlignment(Pos.CENTER);
        button.setOnAction(e -> {
            File selectedFile = fileChooser.showOpenDialog(Graphical.getPrimaryStage());
            System.out.println(selectedFile.getAbsolutePath());
            model.newGame(selectedFile.getAbsolutePath());
            Graphical.getViews().get("custom").initView();
            Graphical.getViews().get("play").initView();
            Graphical.getPrimaryStage().setScene(Graphical.getViews().get("custom").getScene());
        });

        displayArea.setText(
                "Welcome to the notorius world of Zuul, the most boring game of the year\n Please select a map to load");
        displayArea.setFont(Font.font(20));

        VBox vBox = new VBox(displayArea, button);
        vBox.setAlignment(Pos.CENTER);
        this.scene = new Scene(vBox, 960, 700);
    }

    
    /** 
     * @param update
     */
    @Override
    public void update(Model update) {

    }
}
