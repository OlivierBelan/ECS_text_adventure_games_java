package graphical.Controllers;

import java.util.ArrayList;
import java.util.HashMap;

import graphical.Graphical;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import libs.character.components.C_Character;
import libs.item.components.C_Item;
import libs.item.components.C_ItemList;
import libs.room.components.C_Room;

public class CustomController extends AController {

    public CustomController() {
    }

    /**
     * @param mapChoiceBox
     */
    private void clearChoiceBox(HashMap<String, ChoiceBox<String>> mapChoiceBox) {
        mapChoiceBox.get("remove").getItems().clear();
        mapChoiceBox.get("linkA").getItems().clear();
        mapChoiceBox.get("linkB").getItems().clear();
    }

    /**
     * @param mapChoiceBox
     */
    public void initChoiceBox(HashMap<String, ChoiceBox<String>> mapChoiceBox) {
        ArrayList<C_Character> characters = manager.getComponentManager().getComponentsByClass(C_Character.class);
        ArrayList<C_Room> rooms = manager.getComponentManager().getComponentsByClass(C_Room.class);
        ArrayList<C_ItemList> itemsList = manager.getComponentManager().getComponentsByClass(C_ItemList.class);
        ArrayList<C_Item> items = manager.getComponentManager().getComponentsByClass(C_Item.class);
        clearChoiceBox(mapChoiceBox);
        for (C_Character c_Character : characters) {
            mapChoiceBox.get("remove").getItems().add(c_Character.getName());
        }
        for (C_Room c_room : rooms) {
            mapChoiceBox.get("remove").getItems().add(c_room.getName());
        }
        for (C_ItemList c_ItemList : itemsList) {
            for (C_Item c_Item : c_ItemList.getItems()) {
                mapChoiceBox.get("remove").getItems().add(c_Item.getDescription());
            }
        }
        for (C_Item c_Item : items) {
            mapChoiceBox.get("remove").getItems().add(c_Item.getDescription());
        }
        mapChoiceBox.get("linkA").getItems().addAll(mapChoiceBox.get("remove").getItems());
        mapChoiceBox.get("linkB").getItems().addAll(mapChoiceBox.get("remove").getItems());
    }

    /**
     * @param mapChoiceBox
     */
    private void clearAddField(HashMap<String, ChoiceBox<String>> mapChoiceBox) {
        mapChoiceBox.get("north").getItems().clear();
        mapChoiceBox.get("east").getItems().clear();
        mapChoiceBox.get("south").getItems().clear();
        mapChoiceBox.get("west").getItems().clear();
    }

    /**
     * @param mapChoiceBox
     * @param HashMap<String
     * @param mapTextFields
     */
    public void initAddField(HashMap<String, ChoiceBox<String>> mapChoiceBox,
            HashMap<String, TextField> mapTextFields) {
        clearAddField(mapChoiceBox);
        ArrayList<C_Room> c_Rooms = manager.getComponentManager().getComponentsByClass(C_Room.class);
        for (C_Room c_Room : c_Rooms) {
            mapChoiceBox.get("north").getItems().add(c_Room.getName());
        }
        mapChoiceBox.get("north").getItems().add("null");
        mapChoiceBox.get("east").getItems().addAll(mapChoiceBox.get("north").getItems());
        mapChoiceBox.get("south").getItems().addAll(mapChoiceBox.get("north").getItems());
        mapChoiceBox.get("west").getItems().addAll(mapChoiceBox.get("north").getItems());
        mapTextFields.get("weight").clear();
        mapTextFields.get("description").clear();
    }

    /**
     * @param buttonsArea
     * @param mapChoiceBox
     * @param HashMap<String
     * @param mapTextFields
     */
    public void addRoomField(GridPane buttonsArea, HashMap<String, ChoiceBox<String>> mapChoiceBox,
            HashMap<String, TextField> mapTextFields) {
        buttonsArea.getChildren().remove(mapTextFields.get("description"));
        buttonsArea.add(mapTextFields.get("description"), 3, 4);
        buttonsArea.add(mapChoiceBox.get("north"), 4, 4);
        buttonsArea.add(mapChoiceBox.get("east"), 5, 4);
        buttonsArea.add(mapChoiceBox.get("south"), 6, 4);
        buttonsArea.add(mapChoiceBox.get("west"), 7, 4);
    }

