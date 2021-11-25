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
import org.springframework.samples.IdusMartii.model.Match;
import org.springframework.samples.IdusMartii.model.Player;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class MatchServiceTests {
	
	
	@Autowired
	private MatchService matchService;
	
	
	@Test
	public void testFindAll() {
		Iterable<Match> matches = matchService.findAll();
		List <String> nombres = new ArrayList<>();
		matches.forEach(m -> nombres.add(m.getName()));
		assertEquals(nombres.get(0),"partida1");
		
	}
	
	@Test
	public void testFindById() {
		Match match = matchService.findById(1);
		
		assertEquals(match.getName(),"partida1");
		
	}
	
	@Test
	public void testSaveMatch() {
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

}
