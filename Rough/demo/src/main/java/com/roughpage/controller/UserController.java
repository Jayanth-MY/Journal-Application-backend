package com.roughpage.controller;


import com.roughpage.entity.JournalEntry;
import com.roughpage.entity.User;
import com.roughpage.service.JournalServices;
import com.roughpage.service.UserServices;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserServices userservices;

    @GetMapping("/listall")
    public ResponseEntity<List<User>> getUserEntries(){
        List<User> list= userservices.getallEntries();
        if(list.isEmpty()){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        else{
            return new ResponseEntity(list,HttpStatus.OK);
        }
    }

    @GetMapping("/getbyid/{id}")
    public ResponseEntity<User> getUserEntrybyId(@PathVariable  ObjectId id){
        Optional<User> myentry=userservices.getbyId(id);
        if(myentry.isPresent()){
            return new ResponseEntity<>(myentry.get(), HttpStatus.OK);
        }
        else{
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addUserEntry(@RequestBody User obj){
        try {
            userservices.addEntry(obj);
            return new  ResponseEntity<>(HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


    }
    @PostMapping("/addall")
    public ResponseEntity<HttpStatus> addAll(@RequestBody List<User> list ){
        userservices.addAllJournals(list);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable ObjectId id, @RequestBody User newentry){
    Optional<User> old=userservices.getbyId(id);
        if (old.isPresent()) {
            userservices.addEntry(newentry);
            return new ResponseEntity(HttpStatus.OK);
        }
        else{
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable ObjectId id){
        Optional<User> old=userservices.getbyId(id);
        if(old.isPresent()){
            userservices.delete(id);
             return new ResponseEntity(HttpStatus.OK);
        }
        else{
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }



    @DeleteMapping("/clear")
    public Boolean deleteall(){
        userservices.clearAll();
        return true;

    }

    @GetMapping("/getByName/{name}")
    public ResponseEntity<User> getByName(@PathVariable String name){
        User temp=userservices.getbyName(name);
       if(temp==null){
           return new  ResponseEntity(null,HttpStatus.NOT_FOUND);
       }
       return new ResponseEntity(temp,HttpStatus.OK);
    }

    @GetMapping("/getlistByName/{name}")
    public ResponseEntity<ArrayList<JournalEntry>> getlistByName(@PathVariable String name){
        User temp=userservices.getbyName(name);
        if(temp==null){
            return new  ResponseEntity(null,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(temp.getJournalEntries(),HttpStatus.OK);
    }

}
