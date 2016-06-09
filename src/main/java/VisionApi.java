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

public class VisionApi {
    public static String FaceRequestWithBytes(BufferedImage photo){
        String faceApiUrlText = "https://api.projectoxford.ai/face/v1.0/detect?returnFaceAttributes=age,gender,headPose,smile,facialHair,glasses";
        OutputStream faceApiWriter = null;
        BufferedReader faceApiReader = null;
        HttpURLConnection faceApiConn = null;

        try {
            String apiKey = Files.readAllLines(Paths.get("src/main/resources/secrets/face_api_key.key")).get(0);

            URL faceApiUrl = new URL(faceApiUrlText);
            faceApiConn = (HttpURLConnection)faceApiUrl.openConnection();
            faceApiConn.setRequestMethod("POST");
            faceApiConn.setRequestProperty("Content-Type", "application/octet-stream");
            faceApiConn.setRequestProperty("Ocp-Apim-Subscription-Key", apiKey);
            faceApiConn.setDoOutput(true);
            faceApiConn.setDoInput(true);

            faceApiWriter = faceApiConn.getOutputStream();
            ImageIO.write(photo, "jpeg", faceApiWriter);
            faceApiWriter.close();

            faceApiReader =
                    new BufferedReader(new InputStreamReader(faceApiConn.getInputStream()));
            StringBuilder faceApiResponse = new StringBuilder();
            String line;
            while((line = faceApiReader.readLine()) != null){
                faceApiResponse.append(line + "\n");
            }

            return faceApiResponse.toString();
        }
        catch (IOException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        finally {
            try {
                if (faceApiWriter != null)
                    faceApiWriter.close();
                if (faceApiReader != null)
                    faceApiReader.close();
                if (faceApiConn != null)
                    faceApiConn.disconnect();
            }
            catch (Exception e){}
        }
        return null;
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

            return emotionApiResponse.toString();
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

            return emotionApiResponse.toString();
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
