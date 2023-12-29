import java.io.IOException;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class GoogleQuery {
    
    private String apiKey;
    private String searchEngineID;

    public GoogleQuery(String apiKey, String searchEngineID) {
        this.apiKey = apiKey;
        this.searchEngineID = searchEngineID;
    }

    public ArrayList<String> search(String query) throws IOException {

        ArrayList<String> responseArray = new ArrayList<>();

        String page1Url = buildSearchString(query, 1);
        responseArray.add(performSearch(page1Url));

        String page2Url = buildSearchString(query, 11);
        responseArray.add(performSearch(page2Url));

        return responseArray;
    }

    public String buildSearchString(String query, int start) {
        return "https://www.googleapis.com/customsearch/v1?q=" + query
               + "&cx=" + searchEngineID
               + "&key=" + apiKey
               + "&lr=lang_zh-TW"
               + "&cr=countryTW"
               + "&num=10"
               + "&start=" + start;    
    }

    public String performSearch(String urlString) throws IOException{

        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();

        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");

        int responseCode = connection.getResponseCode();
        if(responseCode == 200) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();
            return response.toString();

        } else {
            return "Error Code: " + responseCode;
        }
        
    }

}
