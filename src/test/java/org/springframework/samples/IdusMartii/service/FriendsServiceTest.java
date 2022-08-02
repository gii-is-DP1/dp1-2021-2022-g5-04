package org.springframework.samples.IdusMartii.service;


import static org.junit.Assert.assertTrue;




import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.IdusMartii.model.User;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class FriendsServiceTest {

    @Autowired
    private FriendsService friendsService;

    @Autowired
	private UserService userService;


    @Test
    public void saveFriends(){
        User a = userService.findUser("friend1").get();
        User b = userService.findUser("friend2").get();
        friendsService.saveFriends(a.getUsername(), b.getUsername());
        
		
        assertTrue(a.getFriends().contains(b));

    }
    
    @Test
    public void deleteAllFriendsFromUser(){
        User a = userService.findUser("friend1").get();
        User b = userService.findUser("friend2").get();
        User c = userService.findUser("friend3").get();

        friendsService.saveFriends(a.getUsername(), b.getUsername());
        friendsService.saveFriends(a.getUsername(), c.getUsername());
        friendsService.deleteAllFriendsFromUser(a);
		
        assertTrue(a.getFriends().size()==0);

    }
   
    
    
   
}
