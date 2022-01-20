package org.springframework.samples.IdusMartii.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.IdusMartii.model.Achievement;
import org.springframework.samples.IdusMartii.model.AchievementType;
import org.springframework.samples.IdusMartii.model.User;
import org.springframework.samples.IdusMartii.repository.AchievementRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
		log.info("Buscando todos los logros...");
		return achievementRepository.findAll();
	}
	
	@Transactional
	public List<Achievement> findByAchievementType(String tipo){
		log.info("Buscando logros del tipo " + tipo);
		AchievementType tipoId = achievementRepository.findAchievementTypeByName(tipo);
		return achievementRepository.findByAchievementType(tipoId);
	}
	

	@Transactional
	public List<Achievement> findByUser(User user) throws DataAccessException {
		log.info("Llamada al metodo findByUser(User)");
		log.debug("atributo: " + user);
		return achievementRepository.findByUser(user);
	}
	

	@Transactional
	public void saveAchievement(Achievement ac) throws DataAccessException {
		log.info("Guardando Logro...");
		achievementRepository.save(ac);
	}
	
	@Transactional
	public List<AchievementType> getAllAchievementsTypes() throws DataAccessException{
		log.info("Obteniendo tipos de logro");
		return achievementRepository.findAllAchievementsTypes();
	}
	
	@Transactional
	public AchievementType getAchievementTypeByName(String name) throws DataAccessException{
		log.info("Buscando logros por tipo...");
		log.debug("Atributo: " + name);
		if (achievementRepository.findAchievementTypeByName(name) == null) {
			log.error("No se ha encontrado ningún logro con el tipo: " + name);
		}
		else {
			log.info("Logros encontrados.");
		}
		return achievementRepository.findAchievementTypeByName(name);
	}

	@Transactional
	public Achievement findById(Integer id) throws DataAccessException {
		return achievementRepository.findById(id).get();
	}
	
	@Transactional
	public Integer nextId() throws DataAccessException {
		List<Integer> temp = new ArrayList<>();
		achievementRepository.findAll().forEach(c->temp.add(c.getId()));
		return (temp.size());
	}
	
	@Transactional
	public void deleteAllAchievementsFromUser(User user) throws DataAccessException {
		if (achievementRepository.findByUser(user).isEmpty()) {
			
		} else {
			List<Achievement> achievementsFromUser = achievementRepository.findByUser(user);
			for (Achievement a: achievementsFromUser) {
				achievementRepository.delete(a);
			}
		}
	}

}
