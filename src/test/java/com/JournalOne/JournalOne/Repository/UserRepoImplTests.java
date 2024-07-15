package com.JournalOne.JournalOne.Repository;

import com.JournalOne.JournalOne.repository.criterias.UserRepoCritImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserRepoImplTests {

    @Autowired
    private UserRepoCritImpl userRepoCrit;
    @Test
    public void testSaveNewUser()
    {
    Assertions.assertNotNull(userRepoCrit.getUserForSA());
    }

}
