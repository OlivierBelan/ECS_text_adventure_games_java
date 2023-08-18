package graphical.Views;

import java.util.HashMap;

import graphical.Models.*;
import graphical.Controllers.CustomController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class CustomView extends AView {

    private final static HashMap<String, TextField> mapTextFields = new HashMap<>();
    private final static HashMap<String, ChoiceBox<String>> mapChoiceBox = new HashMap<>();
    private final CustomController customController = new CustomController();
    static {
        mapChoiceBox.put("remove", new ChoiceBox<>());
        mapChoiceBox.put("linkA", new ChoiceBox<>());
        mapChoiceBox.put("linkB", new ChoiceBox<>());
        mapChoiceBox.put("type", new ChoiceBox<>());
        mapChoiceBox.put("north", new ChoiceBox<>());
        mapChoiceBox.put("east", new ChoiceBox<>());
        mapChoiceBox.put("south", new ChoiceBox<>());
        mapChoiceBox.put("west", new ChoiceBox<>());
        mapChoiceBox.get("type").getItems().add("Item");
        mapChoiceBox.get("type").getItems().add("Room");
        mapChoiceBox.get("type").getItems().add("Player");

        mapTextFields.put("name", new TextField());
        mapTextFields.put("weight", new TextField());
        mapTextFields.put("description", new TextField());
    }

    public CustomView(Model model) {
        super(model);
        this.customController.setController(model);
    }

    @Override
    public void initView() {
        this.customController.setManager(model.getManager());
        this.customController.initChoiceBox(mapChoiceBox);
        this.customController.initAddField(mapChoiceBox, mapTextFields);
        final int gap = 5;

        VBox root = new VBox(2 * gap);
        root.setAlignment(Pos.CENTER);
        displayArea.setText(model.getMessage().message);
        displayArea.setFont(Font.font(20));
        GridPane buttonsArea = new GridPane();
        buttonsArea.setHgap(gap);
        buttonsArea.setVgap(gap);
        buttonsArea.setAlignment(Pos.CENTER);

        Button help = new Button("help");
        help.setOnAction((e) -> this.customController.helpButton());
        buttonsArea.add(help, 0, 0);

        Button look = new Button("look");
        look.setOnAction((e) -> this.customController.lookButton());
        buttonsArea.add(look, 0, 1);

        Button remove = new Button("remove");
        remove.setOnAction((e) -> this.customController.removeButton(mapChoiceBox));
        buttonsArea.add(remove, 0, 2);
        buttonsArea.add(mapChoiceBox.get("remove"), 1, 2);

        Button link = new Button("link");
        link.setOnAction(action -> {
            this.customController.link(mapChoiceBox);
        });
        buttonsArea.add(link, 0, 3);
        buttonsArea.add(mapChoiceBox.get("linkA"), 1, 3);
        buttonsArea.add(mapChoiceBox.get("linkB"), 2, 3);

        Button add = new Button("add");
        add.setOnAction(action -> {
            this.customController.add(mapChoiceBox, mapTextFields);
        });
        buttonsArea.add(add, 0, 4);
        mapChoiceBox.get("type").setOnAction((e) -> {
            if (mapChoiceBox.get("type").getValue().equals("Room")) {
                this.customController.addRoomField(buttonsArea, mapChoiceBox, mapTextFields);
            } else if (mapChoiceBox.get("type").getValue().equals("Item")
                    || mapChoiceBox.get("type").getValue().equals("Player")) {
                this.customController.addItemField(buttonsArea, mapChoiceBox, mapTextFields);
            }
        });
        buttonsArea.add(mapChoiceBox.get("type"), 1, 4);
        buttonsArea.add(mapTextFields.get("name"), 2, 4);

        Button play = new Button("play mode");
        play.setOnAction((e) -> this.customController.playButton());
        buttonsArea.add(play, 1, 5);

        Button newGame = new Button("New Game");
        newGame.setOnAction((e) -> this.customController.newGameButton());
        buttonsArea.add(newGame, 0, 5);

        root.getChildren().addAll(displayArea, buttonsArea);
        this.scene = new Scene(root, 900, 700);
    }

    /**
     * @param update
     */
    @Override
    public void update(Model update) {
        this.model = update;
        this.customController.setManager(update.getManager());
        this.customController.initChoiceBox(mapChoiceBox);
        this.customController.initAddField(mapChoiceBox, mapTextFields);
        displayArea.setText(update.getMessage().message);
    }
}
