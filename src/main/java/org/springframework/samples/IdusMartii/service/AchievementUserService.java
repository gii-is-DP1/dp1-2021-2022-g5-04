package org.springframework.samples.IdusMartii.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.IdusMartii.enumerates.Plays;
import org.springframework.samples.IdusMartii.enumerates.Role;
import org.springframework.samples.IdusMartii.model.Achievement;
import org.springframework.samples.IdusMartii.model.Player;
import org.springframework.samples.IdusMartii.model.User;
import org.springframework.samples.IdusMartii.repository.AchievementUserRepository;
import org.springframework.samples.IdusMartii.repository.PlayerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AchievementUserService {
	@Autowired
	private AchievementUserRepository achievementUserRepository;
	@Autowired
	private PlayerService playerService;
	@Autowired
	private UserService userService;
	
	@Transactional
	public void saveAchievementUser(String username, Integer id) throws DataAccessException {
		achievementUserRepository.saveAchievementUser(username, Integer.valueOf(id));
	}
	
	@Transactional
	public boolean checkAchievementJugadas(User user, Integer valor) throws DataAccessException {
		return playerService.findbyUsername(user.getUsername()).size() == valor ;
	}
	

	@Transactional
	public Integer listStatistics(User user) throws DataAccessException {
		return playerService.findbyUsername(user.getUsername()).size() ;
	}
	
	@Transactional
	public Integer listStatisticsWin(User user) throws DataAccessException {
		return playerService.findbyUsername(user.getUsername()).size() ;}
	
	
	@Transactional
	public Map<Integer,List< String>> rankingStatistics() throws DataAccessException {
		Map<Integer, List<String>> rest = new HashMap<>();
		Iterable<Player> players =  playerService.findAll();
		List<User> users =  userService.findUsers();
		Set<Integer> indice = new HashSet<>();

		for(User user:users) {
			Integer temp =  listStatistics( user);
			indice.add(temp);
		}

		for(Integer i : indice) {
			List<String> tempp = new ArrayList<>();

			for(User user :users) {
				Integer temppp =  listStatistics( user);
			if(i == temppp) {
				tempp.add(user.getUsername());
			}}

				
				
			
			rest.put(i, tempp);
			
		}
		
		return rest;
	}

	
	
	

}
