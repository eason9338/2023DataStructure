package com.datastructure2023.BackendPractice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.datastructure2023.BackendPractice.service.SearchResultHandler;
import com.datastructure2023.BackendPractice.model.SearchResultItem;

import java.io.IOException;
import java.util.ArrayList;

@RestController
public class SearchController {

    @Autowired
    private SearchResultHandler searchResultHandler = new SearchResultHandler("AIzaSyDaMRJAuiClBmf_TatB_3PMtsKqiRt18XM", "e618816a42f6949e9");

    @PostMapping("/searchResult")
    public ResponseEntity<?> search(@RequestBody String query) {
        try {
            ArrayList<SearchResultItem> results = searchResultHandler.search(query);
            return ResponseEntity.ok(results);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("搜尋時發生錯誤: " + e.getMessage());
        }
    }
}