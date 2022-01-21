package org.springframework.samples.IdusMartii.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.samples.IdusMartii.model.Invitation;
import org.springframework.samples.IdusMartii.model.Match;
import org.springframework.samples.IdusMartii.model.User;

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

        assertEquals(listInvitaciones.get(0),"friend1");
    }

    @Test
    public void testFindById(){
        Invitation invitacionId= invitationService.findById(0);
        assertEquals(invitacionId.getUser().getUsername(),"friend1");
    }

    @Test
    public void testSaveInvitation(){
        Invitation invitacion = new Invitation();
        User usuario = new User();
        usuario.setUsername("friend8");
        invitacion.setUser(usuario);
        Match partida = new Match();
        invitacion.setMatch(partida);
        this.invitationService.saveInvitation(invitacion);

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
        User usuario = userService.findbyUsername("friend1");
        List<Invitation> listInvitaciones = invitationService.findByUser(usuario);

        assertThat(listInvitaciones.size()).isEqualTo(1);

    }

    @Test
    public void testFindByUserAndMatch(){
        User usuario = userService.findbyUsername("friend1");
        Match match1 = matchService.findById(1);
        List<Invitation> listInvitaciones = invitationService.findByUserAndMatch(usuario, match1);

        assertThat(listInvitaciones.size()).isEqualTo(1);
    }

    @Test
    public void testDeleteInvitation(){
        Invitation invitacionId= invitationService.findById(0);
        this.invitationService.deleteInvitation(invitacionId);
        Iterable<Invitation> invitaciones = invitationService.findAll();
        List<String> listInvitaciones = new ArrayList<>();
		invitaciones.forEach(p -> listInvitaciones.add(p.getId().toString()));
        assertThat(listInvitaciones.size()).isEqualTo(1);
    }
        
    
}
