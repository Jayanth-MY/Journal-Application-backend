package com.roughpage.service;

import com.roughpage.entity.JournalEntry;
import com.roughpage.entity.User;
import com.roughpage.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserServices {

    @Autowired
    private UserRepository userRepository;


    public void addEntry(User obj){
        userRepository.save(obj);
    }

    public List<User> getallEntries(){
        try {
            return userRepository.findAll();
        }
        catch(Exception e){
            return null;
        }
    }

    public Optional<User> getbyId(ObjectId id){
        try{
            return userRepository.findById(id); }
        catch(Exception e){
            return Optional.empty() ;
        }
    }
    public void update(User obj ){
        userRepository.save(obj);

    }


    public void delete(ObjectId id){
        userRepository.deleteById(id);
    }

    public void clearAll(){
        userRepository.deleteAll();
    }

    public void addAllJournals(List<User> list){
        userRepository.saveAll(list);
    }

    public User getbyName(String name ){
        Optional<User> obj=userRepository.findByName(name);
        if(obj.isPresent()){
            return userRepository.findByName(name).get();
        }
        return null;
    }
    public void addEntries(JournalEntry jobj,User uobj ){
       uobj.getJournalEntries().add(jobj);
       userRepository.save(uobj);
    }

    public void save(User user ){
        userRepository.save(user);
    }

}
