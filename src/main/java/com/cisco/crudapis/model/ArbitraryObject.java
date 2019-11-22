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
    if (this.id != null && !this.arbitraryObjectFields.containsKey("uid")) {
      this.arbitraryObjectFields.append("uid", this.id);
    }
    return this.arbitraryObjectFields != null ? this.arbitraryObjectFields.toJson() : "";
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setArbitraryObjectFields(org.bson.Document arbitraryObjectFields) {
    this.arbitraryObjectFields = arbitraryObjectFields;
  }

}
