package graphical.Views;

import java.util.HashMap;

import graphical.Models.*;
import graphical.Controllers.PlayController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class PlayView extends AView {

    private final static HashMap<String, ChoiceBox<String>> mapChoiceBox = new HashMap<>();
    private final PlayController playController = new PlayController();

    static {
        mapChoiceBox.put("go", new ChoiceBox<>());
        mapChoiceBox.put("take", new ChoiceBox<>());
        mapChoiceBox.put("giveCharacter", new ChoiceBox<>());
        mapChoiceBox.put("giveItem", new ChoiceBox<>());
        mapChoiceBox.put("drop", new ChoiceBox<>());
    }

    public PlayView(Model controller) {
        super(controller);
        this.playController.setController(controller);
    }

    @Override
    public void initView() {
        this.playController.setManager(model.getManager());
        final int gap = 5;

        this.playController.goChoiceList(mapChoiceBox);
        VBox root = new VBox(2 * gap);
        root.setAlignment(Pos.CENTER);
        displayArea.setText(model.getMessage().message);
        displayArea.setFont(Font.font(20));
        GridPane buttonsArea = new GridPane();
        buttonsArea.setHgap(gap);
        buttonsArea.setVgap(gap);
        buttonsArea.setAlignment(Pos.CENTER);

        Button help = new Button("help");
        help.setOnAction((e) -> this.playController.helpButton());
        buttonsArea.add(help, 0, 0);

        Button look = new Button("look");
        look.setOnAction((e) -> this.playController.lookButton());
        buttonsArea.add(look, 0, 1);

        Button go = new Button("go");
        go.setOnAction((e) -> this.playController.goButton(mapChoiceBox));
        buttonsArea.add(go, 0, 2);
        buttonsArea.add(mapChoiceBox.get("go"), 1, 2);

        Button take = new Button("take");
        take.setOnAction((e) -> this.playController.takeButton(mapChoiceBox));
        buttonsArea.add(take, 0, 3);
        buttonsArea.add(mapChoiceBox.get("take"), 1, 3);

        Button drop = new Button("drop");
        drop.setOnAction((e) -> this.playController.dropButton(mapChoiceBox));
        buttonsArea.add(drop, 0, 4);
        buttonsArea.add(mapChoiceBox.get("drop"), 1, 4);

        Button give = new Button("give");
        give.setOnAction((e) -> this.playController.giveButton(mapChoiceBox));
        buttonsArea.add(give, 0, 5);
        buttonsArea.add(mapChoiceBox.get("giveItem"), 1, 5);
        buttonsArea.add(mapChoiceBox.get("giveCharacter"), 2, 5);

        Button custom = new Button("custom mode");
        custom.setOnAction((e) -> this.playController.customButton());
        buttonsArea.add(custom, 0, 6);

        root.getChildren().addAll(displayArea, buttonsArea);
        this.scene = new Scene(root, 900, 700);

    }

    
    /** 
     * @param update
     */
    @Override
    public void update(Model update) {
        this.model = update;
        this.playController.setController(model);
        this.playController.setManager(update.getManager());
        this.playController.goChoiceList(mapChoiceBox);
        displayArea.setText(update.getMessage().message);
    }
}
