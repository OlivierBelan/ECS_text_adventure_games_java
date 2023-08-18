
// import engine.*;
// import communication.Server;
// import engine.Manager.*;
// import communication.Server;
import graphical.Graphical;

public class Game {

    // create the first instance of Game
    private static Game instance = new Game();
    private Graphical graphical = new Graphical();
    // private Manager manager = new Manager();
    // private Server server = new Server();
    
    private Game() {
        // this.manager.loadMap("./map/maps.txt");
        // this.manager.init();
        // this.manager.receive(server.initSend());
        // this.server.initReceive(this.manager.send());

    }

    public static Game getInstance() {
        return instance;
    }

    public boolean play() {
        boolean isfinish = false;
        
        graphical.run();
        // while (!isfinish) {
            // isfinish = this.manager.receive(this.server.send());
            // this.server.receive(this.manager.send());
            // isfinish = this.manager.receive(this.graphical.send());
            // this.graphical.receive(this.manager.send());;
        // }
        return isfinish;
        // return false;
    }
}
