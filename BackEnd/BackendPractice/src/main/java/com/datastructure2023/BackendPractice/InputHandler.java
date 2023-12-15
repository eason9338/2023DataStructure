package com.datastructure2023.BackendPractice;

import java.util.Map;
import java.util.Collections;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class InputHandler {
    
    static class Input {
        private String input;
       
        public String getInput() { return input; }

        public void setInput(String data) { input = data; }
    }

    @PostMapping("/add")
    public Map<String, String> addNumbers(@RequestBody Input input) {
        String result = "Your input is: " + input.getInput();
        return Collections.singletonMap("result", result);
    }
}
