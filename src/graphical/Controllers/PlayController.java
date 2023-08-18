package graphical.Controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import engine.Entities.Entity;
import graphical.Graphical;
import javafx.scene.control.ChoiceBox;
import libs.character.components.C_Character;
import libs.item.components.C_Item;
import libs.item.components.C_ItemList;
import libs.room.components.C_Room;

public class PlayController extends AController {

    public PlayController() {
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
    public void goButton(HashMap<String, ChoiceBox<String>> mapChoiceBox) {
        action.setAction("go");
        ArrayList<String> option = new ArrayList<>();
        String value = (String) mapChoiceBox.get("go").getValue();
        System.out.println("VALUE = " + value);
        if (value != null) {
            option.add(value);
            action.setOptions(option);
        }
        this.controller.compute(action);
    }

    
    /** 
     * @param mapChoiceBox
     */
    public void takeButton(HashMap<String, ChoiceBox<String>> mapChoiceBox) {
        action.setAction("take");
        ArrayList<String> option = new ArrayList<>();
        String value = (String) mapChoiceBox.get("take").getValue();
        if (value != null) {
            option.add(value);
            action.setOptions(option);
            System.out.println(action.getAction() + " " + action.getOptions());
        }
        this.controller.compute(action);
    }

    
    /** 
     * @param mapChoiceBox
     */
    public void dropButton(HashMap<String, ChoiceBox<String>> mapChoiceBox) {
        action.setAction("drop");
        ArrayList<String> option = new ArrayList<>();
        String value = (String) mapChoiceBox.get("drop").getValue();
        if (value != null) {
            option.add(value);
            action.setOptions(option);
        }
        this.controller.compute(action);
    }

    
    /** 
     * @param mapChoiceBox
     */
    public void giveButton(HashMap<String, ChoiceBox<String>> mapChoiceBox) {
        action.setAction("give");
        ArrayList<String> option = new ArrayList<>();
        String valueItem = (String) mapChoiceBox.get("giveItem").getValue();
        String valueCharacter = (String) mapChoiceBox.get("giveCharacter").getValue();
        if (valueItem != null) {
            option.add(valueItem);
            if (valueCharacter != null) {
                option.add(valueCharacter);
            }
        }
        action.setOptions(option);
        this.controller.compute(action);
    }

    
    /** 
     * @param mapChoiceBox
     */
    private void clearMapChoiceBox(HashMap<String, ChoiceBox<String>> mapChoiceBox) {
        mapChoiceBox.get("go").getItems().clear();
        mapChoiceBox.get("take").getItems().clear();
        mapChoiceBox.get("drop").getItems().clear();
        mapChoiceBox.get("giveItem").getItems().clear();
        mapChoiceBox.get("giveCharacter").getItems().clear();
    }

    
    /** 
     * @param mapChoiceBox
     */
    public void goChoiceList(HashMap<String, ChoiceBox<String>> mapChoiceBox) {
        Entity e_Player = manager.getEntityManager().getEntityById(this.controller.getMessage().id);
        Entity e_Room = manager.getEntityByEntity(e_Player, C_Room.class);
        C_Room c_Room = manager.getComponentManager().getComponentByEntityAndType(e_Room, C_Room.class);
        C_ItemList c_PlayerItem = manager.getComponentManager().getComponentByEntityAndType(e_Player, C_ItemList.class);
        C_ItemList c_RoomItem = manager.getComponentManager().getComponentByEntityAndType(e_Room, C_ItemList.class);
        clearMapChoiceBox(mapChoiceBox);
        for (Map.Entry<String, UUID> exits : c_Room.getExits().entrySet()) {
            if (exits.getValue() != null) {
                mapChoiceBox.get("go").getItems().addAll(exits.getKey());
            }
        }
        for (UUID entityId : e_Room.entities) {
            C_Character c_Character = manager.getComponentManager()
                    .getComponentByEntityAndType(manager.getEntityManager().getEntityById(entityId), C_Character.class);
            if (c_Character != null && entityId != e_Player.getId()) {
                mapChoiceBox.get("giveCharacter").getItems().add(c_Character.getName());
            }
        }
        for (C_Item item : c_PlayerItem.getItems()) {
            mapChoiceBox.get("giveItem").getItems().addAll(item.getDescription());
            mapChoiceBox.get("drop").getItems().addAll(item.getDescription());
        }
        for (C_Item item : c_RoomItem.getItems()) {
            mapChoiceBox.get("take").getItems().addAll(item.getDescription());
        }
    }

    public void customButton() {
        action.setAction("custom");
        Graphical.getPrimaryStage().setScene(Graphical.getViews().get("custom").getScene());
        this.controller.compute(action);
    }

}
