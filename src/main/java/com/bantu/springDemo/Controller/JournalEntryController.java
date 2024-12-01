package com.bantu.springDemo.Controller;


import com.bantu.springDemo.Entity.JournalEntry;
import com.bantu.springDemo.Entity.Users;
import com.bantu.springDemo.Services.JournalEntryService;
import com.bantu.springDemo.Services.UsersService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    public JournalEntryService journalEntryService;

    @Autowired
    public UsersService usersService;

    @GetMapping()
    public ResponseEntity<?> getAllJournalEntriesOfUser(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        Users user=usersService.findByUserName(username);
        List<JournalEntry> entries=user.getJournalEntries();
        if(entries!=null && !entries.isEmpty()){
        return new ResponseEntity<>(entries,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry journalEntry ){

        try{
            Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
            String username=authentication.getName();
           journalEntryService.saveEntry(journalEntry,username);
           return new ResponseEntity<>(journalEntry,HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/id/{id}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId id){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        Users user=usersService.findByUserName(username);
        List<JournalEntry> entries=user.getJournalEntries().stream().filter(x->x.getId().equals(id)).collect(Collectors.toList());

        if(!entries.isEmpty()){
            Optional<JournalEntry> journalEntry=journalEntryService.findByID(id);
            if(journalEntry.isPresent())
               return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{id}")
    public boolean deleteJournalEntryById(@PathVariable ObjectId id){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        boolean removed= journalEntryService.deleteById(id,username);

        return removed;
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<?> updateJournalEntryById(@PathVariable ObjectId id,
                                               @RequestBody JournalEntry newEntry){

        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        Users user=usersService.findByUserName(username);
        List<JournalEntry> entries=user.getJournalEntries().stream().filter(x->x.getId().equals(id)).collect(Collectors.toList());

        if(!entries.isEmpty()){
            Optional<JournalEntry> oldEntry=journalEntryService.findByID(id);
            if(oldEntry.isPresent()){
                JournalEntry old=oldEntry.get();
                old.setTitle(newEntry.getTitle()!=null && !newEntry.getTitle().equals("")  ? newEntry.getTitle() : old.getTitle());
                old.setContent(newEntry.getContent()!=null && !newEntry.getContent().equals("")  ? newEntry.getContent() : old.getContent());
                journalEntryService.saveEntry(old);
                return new ResponseEntity<>(old,HttpStatus.OK);
            }

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
}
