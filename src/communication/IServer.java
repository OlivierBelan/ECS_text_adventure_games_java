package communication;

public interface IServer {

    public Message send();

    public boolean receive(Message message);
}
