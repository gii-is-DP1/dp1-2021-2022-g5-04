package org.springframework.samples.IdusMartii.service;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;


import java.util.List;



import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.samples.IdusMartii.model.Invitation;
import org.springframework.samples.IdusMartii.model.Match;
import org.springframework.samples.IdusMartii.model.User;
import org.springframework.samples.IdusMartii.service.exceptions.PlayerAlreadyInMatch;
import org.springframework.samples.IdusMartii.service.exceptions.NotExistingUsername;

import java.util.ArrayList;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class InvitationServiceTest {

    @Autowired
	private InvitationService invitationService;

    @Autowired
	private MatchService matchService;

    @Autowired
	private UserService userService;
    
    @Test
    public void testFindAll(){
        Iterable<Invitation> invitaciones = invitationService.findAll();
        List<String> listInvitaciones = new ArrayList<>();
		invitaciones.forEach(p -> listInvitaciones.add(p.getUser().getUsername()));

        assertFalse(listInvitaciones.isEmpty());
    }

    @Test
    public void testFindById(){
        Invitation invitacionId= invitationService.findById(1);
        assertTrue(invitacionId.getUser()==userService.findUser("ppp").get());
    }

    @Test
    public void testSaveInvitation() throws DataAccessException, PlayerAlreadyInMatch, NotExistingUsername{
        Invitation invitacion = new Invitation();
        User usuario = new User();
        usuario.setUsername("friend8");
        invitacion.setUser(usuario);
        Match partida = new Match();
        invitacion.setMatch(partida);
        this.invitationService.saveInvitation(invitacion, partida);
        

        Invitation invit = invitationService.findById(2);

        assertThat(invit.getUser().getUsername()).isEqualTo("friend8");
    }
    // @Test
    // public void testAcceptInvitation(){
    //     User user = userService.findbyUsername("ppp");
    //     Match match = matchService.findById(1);
    //     Iterable <Player> players = playerService.findAll();
	// 	List <String> nombres = new ArrayList<>();
	// 	players.forEach(p -> nombres.add(p.getName()));
    //     int found = nombres.size();
    //     Invitation invitation = invitationService.findByUserAndMatch(user, match).get(0);
        
        
        
    //     this.invitationService.acceptInvitation(1, match, user);
    //     Iterable <Player> players2 = playerService.findAll();
	// 	List <String> nombres2 = new ArrayList<>();
	// 	players2.forEach(p -> nombres2.add(p.getName()));
    //     assertThat(nombres2).isEqualTo(found+1);
    // }

    @Test
    public void testFindByUser(){
        User usuario = userService.findUser("ppp").get();
        List<Invitation> listInvitaciones = invitationService.findByUser(usuario);

        assertFalse(listInvitaciones.isEmpty());

    }

    @Test
    public void testFindByUserAndMatch(){
        User usuario = userService.findUser("friend1").get();
        Match match1 = matchService.findById(1);
        List<Invitation> listInvitaciones = invitationService.findByUserAndMatch(usuario, match1);

        assertTrue(listInvitaciones.isEmpty());
    }

    @Test
    public void testDeleteInvitation(){
    	 Iterable<Invitation> invitaciones = invitationService.findAll();
         List<String> listInvitaciones = new ArrayList<>();
 		invitaciones.forEach(p -> listInvitaciones.add(p.getId().toString()));
        Invitation invitacionId= invitationService.findById(1);
        this.invitationService.deleteInvitation(invitacionId);
        Iterable<Invitation> invitacionesa = invitationService.findAll();
        List<String> listInvitacionesb = new ArrayList<>();
		invitacionesa.forEach(p -> listInvitacionesb.add(p.getId().toString()));
        assertNotEquals(listInvitacionesb.size(),listInvitaciones.size() );
    }
        
    
}
