package sample;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

public class ApiClient {
    public String GetData(String urlString) throws IOException {
        URL url = new URL(urlString);

        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestProperty("accept", "application/json");
        InputStream inputStream = connection.getInputStream();
        StringBuilder stringBuilder = new StringBuilder();
        try (Reader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.defaultCharset()))){
            int c = 0;
            while ((c = reader.read()) != -1){
                stringBuilder.append((char)c);
            }
        }
        String response = stringBuilder.toString();

        return response;
    }

}
