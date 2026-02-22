package com.roughpage.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;


@Document(collection="JournalEntries")
@Getter
@Setter
@Data
@NoArgsConstructor
public class JournalEntry {
@Id
public ObjectId id;

@Indexed(unique=true)
@NonNull
private String title;

@NonNull
private String content;
private LocalDate date= LocalDate.now();


}
