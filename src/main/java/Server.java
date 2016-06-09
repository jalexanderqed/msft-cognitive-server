/**
 * Created by john on 6/8/16.
 */

import spark.Request;
import spark.Response;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;

import static spark.Spark.*;

public class Server {
    public void setup(){
        System.out.println("Starting server.");

        port(8080);
        post("/upload", (req, res) -> handleUpload(req, res));
        get("/test", (req, res) -> "Hello World!");

        System.out.println("Server setup complete.");
    }

    public static String handleUpload(Request req, Response res){
        try {
            ByteArrayInputStream imageBytes = new ByteArrayInputStream(req.bodyAsBytes());
            BufferedImage image = ImageIO.read(imageBytes);
            return VisonApi.EmotionRequestWithBytes(image);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return "";
    }
}
