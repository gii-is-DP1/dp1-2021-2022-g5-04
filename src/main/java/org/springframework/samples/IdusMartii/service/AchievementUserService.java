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
	@Autowired
    private MatchService matchService;
	
    
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
        List<Double> list =new ArrayList();
		Double partidasJugadas = (double) userService.matchesPlayedForUser(user);
        Double victorias = (double)user.getVictorias();
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
    public List<String> ranking() throws DataAccessException {
        List<String> result = new ArrayList<>();
        
        Iterable<User> users =  userService.findAll();
        Double maxPJ=-1.0;
        Double maxPG=-1.0;
        Double maxPP=-1.0;
        Double maxPV=-1.0;
        Double maxPD=-1.0;
        String jmaxPJ="";
        String jmaxPG="";
        String jmaxPP="";
        String jmaxPV="";
        String jmaxPD="";     
        for(User u: users){
            List<Double> ranking = listStatistics(u);
            if(ranking.get(0)>maxPJ){
                maxPJ=ranking.get(0);
                jmaxPJ=u.getUsername();
            }
            if(ranking.get(1)>maxPG){
                maxPG=ranking.get(1);
                jmaxPG=u.getUsername();
            }
            if(ranking.get(2)>maxPP){
                
                maxPP=ranking.get(2);
                jmaxPP=u.getUsername();
            }
            if(ranking.get(3)>maxPV){
                
                maxPV=ranking.get(3);
                jmaxPV=u.getUsername();
            }
            if(ranking.get(4)>maxPD){
               
                maxPD=ranking.get(4);
                jmaxPD=u.getUsername();
            }
           


        }
        result.add(jmaxPJ);
        result.add(jmaxPG);
        result.add(jmaxPP);
        result.add(jmaxPV);
        result.add(jmaxPD);
        
        return result;
    }
    @Transactional
    public List<Double> rankingStatistics() throws DataAccessException {
        List<String> rankings = ranking();
        
        List<Double> result = new ArrayList<>();
        for(int i = 0; i<rankings.size();i++){
            String username = rankings.get(i); 
            
            List<Double> estadisticas = listStatistics(userService.findUser(username).get());
            result.add(estadisticas.get(i));
        }
        
        return result;	
    }        
}
