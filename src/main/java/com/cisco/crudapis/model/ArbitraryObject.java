package com.cisco.crudapis.model;

//TODO - Think about empty json
//TODO - Think about JSON under another JSON.
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map.Entry;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.json.*;

@Document("ArbitraryObject")
public class ArbitraryObject {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private String id;

  org.bson.Document arbitraryObjectFields;

  public ArbitraryObject(String arbitraryObjectFields) {
    this.arbitraryObjectFields = org.bson.Document.parse(arbitraryObjectFields);
  }

  public ArbitraryObject() {}

  public ArbitraryObject(String id, org.bson.Document arbitraryObjectFields) {
    this.id = id;
    this.arbitraryObjectFields = arbitraryObjectFields;
  }

  public String getId() {
    return id;
  }

  public org.bson.Document getArbitraryObjectFields() {
    return arbitraryObjectFields;
  }

  public String getAsRawJSON() {
    this.arbitraryObjectFields.append("uid", this.id);
    return this.arbitraryObjectFields.toJson();
//    JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
//    jsonObjectBuilder.add("uid", this.id);
//
//    for (Entry<String, Object> entry: this.arbitraryObjectFields.entrySet()) {
//      jsonObjectBuilder.add(entry.getKey(), entry.getValue() == null ? "" : entry.getValue().toString());
//    }
//    return jsonObjectBuilder.build();
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setArbitraryObjectFields(org.bson.Document arbitraryObjectFields) {
    this.arbitraryObjectFields = arbitraryObjectFields;
  }

}
