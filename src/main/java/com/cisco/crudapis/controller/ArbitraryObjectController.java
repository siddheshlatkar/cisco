package com.cisco.crudapis.controller;

import com.cisco.crudapis.model.ArbitraryObject;
import com.cisco.crudapis.service.ArbitraryObjectService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.json.Json;
import javax.json.JsonObject;

/**
 * Controller for Arbitrary object CRUD operations.
 */
@RestController
public class ArbitraryObjectController {

  @Autowired
  private ArbitraryObjectService arbitraryObjectService;

  /**
   * API for creating {@link ArbitraryObject}. Arbitrary object has to be passed as valid JSON.
   *
   * @param arbitraryObjectJSON Arbitrary object passed as JSON.
   * @return {@link ResponseEntity} containing newly created Arbitrary object.
   */
  @PostMapping(path = "api/objects")
  public ResponseEntity<ArbitraryObject> create(@RequestBody String arbitraryObjectJSON) {

    if (!isValidJSON(arbitraryObjectJSON)) {
      JsonObject errorJson = Json.createObjectBuilder().add("verb", "POST")
              .add("url", "http://cisco-crud.us-east-1.elasticbeanstalk.com/api/objects/")
              .add("message", "Not a JSON object")
              .build();
      return new ResponseEntity(errorJson.toString(), HttpStatus.BAD_REQUEST);
    }

    ArbitraryObject arbitraryObject = arbitraryObjectService.create(arbitraryObjectJSON);
    return new ResponseEntity(arbitraryObject.getAsRawJSON(), HttpStatus.CREATED);
  }

  /**
   * API for retrieving an {@link ArbitraryObject} if its uid is known.
   *
   * @param uid uid of {@link ArbitraryObject} to be retrieved.
   * @return {@link ResponseEntity} containing requested {@link ArbitraryObject}
   */
  @GetMapping(path = "api/objects/{uid}")
  public ResponseEntity<ArbitraryObject> get(@PathVariable("uid") String uid) {

    ArbitraryObject arbitraryObject = arbitraryObjectService.find(uid);

    if (arbitraryObject == null) {
      JsonObject errorJson = Json.createObjectBuilder().add("verb", "GET")
              .add("url", "http://cisco-crud.us-east-1.elasticbeanstalk.com/api/objects/" + uid)
              .add("message", "Object with specified uid does not exists.")
              .build();
      return new ResponseEntity(errorJson.toString(), HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity(arbitraryObject.getAsRawJSON(), HttpStatus.OK);
  }

  /**
   * API for retrieving all the existing {@link ArbitraryObject}s.
   *
   * @return {@link ResponseEntity} containing links to the all existing {@link ArbitraryObject}s.
   */
  @GetMapping(path = "api/objects/")
  public ResponseEntity<List<String>> getAll() {
    List<ArbitraryObject> arbitraryObjects = arbitraryObjectService.findAll();
    List<JsonObject> uids = new ArrayList();
    for (ArbitraryObject object : arbitraryObjects) {
      JsonObject jsonObject = Json.createObjectBuilder().add("uid", "http://cisco-crud.us-east-1.elasticbeanstalk.com/api/objects/" + object.getId()).build();
      uids.add(jsonObject);
    }
    return new ResponseEntity(uids.toString(), HttpStatus.OK);
  }

  /**
   * API for updating an {@link ArbitraryObject} if it's uid is known. This is a idempotent API.
   *
   * @param uid                 uid of {@link ArbitraryObject} to be updated.
   * @param arbitraryObjectJSON new contents of {@link ArbitraryObject}.
   * @return {@link ResponseEntity} containing updated {@link ArbitraryObject}.
   */
  @PutMapping(path = "api/objects/{uid}")
  public ResponseEntity<ArbitraryObject> update(@PathVariable("uid") String uid,
                                                @RequestBody String arbitraryObjectJSON) {

    if (!isValidJSON(arbitraryObjectJSON)) {
      JsonObject errorJson = Json.createObjectBuilder().add("verb", "PUT")
              .add("url", "http://cisco-crud.us-east-1.elasticbeanstalk.com/api/objects/")
              .add("message", "Not a JSON object")
              .build();
      return new ResponseEntity(errorJson.toString(), HttpStatus.BAD_REQUEST);
    }

    ArbitraryObject updatedArbitraryObject = arbitraryObjectService.update(uid,
            Document.parse(arbitraryObjectJSON));
    return new ResponseEntity(updatedArbitraryObject.getAsRawJSON(), HttpStatus.OK);
  }

  /**
   * API to delete an {@link ArbitraryObject} if it's uid is known.
   *
   * @param uid uid of {@link ArbitraryObject} to be deleted.
   * @return {@link ResponseEntity} with HttpStatus.OK
   */
  @DeleteMapping(path = "api/objects/{uid}")
  public ResponseEntity delete(@PathVariable("uid") String uid) {
    ArbitraryObject arbitraryObject = arbitraryObjectService.find(uid);
    if (arbitraryObject == null) {
      JsonObject errorJson = Json.createObjectBuilder().add("verb", "DELETE")
              .add("url", "http://cisco-crud.us-east-1.elasticbeanstalk.com/api/objects/" + uid)
              .add("message", "Object with specified uid does not exists.")
              .build();
      return new ResponseEntity(errorJson.toString(), HttpStatus.BAD_REQUEST);
    }
    arbitraryObjectService.delete(uid);
    return new ResponseEntity(HttpStatus.OK);
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

