package org.springframework.samples.IdusMartii.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.idusmartii.enumerates.Faction;
import org.springframework.samples.idusmartii.enumerates.Plays;
import org.springframework.samples.idusmartii.enumerates.Vote;
import org.springframework.samples.idusmartii.model.Match;
import org.springframework.samples.idusmartii.model.Player;
import org.springframework.samples.idusmartii.model.User;
import org.springframework.samples.idusmartii.service.MatchService;
import org.springframework.samples.idusmartii.service.UserService;
import org.springframework.stereotype.Service;


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
		assertEquals(nombres.get(1),"partida2");
		
	}

	@Test
	public void testFindMatchesFromUser(){
		Optional<User> usuario= userService.findUser("admin1");
		List<Match> listaPart= matchService.findMatchesFromUser(usuario.get());

		assertEquals(listaPart.size(), 2);
	}
	@Test
	public void testFindMatchesFromHost(){
		Optional<User> usuario= userService.findUser("admin1");
		List<Match> listaPart= matchService.findMatchesFromHost(usuario.get());

		assertEquals(listaPart.size(), 2);
	}


	@Test
	public void testMatches(){
		User usNotAdmin= userService.findUser("friend1").get();
		User admin= userService.findUser("admin1").get();
		Iterable<Match> matchesAdmin = matchService.findAll();
		List<Match> listaPart= matchService.findMatchesFromUser(usNotAdmin);
		List<Match> listaPartMatchs= matchService.matches(usNotAdmin);
		List<Match> matchesAdminList = new ArrayList<>();
		matchesAdmin.forEach(m -> matchesAdminList.add(m));
		List<Match> partidas= matchService.matches(admin);

		assertEquals(matchesAdminList.size(), partidas.size());
		assertEquals(listaPart.size(), listaPartMatchs.size());
	}
	@Test
	public void testMatchesCreated(){
		User usNotAdmin= userService.findUser("friend1").get();
		User admin= userService.findUser("admin1").get();
		Iterable<Match> matchesAdmin = matchService.findAll();
		List<Match> listaPart= matchService.findMatchesFromHost(usNotAdmin);
		List<Match> listaPartMatchs= matchService.matchesCreated(usNotAdmin);
		List<Match> matchesAdminList = new ArrayList<>();
		matchesAdmin.forEach(m -> matchesAdminList.add(m));
		List<Match> partidas= matchService.matchesCreated(admin);

		assertEquals(matchesAdminList.size(), partidas.size());
		assertEquals(listaPart.size(), listaPartMatchs.size());
	}
	@Test
	public void testMatchContainUser(){
		User user = userService.findUser("friend1").get();
		Match match = matchService.findById(1);
		assertTrue(matchService.matchContainUser(match, user));	
	}
	@Test
	public void testMatchesInProgressNotFinished(){	
		assertEquals(0, matchService.matchesInProgress_NotFinished().size());	
	}
	@Test
	public void testMatchesFinished(){	
		assertEquals(1, matchService.matchesFinished().size());	
	}
	@Test
	public void testMatchesLobby(){	
		assertEquals(1, matchService.matchesLobby().size());	
	}
	@Test
	public void testFindById() {
		Match match = matchService.findById(1);
		
		assertEquals(match.getName(),"partida1");
		
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
	public void testIsHost() { 
		Match match = matchService.findById(1);
		Player player = match.getPlayers().get(0);
		assertTrue(matchService.isHost(player, match));
	}
	@Test
	public void testVotes() { 
		Match match = matchService.findById(1);
		match.setRound(1);
		assertEquals(2, matchService.votes(match).size());
		match.setRound(2);
		assertEquals(3, matchService.votes(match).size());
	}
	@Test
	public void testVotedUser() {
		Match match = matchService.findById(1);
		Player player = match.getPlayers().get(0);
		player.setVote(Vote.GREEN);
		String votedUser = matchService.votedUser(match);
		assertEquals("admin1", votedUser);
	}

	@Test
	public void testNoHostPlayers(){
		Match match = matchService.findById(1);
		List<Player> test = matchService.noHostPlayers(match);
		List<Player> players = match.getPlayers();
		players.remove(0);
		for(int i=0;i<players.size();i++){
			assertEquals(players.get(i), test.get(i));
		}
		

		
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
	public void testStartMatch(){
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
	@Test
	public void testSufragium(){
		Match match = matchService.findById(1);
		Player player = match.getPlayers().get(0);
		player.setCard1(Faction.LOYAL);
		match.setRound(3);
		match.setVotesInFavor(7);
		match.setVotesAgainst(4);
		assertEquals(Faction.LOYAL, matchService.sufragium(match));

		player.setCard1(Faction.TRAITOR);
		match.setRound(3);
		match.setVotesInFavor(4);
		match.setVotesAgainst(7);
		assertEquals(Faction.TRAITOR, matchService.sufragium(match));

		player.setCard1(Faction.TRAITOR);
		match.setRound(4);
		match.setVotesInFavor(15);
		match.setVotesAgainst(14);
		assertEquals(Faction.TRAITOR, matchService.sufragium(match));

	}

}
