package com.cisco.crudapis.service;

import com.cisco.crudapis.model.ArbitraryObject;
import com.cisco.crudapis.repository.ArbitraryObjectRepository;
import org.bson.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ArbitraryObjectServiceTest {

  @Mock
  private ArbitraryObjectRepository mockArbitraryObjectRepository;

  @InjectMocks
  private ArbitraryObjectService arbitraryObjectService;

  @Test
  public void testCreate() {
    String arbitraryObjectJson = "{"
            + "\"userName\": \"Bag\","
            + "\"password\": \"Blue\""
            + "}";
    when(mockArbitraryObjectRepository.save(any(ArbitraryObject.class)))
            .thenReturn(new ArbitraryObject("123abc123", Document.parse(arbitraryObjectJson)));

    ArbitraryObject arbitraryObject = arbitraryObjectService.create(arbitraryObjectJson);

    assertEquals("123abc123", arbitraryObject.getId());
    assertEquals("{\"userName\": \"Bag\", \"password\": \"Blue\"}", arbitraryObject.getArbitraryObjectFields().toJson());
    assertEquals("{\"userName\": \"Bag\", \"password\": \"Blue\", \"uid\": \"123abc123\"}", arbitraryObject.getAsRawJSON());
  }

  @Test
  public void testFindWhenObjectExists() {
    when(mockArbitraryObjectRepository.existsById("pqr897pqr"))
            .thenReturn(false);

    ArbitraryObject arbitraryObject = arbitraryObjectService.find("pqr897pqr");
    assertEquals(null, arbitraryObject);
  }

  @Test
  public void testFindWhenObjectDoesNotExists() {
    String arbitraryObjectJson = "{"
            + "\"userName\" : \"Sid\","
            + "\"password\" : \"lat\","
            + "\"school\" : {\"university\" : \"Northeastern\","
            + "\"College\" : \"Khouri\","
            + "\"Course\" : \"MSCS\""
            + "},"
            + "\"city\" : \"Boston\""
            + "}";

    when(mockArbitraryObjectRepository.existsById("mnb234mnb"))
            .thenReturn(true);
    when(mockArbitraryObjectRepository.findById("mnb234mnb"))
            .thenReturn(java.util.Optional.of(new ArbitraryObject("mnb234mnb", Document.parse(arbitraryObjectJson))));

    ArbitraryObject arbitraryObject = arbitraryObjectService.find("mnb234mnb");

    assertEquals("mnb234mnb", arbitraryObject.getId());
    assertEquals("{\"userName\": \"Sid\", \"password\": \"lat\", \"school\": {\"university\": \"Northeastern\", \"College\": \"Khouri\", \"Course\": \"MSCS\"}, \"city\": \"Boston\"}", arbitraryObject.getArbitraryObjectFields().toJson());
    assertEquals("{\"userName\": \"Sid\", \"password\": \"lat\", \"school\": {\"university\": \"Northeastern\", \"College\": \"Khouri\", \"Course\": \"MSCS\"}, \"city\": \"Boston\", \"uid\": \"mnb234mnb\"}", arbitraryObject.getAsRawJSON());
  }

  @Test
  public void findAll() {
    String arbitraryObjectJson1 = "{"
            + "\"userName\": \"Bag\","
            + "\"password\": \"Blue\""
            + "}";

    String arbitraryObjectJson2 = "{"
            + "\"userName\" : \"Sid\","
            + "\"password\" : \"lat\","
            + "\"school\" : {\"university\" : \"Northeastern\","
            + "\"College\" : \"Khouri\","
            + "\"Course\" : \"MSCS\""
            + "},"
            + "\"city\" : \"Boston\""
            + "}";

    List<ArbitraryObject> mockedObjectList = new ArrayList();
    mockedObjectList.add(new ArbitraryObject("123abc123", Document.parse(arbitraryObjectJson1)));
    mockedObjectList.add(new ArbitraryObject("987tre986", Document.parse(arbitraryObjectJson2)));

    when(mockArbitraryObjectRepository.findAll()).thenReturn(mockedObjectList);
    List<ArbitraryObject> arbitraryObjectList = arbitraryObjectService.findAll();
    assertEquals(2, arbitraryObjectList.size());
    assertTrue(arbitraryObjectList.get(0).getId().equals("123abc123"));
    assertTrue(arbitraryObjectList.get(1).getId().equals("987tre986"));
  }

  @Test
  public void update() {
    String originalJson = "{"
            + "\"userName\": \"Bag\","
            + "\"password\": \"Blue\""
            + "}";

    String updatedJson = "{"
            + "\"userName\" : \"Sid\","
            + "\"password\" : \"lat\","
            + "\"school\" : {\"university\" : \"Northeastern\","
            + "\"College\" : \"Khouri\","
            + "\"Course\" : \"MSCS\""
            + "},"
            + "\"city\" : \"Boston\""
            + "}";

    when(mockArbitraryObjectRepository.save(any(ArbitraryObject.class))).thenReturn(new ArbitraryObject("123tre12", Document.parse(updatedJson)));

    ArbitraryObject updatedObject = arbitraryObjectService.update("123abc123", Document.parse(updatedJson));

    assertEquals("123tre12", updatedObject.getId());
    assertEquals("{\"userName\": \"Sid\", \"password\": \"lat\", \"school\": {\"university\": \"Northeastern\", \"College\": \"Khouri\", \"Course\": \"MSCS\"}, \"city\": \"Boston\"}", updatedObject.getArbitraryObjectFields().toJson());
    assertEquals("{\"userName\": \"Sid\", \"password\": \"lat\", \"school\": {\"university\": \"Northeastern\", \"College\": \"Khouri\", \"Course\": \"MSCS\"}, \"city\": \"Boston\", \"uid\": \"123tre12\"}", updatedObject.getAsRawJSON());
  }
}