package com.cisco.crudapis.service;

import com.cisco.crudapis.model.ArbitraryObject;
import com.cisco.crudapis.repository.ArbitraryObjectRepository;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Service for Arbitrary object CRUD operations.
 */
@Service
public class ArbitraryObjectService {

  @Autowired
  private ArbitraryObjectRepository arbitraryObjectRepository;

  /**
   * Creates and stores {@link ArbitraryObject} in database.
   *
   * @param arbitraryObjectJson Arbitrary object to be created passed as Json.
   * @return newly created and database stored Arbitrary object.
   */
  public ArbitraryObject create(String arbitraryObjectJson) {
    ArbitraryObject arbitraryObject = new ArbitraryObject(arbitraryObjectJson);
    ArbitraryObject object = arbitraryObjectRepository.save(arbitraryObject);
    return object;
  }

  /**
   * Used to lookup {@link ArbitraryObject} in database when its uid is known.
   *
   * @param uid uid of the Arbitrary object to be searched.
   * @return Arbitrary object with specified uid.
   */
  public ArbitraryObject find(String uid) {
    if (!arbitraryObjectRepository.existsById(uid)) {
      return null;
    }
    return arbitraryObjectRepository.findById(uid).get();
  }

  /**
   * Retrieves all the {@link ArbitraryObject}s present in database.
   *
   * @return List of all {@link ArbitraryObject}s present in database.
   */
  public List<ArbitraryObject> findAll() {
    return arbitraryObjectRepository.findAll();
  }

  /**
   * Updates {@link ArbitraryObject} with specified uid and specified contents.
   *
   * @param uid       uid of the Arbitrary object to be updated.
   * @param document  New contents of the object.
   * @return          Updated Arbitrary object.
   */
  public ArbitraryObject update(String uid, Document document) {
    return arbitraryObjectRepository.save(new ArbitraryObject(uid, document));
  }

  /**
   * Deletes an {@link ArbitraryObject}.
   * @param uid uid of the Arbitrary object to be deleted.
   */
  public void delete(String uid) {
    arbitraryObjectRepository.deleteById(uid);
  }
}
