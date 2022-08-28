package org.springframework.samples.idusmartii.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.persistence.Tuple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.idusmartii.model.Achievement;
import org.springframework.samples.idusmartii.model.AchievementType;
import org.springframework.samples.idusmartii.model.User;
import org.springframework.samples.idusmartii.repository.AchievementRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AchievementService {
	
	@Autowired
	private AchievementRepository achievementRepository;
	@Autowired
    private PlayerService playerService;
    @Autowired
    private UserService userService;
	
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
		log.info("Buscando logro por Id...");
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
		log.info("Intentando borrar los logros del usuario: " + user);
		log.info("User encontrado");
		List<Achievement> achievementsFromUser = user.getAchievements();
		for (int i=0; i<achievementsFromUser.size();i++) {
            achievementsFromUser.remove(user.getAchievements().get(i));
		}
	
	}
	@Transactional
    public void saveAchievementUser(String username, Integer id) throws DataAccessException {
        log.info("Guardando Logro...");
        achievementRepository.saveAchievementUser(username, Integer.valueOf(id));
    }
    
    @Transactional
    public boolean checkAchievementJugadas(User user, Integer valor) throws DataAccessException {
        log.info("Comparando partidas jugadas con el valor del logro...");
        return playerService.findbyUsername(user.getUsername()).size() >= valor ;
    }
    @Transactional
    public boolean checkAchievementGanadas(User user, Double valor) throws DataAccessException {
        log.info("Comparando partidas jugadas con el valor del logro...");
        return playerService.findUserWins(user, true) >= valor;
    }

    @Transactional
    public List<Double> listStatistics(User user) throws DataAccessException {
        List<Double> list =new ArrayList<Double>();
		Double partidasJugadas = (double) userService.matchesPlayedForUser(user);
        Double victorias = playerService.findUserWins(user, true);
        Double derrotas = partidasJugadas-victorias;
        Double PorWin;
        Double PorLos;
        if (partidasJugadas==0.0){
            PorWin=0.0;
            PorLos=0.0;
        }
        else{
            PorWin = victorias*100/partidasJugadas;
            PorLos = derrotas*100/partidasJugadas;
        }
		list.add(partidasJugadas);
		list.add(victorias);
        list.add(derrotas);
		list.add(PorWin);
        list.add(PorLos);
		return list;
    }
    @Transactional
    public Map<String, Long> rankingRatioWin() throws DataAccessException {
        Map<String, Long> map = new HashMap<>();
        
        for (Tuple i:achievementRepository.topMatchPlaying(true)){
           for(Tuple j:achievementRepository.topWins(true)){
            if(i.get(0).equals(j.get(0))){
                if((long)i.get(1)==0){
                    map.put(i.get(0).toString(),((long)0));
                }
                else{
                    map.put(i.get(0).toString(),((long)j.get(1)/(long)i.get(1))*100);
                }
            }

           }       

        }
        Map<String, Long> result = map.entrySet().stream()
                .sorted(Entry.comparingByValue())
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));	
        return result;
    }
    @Transactional
    public Map<String, Long> rankingRatioLoss() throws DataAccessException {
        Map<String, Long> map = new HashMap<>();
        for (Tuple i:achievementRepository.topMatchPlaying(true)){
           for(Tuple j:achievementRepository.topLoss(true)){
            if(i.get(0).equals(j.get(0))){
                if((long)i.get(1)==0){
                    map.put(i.get(0).toString(),((long)0));
                }
                else{
                    map.put(i.get(0).toString(),((long)j.get(1)/(long)i.get(1))*100);
                }
            }

           }       

        }
        Map<String, Long> result = map.entrySet().stream()
                .sorted(Entry.comparingByValue())
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));	
        return result;
    }
    

    @Transactional
    public List<String> ranking() throws DataAccessException {
        List<String> result = new ArrayList<>();
        log.info(""+achievementRepository.topMatchPlaying(true).size());
        
        result.add(achievementRepository.topMatchPlaying(true).get(0).get(0).toString());
        result.add(achievementRepository.topWins(true).get(0).get(0).toString());
        result.add(achievementRepository.topLoss(true).get(0).get(0).toString());
        result.add(rankingRatioWin().keySet().toArray()[0].toString());
        result.add(rankingRatioLoss().keySet().toArray()[0].toString());
        return result;
    }
    @Transactional
    public List<Long> rankingStatistics() throws DataAccessException {
        List<Long> result = new ArrayList<>();
        result.add((long)achievementRepository.topMatchPlaying(true).get(0).get(1));  
        result.add((long)achievementRepository.topWins(true).get(0).get(1));
        result.add((long)achievementRepository.topLoss(true).get(0).get(1));
        result.add((long)rankingRatioWin().get(rankingRatioWin().keySet().toArray()[0].toString()));
        result.add((long)rankingRatioLoss().get(rankingRatioLoss().keySet().toArray()[0].toString()));
        return result;	
    }        
}


