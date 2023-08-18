package communication;

import java.util.UUID;

import libs.parser.*;

public class Server implements IServer {
    private Message send;
    private Message receive;
    private UUID id;
    // private Parser parser = new Parser();

    public Server() {
    }

    public void init() {
        send = new Message();
        send.event = Message.Event.CREATE;
    }

    public void read() {
        // Action action = parser.getCommand(id);
        send = new Message();
        send.id = id;
        // send.action = action;
        send.event = Message.Event.PLAY;
    }

    
    /** 
     * @param action
     */
    public void setMessage(Action action) {
        // Action action = parser.getCommand(id);
        send = new Message();
        send.id = id;
        send.action = action;
        send.event = Message.Event.PLAY;
    }
    
    
    /** 
     * @return Message
     */
    public Message initSend() {
        this.init();
        // System.err.println("SEND INIT FROM SERVER");
        return send;
    }

    
    /** 
     * @param receive
     */
    public void initReceive(Message receive) {
        this.receive = receive;
        this.id = receive.id;
        // System.err.println("RECEIVE INIT FROM SERVER ID = " + this.id);
        // if (receive.message != null) {
        //     System.out.println("\n" + receive.message);
        // }

        // System.out.println(toServer.getMessage());
    }

    
    /** 
     * @param receive
     * @return boolean
     */
    @Override
    public boolean receive(Message receive) {
        this.receive = receive;
        // System.err.println("RECEIVE FROM SERVER");
        // if (receive.message != null) {
        //     System.out.println("\n" + receive.message);
        // }
        return true;
    }

    
    /** 
     * @return Message
     */
    @Override
    public Message send() {
        // System.err.println("SEND FROM SERVER");
        // this.read();
        return send;
    }

    
    /** 
     * @return Message
     */
    public Message getReceive() {
        return receive;
    }
}
