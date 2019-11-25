package com.cisco.crudapis.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ArbitraryObjectTest {

  private ArbitraryObject arbitraryObject;

  @Before
  public void setUp() {
    String validJson = "{"
            + "\"userName\": \"Bag\","
            + "\"password\": \"Blue\""
            + "}";
    arbitraryObject = new ArbitraryObject("123abc123", org.bson.Document.parse(validJson));
  }

  @Test
  public void testInitialization() {
    String expectedRawJsonOfArbitraryObject = "{\"userName\": \"Bag\", \"password\": \"Blue\", \"uid\": \"123abc123\"}";
    assertEquals(expectedRawJsonOfArbitraryObject, arbitraryObject.getAsRawJSON());
  }

  @Test(expected = org.bson.json.JsonParseException.class)
  public void testInitializationWithInvalidJson() {
    String validJson = "{"
            + "\"userName\": \"Bag\","
            + "\"password\": \"Blue\"";

    ArbitraryObject arbitraryObject = new ArbitraryObject("123abc123", org.bson.Document.parse(validJson));
  }

  @Test
  public void testGetId() {
    assertEquals("123abc123", arbitraryObject.getId());
  }

  @Test
  public void testGetArbitraryObjectFields() {
    org.bson.Document fields = arbitraryObject.getArbitraryObjectFields();
    assertEquals("{\"userName\": \"Bag\", \"password\": \"Blue\"}", fields.toJson());
  }

  @Test
  public void testGetAsRawJson() {
    String validComplexJson = "{"
            + "\"userName\" : \"Sid\","
            + "\"password\" : \"lat\","
            + "\"school\" : {\"university\" : \"Northeastern\","
            + "\"College\" : \"Khouri\","
            + "\"Course\" : \"MSCS\""
            + "},"
            + "\"city\" : \"Boston\""
            + "}";
    ArbitraryObject arbitraryObject = new ArbitraryObject("123abc123", org.bson.Document.parse(validComplexJson));
    String expectedRawJsonOfArbitraryObject = "{\"userName\": \"Sid\", \"password\": \"lat\", \"school\": {\"university\": \"Northeastern\", \"College\": \"Khouri\", \"Course\": \"MSCS\"}, \"city\": \"Boston\", \"uid\": \"123abc123\"}";
    assertEquals(expectedRawJsonOfArbitraryObject, arbitraryObject.getAsRawJSON());
  }
}