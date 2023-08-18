package libs.parser;

import java.util.ArrayList;

public class Action {
    private String action;
    private ArrayList<String> options;
    public String getAction() {
        return action;
    }
    public ArrayList<String> getOptions() {
        return options;
    }
    public void setAction(String action) {
        this.action = action;
    }
    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }
}
