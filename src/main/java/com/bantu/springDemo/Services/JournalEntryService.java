package com.bantu.springDemo.Services;

import com.bantu.springDemo.Entity.JournalEntry;
import com.bantu.springDemo.Entity.Users;
import com.bantu.springDemo.Repository.JournalEntryRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    public JournalEntryRepo journalEntryRepo;

    @Autowired
    public UsersService usersService;

    @Transactional
    public void saveEntry(JournalEntry journalEntry,String username){
        Users user=usersService.findByUserName(username);
        journalEntry.setDate(LocalDateTime.now());
        JournalEntry entry=journalEntryRepo.save(journalEntry);
        user.getJournalEntries().add(entry);
        usersService.saveUser(user);
    }

    public void saveEntry(JournalEntry journalEntry){

      journalEntryRepo.save(journalEntry);

    }

    public List<JournalEntry> getEntries(){
        return journalEntryRepo.findAll();
    }


    public Optional<JournalEntry> findByID(ObjectId id){
        return journalEntryRepo.findById(id);
    }

    @Transactional
    public boolean deleteById(ObjectId id, String username){
        boolean removed=false;
        try{
            Users user=usersService.findByUserName(username);
             removed=user.getJournalEntries().removeIf(x -> x.getId().equals(id));
            if(removed) {
                usersService.saveUser(user);
                journalEntryRepo.deleteById(id);
                return removed;
            }

        }
        catch (Exception e){
            throw new RuntimeException("An error occured while deleting an entry", e);
        }
        return removed;
    }
}
