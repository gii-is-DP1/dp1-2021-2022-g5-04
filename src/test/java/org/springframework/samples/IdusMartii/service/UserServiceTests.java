package org.springframework.samples.IdusMartii.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.IdusMartii.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))

public class UserServiceTests {
	@Autowired
    protected UserService userService;

    @Test
    void shouldFindUserById(){
        Optional<User> usuario= this.userService.findUser("admin1");
        assertThat(usuario.get().getUsername()).isEqualTo("admin1");
    }

    @Test
    @Transactional
    public void shouldInsertUser(){


        User usuario = new User();
        usuario.setUsername("jc");
        usuario.setEmail("jc@gmail.com");
        usuario.setPassword("jc");
        this.userService.saveUser(usuario);

        assertThat(usuario.getUsername()).isEqualTo("jc");

    }
	

}
