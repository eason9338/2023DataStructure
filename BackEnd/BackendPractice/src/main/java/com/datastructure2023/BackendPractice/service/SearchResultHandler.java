package com.datastructure2023.BackendPractice.service;

import org.springframework.beans.factory.annotation.Value;
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

public class SearchResultHandler {

    public GoogleQuery googleQuery;

    public SearchResultHandler() {
        this.googleQuery = new GoogleQuery("AIzaSyDaMRJAuiClBmf_TatB_3PMtsKqiRt18XM", "e618816a42f6949e9");
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
