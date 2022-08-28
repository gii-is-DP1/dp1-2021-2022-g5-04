package org.springframework.samples.IdusMartii.service;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.IdusMartii.model.Achievement;
import org.springframework.samples.IdusMartii.model.AchievementType;
import org.springframework.samples.IdusMartii.model.User;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class AchievementServiceTest {

    @Autowired
    private AchievementService achievementService;
    @Autowired
    private UserService userService;


    @Test
    public void testAchievementCount(){
        int contar= achievementService.achievementCount();
        
        assertTrue(contar>0);;
    }

    @Test
    public void testFindAll(){
        Iterable<Achievement> logrosT= achievementService.findAll();
        List <String> logros = new ArrayList<>();
		logrosT.forEach(l -> logros.add(l.getName()));

        assertEquals(logros.get(0), "Primera Partida");
        assertEquals(logros.get(1), "5 Partidas");

    }
    @Test
    public void testFindById(){
        Achievement logroId=achievementService.findById(1);

        assertThat(logroId.getName()).isEqualTo("Primera Partida");
    }
    @Test
    public void testFindByAchievementType(){
        List<Achievement> list = achievementService.findByAchievementType("jugadas");
            assertEquals("Primera Partida", list.get(0).getName());        
    }
    @Test
    public void testSaveAchievement(){
        Iterable<Achievement> logrosT= achievementService.findAll();
        List <Achievement> logros = new ArrayList<>();
		logrosT.forEach(l -> logros.add(l));
        AchievementType type = achievementService.getAchievementTypeByName("jugadas");
        Achievement achievement = new Achievement();
        achievement.setAchievementType(type);
        achievement.setName("Diez partidas");  
        achievementService.saveAchievement(achievement);
        Iterable<Achievement> logrosT2= achievementService.findAll();
        List <Achievement> logros2 = new ArrayList<>();
		logrosT2.forEach(l -> logros2.add(l));
        assertEquals(logros2.size()-1, logros.size());
    }


    @Test
    public void getAllAchievementsTypes(){  
    	List<AchievementType> a = achievementService.getAllAchievementsTypes();
        assertTrue(a.size()>0);
    }
    @Test
    public void testGetAchievementTypeByName(){
        AchievementType type = achievementService.getAchievementTypeByName("jugadas");
        assertNotNull(type);        
    }
    @Test
    public void testDeleteAllAchievementFromUser(){       
        User user = userService.findUser("admin1").get();
        List <Achievement> logros = user.getAchievements();
        int size = logros.size();
        achievementService.deleteAllAchievementsFromUser(user);
        List <Achievement> logros2 = user.getAchievements();	
        assertEquals(logros2.size()+1, size);       
    }

    @Test
    public void testSaveAchievementUser(){
        User user = userService.findUser("friend1").get();
        Achievement a = achievementService.findById(2);
        achievementService.saveAchievementUser("friend1", 2); 
        assertTrue(user.getAchievements().contains(a));
    }
    @Test
    public void testCheckAchievementJugadas(){
        User user = userService.findUser("admin1").get();
        assertTrue(achievementService.checkAchievementJugadas(user, 1));
    }
    @Test
    public void testCheckAchievementGanadas(){
        User user = userService.findUser("admin1").get();
        assertTrue(achievementService.checkAchievementGanadas(user, 1.0));
    }
    @Test
    public void testListStatistics(){
        User user = userService.findUser("admin1").get();
        List<Double> list = achievementService.listStatistics(user);
        assertEquals(5, list.size());
    }
    @Test
    public void testRankingRatioWin(){       
        assertTrue(!achievementService.rankingRatioWin().isEmpty());

    }
    @Test
    public void testRankingRatioLoss(){       
        assertTrue(!achievementService.rankingRatioLoss().isEmpty());

    }
    @Test
    public void testRanking(){
        assertEquals(5, achievementService.ranking().size());
    }
    
}
