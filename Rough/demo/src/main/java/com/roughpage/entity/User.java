package com.roughpage.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Document(collection="Users")
@Getter
@Setter
public class User {


@Id
private ObjectId id;

@Indexed(unique=true)
@NonNull
private String name;

@NonNull
private String password;

@DBRef
public ArrayList<JournalEntry> journalEntries=new  ArrayList<>();



}
