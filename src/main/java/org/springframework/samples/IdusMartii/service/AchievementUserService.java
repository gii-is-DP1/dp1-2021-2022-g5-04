package org.springframework.samples.IdusMartii.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.IdusMartii.model.User;
import org.springframework.samples.IdusMartii.repository.AchievementUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
		log.info("Guardando Logro...");
		achievementUserRepository.saveAchievementUser(username, Integer.valueOf(id));
	}
	
	@Transactional
	public boolean checkAchievementJugadas(User user, Integer valor) throws DataAccessException {
		log.info("Comparando partidas jugadas con el valor del logro...");
		return playerService.findbyUsername(user.getUsername()).size() == valor ;
	}
	

	@Transactional
	public Integer listStatistics(User user) throws DataAccessException {
		return playerService.findbyUsername(user.getUsername()).size() ;
	}
	
	
	
	@Transactional
	public Map<Integer,List< String>> rankingStatistics() throws DataAccessException {
		Map<Integer, List<String>> rest = new HashMap<>();
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
			}
		}
			rest.put(i, tempp);
		}
		return rest;
	}
		@Transactional
		public Map<Integer,List< String>> rankingWinners() throws DataAccessException {
			Map<Integer, List<String>> rest = new HashMap<>();
			List<User> users =  userService.findUsers();
			Set<Integer> indice = new HashSet<>();
			for(User user:users) {
				Integer temp = 0;
				if(user.getVictorias()!=null) {
					 temp =  user.getVictorias();
				}else {
				 temp =  0;
				}
				indice.add(temp);
			}
			for(Integer i : indice) {
				List<String> tempp = new ArrayList<>();
				for(User user :users) {
					Integer temp = 0;
					if(user.getVictorias()!=null) {
						 temp =  user.getVictorias();
					} else {
					 temp =  0;
					}
					if(i == temp) {
					tempp.add(user.getUsername());
					}
				}
				rest.put(i, tempp);
			}
		return rest;
	}
}
