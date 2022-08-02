package org.springframework.samples.IdusMartii.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.persistence.Tuple;

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
        for (Tuple i:achievementUserRepository.topMatchPlaying(true)){
           for(Tuple j:achievementUserRepository.topWins(true)){
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
        for (Tuple i:achievementUserRepository.topMatchPlaying(true)){
           for(Tuple j:achievementUserRepository.topLoss(true)){
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
        result.add(achievementUserRepository.topMatchPlaying(true).get(0).get(0).toString());
        result.add(achievementUserRepository.topWins(true).get(0).get(0).toString());
        result.add(achievementUserRepository.topLoss(true).get(0).get(0).toString());
        result.add(rankingRatioWin().keySet().toArray()[0].toString());
        result.add(rankingRatioLoss().keySet().toArray()[0].toString());
        return result;
    }
    @Transactional
    public List<Long> rankingStatistics() throws DataAccessException {
        List<Long> result = new ArrayList<>();
        result.add((long)achievementUserRepository.topMatchPlaying(true).get(0).get(1));  
        result.add((long)achievementUserRepository.topWins(true).get(0).get(1));
        result.add((long)achievementUserRepository.topLoss(true).get(0).get(1));
        result.add((long)rankingRatioWin().get(rankingRatioWin().keySet().toArray()[0].toString()));
        result.add((long)rankingRatioLoss().get(rankingRatioLoss().keySet().toArray()[0].toString()));
        return result;	
    }        
}
