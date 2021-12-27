package org.springframework.samples.IdusMartii.service;

import org.springframework.samples.IdusMartii.repository.MatchRepository;
import org.springframework.samples.IdusMartii.enumerates.Plays;
import org.springframework.samples.IdusMartii.enumerates.Role;
import org.springframework.samples.IdusMartii.model.Match;
import org.springframework.samples.IdusMartii.model.Player;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.DataAccessException;

@Service
public class MatchService {
    @Autowired 
	MatchRepository matchRepository;

	@Transactional
	public Iterable<Match> findAll(){
		return matchRepository.findAll();
	}

 
    
	@Transactional(readOnly = true)
	public Match findById(Integer id) throws DataAccessException{
		return matchRepository.findById(id).get();
	}
	
	@Transactional
	public void saveMatch(Match match) throws DataAccessException {
		matchRepository.save(match);
	}
	
	@Transactional
	public boolean roundI(Match match) throws DataAccessException {
		if (match.getRound() == 0) {
			return true;
		} else {
			return false;
		}
	}
	@Transactional
	public boolean roundII(Match match) throws DataAccessException {
		if (match.getRound() == 1) {
			return true;
		} else {
			return false;
		}
	}
	@Transactional
	public List<Player> playersWithNoConsulRole(Match match) throws DataAccessException {
		List<Player> resultado = new ArrayList<Player>();
		List<Player> players = match.getPlayers();
		for(Player p: players) {
			if(p.getRole() != Role.CONSUL && !p.isAsigned()) {
				resultado.add(p);
			}
		}
		return resultado;
	}
	@Transactional
	public boolean pretorNotAsigned(Match match) throws DataAccessException {
		boolean resultado = true;
		List<Player> players = match.getPlayers();
		for (Player p: players) {
			if (p.getRole() == Role.PRETOR && p.isAsigned()) {
				resultado = false;
			}
		}
		return resultado;
	}
	@Transactional
	public boolean edilNotAsigned(Match match) throws DataAccessException {
		boolean resultado = true;
		List<Player> players = match.getPlayers();
		int i = 0;
		for (Player p: players) {
			if (p.getRole() == Role.EDIL && p.isAsigned()) {
				i += 1;
			}
		}
		if (i == 2) {
			resultado = false;
		}
		return resultado;
	}
    
}