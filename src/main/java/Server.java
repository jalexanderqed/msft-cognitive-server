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
            System.out.println("Handling upload.");
            int size;
            byte[] bytes;
            ByteArrayInputStream imageBytes = new ByteArrayInputStream(bytes = req.bodyAsBytes());
            System.out.println("Request size: " + bytes.length);
            BufferedImage image = ImageIO.read(imageBytes);
            String emotionReturn = VisionApi.EmotionRequestWithBytes(image);
            String faceReturn = VisionApi.FaceRequestWithBytes(image);
            return "{'emotions': " + emotionReturn + ", 'face': " + faceReturn + '}';
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return "";
    }
}
