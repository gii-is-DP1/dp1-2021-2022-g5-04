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
import org.springframework.samples.IdusMartii.enumerates.Faction;
import org.springframework.samples.IdusMartii.enumerates.Plays;
import org.springframework.samples.IdusMartii.enumerates.Role;
import org.springframework.samples.IdusMartii.enumerates.Vote;
import org.springframework.samples.IdusMartii.model.Player;
import org.springframework.samples.IdusMartii.model.Match;
import org.springframework.samples.IdusMartii.model.User;
import org.springframework.stereotype.Service;



public class PlayerServiceTests {
	
	@Autowired
	private PlayerService playerService;

	@Autowired
	private MatchService matchService;

	@Autowired
	private UserService userService;
	
	@Test
	public void testCount() {
		int count=playerService.playerCount();
		assertEquals(count,6);
	}
	
	@Test
	public void testFindAll() {
		Iterable<Player> players = playerService.findAll();
		List <String> nombres = new ArrayList<>();
		players.forEach(p -> nombres.add(p.getName()));
		assertEquals(nombres.get(0),"player1");
		assertEquals(nombres.get(1),"player2");
		assertEquals(nombres.get(2),"player3");
		assertEquals(nombres.get(3),"player4");
		assertEquals(nombres.get(4),"player5");
		assertEquals(nombres.get(5),"player6");
	
	}
	
	@Test
	public void testFindById() {
		Player player = playerService.findbyId(1);
		
		assertEquals(player.getName(),"player1");
		
	}
	
	@Test
	public void testSavePlayer() {
		Iterable <Player> players = playerService.findAll();
		List <String> nombres = new ArrayList<>();
		players.forEach(p -> nombres.add(p.getName()));
		int found = nombres.size();

		Player player = new Player();
		player.setCard1(Faction.LOYAL);
		player.setCard2(Faction.LOYAL);
		this.playerService.savePlayer(player);
		
		Iterable <Player> players2 = playerService.findAll();
		List <String> nombres2 = new ArrayList<>();
		players2.forEach(p -> nombres2.add(p.getName()));
                                
                
		assertThat(nombres2.size()).isEqualTo(found + 1);
		
	}

	@Test
	public void testFindByMatchAndUsername(){
		Match partida = matchService.findById(1);
		Optional<User> usuario = userService.findUser("friend1");
	
		User nombreUsuario = usuario.get();
		Player playerTest = playerService.findByMatchAndUser(partida,nombreUsuario);
		assertThat(playerTest.getUser().getUsername()).isEqualTo("friend1");
		assertThat(playerTest.getMatch().getName()).isEqualTo("partida1");
	}

	@Test
	public void testDeletePlayer(){
	
		Player delPlayer = new Player();
		delPlayer.setCard1(Faction.TRAITOR);
		delPlayer.setCard2(Faction.MERCHANT);
		this.playerService.savePlayer(delPlayer);	
		
		Iterable <Player> players2 = playerService.findAll();
		List <String> nombres2 = new ArrayList<>();
		players2.forEach(p -> nombres2.add(p.getName()));
		int found = nombres2.size();
		
		this.playerService.deletePlayer(delPlayer);

		Iterable <Player> players3 = playerService.findAll();
		List <String> nombres3 = new ArrayList<>();
		players3.forEach(p -> nombres3.add(p.getName()));

		assertThat(nombres3.size()).isEqualTo(found-1);
	}

	@Test
	public void testFindByRole(){
		List<Player> roles = playerService.findByRole(Role.NO_ROL);
		int tamañoR = roles.size();

		assertThat(tamañoR).isEqualTo(2);
	}

	@Test
	public void testCanVote(){
		Match match1 = matchService.findById(1);
		match1.setPlays(Plays.EDIL);
		boolean voto1 = playerService.canVote(match1.getPlayers().get(1), match1);
		boolean voto2 = playerService.canVote(match1.getPlayers().get(3), match1);

		assertThat(voto1).isFalse();
		assertThat(voto2).isTrue();

	}

	@Test
	public void testCanVoteYellow(){
		Match match1 = matchService.findById(1);
		match1.setPlays(Plays.YELLOWEDIL);
		boolean voto1 = playerService.canVoteYellow(match1.getPlayers().get(1), match1);
		boolean voto2 = playerService.canVoteYellow(match1.getPlayers().get(3), match1);

		assertThat(voto1).isFalse();
		assertThat(voto2).isTrue();

	}

	@Test
	public void testShowCards(){
		Player num4 = playerService.findbyId(4);
		boolean mostrar = playerService.showCards(num4);
		assertThat(mostrar).isTrue();
	}

	@Test
	public void testPlayerYellow(){
		Match match1 = matchService.findById(1);
		match1.setPlays(Plays.YELLOWEDIL);
		match1.getPlayers().get(3).setVote(Vote.YELLOW);
		Player plam = playerService.playerYellow(match1.getPlayers().get(3), match1);

		assertThat(plam).isEqualTo(match1.getPlayers().get(3));
	}

	@Test 
	public void testCheckVote(){
		Match match1 = matchService.findById(1);
		match1.setPlays(Plays.PRETOR);
		match1.getPlayers().get(2).setRole(Role.PRETOR);
		boolean compVoto= playerService.checkVote(match1.getPlayers().get(2), match1);

		assertThat(compVoto).isTrue();
	}

	@Test
	public void testChooseFaction(){
		Match match1 = matchService.findById(1);
		match1.setPlays(Plays.CONSUL);
		match1.setRound(1);
		match1.getPlayers().get(5).setRole(Role.CONSUL);
		boolean selectfaccT1= playerService.chooseFaction(match1.getPlayers().get(5), match1);

		match1.getPlayers().get(2).setVote(Vote.GREEN);
		match1.getPlayers().get(3).setVote(Vote.GREEN);
		boolean selectfaccT2= playerService.chooseFaction(match1.getPlayers().get(0), match1);
		
		boolean selectfaccF= playerService.chooseFaction(match1.getPlayers().get(4), match1);

		assertThat(selectfaccT1).isTrue();
		assertThat(selectfaccT2).isTrue();
		assertThat(selectfaccF).isFalse();
	}

	@Test
	public void testCountVotes(){
		Match match1 = matchService.findById(1);
		match1.setPlays(Plays.CONSUL);
		match1.getPlayers().get(0).setRole(Role.CONSUL);
		match1.getPlayers().get(2).setVote(Vote.GREEN);
		match1.getPlayers().get(3).setVote(Vote.GREEN);
		boolean contarT = playerService.countVotes(match1.getPlayers().get(0), match1);
		boolean contarF = playerService.countVotes(match1.getPlayers().get(1), match1);

		assertThat(contarT).isTrue();
		assertThat(contarF).isFalse();
	}

	@Test
	public void testChooseRol(){
		Match match1 = matchService.findById(1);
		match1.setPlays(Plays.CONSUL);
		boolean selectRolT= playerService.chooseRol(match1.getPlayers().get(0), match1);
		boolean selectRolF= playerService.chooseRol(match1.getPlayers().get(1), match1);

		assertThat(selectRolT).isTrue();
		assertThat(selectRolF).isFalse();
	}


	@Test
	public void testAfterVotes(){
		Match match1 = matchService.findById(1);
		match1.getPlayers().get(2).setVote(Vote.GREEN);
		match1.getPlayers().get(3).setVote(Vote.GREEN);
		boolean votosdsp= playerService.afterVotes(match1.getPlayers().get(2), match1);

		assertThat(votosdsp).isTrue();
	}
}
		
	
	
