package j3lcardmarket.atelier3.commons.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtils {

    public static String httpRequest(String urlString) {
        return httpRequest(urlString, "GET");
    }
    public static String httpRequest(String urlString, String method) {
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod(method);
            urlConnection.connect();

            int statusCode = urlConnection.getResponseCode();
            if (statusCode != 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String line;
                StringBuilder responseBody = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    responseBody.append(line);
                }
                return responseBody.toString();
            }
        } catch (IOException e) {
            System.out.println("Error in checking HTTP status: " + e.getMessage());
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null;
    }
}
