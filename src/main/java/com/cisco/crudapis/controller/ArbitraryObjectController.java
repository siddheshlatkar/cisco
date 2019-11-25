package com.cisco.crudapis.controller;

import com.cisco.crudapis.model.ArbitraryObject;
import com.cisco.crudapis.service.ArbitraryObjectService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.json.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.json.JsonObject;

@RestController
public class ArbitraryObjectController {

  @Autowired
  private ArbitraryObjectService arbitraryObjectService;

  @PostMapping(path = "api/objects")
  public ResponseEntity<ArbitraryObject> create(@RequestBody String arbitraryObjectJSON) {

    if (!isValidJSON(arbitraryObjectJSON)) {
      JsonObject errorJson = Json.createObjectBuilder().add("verb", "POST")
              .add("url", "cisco-crud.us-east-1.elasticbeanstalk.com/api/objects/")
              .add("message", "Not a JSON object")
              .build();
      return new ResponseEntity(errorJson.toString(), HttpStatus.BAD_REQUEST);
    }

    ArbitraryObject arbitraryObject = arbitraryObjectService.create(arbitraryObjectJSON);
    return new ResponseEntity(arbitraryObject.getAsRawJSON(), HttpStatus.CREATED);
  }

  @GetMapping(path = "api/objects/{uid}")
  public ResponseEntity<ArbitraryObject> get(@PathVariable("uid") String uid) {

    ArbitraryObject arbitraryObject = arbitraryObjectService.find(uid);

    if (arbitraryObject == null) {
      return new ResponseEntity("", HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity(arbitraryObject.getAsRawJSON(), HttpStatus.OK);
  }

  @GetMapping(path = "api/objects/")
  public ResponseEntity<List<String>> getAll() {
    List<ArbitraryObject> arbitraryObjects = arbitraryObjectService.findAll();
    List<JsonObject> uids = new ArrayList();
    for (ArbitraryObject object: arbitraryObjects) {
      JsonObject jsonObject = Json.createObjectBuilder().add("uid", "cisco-crud.us-east-1.elasticbeanstalk.com/api/objects/" + object.getId()).build();
      uids.add(jsonObject);
    }
    return new ResponseEntity(uids.toString(), HttpStatus.OK);
  }

  @PutMapping(path = "api/objects/{uid}")
  public ResponseEntity<ArbitraryObject> update(@PathVariable("uid") String uid, @RequestBody String arbitraryObjectJSON) {

    if (!isValidJSON(arbitraryObjectJSON)) {
      JsonObject errorJson = Json.createObjectBuilder().add("verb", "PUT")
              .add("url", "cisco-crud.us-east-1.elasticbeanstalk.com/api/objects/")
              .add("message", "Not a JSON object")
              .build();
      return new ResponseEntity(errorJson.toString(), HttpStatus.BAD_REQUEST);
    }

    ArbitraryObject updatedArbitraryObject = arbitraryObjectService.update(uid, Document.parse(arbitraryObjectJSON));
    return new ResponseEntity(updatedArbitraryObject.getAsRawJSON(), HttpStatus.OK);
  }

  @DeleteMapping(path = "api/objects/{uid}")
  public ResponseEntity delete(@PathVariable("uid") String uid) {
    arbitraryObjectService.delete(uid);
    return new ResponseEntity("", HttpStatus.OK);
  }

  private boolean isValidJSON(String arbitraryObjectJSON) {
    try {
      ObjectMapper mapper = new ObjectMapper();
      mapper.readTree(arbitraryObjectJSON);
      return true;
    } catch (IOException e) {
      return false;
    }
  }
}

