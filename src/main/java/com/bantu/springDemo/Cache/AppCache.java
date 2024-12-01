package com.bantu.springDemo.Cache;

import com.bantu.springDemo.Entity.ConfigJournalAppEntity;
import com.bantu.springDemo.Repository.ConfigJournalAppRepo;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {
    public Map<String,String> AppCachee;

    @Autowired
    public ConfigJournalAppRepo configJournalAppRepo;

    @PostConstruct
    public void init(){
        AppCachee=new HashMap<>();
        List<ConfigJournalAppEntity> allApis=configJournalAppRepo.findAll();
        System.out.println(allApis);
        for(ConfigJournalAppEntity e:allApis){
            AppCachee.put(e.getKey(),e.getValue());
        }
        System.out.println(AppCachee);
    }



}
