package org.springframework.samples.IdusMartii.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.IdusMartii.enumerates.Faction;
import org.springframework.samples.IdusMartii.model.Player;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class PlayerServiceTests {
	
	@Autowired
	private PlayerService playerService;
	
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
}
		
	
	
