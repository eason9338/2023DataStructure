package com.datastructure2023.BackendPractice.service;

import org.springframework.stereotype.Service;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;

import com.datastructure2023.BackendPractice.model.SearchResultItem;

@Service
public class SearchResultHandler {

    public GoogleQuery googleQuery;

    public SearchResultHandler(String apiKey, String searchEngineID) {
        this.googleQuery = new GoogleQuery(apiKey, searchEngineID);
    }

    public ArrayList<SearchResultItem> search(String query) throws IOException{
        ArrayList<String> responseArray = googleQuery.search(query);
        ArrayList<SearchResultItem> finalSearchResults = new ArrayList<>();
        for(String response: responseArray) {
            ArrayList<SearchResultItem> newSearchResults = extractLink(response);
            finalSearchResults.addAll(newSearchResults);
        }

        return finalSearchResults;
    }

    // public ArrayList<SearchResultItem> search(String query) throws IOException{
    //     String jsonResponse = googleQuery.search(query);
    //     return extractLink(jsonResponse);
    // }

    public ArrayList<SearchResultItem> extractLink(String jsonResponse) {
        ArrayList<SearchResultItem> searchResults = new ArrayList<>();
        JsonObject jsonObject = new Gson().fromJson(jsonResponse, JsonObject.class);
        JsonArray items = jsonObject.getAsJsonArray("items");

        for(JsonElement itemElement: items) {
            JsonObject itemObject = itemElement.getAsJsonObject();
            String title = itemObject.get("title").getAsString();
            String link = itemObject.get("link").getAsString();
            searchResults.add(new SearchResultItem(title, link));
        }

        return searchResults;
    }

}
