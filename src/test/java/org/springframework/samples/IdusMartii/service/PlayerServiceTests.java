package org.springframework.samples.IdusMartii.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.IdusMartii.enumerates.Faction;
import org.springframework.samples.IdusMartii.model.Owner;
import org.springframework.samples.IdusMartii.model.Player;
import org.springframework.samples.IdusMartii.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class PlayerServiceTests {
	
	@Autowired
	private PlayerService playerService;
	
	@Test
	public void testCountWithInitialData() {
		int count=playerService.playerCount();
		assertEquals(count,2);
	}
	
	@Test
	public void testFindAll() {
		Iterable<Player> players = playerService.findAll();
		List <String> nombres = new ArrayList<>();
		players.forEach(p -> nombres.add(p.getName()));
		assertEquals(nombres.get(0),"player1");
		assertEquals(nombres.get(1),"player2");
		
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
		
	
	
