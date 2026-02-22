package com.roughpage.repository;

import com.roughpage.entity.JournalEntry;
import com.roughpage.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, ObjectId> {

    public Optional<User> findByName(String name);
}
