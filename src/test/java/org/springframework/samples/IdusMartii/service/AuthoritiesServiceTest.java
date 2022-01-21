package org.springframework.samples.IdusMartii.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.IdusMartii.model.Authorities;
import org.springframework.samples.IdusMartii.model.User;

public class AuthoritiesServiceTest {

    @Autowired
	private AuthoritiesService authoritiesService;
    @Autowired
	private UserService userService;

    @Test
    public void testSaveAuthorities(){
        Authorities auditoria = new Authorities();
        User usuario = new User();
        usuario.setUsername("friend8");
        auditoria.setUser(usuario);
        auditoria.setAuthority("friend");
        this.authoritiesService.saveAuthorities(auditoria);

        assertThat(auditoria.getUser().getUsername()).isEqualTo("friend8");
    }

    @Test
    public void testSaveAuthorities2(){
        Authorities auditoria = new Authorities();
        User usuario = new User();
        usuario.setUsername("friend8");
        this.authoritiesService.saveAuthorities("friend8", "consul");

        assertEquals(auditoria.getUser(), usuario);
    }

    @Test
    public void testGetAuthorities(){
        User usuario = userService.findbyUsername("admin1");
        boolean audit= authoritiesService.getAuthorities(usuario.getUsername());

        assertThat(audit).isTrue();
    }




    
}
