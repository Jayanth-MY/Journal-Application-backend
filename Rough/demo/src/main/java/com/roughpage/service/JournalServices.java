package com.roughpage.service;

import com.mongodb.lang.Nullable;
import com.roughpage.entity.JournalEntry;
import com.roughpage.repository.JournalRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class JournalServices  {

    @Autowired
    private JournalRepository journalRepository;


    public void addEntry(JournalEntry obj){
        journalRepository.save(obj);
    }

    public List<JournalEntry> getallEntries(){
        try {
            return journalRepository.findAll();
        }
        catch(Exception e){
            return null;
        }
    }

    public Optional<JournalEntry> getbyId(ObjectId id){
        try{
            return journalRepository.findById(id); }
        catch(Exception e){
            return Optional.empty() ;
        }
    }
    public void update(JournalEntry obj ){
        journalRepository.save(obj);

    }
    public JournalEntry save(JournalEntry obj){
        return journalRepository.save(obj);
    }

    public void delete(ObjectId id){
        journalRepository.deleteById(id);
    }

    public void clearAll(){
        journalRepository.deleteAll();
    }

    public void addAllJournals(List<JournalEntry> list){
        journalRepository.saveAll(list);
    }

    public JournalEntry getJournalByTitle(String title){
        Optional<JournalEntry> obj = journalRepository.findByTitle(title);
        if(obj.isPresent()){
            return obj.get();
        }
        else{
            return null;
        }
    }

}
