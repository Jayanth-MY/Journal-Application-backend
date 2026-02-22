package com.roughpage.repository;

import com.roughpage.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface JournalRepository extends MongoRepository<JournalEntry, ObjectId> {

public Optional<JournalEntry> findByTitle(String title);
}
