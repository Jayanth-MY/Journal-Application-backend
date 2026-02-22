package com.roughpage.controller;


import com.roughpage.entity.JournalEntry;
import com.roughpage.entity.User;
import com.roughpage.repository.UserRepository;
import com.roughpage.service.JournalServices;
import com.roughpage.service.UserServices;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournlEntryController {


    @Autowired
    private JournalServices journalservices;

    @Autowired
    private UserServices userservices;



    @GetMapping("/listall")
    public ResponseEntity<List<JournalEntry>> getJournalEntries(){
        List<JournalEntry> list= journalservices.getallEntries();
        if(list.isEmpty()){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        else{
            return new ResponseEntity(list,HttpStatus.OK);
        }
    }

    @GetMapping("/getbyid/{id}")
    public ResponseEntity<JournalEntry> getJournalEntrybyId(@PathVariable  ObjectId id){
        Optional<JournalEntry> myentry=journalservices.getbyId(id);
        if(myentry.isPresent()){
            return new ResponseEntity<>(myentry.get(), HttpStatus.OK);
        }
        else{
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @Transactional
    @PostMapping("/add/{name}")
    public ResponseEntity<HttpStatus> addJournalEntry(@RequestBody JournalEntry obj, @PathVariable String name){
        User obj1=userservices.getbyName(name);
        if(obj1==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else{
            try {
                JournalEntry je=journalservices.save(obj);
                userservices.addEntries(je,obj1);
                return new  ResponseEntity<>(HttpStatus.CREATED);
            }catch(Exception e){
                JournalEntry je=journalservices.getJournalByTitle(obj.getTitle());
                userservices.addEntries(je,obj1);
                return new  ResponseEntity<>(HttpStatus.CREATED);
            }


        }

    }

    @Transactional
    @PostMapping("/addall/{name}")
    public ResponseEntity<HttpStatus> addAll(@RequestBody List<JournalEntry> list ,@PathVariable String name){
        User user= userservices.getbyName(name);
        if(user==null){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
            JournalEntry je=new JournalEntry();
            for(JournalEntry obj:list){
                try {
                    je = journalservices.save(obj);
                    userservices.addEntries(je,user);

                }catch(Exception e){
                    je=journalservices.getJournalByTitle(obj.getTitle());
                    userservices.addEntries(je,user);
                }
            }
            return new ResponseEntity<>(HttpStatus.CREATED);
        }


    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable ObjectId id, @RequestBody JournalEntry newentry){
    Optional<JournalEntry> old=journalservices.getbyId(id);
        if (old.isPresent()) {
            old.get().setTitle(newentry.getTitle());
            old.get().setContent(newentry.getContent());
            journalservices.save(old.get());
            return new ResponseEntity(HttpStatus.OK);
        }
        else{
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    @DeleteMapping("/delete/{id}/{username}")
    public ResponseEntity<?> delete(@PathVariable ObjectId id,@PathVariable String username){
        Optional<JournalEntry> old=journalservices.getbyId(id);
        if(old.isPresent()){
            journalservices.delete(id);
            User user=userservices.getbyName(username);
            if(user!=null){
                user.getJournalEntries().remove(old.get());
                userservices.save(user);
            }
             return new ResponseEntity(HttpStatus.OK);
        }
        else{
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }



    @DeleteMapping("/clear")
    public Boolean deleteall(){
        journalservices.clearAll();
        return true;

    }


}
