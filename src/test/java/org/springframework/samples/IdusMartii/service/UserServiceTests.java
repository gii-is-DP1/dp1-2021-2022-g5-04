package org.springframework.samples.IdusMartii.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

import org.hibernate.exception.DataException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.IdusMartii.model.User;
import org.springframework.samples.IdusMartii.repository.UserRepository;
import org.springframework.samples.IdusMartii.service.exceptions.DuplicatedUsername;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.assertEquals;
@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))

public class UserServiceTests {
	@Autowired
    protected UserService userService;

    @Autowired
    protected UserRepository userRepository;

    @Test
    void testFindUser(){
        Optional<User> usuario= this.userService.findUser("admin1");
        assertThat(usuario.get().getUsername()).isEqualTo("admin1");
    }

    @Test
    @Transactional
    public void testSaveUser() throws DataAccessException, DuplicatedUsername{ //H6


        User usuario = new User();
        usuario.setUsername("jc");
        usuario.setEmail("jc@gmail.com");
        usuario.setPassword("jc");
        this.userService.saveUser(usuario);
        
        assertThat(usuario.getUsername()).isEqualTo("jc");

    }
    @Test
	public void testFindAll() {
		Iterable<User> users = userService.findAll();
		List <String> usernames = new ArrayList<>();
		users.forEach(u -> usernames.add(u.getUsername()));
		assertEquals(usernames.get(0),"admin1");
		assertEquals(usernames.get(1),"ppppp");
        assertEquals(usernames.get(2),"friend1");
        assertEquals(usernames.get(3),"friend2");
        assertEquals(usernames.get(4),"friend3");
        assertEquals(usernames.get(5),"friend4");
        assertEquals(usernames.get(6),"friend5");	
	}
    @Test
	public void testFindUserByText() {
        List<User> users= this.userService.findUsersByText("friend");
        List <String> usernames = new ArrayList<>();
        users.forEach(u -> usernames.add(u.getUsername()));
        assertEquals(usernames.get(0),"friend1");
        assertEquals(usernames.get(1),"friend2");
        assertEquals(usernames.get(2),"friend3");
        assertEquals(usernames.get(3),"friend4");
        assertEquals(usernames.get(4),"friend5");
	}
    @Test
	public void testFindFriends() {
        List<User> users= this.userService.findFriends("admin1");
        List <String> usernames = new ArrayList<>();
        users.forEach(u -> usernames.add(u.getUsername()));
        assertEquals(usernames.get(0),"friend1");
	}
    @Test
	public void testDelete()throws DataException, DuplicatedUsername{
        User usuario = this.userService.findUser("ppppp").get();
        Iterable<User> users = userService.findAll();
        List<User> usersList = new ArrayList<>();
        users.forEach(u -> usersList.add(u));
        int found = usersList.size();
        this.userService.delete(usuario);
        Iterable<User> users2 = userService.findAll();
        List<User> usersList2 = new ArrayList<>();
        users2.forEach(u -> usersList2.add(u));

        assertThat(usersList2.size()).isEqualTo(found-1);
		
	}
    
	

}
