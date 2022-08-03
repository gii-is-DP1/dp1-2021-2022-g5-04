package org.springframework.samples.IdusMartii.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.IdusMartii.enumerates.Plays;
import org.springframework.samples.IdusMartii.model.Match;
import org.springframework.stereotype.Service;
import org.springframework.samples.IdusMartii.model.Player;
import org.springframework.samples.IdusMartii.model.User;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class MatchServiceTests {
	
	
	@Autowired
	private MatchService matchService;

	@Autowired
	private UserService userService;
	
	
	
	@Test
	public void testFindAll() {
		Iterable<Match> matches = matchService.findAll();
		List <String> nombres = new ArrayList<>();
		matches.forEach(m -> nombres.add(m.getName()));
		assertEquals(nombres.get(0),"partida1");
		
	}

	@Test
	public void testIsAdmin(){
		Optional<User> usAdmin= userService.findUser("admin1");
		boolean esAdmin= matchService.isAdmin(usAdmin.get());

		assertThat(esAdmin).isTrue();
	}

	@Test
	public void testMatches(){
		Match match1 = matchService.findById(1);
		Optional<User> usNotAdmin= userService.findUser("friend1");
		List<Match> listaMatchUser= new ArrayList<Match>();
		listaMatchUser.add(match1);
		List<Match> partidas= matchService.matches(usNotAdmin.get());

		assertThat(partidas).isEqualTo(listaMatchUser);
	}
	
	@Test
	public void testFindById() {
		Match match = matchService.findById(1);
		
		assertEquals(match.getName(),"partida1");
		
	}

	@Test
	public void testFindMatchesFromUser(){
		Optional<User> usuario= userService.findUser("friend1");
		List<Match> listaPart= matchService.findMatchesFromUser(usuario.get());

		List<Match> listaP= new ArrayList<Match>();
		Match match1 = matchService.findById(1);
		listaP.add(match1);

		assertThat(listaPart).isEqualTo(listaP);
	}
	
	@Test
	public void testSaveMatch() { //H13
		Iterable <Match> matches = matchService.findAll();
		List <String> nombres = new ArrayList<>();
		matches.forEach(p -> nombres.add(p.getName()));
		int found = nombres.size();

		Match match = new Match();
		match.setName("partida2");
		match.setRound(1);
		match.setTurn(3);
		this.matchService.saveMatch(match);
		
		Iterable <Match> matches2 = matchService.findAll();
		List <String> nombres2 = new ArrayList<>();
		matches2.forEach(p -> nombres2.add(p.getName()));
                                
                
		assertThat(nombres2.size()).isEqualTo(found + 1);
		
	}
	@Test
	public void testStartMatch(){
		Match match = matchService.findById(1);
		matchService.startMatch(match);
		//assertThat(match.getPlays()).isEqualTo(Plays.EDIL);
		assertThat(match.getRound()).isEqualTo(1);
	}

	@Test
	public void testRoundI(){
		Match match1 = matchService.findById(1);
		match1.setRound(1);
		boolean ronda1=matchService.roundI(match1);
		
		assertThat(ronda1).isTrue();
	}

	@Test
	public void testRoundII(){
		Match match1 = matchService.findById(1);
		match1.setRound(2);
		boolean ronda2=matchService.roundII(match1);

		assertThat(ronda2).isTrue();
	}

	@Test
	public void testPlayersWithNoConsulRole(){
		Match match1 = matchService.findById(1);
		List<Player> jugNoConsul= matchService.playersWithNoConsulRole(match1);
		List<Player> jugadoresN= new ArrayList<>();
		jugadoresN.add(match1.getPlayers().get(1));
		jugadoresN.add(match1.getPlayers().get(2));
		jugadoresN.add(match1.getPlayers().get(3));
		jugadoresN.add(match1.getPlayers().get(4));
		jugadoresN.add(match1.getPlayers().get(5));

		assertThat(jugNoConsul).isEqualTo(jugadoresN);
	}

	@Test
	public void testPretorNotAsigned(){
		Match match1 = matchService.findById(1);
		match1.getPlayers().get(1).setAsigned(true);
		boolean pretorN= matchService.pretorNotAsigned(match1);
		
		assertThat(pretorN).isFalse();
	}

	@Test
	public void testStarMatch(){
		Match match1 = matchService.findById(1);
		boolean empezarPartida= matchService.startMatchButton(match1);
		assertThat(empezarPartida).isTrue();

		Match partidaNueva = new Match();
		List<Player> jugNueva= new ArrayList<>();
		jugNueva.add(match1.getPlayers().get(0));
		jugNueva.add(match1.getPlayers().get(4));
		partidaNueva.setPlayers(jugNueva);
		boolean empezarPartida2= matchService.startMatchButton(partidaNueva);
		assertThat(empezarPartida2).isFalse();
	}

	@Test
	public void testEdilNotAsigned(){
		Match match1 = matchService.findById(1);
		match1.getPlayers().get(2).setAsigned(true);
		match1.getPlayers().get(3).setAsigned(true);
		boolean edilN= matchService.edilNotAsigned(match1);

		assertThat(edilN).isFalse();

	}

	@Test
	public void testAvanzarTurno1(){
		Match match1 = matchService.findById(1);
		match1.setTurn(5);
		this.matchService.avanzarTurno(match1, match1.getPlayers());
		assertThat(match1.getPlays()).isEqualTo(Plays.CONSUL);
	}

	@Test
	public void testAvanzarTurno2(){
		Match match1 = matchService.findById(1);
		match1.setRound(1);
		match1.getPlayers().get(0).isAsigned();
		match1.getPlayers().get(1).isAsigned();
		match1.getPlayers().get(2).isAsigned();
		match1.getPlayers().get(3).isAsigned();
		match1.getPlayers().get(4).isAsigned();
		match1.getPlayers().get(5).isAsigned();
		this.matchService.avanzarTurno(match1, match1.getPlayers());
		assertThat(match1.getPlays()).isEqualTo(Plays.EDIL);
		assertThat(match1.getPlayers().get(0).isAsigned()).isFalse();
	}
	@Test
	public void testAvanzarTurno3(){
		Match match1 = matchService.findById(1);
		
		match1.setTurn(3);
		this.matchService.avanzarTurno(match1, match1.getPlayers());
		assertThat(match1.getPlays()).isEqualTo(Plays.EDIL);
	}

	@Test
	public void testVotacionCompleta(){
		Match match1 = matchService.findById(1);
		match1.setPlays(Plays.YELLOWEDIL);
		this.matchService.votacionCompletada(2, match1);

		assertThat(match1.getPlays()).isEqualTo(Plays.CONSUL);
	}

	@Test
	public void testVotacionCompleta2(){
		Match match1 = matchService.findById(1);
		this.matchService.votacionCompletada(2, match1);

		assertThat(match1.getPlays()).isEqualTo(Plays.PRETOR);
	}


}
