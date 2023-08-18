/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
// import commands.commandsList.HelpCommand;

/**
 *
 * @author rej
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // new Game().play();
        // Factory<Command> factory = new Factory<Command>(){};
        // factory.registerSupplier("PENIS", HelpCommand::new);
        // factory.getSupplier("PENIS").excute();
        // CommandFactorySupplier.registerSupplier("test", HelpCommand::new);
        Game game = Game.getInstance();
        game.play();
    }
}
