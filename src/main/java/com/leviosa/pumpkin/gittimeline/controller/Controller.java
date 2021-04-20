package com.leviosa.pumpkin.gittimeline.controller;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.json.*;

@RestController
public class Controller {
    @GetMapping("/timeline/{username}")
    public String getTimeline(@PathVariable(value = "username") String username) throws JSONException {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.github.com/users/" + username + "/repos";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        JSONArray jsonArray = new JSONObject("{\"array\": " + response.getBody() + "}").getJSONArray("array");
        JSONArray result = new JSONArray();
        for (var i = 0; i < jsonArray.length(); ++i) {
            JSONObject object = new JSONObject();
            object.put("name", jsonArray.getJSONObject(i).get("name"));
            object.put("created_at", jsonArray.getJSONObject(i).getString("created_at"));
            result.put(object);
        }
        return result.toString();
    }
}