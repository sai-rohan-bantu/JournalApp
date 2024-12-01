package com.bantu.springDemo.Repository;

import com.bantu.springDemo.Entity.Users;
import com.mongodb.client.MongoDatabase;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UsersRepo extends MongoRepository<Users, ObjectId> {

    Users findByUsername(String username);

    void deleteByUsername(String name);
}
