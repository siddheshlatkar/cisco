package com.cisco.crudapis.model;

import org.springframework.data.mongodb.core.mapping.Document;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Represents Arbitrary object to be used in CRUD APIs.
 */
@Document("ArbitraryObject")
public class ArbitraryObject {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private String id;

  private org.bson.Document arbitraryObjectFields;

  public ArbitraryObject() {
  }

  public ArbitraryObject(String arbitraryObjectFields) throws org.bson.json.JsonParseException {
    this.arbitraryObjectFields = org.bson.Document.parse(arbitraryObjectFields);
  }

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

  /**
   * Returns Arbitrary object as a Json.
   * @return  Arbitrary object as a Json.
   */
  public String getAsRawJSON() {
    if (this.id != null && !this.arbitraryObjectFields.containsKey("id")) {
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
