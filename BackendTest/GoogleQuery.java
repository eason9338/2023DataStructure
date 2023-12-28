import java.io.IOException;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class GoogleQuery {
    
    private String apiKey;
    private String searchEngineID;
    public String query = "實習";

    public GoogleQuery(String apiKey, String searchEngineID) {
        this.apiKey = apiKey;
        this.searchEngineID = searchEngineID;
    }

    public String search(String query) throws IOException {
        String urlString = "https://www.googleapis.com/customsearch/v1?q=" + query + "&cx=" + searchEngineID + "&key=" + apiKey + "&lr=lang_zh-TW" + "&cr=countryTW";
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
        // System.out.println("網址連結：" + urlString);
        int responseCode = connection.getResponseCode();
        if(responseCode == 200) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();

            while((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();
            //response 就是 Google API 回應的JSON文檔
            return response.toString();
        } else {
            return "Response returned with error code: " + responseCode;
        }

    }
}
