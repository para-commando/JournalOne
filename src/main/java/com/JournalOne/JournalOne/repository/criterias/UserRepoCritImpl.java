package com.JournalOne.JournalOne.repository.criterias;

import com.JournalOne.JournalOne.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class UserRepoCritImpl {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<User> getUserForSA()
    {
       Query query = new Query();
        query.addCriteria(Criteria.where("email").exists(true).ne(null));
        query.addCriteria(Criteria.where("sentimentAnalysis").is(true));
        query.addCriteria(Criteria.where("userName").nin("rio","bao"));
        query.addCriteria(Criteria.where("roles").in("ADMIN","USER"));
        // the below one is how or conditions can be made and above one is how and conditions can be made
        Query query1 = new Query();
        Criteria criteria = new Criteria();
        query1.addCriteria(criteria.orOperator(Criteria.where("email").exists(true),Criteria.where("sentimentAnalysis").is(true)));
        // observer here that we are providing class name to mongo template and not collection name
      List<User> users = mongoTemplate.find(query, User.class);
      return users;
    }
}
