package org.springframework.samples.IdusMartii.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.IdusMartii.model.Achievement;
import org.springframework.samples.IdusMartii.model.User;
import org.springframework.samples.IdusMartii.repository.AchievementRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AchievementService {
	
	@Autowired
	private AchievementRepository achievementRepository;
	
	@Transactional
	public int achievementCount() {
		return (int) achievementRepository.count();
	}
	
	@Transactional
	public Iterable<Achievement> findAll(){
		return achievementRepository.findAll();
	}
	

	@Transactional
	public List<Achievement> findByUser(User user) throws DataAccessException {
		return achievementRepository.findByUser(user);
	}
	

	@Transactional
	public void saveAchievement(Achievement ac) throws DataAccessException {
		
		achievementRepository.save(ac);

	}
	

	@Transactional
	public Achievement findById(Integer id) throws DataAccessException {
		
		return achievementRepository.findById(id).get();

	}
	
	@Transactional
	public Integer nextId( ) throws DataAccessException {
		
		List<Integer> temp = new ArrayList<>();
				achievementRepository.findAll().forEach(c->temp.add(c.getId()));
				return (temp.size());
		 

	}
	
	

}
