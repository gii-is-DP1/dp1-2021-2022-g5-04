package org.springframework.samples.IdusMartii.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.IdusMartii.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.assertEquals;
@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))

public class UserServiceTests {
	@Autowired
    protected UserService userService;

    @Test
    void testFindUser(){
        Optional<User> usuario= this.userService.findUser("admin1");
        assertThat(usuario.get().getUsername()).isEqualTo("admin1");
    }

    @Test
    @Transactional
    public void testSaveUser(){


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
		assertEquals(usernames.get(1),"ppp");
        assertEquals(usernames.get(2),"friend1");
        assertEquals(usernames.get(3),"friend2");
        assertEquals(usernames.get(4),"friend3");
        assertEquals(usernames.get(5),"friend4");
        assertEquals(usernames.get(6),"friend5");

		
	}
	

}
