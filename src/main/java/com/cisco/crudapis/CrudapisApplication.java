package com.cisco.crudapis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class CrudapisApplication {

  public static void main(String[] args) {
    SpringApplication.run(CrudapisApplication.class, args);
  }

}
