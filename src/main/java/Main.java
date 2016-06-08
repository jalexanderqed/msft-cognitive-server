import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by jalexander on 6/8/2016.
 */
public class Main {
    public static void main(String[] args){
        try {
            for (String line : Files.readAllLines(Paths.get("src/main/resources/.txt"))){
                System.out.println("line:" + line);
            }
        }
        catch (IOException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
