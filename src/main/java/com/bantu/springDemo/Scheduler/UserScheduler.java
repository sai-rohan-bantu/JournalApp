package com.bantu.springDemo.Scheduler;

import com.bantu.springDemo.Cache.AppCache;
import com.bantu.springDemo.Entity.JournalEntry;
import com.bantu.springDemo.Entity.Users;
import com.bantu.springDemo.Enums.Sentiment;
import com.bantu.springDemo.Repository.UsersRepoImpl;
import com.bantu.springDemo.Services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserScheduler {

    @Autowired
    public EmailService emailService;

    @Autowired
    public UsersRepoImpl usersRepo;


    @Autowired
    public AppCache appCache;

//    @Scheduled(cron = "0 0 9 * * SUN")
    public void fetchUsersAndSendSA(){
        List<Users> users=usersRepo.getUserForSA();
        for(Users user: users){
            List<JournalEntry> entriesOfUser=user.getJournalEntries();
            List<Sentiment> sentiments = entriesOfUser.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x -> x.getSentiment()).collect(Collectors.toList());
            Map<Sentiment,Integer> sentimentsCounts=new HashMap<>();
            for(Sentiment sentiment:sentiments){
                if(sentiment!=null){
                    sentimentsCounts.put(sentiment,sentimentsCounts.getOrDefault(sentiment,0)+1);
                }
            }
            Sentiment mostFrequentSentiment=null;
            int maxCount=0;
            for(Map.Entry<Sentiment,Integer> entry: sentimentsCounts.entrySet()){
                if(entry.getValue()>maxCount){
                    maxCount= entry.getValue();
                    mostFrequentSentiment=entry.getKey();
                }
            }
            if(mostFrequentSentiment !=null){
                emailService.sendEmail(user.getEmail(),"Sentiment for last 7 days ",mostFrequentSentiment.toString());
            }
        }
    }

    @Scheduled(cron = "0 0/10 * ? * *" )
    public void clearCache(){
        appCache.init();
    }
}
