package com.cisco.crudapis.controller;

import com.cisco.crudapis.model.ArbitraryObject;
import com.cisco.crudapis.service.ArbitraryObjectService;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.json.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.json.JsonObject;

@RestController
public class ArbitraryObjectController {

  @Autowired
  private ArbitraryObjectService arbitraryObjectService;

  @PostMapping(path = "api/objects")
  public ResponseEntity<ArbitraryObject> create(@RequestBody String arbitraryObjectJSON) {
    if (!isValidJSON(arbitraryObjectJSON)) {
      return new ResponseEntity("Invalid JSON", HttpStatus.BAD_REQUEST);
    }
    ArbitraryObject arbitraryObject = arbitraryObjectService.create(arbitraryObjectJSON);
    return new ResponseEntity(arbitraryObject.getAsRawJSON(), HttpStatus.OK);
  }

  @GetMapping(path = "api/objects/{uid}")
  public ResponseEntity<ArbitraryObject> get(@PathVariable("uid") String uid) {
    ArbitraryObject arbitraryObject = arbitraryObjectService.find(uid);
    return new ResponseEntity(arbitraryObject.getAsRawJSON(), HttpStatus.OK);
  }

  @GetMapping(path = "api/objects/")
  public ResponseEntity<List<String>> getAll() {
    List<ArbitraryObject> arbitraryObjects = arbitraryObjectService.findAll();
//    List<String> uids = arbitraryObjects.stream()
//            .map(object -> Document.parse("{\"uid\": \"localhost:8080/api/objects/" + object.getId() + "\"}").toJson())
//            .collect(Collectors.toList());
    List<JsonObject> uids = new ArrayList();
    for (ArbitraryObject object: arbitraryObjects) {
      JsonObject jsonObject = Json.createObjectBuilder().add("uid", "localhost:8080/api/objects/" + object.getId()).build();
      uids.add(jsonObject);
    }
    return new ResponseEntity(uids.toString(), HttpStatus.OK);
  }


  private boolean isValidJSON(String arbitraryObjectJSON) {
    try {
      final ObjectMapper mapper = new ObjectMapper();
      mapper.readTree(arbitraryObjectJSON);
      return true;
    } catch (IOException e) {
      return false;
    }
  }
}

