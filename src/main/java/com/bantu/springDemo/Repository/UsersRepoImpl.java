package com.bantu.springDemo.Repository;

import com.bantu.springDemo.Entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class UsersRepoImpl {

    @Autowired
    public MongoTemplate mongoTemplate;

    public List<Users> getUserForSA() {
        Query query = new Query();
        query.addCriteria(Criteria.where("email").regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,6}$"));
        query.addCriteria(Criteria.where("sentimentAnalysis").is(true));
        return mongoTemplate.find(query, Users.class);
    }

    public List<Users> getUsersWithEmailAndPassword(){
        Query query=new Query();
        Criteria criteria=new Criteria();
        query.addCriteria(criteria.andOperator(
                Criteria.where("email").exists(true).ne(null).ne(""),
                Criteria.where("sentimentAnalysis").is(true)));
        List<Users> user=mongoTemplate.find(query,Users.class);
        return user;
    }
}