    /**
     * @param mapChoiceBox
     * @param HashMap<String
     * @param mapTextFields
     */
    public void add(HashMap<String, ChoiceBox<String>> mapChoiceBox, HashMap<String, TextField> mapTextFields) {
        action.setAction("add");
        ArrayList<String> option = new ArrayList<>();
        option.add(mapChoiceBox.get("type").getValue());
        option.add(mapTextFields.get("name").getText());
        if (mapChoiceBox.get("type").getValue() != null && !mapChoiceBox.get("type").getValue().isEmpty()
                && mapChoiceBox.get("type").getValue().equals("Room")) {
            option.add(mapTextFields.get("description").getText());
            option.add(mapChoiceBox.get("north").getValue());
            option.add(mapChoiceBox.get("east").getValue());
            option.add(mapChoiceBox.get("south").getValue());
            option.add(mapChoiceBox.get("west").getValue());
        } else if (mapChoiceBox.get("type").getValue() != null && !mapChoiceBox.get("type").getValue().isEmpty()
                && (mapChoiceBox.get("type").getValue().equals("Item")
                        || mapChoiceBox.get("type").getValue().equals("Player"))) {
            option.add(mapTextFields.get("weight").getText());
        }
        action.setOptions(option);
        this.controller.compute(action);
    }

    /**
     * @param mapChoiceBox
     */
    private void removeAddRoomField(GridPane buttonsArea, HashMap<String, ChoiceBox<String>> mapChoiceBox) {
        buttonsArea.getChildren().remove(mapChoiceBox.get("north"));
        buttonsArea.getChildren().remove(mapChoiceBox.get("east"));
        buttonsArea.getChildren().remove(mapChoiceBox.get("south"));
        buttonsArea.getChildren().remove(mapChoiceBox.get("west"));
    }

    /**
     * @param buttonsArea
     * @param mapChoiceBox
     * @param HashMap<String
     * @param mapTextFields
     */
    public void addItemField(GridPane buttonsArea, HashMap<String, ChoiceBox<String>> mapChoiceBox,
            HashMap<String, TextField> mapTextFields) {
        removeAddRoomField(buttonsArea, mapChoiceBox);
        buttonsArea.getChildren().remove(mapTextFields.get("weight"));
        buttonsArea.add(mapTextFields.get("weight"), 3, 4);
    }

    public void lookButton() {
        action.setAction("look");
        this.controller.compute(action);

    }

    public void helpButton() {
        action.setAction("help");
        this.controller.compute(action);

    }

    /**
     * @param mapChoiceBox
     */
    public void link(HashMap<String, ChoiceBox<String>> mapChoiceBox) {
        action.setAction("link");
        ArrayList<String> options = new ArrayList<>();
        options.add(mapChoiceBox.get("linkA").getValue());
        options.add(mapChoiceBox.get("linkB").getValue());
        action.setOptions(options);
        this.controller.compute(action);
    }

    /**
     * @param mapChoiceBox
     */
    public void removeButton(HashMap<String, ChoiceBox<String>> mapChoiceBox) {
        action.setAction("remove");
        ArrayList<String> option = new ArrayList<>();
        System.out.println("REMOVE= " + mapChoiceBox.get("remove").getValue());
        String value = (String) mapChoiceBox.get("remove").getValue();
        if (value != null) {
            option.add(value);
            action.setOptions(option);
        }
        this.controller.compute(action);
    }

    public void playButton() {
        action.setAction("play");
        Graphical.getPrimaryStage().setScene(Graphical.getViews().get("play").getScene());
        this.controller.compute(action);
    }

    public void newGameButton() {
        action.setAction("loader");
        Graphical.getPrimaryStage().setScene(Graphical.getViews().get("loader").getScene());
        this.controller.compute(action);
    }
}
