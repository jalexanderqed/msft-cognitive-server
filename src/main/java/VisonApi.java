/**
 * Created by john on 6/8/16.
 */

import java.awt.image.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.imageio.ImageIO;
import java.nio.file.Files;
import java.nio.file.Paths;

public class VisonApi {
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
