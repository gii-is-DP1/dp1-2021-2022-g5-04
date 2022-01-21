package org.springframework.samples.IdusMartii.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.IdusMartii.model.Achievement;
import org.springframework.samples.IdusMartii.model.Chat;
import org.springframework.samples.IdusMartii.model.Match;
import org.springframework.samples.IdusMartii.model.User;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class FriendsServiceTest {

    @Autowired
    private MatchService matchService;
    
    @Autowired
    private FriendsService friendsService;

    @Autowired
	private UserService userService;


    @Test
    public void saveFriends(){
        User a = userService.findbyUsername("friend1");
        User b = userService.findbyUsername("friend2");
        friendsService.saveFriends(a.getUsername(), b.getUsername());
        
		
        assertTrue(a.getFriends().contains(b));

    }
    
    @Test
    public void deleteAllFriendsFromUser(){
        User a = userService.findbyUsername("friend1");
        User b = userService.findbyUsername("friend2");
        User c = userService.findbyUsername("friend3");

        friendsService.saveFriends(a.getUsername(), b.getUsername());
        friendsService.saveFriends(a.getUsername(), c.getUsername());
        friendsService.deleteAllFriendsFromUser(a);
		
        assertTrue(a.getFriends().size()==0);

    }
   
    
    
   
}
