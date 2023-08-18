package communication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import libs.parser.Action;

public class Message {

    public enum Event {
        GET, POST, DELETE, CREATE, PLAY
    }
    public enum Code {
        SUCCESS, ERROR
    }
    public Event event;
    public Code code;
    public UUID id;
    public Action action;
    public String message;
    public String type;
    
    public HashMap<String, HashMap<String, String>> data;
    public HashMap<String, ArrayList<Message>> linkedData;

    public Message() {
    }

}
