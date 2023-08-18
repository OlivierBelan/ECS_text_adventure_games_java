package libs.reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class ReadFile {
    public ReadFile() {
    }

    private Scanner scanner;

    public ArrayList<String> readFile(String filePath) {
        ArrayList<String> res = new ArrayList<>();
        try {
            scanner = new Scanner(new File(filePath));
            while (scanner.hasNextLine()) {
                res.add(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return res;
    }
}
