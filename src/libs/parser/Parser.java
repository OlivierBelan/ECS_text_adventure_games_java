package libs.parser;

// import java.lang.invoke.ClassSpecializer.Factory;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class Parser {
    private Scanner reader; // source of command input

    public Parser() {
        reader = new Scanner(System.in);
    }

    public Action getCommand(UUID id) {
        String inputLine; // will hold the full input line
        ArrayList<String> commandOptions = new ArrayList<String>();
        String commandName = null;
        Action command = new Action();

        System.out.print("Player"+"["+id+"]> "); // print prompt
        inputLine = reader.nextLine(); // will hold the full input line

        // Find up to two words on the line.
        Scanner tokenizer = new Scanner(inputLine); // open tokenizer

        // Get first word, it would be the name of command
        if (tokenizer.hasNext()) {
            commandName = tokenizer.next();
        }
        // Put all options in array of string
        while (tokenizer.hasNext()) {
            commandOptions.add(tokenizer.next());
        }
        tokenizer.close(); // close tokenizer
        command.setAction(commandName);
        command.setOptions(commandOptions);
        return command;
    }
}
