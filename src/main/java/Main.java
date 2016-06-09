import java.awt.image.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import JSONObjects.EmotionApiResponse;
import JSONObjects.EmotionApiResult;
import com.google.gson.Gson;

import javax.imageio.ImageIO;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by jalexander on 6/8/2016.
 */
public class Main {
    public static void main(String[] args) {
        BufferedImage image;

        try{
            File imgPath = new File("src/main/resources/img/tedcruz.jpg");
            image = ImageIO.read(imgPath);
        }
        catch(IOException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return;
        }

        System.out.println("\n\n\n-------------------------------");

        String jsonResult = EmotionRequestWithBytes(image);
        Gson gson = new Gson();
        EmotionApiResult[] emotionApiResult = gson.fromJson(jsonResult, EmotionApiResponse.class).results;
        if(emotionApiResult.length > 0) {
            for (String emotion : emotionApiResult[0].scores.keySet()) {
                System.out.println(emotion + ": " + emotionApiResult[0].scores.get(emotion));
            }
        }
        else{
            System.out.println("No faces detected.");
        }

        System.out.println("-------------------------------\n\n\n");
    }

    public static String EmotionRequestWithBytes(BufferedImage photo){
        String emotionApiUrlText = "https://api.projectoxford.ai/emotion/v1.0/recognize";
        OutputStream emotionApiWriter = null;
        BufferedReader emotionApiReader = null;
        HttpURLConnection emotionApiConn = null;

        try {
            String apiKey = Files.readAllLines(Paths.get("src/main/resources/secrets/emotion_api_key.key")).get(0);

            URL emotionApiUrl = new URL(emotionApiUrlText);
            emotionApiConn = (HttpURLConnection)emotionApiUrl.openConnection();
            emotionApiConn.setRequestMethod("POST");
            emotionApiConn.setRequestProperty("Content-Type", "application/octet-stream");
            emotionApiConn.setRequestProperty("Ocp-Apim-Subscription-Key", apiKey);
            emotionApiConn.setDoOutput(true);
            emotionApiConn.setDoInput(true);

            emotionApiWriter = emotionApiConn.getOutputStream();
            ImageIO.write(photo, "jpeg", emotionApiWriter);
            emotionApiWriter.close();

            emotionApiReader =
                    new BufferedReader(new InputStreamReader(emotionApiConn.getInputStream()));
            StringBuilder emotionApiResponse = new StringBuilder();
            String line;
            while((line = emotionApiReader.readLine()) != null){
                emotionApiResponse.append(line + "\n");
            }
            System.out.println(emotionApiResponse.toString());

            String jsonResult = "{'results': " + emotionApiResponse.toString() + '}';
            return jsonResult;
        }
        catch (IOException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        finally {
            try {
                if (emotionApiWriter != null)
                    emotionApiWriter.close();
                if (emotionApiReader != null)
                    emotionApiReader.close();
                if (emotionApiConn != null)
                    emotionApiConn.disconnect();
            }
            catch (Exception e){}
        }
        return null;
    }

    public static String EmotionRequestWithUrl(String url){
        String emotionApiUrlText = "https://api.projectoxford.ai/emotion/v1.0/recognize";
        PrintWriter emotionApiWriter = null;
        BufferedReader emotionApiReader = null;
        HttpURLConnection emotionApiConn = null;

        try {
            String apiKey = Files.readAllLines(Paths.get("src/main/resources/secrets/emotion_api_key.key")).get(0);

            URL emotionApiUrl = new URL(emotionApiUrlText);
            emotionApiConn = (HttpURLConnection)emotionApiUrl.openConnection();
            emotionApiConn.setRequestMethod("POST");
            emotionApiConn.setRequestProperty("Content-Type", "application/json");
            emotionApiConn.setRequestProperty("Ocp-Apim-Subscription-Key", apiKey);
            emotionApiConn.setDoOutput(true);
            emotionApiConn.setDoInput(true);

             emotionApiWriter = new PrintWriter(emotionApiConn.getOutputStream());
            emotionApiWriter.print("{ 'url': '" + url + "' }");
            emotionApiWriter.close();

            emotionApiReader =
                    new BufferedReader(new InputStreamReader(emotionApiConn.getInputStream()));
            StringBuilder emotionApiResponse = new StringBuilder();
            String line;
            while((line = emotionApiReader.readLine()) != null){
                emotionApiResponse.append(line + "\n");
            }
            System.out.println(emotionApiResponse.toString());

            String jsonResult = "{'results': " + emotionApiResponse.toString() + '}';
            return jsonResult;
        }
        catch (IOException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        finally {
            try {
                if (emotionApiWriter != null)
                    emotionApiWriter.close();
                if (emotionApiReader != null)
                    emotionApiReader.close();
                if (emotionApiConn != null)
                    emotionApiConn.disconnect();
            }
            catch (Exception e){}
        }
        return null;
    }
}
