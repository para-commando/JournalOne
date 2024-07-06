package com.JournalOne.JournalOne.service;

import com.JournalOne.JournalOne.entity.User;
import com.JournalOne.JournalOne.repository.UserRepo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepo userRepo;

    @BeforeAll
    static void methodOne()
    {

    }

    // similarly @AfterEach and AfterAll exists too
    @BeforeEach
    void methodTwo()
    {

    }
    @Test
    public void testAdd()
    {
        User user = userRepo.findByUserName("userOne").orElse(null);
       // assertEquals(4,2+1);
        assertNotNull(user,"Test failed for null check");
        assertTrue(!user.getJournalOneEntriesList().isEmpty());
        assertTrue(5>2);
    }

    @ParameterizedTest
    @ValueSource(strings={
            "userOne"
    })
    public void testingFindByUserName(String name)
    {
        User user = userRepo.findByUserName(name).orElse(null);
        assertNotNull(user,"Failed for the user "+name);
    }

    @ParameterizedTest
    // even the below data can be loaded from a file
    @CsvSource({
            "1,1,2",
            "2,12,14"
    })
    public void paramTest(int a,int b, int expected)
    {

        assertEquals(expected,a+b);

    }

    // this will disable it
    @Disabled
    @Test
    public void testAdd2()
    {
        assertTrue(5>2);
    }

    @ParameterizedTest
    @ArgumentsSource(UserArgumentsProvider.class)
    public void testFindByUserName(User user)
    {
        assertTrue(userService.saveUser(user));
    }
}
