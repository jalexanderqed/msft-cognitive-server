import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by jalexander on 6/8/2016.
 */
public class Main {
    public static void main(String[] args){
        for(String line : Files.readAllLines(Paths.get("secret-test.txt")));
    }
}
