package com.cisco.crudapis.service;

import com.cisco.crudapis.model.ArbitraryObject;
import com.cisco.crudapis.repository.ArbitraryObjectRepository;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArbitraryObjectService {

  @Autowired
  private ArbitraryObjectRepository arbitraryObjectRepository;

  public ArbitraryObject create(String arbitraryObjectJson) {
    ArbitraryObject arbitraryObject = new ArbitraryObject(arbitraryObjectJson);
    ArbitraryObject object = arbitraryObjectRepository.save(arbitraryObject);
    return object;
  }

  public ArbitraryObject find(String uid) {
    if (!arbitraryObjectRepository.existsById(uid)) {
      return new ArbitraryObject();
    }
    return arbitraryObjectRepository.findById(uid).get();
  }

  public List<ArbitraryObject> findAll() {
    return arbitraryObjectRepository.findAll();
  }

  public ArbitraryObject update(String uid, Document document) {
    arbitraryObjectRepository.deleteById(uid);
    return arbitraryObjectRepository.save(new ArbitraryObject(uid, document));
  }

  public void delete(String uid) {
    arbitraryObjectRepository.deleteById(uid);
  }
}
