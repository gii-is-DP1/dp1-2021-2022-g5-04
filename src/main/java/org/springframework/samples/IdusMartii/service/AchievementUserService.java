package org.springframework.samples.IdusMartii.service;

import java.util.List;

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
	
	@Transactional
	public void saveAchievementUser(String username, Integer id) throws DataAccessException {
		achievementUserRepository.saveAchievementUser(username, Integer.valueOf(id));
	}
	
	@Transactional
	public boolean checkAchievementJugadas(User user, Integer valor) throws DataAccessException {
		return playerService.findbyUsername(user.getUsername()).size() == valor ;
		
	}
	
	
//	@Transactional
	//public boolean checkAchievement5Games(User user) throws DataAccessException {
	//	return playerService.findbyUsername(user.getUsername()).size() == 5;
//	}
	
	
	

}
