package org.springframework.samples.IdusMartii.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import static org.junit.Assert.assertTrue;


import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;

import org.springframework.samples.IdusMartii.model.FriendInvitation;

import org.springframework.samples.IdusMartii.model.User;
import org.springframework.samples.IdusMartii.repository.FriendInvitationRepository;
import org.springframework.stereotype.Service;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class FriendsInvitationServiceTest {

    
    @Autowired
    private FriendInvitationService fiService;

    @Autowired
	private UserService userService;

    @Autowired 
	FriendInvitationRepository friendInvitationRepository;
    @Test
    public void testFindAll(){
        Iterable<FriendInvitation> invitaciones = fiService.findAll();
        
        assertNotNull(invitaciones);
    }
    @Test
    public void testfindById(){
    	FriendInvitation fi = fiService.findById(1);        
        assertNotNull(fi);
    }

    
    @Test
    public void saveFriendInvitation() throws DataAccessException{
        Iterable<FriendInvitation> invitaciones = fiService.findAll();
        List<FriendInvitation> test = new ArrayList<>();
        invitaciones.forEach(c->test.add(c));
        FriendInvitation fi = new FriendInvitation();
        User a = userService.findUser("friend1").get();
        User b = userService.findUser("friend2").get();
        fi.setUser_requester(a);
        fi.setUser_requested(b);
        fiService.saveFriendInvitation(fi);
        Iterable<FriendInvitation> invitaciones2 = fiService.findAll();
        List<FriendInvitation> test2 = new ArrayList<>();
        invitaciones2.forEach(v->test2.add(v));
        
        
        assertNotEquals(test.size(), test2.size());
    }

    @Test
    public void findFriendInvitationsByUserRequested() {
        User a = userService.findUser("friend1").get();

    	
        assertNotNull(fiService.findFriendInvitationsByUserRequested(a));

    	
    	
    }
    @Test
    public void deleteFriendInvitation() {
    	 Iterable<FriendInvitation> invitaciones = fiService.findAll();
         List<FriendInvitation> test = new ArrayList<>();
         invitaciones.forEach(v->test.add(v));
         User user = userService.findUser(test.get(0).getUser_requester().getUsername()).get();
    	 fiService.deleteFriendInvitation(test.get(0), user);
    	 Iterable<FriendInvitation> invitaciones2 = fiService.findAll();
         List<FriendInvitation> test2 = new ArrayList<>();
         invitaciones2.forEach(v->test2.add(v));
         assertTrue( test2.isEmpty());
    }
    @Test
    public void  acceptFriendInvitation() {
    	 Iterable<FriendInvitation> invitaciones = fiService.findAll();
         List<FriendInvitation> test = new ArrayList<>();
         invitaciones.forEach(v->test.add(v));
         Integer a = test.get(0).getId();
         User user = userService.findUser(test.get(0).getUser_requested().getUsername()).get();
    	 fiService.acceptFriendInvitation(a, user);
    	 Iterable<FriendInvitation> invitaciones2 = fiService.findAll();
         List<FriendInvitation> test2 = new ArrayList<>();
         invitaciones2.forEach(v->test2.add(v));
      
    	  assertTrue( test2.isEmpty());
    }
    @Test
    public void deleteFriendInvitationsFromUser() {
        User a = userService.findUser("friend1").get();
       List<FriendInvitation> test1 = friendInvitationRepository.findFriendInvitationsByUserRequester(a);
       
        fiService.deleteFriendInvitationsFromUser(a);
        List<FriendInvitation> test2 =friendInvitationRepository.findFriendInvitationsByUserRequester(a);

   
        	  assertNotEquals(test1,test2);
    }
    @Test
    public void letFriendRequest() {
        User a = userService.findUser("admin1").get();
        User b = userService.findUser("friend1").get();

       

   
        	  assertFalse(fiService.letFriendRequest(a, b));
    }

    @Test
    public void saveFriends(){
        User a = userService.findUser("friend1").get();
        User b = userService.findUser("friend2").get();
        fiService.saveFriends(a.getUsername(), b.getUsername());
        
		
        assertTrue(a.getFriends().contains(b));

    }
    
    @Test
    public void deleteAllFriendsFromUser(){
        User a = userService.findUser("friend1").get();
        User b = userService.findUser("friend2").get();
        User c = userService.findUser("friend3").get();

        fiService.saveFriends(a.getUsername(), b.getUsername());
        fiService.saveFriends(a.getUsername(), c.getUsername());
        fiService.deleteAllFriendsFromUser(a);
		
        assertTrue(a.getFriends().size()==0);

    
    
    
    
    
    
    
    
    
    
    
    }
    
   
   
}
