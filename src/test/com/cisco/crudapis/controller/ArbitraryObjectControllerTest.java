package com.cisco.crudapis.controller;

import com.cisco.crudapis.model.ArbitraryObject;
import com.cisco.crudapis.service.ArbitraryObjectService;

import org.bson.Document;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.Matchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.Assert.*;


@RunWith(MockitoJUnitRunner.class)
public class ArbitraryObjectControllerTest {
  private MockMvc mockMvc;

  @Mock
  private ArbitraryObjectService mockArbitraryObjectService;

  @InjectMocks
  private ArbitraryObjectController arbitraryObjectController;

  @Before
  public void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(arbitraryObjectController).build();
  }

  @Test
  public void testCreate() throws Exception {
    String validJson = "{" +
            "\"userName\" : \"Sid\"," +
            "\"password\" : \"lat\"," +
            "\"school\" : {\"university\" : \"Northeastern\"," +
            "\"College\" : \"Khouri\"," +
            "\"Course\" : \"MSCS\"" +
            "}," +
            "\"city\" : \"Boston\"" +
            "}";
    when(mockArbitraryObjectService.create(any(String.class)))
            .thenReturn(new ArbitraryObject("123abc123", Document.parse(validJson)));

    String responseJson = mockMvc.perform(post("/api/objects")
                  .content(validJson))
                  .andExpect(status().isCreated())
                  .andReturn()
                  .getResponse()
                  .getContentAsString();

    assertEquals("{\"userName\": \"Sid\", \"password\": \"lat\", \"school\": {\"university\": \"Northeastern\", \"College\": \"Khouri\", \"Course\": \"MSCS\"}, \"city\": \"Boston\", \"uid\": \"123abc123\"}", responseJson);
  }

  @Test
  public void testCreateInvalidJson() throws Exception {
    String inValidJson = "{" +
            "\"userName\" : \"Sid\"," +
            "\"password\" : \"lat\"," +
            "\"school\" : {\"university\" : \"Northeastern\"," +
            "\"College\" : \"Khouri\"," +
            "\"Course\" : \"MSCS\"" +
            "}," +
            "\"city\" : \"Boston\"";

    String responseJson = mockMvc.perform(post("/api/objects")
            .content(inValidJson))
            .andExpect(status().isBadRequest())
            .andReturn().getResponse().getContentAsString();

    assertEquals("{\"verb\":\"POST\",\"url\":\"cisco-crud.us-east-1.elasticbeanstalk.com/api/objects/\",\"message\":\"Not a JSON object\"}", responseJson);
  }

  @Test
  public void TestGetWhenObjectExists() throws Exception {
    String validJson = "{" +
            "\"userName\" : \"Sid\"," +
            "\"password\" : \"lat\"," +
            "\"school\" : {\"university\" : \"Northeastern\"," +
            "\"College\" : \"Khouri\"," +
            "\"Course\" : \"MSCS\"" +
            "}," +
            "\"city\" : \"Boston\"" +
            "}";

    when(mockArbitraryObjectService.find("123abc123abc123abc"))
            .thenReturn(new ArbitraryObject("123abc123abc123abc", Document.parse(validJson)));

    String responseJson = mockMvc.perform(get("/api/objects/123abc123abc123abc"))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();

    assertEquals("{\"userName\": \"Sid\", \"password\": \"lat\", \"school\": {\"university\": \"Northeastern\", \"College\": \"Khouri\", \"Course\": \"MSCS\"}, \"city\": \"Boston\", \"uid\": \"123abc123abc123abc\"}", responseJson);
  }


  @Test
  public void TestGetWhenObjectDoesNotExists() throws Exception {

    when(mockArbitraryObjectService.find("123abc123abc123abc"))
            .thenReturn(null);

    mockMvc.perform(get("/api/objects/123abc123abc123abc"))
            .andExpect(status().isNoContent());
  }


  @Test
  public void testGetAllObjects() throws Exception {
    String validJson = "{" +
            "\"userName\" : \"Sid\"," +
            "\"password\" : \"lat\"," +
            "\"school\" : {\"university\" : \"Northeastern\"," +
            "\"College\" : \"Khouri\"," +
            "\"Course\" : \"MSCS\"" +
            "}," +
            "\"city\" : \"Boston\"" +
            "}";

    List<ArbitraryObject> objectList = new ArrayList();
    objectList.add(new ArbitraryObject("123abc123", Document.parse(validJson)));
    objectList.add(new ArbitraryObject("mnb567mnb456", Document.parse(validJson)));

    when(mockArbitraryObjectService.findAll()).thenReturn(objectList);

    String responseJson = mockMvc.perform(get("/api/objects/"))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();

    assertEquals("[{\"uid\":\"cisco-crud.us-east-1.elasticbeanstalk.com/api/objects/123abc123\"}, {\"uid\":\"cisco-crud.us-east-1.elasticbeanstalk.com/api/objects/mnb567mnb456\"}]", responseJson);
  }
}